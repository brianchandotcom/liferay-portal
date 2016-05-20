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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

JournalFeed feed = ActionUtil.getFeed(request);

long groupId = BeanParamUtil.getLong(feed, request, "groupId", scopeGroupId);

String feedId = BeanParamUtil.getString(feed, request, "feedId");
String newFeedId = ParamUtil.getString(request, "newFeedId");

String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

if (Validator.isNull(ddmStructureKey) && (feed != null)) {
	ddmStructureKey = feed.getDDMStructureKey();
}

DDMStructure ddmStructure = null;

String ddmStructureName = StringPool.BLANK;

if (Validator.isNotNull(ddmStructureKey)) {
	ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(JournalArticle.class), ddmStructureKey, true);

	if (ddmStructure != null) {
		ddmStructureName = ddmStructure.getName(locale);
	}
}

List<DDMTemplate> ddmTemplates = new ArrayList<DDMTemplate>();

if (ddmStructure != null) {
	ddmTemplates = DDMTemplateLocalServiceUtil.getTemplates(themeDisplay.getScopeGroupId(), PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId(), true);
}

String ddmTemplateKey = ParamUtil.getString(request, "ddmTemplateKey");

if (Validator.isNull(ddmTemplateKey) && (feed != null)) {
	ddmTemplateKey = feed.getDDMTemplateKey();
}

if ((ddmStructure == null) && Validator.isNotNull(ddmTemplateKey)) {
	DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(DDMStructure.class), ddmTemplateKey, true);

	if (ddmTemplate != null) {
		ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(ddmTemplate.getClassPK());

		if (ddmStructure != null) {
			ddmStructureKey = ddmStructure.getStructureKey();
			ddmStructureName = ddmStructure.getName(locale);

			ddmTemplates = DDMTemplateLocalServiceUtil.getTemplates(themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(DDMStructure.class), ddmTemplate.getClassPK());
		}
	}
}

String ddmRendererTemplateKey = ParamUtil.getString(request, "ddmRendererTemplateKey");

if (Validator.isNull(ddmRendererTemplateKey) && (feed != null)) {
	ddmRendererTemplateKey = feed.getDDMTemplateKey();
}

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

	feedURL = PortletURLFactoryUtil.create(request, JournalPortletKeys.JOURNAL, targetLayoutPlid, PortletRequest.RESOURCE_PHASE);

	feedURL.setCacheability(ResourceURL.FULL);
	feedURL.setParameter("groupId", String.valueOf(groupId));
	feedURL.setParameter("feedId", String.valueOf(feedId));
	feedURL.setResourceID("rss");
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((feed == null) ? LanguageUtil.get(request, "new-feed") : feed.getName());
%>

<portlet:actionURL var="editFeedURL">
	<portlet:param name="mvcPath" value="/edit_feed.jsp" />
</portlet:actionURL>

<aui:form action="<%= editFeedURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFeed();" %>'>
	<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="feedId" type="hidden" value="<%= feedId %>" />
	<aui:input name="ddmRendererTemplateKey" type="hidden" value="<%= ddmRendererTemplateKey %>" />
	<aui:input name="contentField" type="hidden" value="<%= contentField %>" />

	<liferay-ui:error exception="<%= DuplicateFeedIdException.class %>" message="please-enter-a-unique-id" />
	<liferay-ui:error exception="<%= FeedContentFieldException.class %>" message="please-select-a-valid-feed-item-content" />
	<liferay-ui:error exception="<%= FeedIdException.class %>" message="please-enter-a-valid-id" />
	<liferay-ui:error exception="<%= FeedNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= FeedTargetLayoutFriendlyUrlException.class %>" message="please-enter-a-valid-target-layout-friendly-url" />
	<liferay-ui:error exception="<%= FeedTargetPortletIdException.class %>" message="please-enter-a-valid-portlet-id" />

	<aui:model-context bean="<%= feed %>" model="<%= JournalFeed.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<c:choose>
				<c:when test="<%= feed == null %>">
					<c:choose>
						<c:when test="<%= journalWebConfiguration.journalFeedForceAutogenerateId() %>">
							<aui:input name="newFeedId" type="hidden" />
							<aui:input name="autoFeedId" type="hidden" value="<%= true %>" />
						</c:when>
						<c:otherwise>
							<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" cssClass="lfr-input-text-container" field="feedId" fieldParam="newFeedId" label="id" name="newFeedId" value="<%= newFeedId %>" />

							<aui:input label="autogenerate-id" name="autoFeedId" type="checkbox" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<aui:input name="id" type="resource" value="<%= feedId %>" />
				</c:otherwise>
			</c:choose>

			<aui:input autoFocus="<%= ((feed != null) && !journalWebConfiguration.journalFeedForceAutogenerateId() && (windowState.equals(WindowState.MAXIMIZED)) || windowState.equals(LiferayWindowState.POP_UP)) %>" cssClass="lfr-input-text-container" name="name" />

			<aui:input cssClass="lfr-textarea-container" name="description" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="journal-feed-target-layout-friendly-url-help" name="targetLayoutFriendlyUrl" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="journal-feed-target-portlet-id-help" name="targetPortletId" />

			<c:if test="<%= feed != null %>">
				<aui:input name="url" type="resource" value="<%= feedURL.toString() %>" />

				<aui:a href="<%= feedURL.toString() %>" label="preview" target="_blank" />
			</c:if>
		</aui:fieldset>

		<c:if test="<%= feed == null %>">
			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
				<liferay-ui:input-permissions modelName="<%= JournalFeed.class.getName() %>" />
			</aui:fieldset>
		</c:if>

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="web-content-contraints">
			<div class="form-group">
				<aui:input name="ddmStructureKey" required="<%= true %>" type="hidden" value="<%= ddmStructureKey %>" />

				<aui:input name="structure" required="<%= true %>" type="resource" value="<%= ddmStructureName %>" />

				<aui:button name="selectStructureButton" onClick='<%= renderResponse.getNamespace() + "openStructureSelector();" %>' value="select" />

				<aui:button disabled="<%= Validator.isNull(ddmStructureKey) %>" name="removeStructureButton" onClick='<%= renderResponse.getNamespace() + "removeStructure();" %>' value="remove" />
			</div>

			<c:choose>
				<c:when test="<%= ddmTemplates.isEmpty() %>">
					<aui:input name="ddmTemplateKey" type="hidden" value="<%= ddmTemplateKey %>" />
				</c:when>
				<c:otherwise>
					<aui:field-wrapper label="template">
						<liferay-ui:table-iterator
							list="<%= ddmTemplates %>"
							listType="com.liferay.dynamic.data.mapping.model.DDMTemplate"
							rowLength="3"
							rowPadding="30"
						>

							<%
							boolean templateChecked = false;

							if (ddmTemplateKey.equals(tableIteratorObj.getTemplateKey())) {
								templateChecked = true;
							}
							%>

							<aui:input checked="<%= templateChecked %>" label="<%= HtmlUtil.escape(tableIteratorObj.getName(locale)) %>" name="ddmTemplateKey" type="radio" value="<%= tableIteratorObj.getTemplateKey() %>" />

							<c:if test="<%= tableIteratorObj.isSmallImage() %>">
								<br />

								<img alt="" hspace="0" src="<%= HtmlUtil.escapeAttribute(tableIteratorObj.getTemplateImageURL(themeDisplay)) %>" vspace="0" />
							</c:if>
						</liferay-ui:table-iterator>
					</aui:field-wrapper>
				</c:otherwise>
			</c:choose>
		</aui:fieldset>

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="presentation-settings">
			<aui:select label="feed-item-content" name="contentFieldSelector">
				<aui:option label="<%= JournalFeedConstants.WEB_CONTENT_DESCRIPTION %>" selected="<%= contentField.equals(JournalFeedConstants.WEB_CONTENT_DESCRIPTION) %>" />

				<optgroup label="<liferay-ui:message key="<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>" />">
					<aui:option data-contentField="<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>" label="use-default-template" selected="<%= contentField.equals(JournalFeedConstants.RENDERED_WEB_CONTENT) %>" value="" />

					<c:if test="<%= (ddmStructure != null) && (ddmTemplates.size() > 1) %>">

						<%
						for (DDMTemplate curTemplate : ddmTemplates) {
						%>

							<aui:option data-contentField="<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>" label='<%= LanguageUtil.format(request, "use-template-x", HtmlUtil.escape(curTemplate.getName(locale)), false) %>' selected="<%= ddmRendererTemplateKey.equals(curTemplate.getTemplateKey()) %>" value="<%= curTemplate.getTemplateKey() %>" />

						<%
						}
						%>

					</c:if>
				</optgroup>

				<c:if test="<%= ddmStructure != null %>">
					<optgroup label="<liferay-ui:message key="structure-fields" />">

						<%
						DDMForm ddmForm = ddmStructure.getDDMForm();

						Map<String, DDMFormField> ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);

						for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
							String ddmFormFieldType = ddmFormField.getType();

							if (ddmFormFieldType.equals("radio") || ddmFormFieldType.equals("select")) {
								DDMFormFieldOptions ddmFormFieldOptions = ddmFormField.getDDMFormFieldOptions();

								for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
									LocalizedValue optionLabels = ddmFormFieldOptions.getOptionLabels(optionValue);

									optionValue = ddmFormField.getName() + StringPool.UNDERLINE + optionValue;
						%>

									<aui:option label='<%= TextFormatter.format(optionLabels.getString(locale), TextFormatter.J) + "(" + LanguageUtil.get(request, ddmFormFieldType) + ")" %>' selected="<%= contentField.equals(optionValue) %>" value="<%= optionValue %>" />

							<%
								}
							}
							else if (!ddmFormFieldType.equals("checkbox")) {
							%>

								<aui:option label='<%= TextFormatter.format(ddmFormField.getName(), TextFormatter.J) + "(" + LanguageUtil.get(request, ddmFormFieldType) + ")" %>' selected="<%= contentField.equals(ddmFormField.getName()) %>" value="<%= ddmFormField.getName() %>" />

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
	</aui:fieldset-group>

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
			<aui:button cssClass="btn-lg" type="submit" />
		</c:if>

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />openStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.VIEW), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classPK: <%= (ddmStructure != null) ? ddmStructure.getPrimaryKey(): 0 %>,
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectStructure',
				groupId: <%= themeDisplay.getSiteGroupId() %>,
				mvcPath: '/select_structure.jsp',
				navigationStartsOn: '<%= DDMNavigationHelper.SELECT_STRUCTURE %>',
				refererPortletName: '<%= JournalPortletKeys.JOURNAL + ".selectStructure" %>',

				<%
				Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
				%>

				refererWebDAVToken: '<%= WebDAVUtil.getStorageToken(portlet) %>',

				showAncestorScopes: true,
				title: '<%= UnicodeLanguageUtil.get(request, "structures") %>'
			},
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(request, "selecting-a-new-structure-changes-the-available-templates-and-available-feed-item-content") %>') && (document.<portlet:namespace />fm.<portlet:namespace />ddmStructureKey.value != event.structurekey)) {
					document.<portlet:namespace />fm.<portlet:namespace />ddmStructureKey.value = event.ddmstructurekey;
					document.<portlet:namespace />fm.<portlet:namespace />ddmTemplateKey.value = '';
					document.<portlet:namespace />fm.<portlet:namespace />ddmRendererTemplateKey.value = '';
					document.<portlet:namespace />fm.<portlet:namespace />contentField.value = '<%= JournalFeedConstants.WEB_CONTENT_DESCRIPTION %>';

					submitForm(document.<portlet:namespace />fm);
				}
			}
		);
	}

	function <portlet:namespace />removeStructure() {
		document.<portlet:namespace />fm.<portlet:namespace />ddmStructureKey.value = '';
		document.<portlet:namespace />fm.<portlet:namespace />ddmTemplateKey.value = '';
		document.<portlet:namespace />fm.<portlet:namespace />ddmRendererTemplateKey.value = '';
		document.<portlet:namespace />fm.<portlet:namespace />contentField.value = '<%= JournalFeedConstants.WEB_CONTENT_DESCRIPTION %>';

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveFeed() {
		document.<portlet:namespace />fm['<portlet:namespace />javax-portlet-action'].value = '<%= feed == null ? "addFeed" : "updateFeed" %>';

		<c:if test="<%= feed == null %>">
			document.<portlet:namespace />fm.<portlet:namespace />feedId.value = document.<portlet:namespace />fm.<portlet:namespace />newFeedId.value;
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.Util.disableToggleBoxes('<portlet:namespace />autoFeedId', '<portlet:namespace />newFeedId', true);
</aui:script>

<aui:script sandbox="<%= true %>">
	var form = $(document.<portlet:namespace />fm);

	var contentFieldSelector = form.fm('contentFieldSelector');

	contentFieldSelector.on(
		'change',
		function() {
			var ddmRendererTemplateKeyValue = '';

			var selectedFeedItemOption = contentFieldSelector.find(':selected');

			var contentFieldValue = selectedFeedItemOption.val();

			var renderedWebContent = '<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>';

			if (selectedFeedItemOption.data('contentfield') === renderedWebContent) {
				contentFieldValue = renderedWebContent;
				ddmRendererTemplateKeyValue = contentFieldValue;
			}

			form.fm('contentField').val(contentFieldValue);
			form.fm('ddmRendererTemplateKey').val(ddmRendererTemplateKeyValue);
		}
	);
</aui:script>