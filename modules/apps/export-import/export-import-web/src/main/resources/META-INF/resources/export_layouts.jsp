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

<liferay-staging:defineObjects />

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

if (Validator.isNull(cmd)) {
	cmd = Constants.EXPORT;
}

String backURL = ParamUtil.getString(request, "backURL");

if (liveGroup == null) {
	liveGroup = group;
	liveGroupId = groupId;
}

String exportConfigurationButtons = ParamUtil.getString(request, "exportConfigurationButtons", "custom");

long exportImportConfigurationId = 0;

ExportImportConfiguration exportImportConfiguration = null;
Map<String, Serializable> exportImportConfigurationSettingsMap = Collections.emptyMap();
Map<String, String[]> parameterMap = Collections.emptyMap();
long[] selectedLayoutIds = null;

if (SessionMessages.contains(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId")) {
	exportImportConfigurationId = (Long)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);
	}

	exportImportConfigurationSettingsMap = (Map<String, Serializable>)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "settingsMap");

	parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
	selectedLayoutIds = GetterUtil.getLongValues(exportImportConfigurationSettingsMap.get("layoutIds"));
}
else {
	exportImportConfigurationId = ParamUtil.getLong(request, "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

		exportImportConfigurationSettingsMap = exportImportConfiguration.getSettingsMap();

		parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
		selectedLayoutIds = GetterUtil.getLongValues(exportImportConfigurationSettingsMap.get("layoutIds"));
	}
}

String rootNodeName = StringPool.BLANK;

if (privateLayout) {
	rootNodeName = LanguageUtil.get(request, "private-pages");
}
else {
	rootNodeName = LanguageUtil.get(request, "public-pages");
}

boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);

String treeId = "layoutsExportTree" + liveGroupId + privateLayout;

if (!cmd.equals(Constants.UPDATE)) {
	String openNodes = SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode");

	if (openNodes == null) {
		selectedLayoutIds = ExportImportHelperUtil.getAllLayoutIds(liveGroupId, privateLayout);
	}
	else {
		selectedLayoutIds = GetterUtil.getLongValues(StringUtil.split(openNodes, ','));
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "exportLayouts");
portletURL.setParameter(Constants.CMD, Constants.EXPORT);

if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
	portletURL.setParameter("tabs2", "new-export-process");
	portletURL.setParameter("exportConfigurationButtons", "saved");
}
else {
	portletURL.setParameter("tabs2", "current-and-previous");
}

portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("liveGroupId", String.valueOf(liveGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));
portletURL.setParameter("rootNodeName", rootNodeName);
portletURL.setParameter("showHeader", String.valueOf(showHeader));

String tabs2Names = StringPool.BLANK;

if (!cmd.equals(Constants.ADD)) {
	tabs2Names = "new-export-process,current-and-previous";
}
%>

<portlet:actionURL name="editExportConfiguration" var="restoreTrashEntriesURL">
	<portlet:param name="mvcPath" value="/export_layouts.jsp" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<div class="container-fluid-1280">
	<liferay-ui:trash-undo
		portletURL="<%= restoreTrashEntriesURL %>"
	/>

	<c:if test="<%= showHeader %>">
		<liferay-ui:header
			backURL="<%= backURL %>"
			title='<%= privateLayout ? LanguageUtil.get(request, "export-private-pages") : LanguageUtil.get(request, "export-public-pages") %>'
		/>
	</c:if>

	<liferay-ui:tabs
		names="<%= tabs2Names %>"
		param="tabs2"
		refresh="<%= false %>"
	>

		<%
		int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(liveGroupId, BackgroundTaskExecutorNames.LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR, false);
		%>

		<div class='<%= (incompleteBackgroundTaskCount == 0) ? "hide" : "in-progress" %>' id="<portlet:namespace />incompleteProcessMessage">
			<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
				<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
			</liferay-util:include>
		</div>

		<liferay-ui:section>
			<div <%= (!cmd.equals(Constants.ADD) && !cmd.equals(Constants.UPDATE)) ? StringPool.BLANK : "class=\"hide\"" %>>
				<aui:nav-bar>
					<aui:nav cssClass="navbar-nav" id="exportConfigurationButtons">
						<aui:nav-item
							data-value="custom"
							iconCssClass="icon-puzzle"
							label="custom"
						/>

						<aui:nav-item
							data-value="saved"
							iconCssClass="icon-archive"
							label="export-templates"
						/>
					</aui:nav>
				</aui:nav-bar>
			</div>

			<div <%= exportConfigurationButtons.equals("custom") ? StringPool.BLANK : "class=\"hide\"" %> id="<portlet:namespace />customConfiguration">
				<portlet:actionURL name="editExportConfiguration" var="updateExportConfigurationURL">
					<portlet:param name="mvcPath" value="/export_layouts.jsp" />
				</portlet:actionURL>

				<portlet:actionURL name="exportLayouts" var="exportPagesURL">
					<portlet:param name="mvcPath" value="/export_layouts.jsp" />
					<portlet:param name="exportLAR" value="<%= Boolean.TRUE.toString() %>" />
				</portlet:actionURL>

				<aui:form action='<%= cmd.equals(Constants.EXPORT) ? exportPagesURL : updateExportConfigurationURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm1">
					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
					<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
					<aui:input name="exportImportConfigurationId" type="hidden" value="<%= exportImportConfigurationId %>" />
					<aui:input name="groupId" type="hidden" value="<%= String.valueOf(groupId) %>" />
					<aui:input name="liveGroupId" type="hidden" value="<%= String.valueOf(liveGroupId) %>" />
					<aui:input name="privateLayout" type="hidden" value="<%= String.valueOf(privateLayout) %>" />
					<aui:input name="rootNodeName" type="hidden" value="<%= rootNodeName %>" />
					<aui:input name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>" type="hidden" value="<%= true %>" />
					<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL %>" type="hidden" value="<%= true %>" />
					<aui:input name="<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>" type="hidden" value="<%= true %>"  />
					<aui:input name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>" type="hidden" value="<%= true %>" />

					<liferay-ui:error exception="<%= LARFileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />

					<div class="export-dialog-tree">
						<c:if test="<%= !cmd.equals(Constants.EXPORT) %>">
							<liferay-staging:configuration-header exportImportConfiguration="<%= exportImportConfiguration %>" label='<%= cmd.equals(Constants.ADD) ? "new-export-template" : "edit-template" %>' />
						</c:if>

						<c:if test="<%= !group.isLayoutPrototype() && !group.isCompany() %>">
							<aui:fieldset cssClass="options-group" label="pages">

								<%
								request.setAttribute("select_pages.jsp-parameterMap", parameterMap);
								%>

								<liferay-util:include page="/select_pages.jsp" servletContext="<%= application %>">
									<liferay-util:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
									<liferay-util:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
									<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
									<liferay-util:param name="treeId" value="<%= treeId %>" />
									<liferay-util:param name="selectedLayoutIds" value="<%= StringUtil.merge(selectedLayoutIds) %>" />
								</liferay-util:include>
							</aui:fieldset>
						</c:if>

						<liferay-staging:content cmd="<%= cmd %>" parameterMap="<%= parameterMap %>" type="<%= Constants.EXPORT %>" />

						<aui:fieldset cssClass="options-group" label="permissions">
							<%@ include file="/permissions.jspf" %>
						</aui:fieldset>
					</div>

					<aui:button-row>
						<c:choose>
							<c:when test="<%= cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE) %>">
								<aui:button type="submit" value="save" />

								<aui:button href="<%= portletURL.toString() %>" type="cancel" />
							</c:when>
							<c:otherwise>
								<aui:button type="submit" value="export" />

								<aui:button href="<%= backURL %>" type="cancel" />
							</c:otherwise>
						</c:choose>
					</aui:button-row>
				</aui:form>
			</div>

			<c:if test="<%= !cmd.equals(Constants.ADD) && !cmd.equals(Constants.UPDATE) %>">
				<div <%= exportConfigurationButtons.equals("saved") ? StringPool.BLANK : "class=\"hide\"" %> id="<portlet:namespace />savedConfigurations">
					<liferay-util:include page="/export_layouts_configurations.jsp" servletContext="<%= application %>">
						<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
						<liferay-util:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
						<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
						<liferay-util:param name="rootNodeName" value="<%= rootNodeName %>" />
					</liferay-util:include>
				</div>
			</c:if>
		</liferay-ui:section>

		<c:if test="<%= !cmd.equals(Constants.ADD) %>">
			<liferay-ui:section>
				<div class="process-list" id="<portlet:namespace />exportProcesses">
					<liferay-util:include page="/export_layouts_processes.jsp" servletContext="<%= application %>">
						<liferay-util:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
						<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
					</liferay-util:include>
				</div>
			</liferay-ui:section>
		</c:if>
	</liferay-ui:tabs>
</div>

<aui:script use="liferay-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="exportLayouts" var="exportProcessesURL">
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
		<portlet:param name="showHeader" value="<%= String.valueOf(showHeader) %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport(
		{
			archivedSetupsNode: '#<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>',
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
			deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
			exportLAR: true,
			form: document.<portlet:namespace />fm1,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			layoutSetSettingsNode: '#<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>',
			locale: '<%= locale.toLanguageTag() %>',
			logoNode: '#<%= PortletDataHandlerKeys.LOGO %>',
			namespace: '<portlet:namespace />',
			pageTreeId: '<%= treeId %>',
			processesNode: '#exportProcesses',
			processesResourceURL: '<%= exportProcessesURL.toString() %>',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
			setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>',
			themeReferenceNode: '#<%= PortletDataHandlerKeys.THEME_REFERENCE %>',
			timeZone: '<%= timeZone.getID() %>',
			userPreferencesNode: '#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>'
		}
	);

	var form = A.one('#<portlet:namespace />fm1');

	form.on(
		'submit',
		function(event) {
			event.preventDefault();

			var A = AUI();

			var allContentRadioChecked = A.one('#<portlet:namespace />allContent').attr('checked');

			if (allContentRadioChecked) {
				var selectedContents = A.one('#<portlet:namespace />selectContents');

				var portletDataControlDefault = A.one('#<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>');

				portletDataControlDefault.val(true);
			}

			submitForm(form, form.attr('action'), false);
		}
	);

	var clickHandler = function(event) {
		var dataValue = event.target.ancestor('li').attr('data-value');

		processDataValue(dataValue);
	};

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

	A.one('#<portlet:namespace />exportConfigurationButtons').delegate('click', clickHandler, 'li a');
</aui:script>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />chooseApplications', '<portlet:namespace />selectApplications', ['<portlet:namespace />showChangeGlobalConfiguration']);
	Liferay.Util.toggleRadio('<portlet:namespace />allApplications', '<portlet:namespace />showChangeGlobalConfiguration', ['<portlet:namespace />selectApplications']);

	Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeDateRange', '<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLast', '<portlet:namespace />rangeLastInputs', ['<portlet:namespace />startEndDate']);
</aui:script>