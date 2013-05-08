<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

JournalFeed feed = (JournalFeed)request.getAttribute(WebKeys.JOURNAL_FEED);

long groupId = BeanParamUtil.getLong(feed, request, "groupId", scopeGroupId);

String feedId = BeanParamUtil.getString(feed, request, "feedId");
String newFeedId = ParamUtil.getString(request, "newFeedId");

String structureId = BeanParamUtil.getString(feed, request, "structureId");

DDMStructure ddmStructure = null;

String ddmStructureName = StringPool.BLANK;

if (Validator.isNotNull(structureId)) {
	try {
		ddmStructure = DDMStructureLocalServiceUtil.getStructure(groupId, PortalUtil.getClassNameId(JournalArticle.class), structureId, true);

		ddmStructureName = ddmStructure.getName(locale);
	}
	catch (NoSuchStructureException nsse) {
	}
}

List<DDMTemplate> ddmTemplates = new ArrayList<DDMTemplate>();

if (ddmStructure != null) {
	ddmTemplates.addAll(DDMTemplateLocalServiceUtil.getTemplates(themeDisplay.getCompanyGroupId(), PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId()));
	ddmTemplates.addAll(DDMTemplateLocalServiceUtil.getTemplates(groupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId()));
}

String templateId = BeanParamUtil.getString(feed, request, "templateId");

if ((ddmStructure == null) && Validator.isNotNull(templateId)) {
	DDMTemplate ddmTemplate = null;

	try {
		ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(groupId, PortalUtil.getClassNameId(DDMStructure.class), templateId, true);
	}
	catch (NoSuchTemplateException nste) {
	}

	if (ddmTemplate != null) {
		try {
			ddmStructure = DDMStructureLocalServiceUtil.getStructure(ddmTemplate.getClassPK());

			structureId = ddmStructure.getStructureKey();
			ddmStructureName = ddmStructure.getName(locale);

			ddmTemplates = DDMTemplateLocalServiceUtil.getTemplates(groupId, PortalUtil.getClassNameId(DDMStructure.class), ddmTemplate.getClassPK());
		}
		catch (NoSuchStructureException nsse) {
		}
	}
}

String rendererTemplateId = BeanParamUtil.getString(feed, request, "rendererTemplateId");

String contentField = BeanParamUtil.getString(feed, request, "contentField");

if (Validator.isNull(contentField) || ((ddmStructure == null) && !contentField.equals(JournalFeedConstants.WEB_CONTENT_DESCRIPTION) && !contentField.equals(JournalFeedConstants.RENDERED_WEB_CONTENT))) {
	contentField = JournalFeedConstants.WEB_CONTENT_DESCRIPTION;
}

String feedFormat = BeanParamUtil.getString(feed, request, "feedFormat", RSSUtil.FORMAT_DEFAULT);
double feedVersion = BeanParamUtil.getDouble(feed, request, "feedVersion", RSSUtil.VERSION_DEFAULT);

String feedType = RSSUtil.getFeedType(feedFormat, feedVersion);

ResourceURL feedURL = null;

if (feed != null) {
	long targetLayoutPlid = PortalUtil.getPlidFromFriendlyURL(feed.getCompanyId(), feed.getTargetLayoutFriendlyUrl());

	feedURL = new PortletURLImpl(request, PortletKeys.JOURNAL, targetLayoutPlid, PortletRequest.RESOURCE_PHASE);

	feedURL.setCacheability(ResourceURL.FULL);

	feedURL.setParameter("struts_action", "/journal/rss");
	feedURL.setParameter("groupId", String.valueOf(groupId));
	feedURL.setParameter("feedId", String.valueOf(feedId));
}
%>

<portlet:actionURL var="editFeedURL">
	<portlet:param name="struts_action" value="/journal/edit_feed" />
</portlet:actionURL>

<aui:form action="<%= editFeedURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFeed();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="feedId" type="hidden" value="<%= feedId %>" />
	<aui:input name="rendererTemplateId" type="hidden" value="<%= rendererTemplateId %>" />
	<aui:input name="contentField" type="hidden" value="<%= contentField %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (feed == null) %>"
		title='<%= (feed == null) ? "new-feed" : feed.getName() %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFeedIdException.class %>" message="please-enter-a-unique-id" />
	<liferay-ui:error exception="<%= FeedContentFieldException.class %>" message="please-select-a-valid-feed-item-content" />
	<liferay-ui:error exception="<%= FeedIdException.class %>" message="please-enter-a-valid-id" />
	<liferay-ui:error exception="<%= FeedNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= FeedTargetLayoutFriendlyUrlException.class %>" message="please-enter-a-valid-target-layout-friendly-url" />
	<liferay-ui:error exception="<%= FeedTargetPortletIdException.class %>" message="please-enter-a-valid-portlet-id" />

	<aui:model-context bean="<%= feed %>" model="<%= JournalFeed.class %>" />

	<aui:fieldset>
		<c:choose>
			<c:when test="<%= feed == null %>">
				<c:choose>
					<c:when test="<%= PropsValues.JOURNAL_FEED_FORCE_AUTOGENERATE_ID %>">
						<aui:input name="newFeedId" type="hidden" />
						<aui:input name="autoFeedId" type="hidden" value="<%= true %>" />
					</c:when>
					<c:otherwise>
						<aui:input cssClass="lfr-input-text-container" field="feedId" fieldParam="newFeedId" label="id" name="newFeedId" value="<%= newFeedId %>" />

						<aui:input label="autogenerate-id" name="autoFeedId" type="checkbox" />
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<aui:field-wrapper label="id">
					<%= feedId %>
				</aui:field-wrapper>
			</c:otherwise>
		</c:choose>

		<aui:input cssClass="lfr-input-text-container" name="name" />

		<aui:input cssClass="lfr-textarea-container" name="description" />

		<aui:input cssClass="lfr-input-text-container" helpMessage="journal-feed-target-layout-friendly-url-help" name="targetLayoutFriendlyUrl" />

		<aui:input cssClass="lfr-input-text-container" helpMessage="journal-feed-target-portlet-id-help" name="targetPortletId" />

		<c:choose>
			<c:when test="<%= feed == null %>">
				<aui:field-wrapper label="permissions">
					<liferay-ui:input-permissions modelName="<%= JournalFeed.class.getName() %>" />
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>
				<aui:field-wrapper label="url">
					<liferay-ui:input-resource url="<%= feedURL.toString() %>" />
				</aui:field-wrapper>
			</c:otherwise>
		</c:choose>
	</aui:fieldset>

	<liferay-ui:panel-container extended="<%= true %>" id="journalFeedSettingsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="journalFeedConstraintsPanel" persistState="<%= true %>" title="web-content-contraints">
			<aui:fieldset>
				<aui:select label="web-content-type" name="type" showEmptyOption="<%= true %>">

					<%
					for (String curType : JournalArticleConstants.TYPES) {
					%>

						<aui:option label="<%= curType %>" />

					<%
					}
					%>

				</aui:select>

				<aui:field-wrapper label="structure">
					<aui:input name="structureId" type="hidden" value="<%= structureId %>" />

					<%= HtmlUtil.escape(ddmStructureName) %>

					<aui:button name="selectStructureButton" onClick='<%= renderResponse.getNamespace() + "openStructureSelector();" %>' value="select" />

					<aui:button disabled="<%= Validator.isNull(structureId) %>" name="removeStructureButton" onClick='<%= renderResponse.getNamespace() + "removeStructure();" %>' value="remove" />
				</aui:field-wrapper>

				<aui:field-wrapper label="template">
					<c:choose>
						<c:when test="<%= ddmTemplates.isEmpty() %>">
							<aui:input name="templateId" type="hidden" value="<%= templateId %>" />
						</c:when>
						<c:otherwise>
							<liferay-ui:table-iterator
								list="<%= ddmTemplates %>"
								listType="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate"
								rowLength="3"
								rowPadding="30"
							>

								<%
								boolean templateChecked = false;

								if (templateId.equals(tableIteratorObj.getTemplateKey())) {
									templateChecked = true;
								}
								%>

								<aui:input checked="<%= templateChecked %>" label="<%= HtmlUtil.escape(tableIteratorObj.getName(locale)) %>" name="templateId" type="radio" value="<%= tableIteratorObj.getTemplateKey() %>" />

								<c:if test="<%= tableIteratorObj.isSmallImage() %>">
									<br />

									<img border="0" hspace="0" src="<%= Validator.isNotNull(tableIteratorObj.getSmallImageURL()) ? tableIteratorObj.getSmallImageURL() : themeDisplay.getPathImage() + "/journal/template?img_id=" + tableIteratorObj.getSmallImageId() + "&t=" + WebServerServletTokenUtil.getToken(tableIteratorObj.getSmallImageId()) %>" vspace="0" />
								</c:if>
							</liferay-ui:table-iterator>
						</c:otherwise>
					</c:choose>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="journalPresentationSettingsPanel" persistState="<%= true %>" title="presentation-settings">
			<aui:fieldset>
				<aui:select label="feed-item-content" name="contentFieldSelector">
					<aui:option label="<%= JournalFeedConstants.WEB_CONTENT_DESCRIPTION %>" selected="<%= contentField.equals(JournalFeedConstants.WEB_CONTENT_DESCRIPTION) %>" />

					<optgroup label='<liferay-ui:message key="<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>" />'>
						<aui:option data-contentField="<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>" label="use-default-template" selected="<%= contentField.equals(JournalFeedConstants.RENDERED_WEB_CONTENT) %>" value="" />

						<c:if test="<%= (ddmStructure != null) && (ddmTemplates.size() > 1) %>">

							<%
							for (DDMTemplate curTemplate : ddmTemplates) {
							%>

								<aui:option data-contentField="<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>" label='<%= LanguageUtil.format(pageContext, "use-template-x", HtmlUtil.escape(curTemplate.getName(locale))) %>' selected="<%= rendererTemplateId.equals(curTemplate.getTemplateKey()) %>" value="<%= curTemplate.getTemplateKey() %>" />

							<%
							}
							%>

						</c:if>
					</optgroup>

					<c:if test="<%= ddmStructure != null %>">
						<optgroup label="<liferay-ui:message key="structure-fields" />">

							<%
							Document doc = SAXReaderUtil.read(ddmStructure.getXsd());

							XPath xpathSelector = SAXReaderUtil.createXPath("//dynamic-element");

							List<Node> nodes = xpathSelector.selectNodes(doc);

							for (Node node : nodes) {
								Element el = (Element)node;

								String elName = el.attributeValue("name");
								String elType = StringUtil.replace(el.attributeValue("type"), StringPool.UNDERLINE, StringPool.DASH);

								if (!elType.equals("boolean") && !elType.equals("list") && !elType.equals("multi-list")) {
							%>

									<aui:option label='<%= TextFormatter.format(elName, TextFormatter.J) + "(" + LanguageUtil.get(pageContext, elType) + ")" %>' selected="<%= contentField.equals(elName) %>" value="<%= elName %>" />

							<%
								}
							}
							%>

						</optgroup>
					</c:if>
				</aui:select>

				<aui:select name="feedType">

					<%
					for (String curFeedType : RSSUtil.FEED_TYPES) {
					%>

						<aui:option label="<%= RSSUtil.getFeedTypeName(curFeedType) %>" selected="<%= feedType.equals(curFeedType) %>" value="<%= curFeedType %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input label="maximum-items-to-display" name="delta" value="10" />

				<aui:select label="order-by-column" name="orderByCol">
					<aui:option label="modified-date" />
					<aui:option label="display-date" />
				</aui:select>

				<aui:select name="orderByType">
					<aui:option label="ascending" value="asc" />
					<aui:option label="descending" value="desc" />
				</aui:select>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>

		<%
		boolean hasSavePermission = false;

		if (feed != null) {
			hasSavePermission = JournalFeedPermission.contains(permissionChecker, feed, ActionKeys.UPDATE);
		}
		else {
			hasSavePermission = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_FEED);
		}
		%>

		<c:if test="<%= hasSavePermission %>">
			<aui:button type="submit" />

			<c:if test="<%= feed != null %>">

				<%
				String taglibPreviewButton = "Liferay.Util.openWindow(id:'" + renderResponse.getNamespace() + "preview', title: '" + UnicodeLanguageUtil.get(pageContext, "feed") + "', uri: '" + feedURL + "'});";
				%>

				<aui:button onClick="<%= taglibPreviewButton %>" value="preview" />
			</c:if>
		</c:if>

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />openStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
				classPK: <%= (ddmStructure != null) ? ddmStructure.getPrimaryKey(): 0 %>,
				ddmResource: '<%= ddmResource %>',
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectStructure',
				groupId: <%= groupId %>,

				<%
				Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
				%>

				refererWebDAVToken: '<%= portlet.getWebDAVStorageToken() %>',

				storageType: '<%= PropsValues.JOURNAL_ARTICLE_STORAGE_TYPE %>',
				structureName: 'structure',
				structureType: 'com.liferay.portlet.journal.model.JournalArticle',
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>'
			},
			function(event){
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-structure-will-change-the-available-templates-and-available-feed-item-content") %>') && (document.<portlet:namespace />fm.<portlet:namespace />structureId.value != event.structurekey)) {
					document.<portlet:namespace />fm.<portlet:namespace />structureId.value = event.ddmstructurekey;
					document.<portlet:namespace />fm.<portlet:namespace />templateId.value = "";
					document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = "";
					document.<portlet:namespace />fm.<portlet:namespace />contentField.value = "<%= JournalFeedConstants.WEB_CONTENT_DESCRIPTION %>";

					submitForm(document.<portlet:namespace />fm);
				}
			}
		);
	}

	function <portlet:namespace />removeStructure() {
		document.<portlet:namespace />fm.<portlet:namespace />structureId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />templateId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />contentField.value = "<%= JournalFeedConstants.WEB_CONTENT_DESCRIPTION %>";

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveFeed() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= feed == null ? Constants.ADD : Constants.UPDATE %>";

		<c:if test="<%= feed == null %>">
			document.<portlet:namespace />fm.<portlet:namespace />feedId.value = document.<portlet:namespace />fm.<portlet:namespace />newFeedId.value;
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectRendererTemplate(rendererTemplateId) {
		document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = rendererTemplateId;
	}

	function <portlet:namespace />selectTemplate(structureId, templateId, dialog) {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-template-will-change-the-structure,-available-input-fields,-and-available-templates") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace />structureId.value = structureId;
			document.<portlet:namespace />fm.<portlet:namespace />templateId.value = templateId;

			if (dialog) {
				dialog.hide();
			}

			submitForm(document.<portlet:namespace />fm);
		}
	}

	Liferay.Util.disableToggleBoxes('<portlet:namespace />autoFeedIdCheckbox','<portlet:namespace />newFeedId', true);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_FEED_FORCE_AUTOGENERATE_ID %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
			</c:when>
			<c:otherwise>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= (feed == null) ? "newFeedId" : "name" %>);
			</c:otherwise>
		</c:choose>
	</c:if>
</aui:script>

<aui:script use="aui-base">
	var feedItemContentSelector = A.one('select#<portlet:namespace />contentFieldSelector');

	var changeFeedItemContent = function() {
		var selectedFeedItemOption = feedItemContentSelector.one(':selected');

		var data = selectedFeedItemOption.attr('data-contentField');
		var value = selectedFeedItemOption.attr('value');

		if (data === '<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>') {
			document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = value;
			document.<portlet:namespace />fm.<portlet:namespace />contentField.value = '<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>';
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = '';
			document.<portlet:namespace />fm.<portlet:namespace />contentField.value = value;
		}
	}

	feedItemContentSelector.on('change', changeFeedItemContent);
</aui:script>