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

<%@ include file="/export/init.jsp" %>

<liferay-staging:defineObjects />

<%
String backURL = ParamUtil.getString(request, "backURL");

if (liveGroup == null) {
	liveGroup = group;
	liveGroupId = groupId;
}

long exportImportConfigurationId = 0;

ExportImportConfiguration exportImportConfiguration = null;

Map<String, Serializable> exportImportConfigurationSettingsMap = Collections.emptyMap();

if (SessionMessages.contains(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId")) {
	exportImportConfigurationId = (Long)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);
	}

	exportImportConfigurationSettingsMap = (Map<String, Serializable>)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "settingsMap");
}
else {
	exportImportConfigurationId = ParamUtil.getLong(request, "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

		exportImportConfigurationSettingsMap = exportImportConfiguration.getSettingsMap();
	}
}

boolean configuredExport = (exportImportConfiguration == null) ? false : true;

String rootNodeName = StringPool.BLANK;

if (configuredExport) {
	privateLayout = MapUtil.getBoolean(exportImportConfigurationSettingsMap, "privateLayout", privateLayout);
}

if (privateLayout) {
	rootNodeName = LanguageUtil.get(request, "private-pages");
}
else {
	rootNodeName = LanguageUtil.get(request, "public-pages");
}

String treeId = "layoutsExportTree" + liveGroupId + privateLayout;

String displayStyle = ParamUtil.getString(request, "displayStyle");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "exportLayoutsView");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("liveGroupId", String.valueOf(liveGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));
portletURL.setParameter("displayStyle", displayStyle);

if (Validator.isBlank(backURL)) {
	backURL = portletURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(!configuredExport ? LanguageUtil.get(request, "new-custom-export") : LanguageUtil.format(request, "new-export-based-on-x", exportImportConfiguration.getName(), false));
%>

<div class="container-fluid-1280">
	<portlet:actionURL name="editExportConfiguration" var="restoreTrashEntriesURL">
		<portlet:param name="mvcRenderCommandName" value="exportLayouts" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
	</portlet:actionURL>

	<liferay-trash:undo
		portletURL="<%= restoreTrashEntriesURL %>"
	/>

	<%
	int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(liveGroupId, BackgroundTaskExecutorNames.LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR, false);
	%>

	<div class="<%= (incompleteBackgroundTaskCount == 0) ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
		<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
			<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
		</liferay-util:include>
	</div>

	<portlet:actionURL name="exportLayouts" var="exportPagesURL">
		<portlet:param name="mvcRenderCommandName" value="exportLayouts" />
		<portlet:param name="exportLAR" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:actionURL>

	<aui:form action='<%= exportPagesURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm1">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EXPORT %>" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="exportImportConfigurationId" type="hidden" value="<%= String.valueOf(exportImportConfigurationId) %>" />
		<aui:input name="groupId" type="hidden" value="<%= String.valueOf(groupId) %>" />
		<aui:input name="liveGroupId" type="hidden" value="<%= String.valueOf(liveGroupId) %>" />
		<aui:input name="privateLayout" type="hidden" value="<%= String.valueOf(privateLayout) %>" />
		<aui:input name="rootNodeName" type="hidden" value="<%= rootNodeName %>" />
		<aui:input name="treeId" type="hidden" value="<%= treeId %>" />
		<aui:input name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>" type="hidden" value="<%= true %>" />
		<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL %>" type="hidden" value="<%= true %>" />
		<aui:input name="<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>" type="hidden" value="<%= true %>" />
		<aui:input name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>" type="hidden" value="<%= true %>" />

		<liferay-ui:error exception="<%= LARFileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />

		<div class="export-dialog-tree">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<c:choose>
						<c:when test="<%= exportImportConfiguration == null %>">
							<aui:input label="title" maxlength='<%= ModelHintsUtil.getMaxLength(ExportImportConfiguration.class.getName(), "name") %>' name="name" placeholder="process-name-placeholder" />
						</c:when>
						<c:otherwise>
							<aui:input label="title" maxlength='<%= ModelHintsUtil.getMaxLength(ExportImportConfiguration.class.getName(), "name") %>' name="name" value="<%= exportImportConfiguration.getName() %>" />
						</c:otherwise>
					</c:choose>
				</aui:fieldset>

				<liferay-staging:deletions
					cmd="<%= Constants.EXPORT %>"
					exportImportConfigurationId="<%= exportImportConfigurationId %>"
				/>

				<c:if test="<%= !group.isLayoutPrototype() && !group.isCompany() %>">
					<liferay-staging:select-pages
						action="<%= Constants.EXPORT %>"
						disableInputs="<%= configuredExport %>"
						exportImportConfigurationId="<%= exportImportConfigurationId %>"
						groupId="<%= liveGroupId %>"
						privateLayout="<%= privateLayout %>"
						treeId="<%= treeId %>"
					/>
				</c:if>

				<liferay-staging:content
					cmd="<%= Constants.EXPORT %>"
					disableInputs="<%= configuredExport %>"
					exportImportConfigurationId="<%= exportImportConfigurationId %>"
					type="<%= Constants.EXPORT %>"
				/>

				<liferay-staging:permissions
					action="<%= Constants.EXPORT %>"
					descriptionCSSClass="permissions-description"
					disableInputs="<%= configuredExport %>"
					exportImportConfigurationId="<%= exportImportConfigurationId %>"
					global="<%= group.isCompany() %>"
					labelCSSClass="permissions-label"
				/>
			</aui:fieldset-group>
		</div>

		<aui:button-row>
			<aui:button type="submit" value="export" />

			<aui:button href="<%= backURL %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script use="liferay-export-import-export-import">
	var exportImport = new Liferay.ExportImport({
		archivedSetupsNode:
			'#<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>',
		commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
		deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
		exportLAR: true,
		form: document.<portlet:namespace />fm1,
		incompleteProcessMessageNode:
			'#<portlet:namespace />incompleteProcessMessage',
		locale: '<%= locale.toLanguageTag() %>',
		namespace: '<portlet:namespace />',
		pageTreeId: '<%= treeId %>',
		rangeAllNode: '#rangeAll',
		rangeDateRangeNode: '#rangeDateRange',
		rangeLastNode: '#rangeLast',
		ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
		setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>',
		timeZoneOffset: <%= timeZoneOffset %>,
		userPreferencesNode:
			'#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>'
	});

	Liferay.component('<portlet:namespace />ExportImportComponent', exportImport);

	var liferayForm = Liferay.Form.get('<portlet:namespace />fm1');

	var form = liferayForm.formNode;

	form.on('submit', function(event) {
		event.halt();

		var exportImport = Liferay.component(
			'<portlet:namespace />ExportImportComponent'
		);

		var dateChecker = exportImport.getDateRangeChecker();

		if (dateChecker.validRange) {
			submitForm(form, form.attr('action'), false);
		}
		else {
			exportImport.showNotification(dateChecker);
		}
	});

	var oldFieldRules = liferayForm.get('fieldRules');

	var fieldRules = [
		{
			body: function(val, fieldNode, ruleValue) {

				<%
				JSONArray blacklistCharJSONArray = JSONFactoryUtil.createJSONArray();

				for (String s : PropsValues.DL_CHAR_BLACKLIST) {
					blacklistCharJSONArray.put(s);
				}
				%>

				var blacklistCharJSONArray = <%= blacklistCharJSONArray.toJSONString() %>;

				for (var i = 0; i < blacklistCharJSONArray.length; i++) {
					if (val.indexOf(blacklistCharJSONArray[i]) !== -1) {
						return false;
					}
				}

				return true;
			},
			custom: true,
			errorMessage:
				'<%= LanguageUtil.get(request, "the-following-are-invalid-characters") + HtmlUtil.escapeJS(Arrays.toString(PropsValues.DL_CHAR_BLACKLIST)) %>',
			fieldName: '<portlet:namespace />name',
			validatorName: 'custom_pageTemplateNameValidator'
		}
	];

	if (oldFieldRules) {
		fieldRules = fieldRules.concat(oldFieldRules);
	}

	liferayForm.set('fieldRules', fieldRules);
</aui:script>

<aui:script>
	Liferay.Util.toggleRadio(
		'<portlet:namespace />chooseApplications',
		'<portlet:namespace />selectApplications',
		['<portlet:namespace />showChangeGlobalConfiguration']
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />allApplications',
		'<portlet:namespace />showChangeGlobalConfiguration',
		['<portlet:namespace />selectApplications']
	);

	Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', [
		'<portlet:namespace />startEndDate',
		'<portlet:namespace />rangeLastInputs'
	]);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />rangeDateRange',
		'<portlet:namespace />startEndDate',
		'<portlet:namespace />rangeLastInputs'
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />rangeLast',
		'<portlet:namespace />rangeLastInputs',
		['<portlet:namespace />startEndDate']
	);
</aui:script>