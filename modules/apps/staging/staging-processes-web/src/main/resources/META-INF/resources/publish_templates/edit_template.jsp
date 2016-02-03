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
String cmd = ParamUtil.getString(request, Constants.CMD, Constants.ADD);

long exportImportConfigurationId = 0;

ExportImportConfiguration exportImportConfiguration = null;

Map<String, Serializable> exportImportConfigurationSettingsMap = Collections.emptyMap();

Map<String, String[]> parameterMap = Collections.emptyMap();

if (SessionMessages.contains(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId")) {
	exportImportConfigurationId = (Long)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);
	}

	exportImportConfigurationSettingsMap = (Map<String, Serializable>)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "settingsMap");

	parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
}
else {
	exportImportConfigurationId = ParamUtil.getLong(request, "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

		exportImportConfigurationSettingsMap = exportImportConfiguration.getSettingsMap();

		parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
	}
}

if (exportImportConfiguration != null) {
	cmd = Constants.UPDATE;
}

long layoutSetBranchId = MapUtil.getLong(parameterMap, "layoutSetBranchId", ParamUtil.getLong(request, "layoutSetBranchId"));
String layoutSetBranchName = MapUtil.getString(parameterMap, "layoutSetBranchName", ParamUtil.getString(request, "layoutSetBranchName"));

String treeId = "liveLayoutsTree";

if (liveGroup.isStaged()) {
	if (!liveGroup.isStagedRemotely()) {
		treeId = "stageLayoutsTree";
	}
	else {
		treeId = "remoteLayoutsTree";
	}
}

treeId = treeId + liveGroupId;

treeId = treeId + privateLayout + layoutSetBranchId;

long[] selectedLayoutIds = null;

String openNodes = SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode");

if (openNodes == null) {
	selectedLayoutIds = ExportImportHelperUtil.getAllLayoutIds(stagingGroupId, privateLayout);

	for (long selectedLayoutId : selectedLayoutIds) {
		SessionTreeJSClicks.openLayoutNodes(request, treeId + "SelectedNode", privateLayout, selectedLayoutId, true);
	}
}
else {
	selectedLayoutIds = GetterUtil.getLongValues(StringUtil.split(openNodes, ','));
}

PortletURL renderURL = renderResponse.createRenderURL();

renderURL.setParameter("mvcRenderCommandName", "viewPublishConfigurations");
renderURL.setParameter("groupId", String.valueOf(stagingGroupId));
renderURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
renderURL.setParameter("layoutSetBranchName", layoutSetBranchName);
renderURL.setParameter("privateLayout", String.valueOf(privateLayout));

response.setHeader("Ajax-ID", request.getHeader("Ajax-ID"));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(renderURL.toString());

renderResponse.setTitle((exportImportConfiguration == null) ? LanguageUtil.get(request, "new-publish-template") : exportImportConfiguration.getName());
%>

<c:if test='<%= SessionMessages.contains(renderRequest, "requestProcessed") %>'>

	<%
	String successMessage = (String)SessionMessages.get(renderRequest, "requestProcessed");
	%>

	<c:if test='<%= Validator.isNotNull(successMessage) && !successMessage.equals("request_processed") %>'>
		<div class="alert alert-success">
			<%= HtmlUtil.escape(successMessage) %>
		</div>
	</c:if>
</c:if>

<div class="container-fluid-1280">
	<div id="<portlet:namespace />customConfiguration">
		<portlet:actionURL name="editPublishConfiguration" var="updatePublishConfigurationURL">
			<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
			<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
		</portlet:actionURL>

		<aui:form action='<%= updatePublishConfigurationURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="exportPagesFm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (exportImportConfiguration == null) ? Constants.ADD : Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= renderURL.toString() %>" />
			<aui:input name="exportImportConfigurationId" type="hidden" value="<%= exportImportConfigurationId %>" />
			<aui:input name="groupId" type="hidden" value="<%= stagingGroupId %>" />
			<aui:input name="layoutSetBranchName" type="hidden" value="<%= layoutSetBranchName %>" />
			<aui:input name="lastImportUserName" type="hidden" value="<%= user.getFullName() %>" />
			<aui:input name="lastImportUserUuid" type="hidden" value="<%= String.valueOf(user.getUserUuid()) %>" />
			<aui:input name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>" type="hidden" value="<%= true %>" />
			<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL %>" type="hidden" value="<%= true %>" />
			<aui:input name="<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>" type="hidden" value="<%= true %>" />
			<aui:input name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>" type="hidden" value="<%= true %>" />

			<div id="<portlet:namespace />publishOptions">
				<div class="export-dialog-tree">
					<aui:fieldset-group markupView="lexicon">
						<liferay-staging:configuration-header exportImportConfiguration="<%= exportImportConfiguration %>" />

						<c:if test="<%= !group.isCompany() %>">
							<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="pages">

								<%
								request.setAttribute("select_pages.jsp-parameterMap", parameterMap);
								%>

								<liferay-util:include page="/publish_templates/select_pages.jsp" servletContext="<%= application %>">
									<liferay-util:param name="<%= Constants.CMD %>" value="<%= cmd %>" />
									<liferay-util:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
									<liferay-util:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
									<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
									<liferay-util:param name="treeId" value="<%= treeId %>" />
									<liferay-util:param name="selectedLayoutIds" value="<%= StringUtil.merge(selectedLayoutIds) %>" />
								</liferay-util:include>
							</aui:fieldset>
						</c:if>

						<liferay-staging:content cmd="<%= cmd %>" parameterMap="<%= parameterMap %>" type="<%= stagingGroup.isStagedRemotely() ? Constants.PUBLISH_TO_REMOTE : Constants.PUBLISH_TO_LIVE %>" />

						<liferay-staging:deletions cmd="<%= cmd %>" />

						<liferay-staging:permissions global="<%= group.isCompany() %>" parameterMap="<%= parameterMap %>" />

						<c:if test="<%= stagingGroup.isStagedRemotely() %>">
							<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" cssClass="options-group" label="remote-live-connection-settings">

								<%
								UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();
								%>

								<%@ include file="/new_publication/publish_layouts_remote_options.jspf" %>
							</aui:fieldset>
						</c:if>
					</aui:fieldset-group>
				</div>

				<aui:button-row>
					<aui:button cssClass="btn-lg" type="submit" value="save" />

					<aui:button cssClass="btn-lg" href="<%= renderURL.toString() %>" type="cancel" />
				</aui:button-row>
			</div>
		</aui:form>
	</div>
</div>

<aui:script>
	function <portlet:namespace />publishPages() {
		var form = AUI.$(document.<portlet:namespace />exportPagesFm);

		if (form.fm('allContent').prop('checked')) {
			form.fm('<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>').val(true);
		}

		submitForm(form);
	}

	Liferay.Util.toggleRadio('<portlet:namespace />allApplications', '<portlet:namespace />showChangeGlobalConfiguration', ['<portlet:namespace />selectApplications']);
	Liferay.Util.toggleRadio('<portlet:namespace />allContent', '<portlet:namespace />showChangeGlobalContent', ['<portlet:namespace />selectContents']);
	Liferay.Util.toggleRadio('<portlet:namespace />publishingEventNow', '<portlet:namespace />publishButton', ['<portlet:namespace />selectSchedule', '<portlet:namespace />addButton']);
	Liferay.Util.toggleRadio('<portlet:namespace />publishingEventSchedule', ['<portlet:namespace />selectSchedule', '<portlet:namespace />addButton'], '<portlet:namespace />publishButton');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeDateRange', '<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLastPublish', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLast', '<portlet:namespace />rangeLastInputs', ['<portlet:namespace />startEndDate']);
</aui:script>

<aui:script use="liferay-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="publishLayouts" var="publishProcessesURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= cmd %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
		<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
		<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
		<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport(
		{
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
			deleteMissingLayoutsNode: '#<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>',
			deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
			form: document.<portlet:namespace />exportPagesFm,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			layoutSetSettingsNode: '#<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>',
			locale: '<%= locale.toLanguageTag() %>',
			logoNode: '#<%= PortletDataHandlerKeys.LOGO %>',
			namespace: '<portlet:namespace />',
			pageTreeId: '<%= treeId %>',
			processesNode: '#publishProcesses',
			processesResourceURL: '<%= publishProcessesURL.toString() %>',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			rangeLastPublishNode: '#rangeLastPublish',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
			remoteAddressNode: '#<portlet:namespace />remoteAddress',
			remoteGroupIdNode: '#<portlet:namespace />remoteGroupId',
			remotePathContextNode: '#<portlet:namespace />remotePathContext',
			remotePortNode: '#<portlet:namespace />remotePort',
			secureConnectionNode: '#secureConnection',
			setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>',
			themeReferenceNode: '#<%= PortletDataHandlerKeys.THEME_REFERENCE %>',
			timeZone: '<%= timeZone.getID() %>',
			userPreferencesNode: '#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>'
		}
	);

	var processDataValue = function(dataValue) {
		var customConfiguration = A.one('#<portlet:namespace />customConfiguration');
		var savedConfigurations = A.one('#<portlet:namespace />savedConfigurations');

		if (dataValue === 'custom') {
			savedConfigurations.hide();

			customConfiguration.show();
		}
		else if (dataValue === 'saved') {
			customConfiguration.hide();

			savedConfigurations.show();
		}
	};
</aui:script>