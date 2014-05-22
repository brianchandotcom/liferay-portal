<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

boolean followRedirect = false;

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

WikiPage redirectPage = null;

long nodeId = BeanParamUtil.getLong(wikiPage, request, "nodeId");
String title = BeanParamUtil.getString(wikiPage, request, "title");

boolean editTitle = ParamUtil.getBoolean(request, "editTitle");

String content = BeanParamUtil.getString(wikiPage, request, "content");
String format = BeanParamUtil.getString(wikiPage, request, "format", WikiPageConstants.DEFAULT_FORMAT);
String parentTitle = BeanParamUtil.getString(wikiPage, request, "parentTitle");

boolean preview = ParamUtil.getBoolean(request, "preview");

boolean newPage = ParamUtil.getBoolean(request, "newPage");

if (wikiPage == null) {
	newPage = true;
}

boolean editable = false;

boolean copyPageAttachments = ParamUtil.getBoolean(request, "copyPageAttachments", true);

List<FileEntry> attachmentsFileEntries = null;

if (wikiPage != null) {
	attachmentsFileEntries = wikiPage.getAttachmentsFileEntries();

	if (WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE)) {
		editable = true;
	}
}
else if ((wikiPage == null) && editTitle) {
	editable = true;

	wikiPage = new WikiPageImpl();

	wikiPage.setNew(true);
	wikiPage.setNodeId(node.getNodeId());
	wikiPage.setFormat(format);
	wikiPage.setParentTitle(parentTitle);
}

if (Validator.isNotNull(title)) {
	try {
		WikiPageLocalServiceUtil.validateTitle(title);

		editable = true;
	}
	catch (PageTitleException pte) {
		editTitle = true;
	}
}

long templateNodeId = ParamUtil.getLong(request, "templateNodeId");
String templateTitle = ParamUtil.getString(request, "templateTitle");

WikiPage templatePage = null;

if ((templateNodeId > 0) && Validator.isNotNull(templateTitle)) {
	try {
		templatePage = WikiPageServiceUtil.getPage(templateNodeId, templateTitle);

		if (Validator.isNull(parentTitle)) {
			parentTitle = templatePage.getParentTitle();

			if (wikiPage.isNew()) {
				format = templatePage.getFormat();

				wikiPage.setContent(templatePage.getContent());
				wikiPage.setFormat(format);
				wikiPage.setParentTitle(parentTitle);
			}
		}
	}
	catch (Exception e) {
	}
}

PortletURL viewPageURL = renderResponse.createRenderURL();

viewPageURL.setParameter("struts_action", "/wiki/view");
viewPageURL.setParameter("nodeName", node.getName());
viewPageURL.setParameter("title", title);

PortletURL editPageURL = renderResponse.createRenderURL();

editPageURL.setParameter("struts_action", "/wiki/edit_page");
editPageURL.setParameter("redirect", currentURL);
editPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
editPageURL.setParameter("title", title);

if (Validator.isNull(redirect)) {
	redirect = viewPageURL.toString();
}
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<c:choose>
	<c:when test="<%= !newPage %>">
		<liferay-util:include page="/html/portlet/wiki/page_tabs.jsp">
			<liferay-util:param name="tabs1" value="content" />
		</liferay-util:include>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portlet/wiki/page_name.jspf" %>
	</c:otherwise>
</c:choose>

<c:if test="<%= preview %>">

	<%
	if (wikiPage == null) {
		wikiPage = new WikiPageImpl();
	}

	try {
		content = SanitizerUtil.sanitize(themeDisplay.getCompanyId(), scopeGroupId, themeDisplay.getUserId(), WikiPage.class.getName(), 0, "text/" + format, content);
	}
	catch (SanitizerException se) {
		content = StringPool.BLANK;
	}

	wikiPage.setContent(content);
	wikiPage.setFormat(format);
	%>

	<liferay-ui:message key="preview" />:

	<div class="preview">
		<%@ include file="/html/portlet/wiki/view_page_content.jspf" %>
	</div>

	<br />
</c:if>

<portlet:actionURL var="editPageActionURL">
	<portlet:param name="struts_action" value="/wiki/edit_page" />
</portlet:actionURL>

<aui:form action="<%= editPageActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "savePage();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= nodeId %>" />
	<aui:input name="newPage" type="hidden" value="<%= newPage %>" />

	<aui:model-context bean="<%= !newPage ? wikiPage : templatePage %>" model="<%= WikiPage.class %>" />

	<c:if test="<%= (wikiPage != null) && (!wikiPage.isNew()) %>">
		<aui:workflow-status showIcon="<%= false %>" showLabel="<%= false %>" status="<%= wikiPage.getStatus() %>" version="<%= String.valueOf(wikiPage.getVersion()) %>" />
	</c:if>

	<c:if test="<%= !editTitle %>">
		<aui:input name="title" type="hidden" value="<%= title %>" />
	</c:if>

	<aui:input name="parentTitle" type="hidden" value="<%= parentTitle %>" />
	<aui:input name="editTitle" type="hidden" value="<%= editTitle %>" />

	<c:if test="<%= wikiPage != null %>">
		<aui:input name="version" type="hidden" value="<%= wikiPage.getVersion() %>" />
	</c:if>

	<c:if test="<%= templatePage != null %>">
		<aui:input name="templateNodeId" type="hidden" value="<%= String.valueOf(templateNodeId) %>" />
		<aui:input name="templateTitle" type="hidden" value="<%= templateTitle %>" />
	</c:if>

	<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />
	<aui:input name="preview" type="hidden" value="<%= preview %>" />

	<liferay-ui:error exception="<%= DuplicatePageException.class %>" message="there-is-already-a-page-with-the-specified-title" />
	<liferay-ui:error exception="<%= PageContentException.class %>" message="the-content-is-not-valid" />
	<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-title" />
	<liferay-ui:error exception="<%= PageVersionException.class %>" message="another-user-has-made-changes-since-you-started-editing-please-copy-your-changes-and-try-again" />

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<c:if test="<%= newPage %>">
		<c:choose>
			<c:when test="<%= editable %>">
				<c:if test="<%= Validator.isNull(title) %>">
					<liferay-ui:header
						backURL="<%= redirect %>"
						title="new-wiki-page"
					/>
				</c:if>

				<div class="alert alert-info">
					<liferay-ui:message key="this-page-does-not-exist-yet-use-the-form-below-to-create-it" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-error">
					<liferay-ui:message key="this-page-does-not-exist-yet-and-the-title-is-not-valid" />
				</div>

				<input onClick="document.location = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>'" type="button" value="<liferay-ui:message key="cancel" />" />
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:choose>
		<c:when test="<%= editable %>">
			<aui:fieldset>
				<c:if test="<%= editTitle %>">
					<aui:input autoFocus="<%= !preview %>" name="title" size="30" value="<%= title %>" />
				</c:if>

				<c:if test="<%= Validator.isNotNull(parentTitle) %>">
					<aui:input name="parent" type="resource" value="<%= parentTitle %>" />
				</c:if>

				<c:choose>
					<c:when test="<%= (WikiPageConstants.FORMATS.length > 1) %>">
						<aui:select changesContext="<%= true %>" name="format" onChange='<%= renderResponse.getNamespace() + "changeFormat(this);" %>'>

							<%
							for (int i = 0; i < WikiPageConstants.FORMATS.length; i++) {
							%>

								<aui:option label='<%= LanguageUtil.get(pageContext, "wiki.formats." + WikiPageConstants.FORMATS[i]) %>' selected="<%= format.equals(WikiPageConstants.FORMATS[i]) %>" value="<%= WikiPageConstants.FORMATS[i] %>" />

							<%
							}
							%>

						</aui:select>

					</c:when>
					<c:otherwise>
						<aui:input name="format" type="hidden" value="<%= format %>" />
					</c:otherwise>
				</c:choose>
			</aui:fieldset>

			<div>

				<%
				request.setAttribute("edit_page.jsp-wikiPage", wikiPage);
				%>

				<liferay-util:include page="<%= WikiUtil.getEditPage(format) %>" />
			</div>

			<c:if test="<%= wikiPage != null %>">
				<liferay-ui:custom-attributes-available className="<%= WikiPage.class.getName() %>">
					<aui:fieldset>

						<%
						long classPK = 0;

						if (templatePage != null) {
							classPK = templatePage.getPrimaryKey();
						}
						else if (page != null) {
							classPK = wikiPage.getPrimaryKey();
						}
						%>

						<liferay-ui:custom-attribute-list
							className="<%= WikiPage.class.getName() %>"
							classPK="<%= classPK %>"
							editable="<%= true %>"
							label="<%= true %>"
						/>
					</aui:fieldset>
				</liferay-ui:custom-attributes-available>
			</c:if>

			<aui:fieldset>
				<c:if test="<%= (attachmentsFileEntries != null) && !attachmentsFileEntries.isEmpty() || ((templatePage != null) && (templatePage.getAttachmentsFileEntriesCount() > 0)) %>">
					<aui:field-wrapper label="attachments">
						<c:if test="<%= (templatePage != null) && (templatePage.getAttachmentsFileEntriesCount() > 0) %>">

							<%
							attachmentsFileEntries = templatePage.getAttachmentsFileEntries();
							%>

							<aui:input name="copyPageAttachments" type="checkbox" value="<%= copyPageAttachments %>" />
						</c:if>

						<c:if test="<%= attachmentsFileEntries != null %>">

							<%
							for (int i = 0; i < attachmentsFileEntries.size(); i++) {
								FileEntry attachmentsFileEntry = attachmentsFileEntries.get(i);
							%>

								<aui:a href="<%= (templatePage != null) && (templatePage.getAttachmentsFileEntriesCount() > 0) ? PortletFileRepositoryUtil.getPortletFileEntryURL(themeDisplay, attachmentsFileEntry, StringPool.BLANK) : null %>"><%= attachmentsFileEntry.getTitle() %></aui:a> (<%= TextFormatter.formatStorageSize(attachmentsFileEntry.getSize(), locale) %>)<%= (i < (attachmentsFileEntries.size() - 1)) ? ", " : "" %>

							<%
							}
							%>

						</c:if>
					</aui:field-wrapper>
				</c:if>

				<%
				long resourcePrimKey = 0;

				if (!newPage) {
					resourcePrimKey = wikiPage.getResourcePrimKey();
				}
				else if (templatePage != null) {
					resourcePrimKey = templatePage.getResourcePrimKey();
				}

				long assetEntryId = 0;
				long classPK = resourcePrimKey;

				if (!newPage && !wikiPage.isApproved() && (wikiPage.getVersion() != WikiPageConstants.VERSION_DEFAULT)) {
					AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(WikiPage.class.getName(), wikiPage.getPrimaryKey());

					if (assetEntry != null) {
						assetEntryId = assetEntry.getEntryId();
						classPK = wikiPage.getPrimaryKey();
					}
				}
				%>

				<c:if test="<%= newPage || wikiPage.isApproved() %>">
					<aui:model-context bean="<%= new WikiPageImpl() %>" model="<%= WikiPage.class %>" />
				</c:if>

				<aui:input label="description-of-the-changes" name="summary" />

				<c:if test="<%= !newPage %>">
					<aui:input label="this-is-a-minor-edit" name="minorEdit" />
				</c:if>

				<c:if test="<%= newPage %>">
					<aui:field-wrapper label="permissions">
						<liferay-ui:input-permissions
							modelName="<%= WikiPage.class.getName() %>"
						/>
					</aui:field-wrapper>
				</c:if>

				<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="wikiPageCategorizationPanel" persistState="<%= true %>" title="categorization">
					<aui:fieldset>
						<aui:input classPK="<%= classPK %>" name="categories" type="assetCategories" />

						<aui:input classPK="<%= classPK %>" name="tags" type="assetTags" />
					</aui:fieldset>
				</liferay-ui:panel>

				<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="wikiPageAssetLinksPanel" persistState="<%= true %>" title="related-assets">
					<aui:fieldset>
						<liferay-ui:input-asset-links
							assetEntryId="<%= assetEntryId %>"
							className="<%= WikiPage.class.getName() %>"
							classPK="<%= classPK %>"
						/>
					</aui:fieldset>
				</liferay-ui:panel>

				<%
				boolean approved = false;
				boolean pending = false;

				if (wikiPage != null) {
					approved = wikiPage.isApproved();
					pending = wikiPage.isPending();
				}
				%>

				<c:if test="<%= !newPage && approved %>">
					<div class="alert alert-info">
						<liferay-ui:message key="a-new-version-will-be-created-automatically-if-this-content-is-modified" />
					</div>
				</c:if>

				<c:if test="<%= pending %>">
					<div class="alert alert-info">
						<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
					</div>
				</c:if>

				<aui:button-row>

					<%
					String saveButtonLabel = "save";

					if ((wikiPage == null) || wikiPage.isDraft() || wikiPage.isApproved()) {
						saveButtonLabel = "save-as-draft";
					}

					String publishButtonLabel = "publish";

					if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, WikiPage.class.getName())) {
						publishButtonLabel = "submit-for-publication";
					}
					%>

					<aui:button name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

					<aui:button name="previewButton" onClick='<%= renderResponse.getNamespace() + "previewPage();" %>' value="preview" />

					<aui:button disabled="<%= pending %>" name="publishButton" onClick='<%= renderResponse.getNamespace() + "publishPage();" %>' primary="<%= true %>" value="<%= publishButtonLabel %>" />

					<c:if test="<%= !newPage && WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.DELETE) %>">
						<c:choose>
							<c:when test="<%= !wikiPage.isDraft() && TrashUtil.isTrashEnabled(scopeGroupId) %>">
								<aui:button name="moveToTrashButton" onClick='<%= renderResponse.getNamespace() + "moveToTrashPage();" %>' value="move-to-the-recycle-bin" />
							</c:when>
							<c:when test="<%= wikiPage.isDraft() %>">
								<aui:button name="discardDraftButton" onClick='<%= renderResponse.getNamespace() + "discardDraftPage();" %>' value="discard-draft" />
							</c:when>
						</c:choose>
					</c:if>

					<aui:button href="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</c:when>
		<c:otherwise>
			<c:if test="<%= (wikiPage != null) && !wikiPage.isApproved() %>">
				<div class="alert alert-info">

					<%
					Format dateFormatDate = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
					%>

					<%= LanguageUtil.format(pageContext, "this-page-cannot-be-edited-because-user-x-is-modifying-it-and-the-results-have-not-been-published-yet", new Object[] {HtmlUtil.escape(wikiPage.getUserName()), dateFormatDate.format(wikiPage.getModifiedDate())}, false) %>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />changeFormat(formatSelect) {
		var currentFormat = formatSelect.options[window.<portlet:namespace />currentFormatIndex].text;

		var newFormat = formatSelect.options[formatSelect.selectedIndex].text;

		var confirmMessage = '<%= UnicodeLanguageUtil.get(pageContext, "you-may-lose-formatting-when-switching-from-x-to-x") %>';

		confirmMessage = AUI().Lang.sub(confirmMessage, [currentFormat, newFormat]);

		if (!confirm(confirmMessage)) {
			formatSelect.selectedIndex = window.<portlet:namespace />currentFormatIndex;

			return;
		}

		if (window.<portlet:namespace />editor) {
			document.<portlet:namespace />fm.<portlet:namespace />content.value = window.<portlet:namespace />editor.getHTML();
		}

		submitForm(document.<portlet:namespace />fm, null, null, false);
	}

	function <portlet:namespace />discardDraftPage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>';

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />getSuggestionsContent() {
		return document.<portlet:namespace />fm.<portlet:namespace />title.value + ' ' + window.<portlet:namespace />editor.getHTML();
	}

	function <portlet:namespace />moveToTrashPage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.MOVE_TO_TRASH %>';

		<portlet:renderURL var="nodeURL">
			<portlet:param name="struts_action" value="/wiki/view" />
			<portlet:param name="title" value="<%= WikiPageConstants.FRONT_PAGE %>" />
			<portlet:param name="tag" value="<%= StringPool.BLANK %>" />
		</portlet:renderURL>

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= nodeURL.toString() %>';

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />previewPage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '';
		document.<portlet:namespace />fm.<portlet:namespace />preview.value = 'true';

		if (window.<portlet:namespace />editor) {
			document.<portlet:namespace />fm.<portlet:namespace />content.value = window.<portlet:namespace />editor.getHTML();
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />publishPage() {
		document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = '<%= WorkflowConstants.ACTION_PUBLISH %>';

		<portlet:namespace />savePage();
	}

	function <portlet:namespace />savePage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= newPage ? Constants.ADD : Constants.UPDATE %>';

		if (window.<portlet:namespace />editor) {
			document.<portlet:namespace />fm.<portlet:namespace />content.value = window.<portlet:namespace />editor.getHTML();
		}

		submitForm(document.<portlet:namespace />fm);
	}

	window.<portlet:namespace />currentFormatIndex = document.<portlet:namespace />fm.<portlet:namespace />format.selectedIndex;
</aui:script>

<%
if (!newPage) {
	PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), viewPageURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-page"), currentURL);
}
%>