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

<%@ include file="/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiEngineRenderer wikiEngineRenderer = (WikiEngineRenderer)request.getAttribute(WikiWebKeys.WIKI_ENGINE_RENDERER);
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

long nodeId = BeanParamUtil.getLong(wikiPage, request, "nodeId");
String title = BeanParamUtil.getString(wikiPage, request, "title");

boolean editTitle = ParamUtil.getBoolean(request, "editTitle");

String selectedFormat = BeanParamUtil.getString(wikiPage, request, "format", wikiGroupServiceOverriddenConfiguration.defaultFormat());
String parentTitle = BeanParamUtil.getString(wikiPage, request, "parentTitle");

boolean editable = false;

if (wikiPage != null) {
	if (WikiPagePermissionChecker.contains(permissionChecker, wikiPage, ActionKeys.UPDATE)) {
		editable = true;
	}
}
else if ((wikiPage == null) && editTitle) {
	editable = true;

	wikiPage = new WikiPageImpl();

	wikiPage.setNew(true);
	wikiPage.setNodeId(node.getNodeId());
	wikiPage.setFormat(selectedFormat);
	wikiPage.setParentTitle(parentTitle);
}

if (Validator.isNotNull(title)) {
	try {
		WikiPageTitleValidator wikiPageTitleValidator = (WikiPageTitleValidator)request.getAttribute(WikiWebKeys.WIKI_PAGE_TITLE_VALIDATOR);

		wikiPageTitleValidator.validate(title);

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
				selectedFormat = templatePage.getFormat();

				wikiPage.setContent(templatePage.getContent());
				wikiPage.setFormat(selectedFormat);
				wikiPage.setParentTitle(parentTitle);
			}
		}
	}
	catch (Exception e) {
	}
}

WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);

PortletURL backToViewPagesURL = wikiURLHelper.getBackToViewPagesURL(node);

if (Validator.isNull(redirect)) {
	redirect = backToViewPagesURL.toString();
}

String headerTitle = ((wikiPage == null) || wikiPage.isNew()) ? LanguageUtil.get(request, "new-wiki-page") : wikiPage.getTitle();

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backToViewPagesURL.toString());

	renderResponse.setTitle(headerTitle);
}
%>

<c:if test="<%= portletTitleBasedNavigation && (wikiPage != null) && !wikiPage.isNew() %>">
	<liferay-frontend:info-bar>
		<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= wikiPage.getStatus() %>" version="<%= String.valueOf(wikiPage.getVersion()) %>" />
	</liferay-frontend:info-bar>
</c:if>

<portlet:actionURL name="/wiki/edit_page" var="editPageActionURL">
	<portlet:param name="mvcRenderCommandName" value="/wiki/edit_page" />
</portlet:actionURL>

<portlet:renderURL var="editPageRenderURL">
	<portlet:param name="mvcRenderCommandName" value="/wiki/edit_page" />
</portlet:renderURL>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<aui:form action="<%= editPageActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "savePage();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="editTitle" type="hidden" value="<%= editTitle %>" />
		<aui:input name="nodeId" type="hidden" value="<%= nodeId %>" />
		<aui:input name="title" type="hidden" value="<%= title %>" />
		<aui:input name="parentTitle" type="hidden" value="<%= parentTitle %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

		<c:if test="<%= wikiPage != null %>">
			<aui:input name="version" type="hidden" value="<%= wikiPage.getVersion() %>" />
		</c:if>

		<c:if test="<%= templatePage != null %>">
			<aui:input name="templateNodeId" type="hidden" value="<%= String.valueOf(templateNodeId) %>" />
			<aui:input name="templateTitle" type="hidden" value="<%= templateTitle %>" />
		</c:if>

		<liferay-ui:error exception="<%= DuplicatePageException.class %>" message="there-is-already-a-page-with-the-specified-title" />
		<liferay-ui:error exception="<%= PageContentException.class %>" message="the-content-is-not-valid" />
		<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-title" />
		<liferay-ui:error exception="<%= PageVersionException.class %>" message="another-user-has-made-changes-since-you-started-editing-please-copy-your-changes-and-try-again" />

		<liferay-ui:asset-categories-error />

		<liferay-ui:asset-tags-error />

		<aui:model-context bean="<%= ((wikiPage != null) && !wikiPage.isNew()) ? wikiPage : templatePage %>" model="<%= WikiPage.class %>" />

		<c:choose>
			<c:when test="<%= !editable %>">
				<c:if test="<%= (wikiPage == null) || wikiPage.isNew() %>">
					<div class="alert alert-info">
						<liferay-ui:message key="this-page-does-not-exist-yet-and-the-title-is-not-valid" />
					</div>

					<aui:button href="<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>" value="cancel" />
				</c:if>

				<c:if test="<%= (wikiPage != null) && !wikiPage.isApproved() %>">
					<div class="alert alert-info">

						<%
						Format dateFormatDate = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
						%>

						<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(wikiPage.getUserName()), dateFormatDate.format(wikiPage.getModifiedDate())} %>" key="this-page-cannot-be-edited-because-user-x-is-modifying-it-and-the-results-have-not-been-published-yet" translateArguments="<%= false %>" />
					</div>
				</c:if>
			</c:when>
			<c:otherwise>
				<aui:fieldset-group markupView="lexicon">
					<aui:fieldset>
						<c:if test="<%= !portletTitleBasedNavigation && (wikiPage != null) && !wikiPage.isNew() %>">
							<div class="text-center">
								<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= wikiPage.getStatus() %>" version="<%= String.valueOf(wikiPage.getVersion()) %>" />
							</div>
						</c:if>

						<c:if test="<%= ((wikiPage == null) || wikiPage.isNew()) && Validator.isNotNull(title) %>">
							<div class="alert alert-info">
								<liferay-ui:message key="this-page-does-not-exist-yet-use-the-form-below-to-create-it" />
							</div>
						</c:if>

						<c:choose>
							<c:when test="<%= editTitle %>">
								<aui:field-wrapper required="<%= true %>">
									<div class="entry-title">
										<h1><liferay-ui:input-editor contents="<%= title %>" editorName="alloyeditor" name="titleEditor" placeholder="title" showSource="<%= false %>" /></h1>
									</div>
								</aui:field-wrapper>
							</c:when>
							<c:otherwise>
								<div class="entry-title">
									<h1><%= HtmlUtil.escape(title) %></h1>
								</div>
							</c:otherwise>
						</c:choose>

						<div>

							<%
							wikiEngineRenderer.renderEditPageHTML(selectedFormat, pageContext, node, wikiPage);
							%>

						</div>
					</aui:fieldset>

					<c:if test="<%= (wikiPage != null) && (wikiPage.getPageId() > 0) %>">
						<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="attachments">
							<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, node.getNodeId(), ActionKeys.ADD_ATTACHMENT) %>">
								<liferay-util:include page="/wiki/edit_page_attachment.jsp" servletContext="<%= application %>" />
							</c:if>

							<liferay-util:include page="/wiki/view_attachments.jsp" servletContext="<%= application %>" />
						</aui:fieldset>
					</c:if>

					<%
					long resourcePrimKey = 0;

					if ((wikiPage != null) && !wikiPage.isNew()) {
						resourcePrimKey = wikiPage.getResourcePrimKey();
					}
					else if (templatePage != null) {
						resourcePrimKey = templatePage.getResourcePrimKey();
					}

					long assetEntryId = 0;
					long classPK = resourcePrimKey;

					if ((wikiPage != null) && !wikiPage.isNew() && !wikiPage.isApproved() && (wikiPage.getVersion() != WikiPageConstants.VERSION_DEFAULT)) {
						AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(WikiPage.class.getName(), wikiPage.getPrimaryKey());

						if (assetEntry != null) {
							assetEntryId = assetEntry.getEntryId();
							classPK = wikiPage.getPrimaryKey();
						}
					}
					%>

					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="categorization">
						<aui:input classPK="<%= classPK %>" name="categories" type="assetCategories" />

						<aui:input classPK="<%= classPK %>" name="tags" type="assetTags" />
					</aui:fieldset>

					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="related-assets">
						<liferay-ui:input-asset-links
							assetEntryId="<%= assetEntryId %>"
							className="<%= WikiPage.class.getName() %>"
							classPK="<%= classPK %>"
						/>
					</aui:fieldset>

					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="configuration">
						<c:if test="<%= (wikiPage == null) || wikiPage.isNew() || wikiPage.isApproved() %>">
							<aui:model-context bean="<%= new WikiPageImpl() %>" model="<%= WikiPage.class %>" />
						</c:if>

						<aui:input label="Summary" name="summary" />

						<%
						Collection<String> formats = wikiEngineRenderer.getFormats();
						%>

						<c:choose>
							<c:when test="<%= !formats.isEmpty() %>">
								<aui:select changesContext="<%= true %>" name="format">

									<%
									for (String format : formats) {
									%>

										<aui:option label="<%= wikiEngineRenderer.getFormatLabel(format, locale) %>" selected="<%= selectedFormat.equals(format) %>" value="<%= format %>" />

									<%
									}
									%>

								</aui:select>
							</c:when>
							<c:otherwise>
								<aui:input name="format" type="hidden" value="<%= selectedFormat %>" />
							</c:otherwise>
						</c:choose>

						<c:if test="<%= (wikiPage != null) && !wikiPage.isNew() %>">
							<aui:input label="this-is-a-minor-edit" name="minorEdit" />
						</c:if>
					</aui:fieldset>

					<c:if test="<%= wikiPage != null %>">
						<liferay-ui:custom-attributes-available className="<%= WikiPage.class.getName() %>">
							<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="custom-fields">
								<liferay-ui:custom-attribute-list
									className="<%= WikiPage.class.getName() %>"
									classPK="<%= (templatePage != null) ? templatePage.getPrimaryKey() : wikiPage.getPrimaryKey() %>"
									editable="<%= true %>"
									label="<%= true %>"
								/>
							</aui:fieldset>
						</liferay-ui:custom-attributes-available>
					</c:if>

					<c:if test="<%= (wikiPage == null) || wikiPage.isNew() %>">
						<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
							<liferay-ui:input-permissions
								modelName="<%= WikiPage.class.getName() %>"
							/>
						</aui:fieldset>
					</c:if>
				</aui:fieldset-group>

				<%
				boolean pending = false;

				if (wikiPage != null) {
					pending = wikiPage.isPending();
				}
				%>

				<c:if test="<%= pending %>">
					<div class="alert alert-info">
						<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
					</div>
				</c:if>

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

				<aui:button-row>
					<aui:button cssClass="btn-lg" disabled="<%= pending %>" name="publishButton" onClick='<%= renderResponse.getNamespace() + "publishPage();" %>' primary="<%= true %>" value="<%= publishButtonLabel %>" />

					<aui:button cssClass="btn-lg" name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

					<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</c:otherwise>
		</c:choose>
	</aui:form>
</div>

<aui:script sandbox="<%= true %>">
	var form = $(document.<portlet:namespace />fm);

	var formatSelect = form.fm('format');

	var currentFormat = formatSelect.find('option:selected').text().trim();

	var currentIndex = formatSelect.prop('selectedIndex');

	formatSelect.on(
		'change',
		function(event) {
			var newFormat = formatSelect.find('option:selected').text().trim();

			var confirmMessage = '<%= UnicodeLanguageUtil.get(request, "you-may-lose-formatting-when-switching-from-x-to-x") %>';

			confirmMessage = _.sub(confirmMessage, currentFormat, newFormat);

			if (!confirm(confirmMessage)) {
				formatSelect.prop('selectedIndex', currentIndex);

				return;
			}

			var titleEditor = window.<portlet:namespace />titleEditor;

			if (titleEditor) {
				form.fm('title').val(titleEditor.getText());
			}

			var editor = window.<portlet:namespace />editor;

			if (editor) {
				form.fm('content').val(editor.getHTML());
			}

			form.attr('action', '<%= editPageRenderURL %>');

			submitForm(form, null, null, false);
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />publishPage() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('workflowAction').val('<%= WorkflowConstants.ACTION_PUBLISH %>');

		<portlet:namespace />savePage();
	}

	function <portlet:namespace />savePage() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= Constants.CMD %>').val('<%= ((wikiPage == null) || wikiPage.isNew()) ? Constants.ADD : Constants.UPDATE %>');

		var titleEditor = window.<portlet:namespace />titleEditor;

		if (titleEditor) {
			form.fm('title').val(titleEditor.getText());
		}

		var editor = window.<portlet:namespace />editor;

		if (editor) {
			form.fm('content').val(editor.getHTML());
		}

		submitForm(form);
	}
</aui:script>

<%
if ((wikiPage != null) && !wikiPage.isNew()) {
	PortletURL viewPageURL = wikiURLHelper.getViewPageURL(node, title);

	PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), viewPageURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-page"), currentURL);
}
%>