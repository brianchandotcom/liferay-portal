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
	cmd = ParamUtil.getString(request, "originalCmd", Constants.PUBLISH_TO_LIVE);
}

String tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");

String closeRedirect = ParamUtil.getString(request, "closeRedirect");

String publishConfigurationButtons = ParamUtil.getString(request, "publishConfigurationButtons", "custom");

boolean quickPublish = ParamUtil.getBoolean(request, "quickPublish");

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

long layoutSetBranchId = MapUtil.getLong(parameterMap, "layoutSetBranchId", ParamUtil.getLong(request, "layoutSetBranchId"));
String layoutSetBranchName = MapUtil.getString(parameterMap, "layoutSetBranchName", ParamUtil.getString(request, "layoutSetBranchName"));

boolean localPublishing = true;

if ((liveGroup.isStaged() && liveGroup.isStagedRemotely()) || cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
	localPublishing = false;
}

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

String publishActionKey = "publish-to-live";

if (cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
	publishActionKey = "publish-to-remote-live";
}

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

Layout selLayout = null;

try {
	selLayout = LayoutLocalServiceUtil.getLayout(selPlid);

	if (selLayout.isPrivateLayout()) {
		tabs1 = "private-pages";
	}
}
catch (NoSuchLayoutException nsle) {
}

treeId = treeId + privateLayout + layoutSetBranchId;

long[] selectedLayoutIds = null;

String openNodes = SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode");

if (openNodes == null) {
	selectedLayoutIds = ExportImportHelperUtil.getAllLayoutIds(stagingGroupId, privateLayout);
}
else {
	selectedLayoutIds = GetterUtil.getLongValues(StringUtil.split(openNodes, ','));
}

UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

PortletURL portletURL = renderResponse.createActionURL();

if (group.isStaged() && group.isStagedRemotely()) {
	cmd = Constants.PUBLISH_TO_REMOTE;
}

portletURL.setParameter(ActionRequest.ACTION_NAME, "publishLayouts");
portletURL.setParameter("mvcRenderCommandName", "publishLayouts");
portletURL.setParameter("closeRedirect", closeRedirect);
portletURL.setParameter("groupId", String.valueOf(stagingGroupId));
portletURL.setParameter("stagingGroupId", String.valueOf(stagingGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));

PortletURL renderURL = renderResponse.createRenderURL();

renderURL.setParameter("mvcRenderCommandName", "publishLayouts");

if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
	renderURL.setParameter("publishConfigurationButtons", "saved");
}

renderURL.setParameter("closeRedirect", closeRedirect);
renderURL.setParameter("groupId", String.valueOf(stagingGroupId));
renderURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
renderURL.setParameter("layoutSetBranchName", layoutSetBranchName);
renderURL.setParameter("privateLayout", String.valueOf(privateLayout));

response.setHeader("Ajax-ID", request.getHeader("Ajax-ID"));
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

<portlet:actionURL name="editExportConfiguration" var="restoreTrashEntriesURL">
	<portlet:param name="mvcPath" value="editExportConfiguration" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<c:if test='<%= !cmd.equals("view_processes") && !quickPublish %>'>
	<div <%= (!cmd.equals(Constants.ADD) && !cmd.equals(Constants.UPDATE)) ? StringPool.BLANK : "class=\"hide\"" %>>
		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav" id="publishConfigurationButtons">
				<aui:nav-item
					data-value="custom"
					iconCssClass="icon-puzzle"
					label="custom"
				/>

				<aui:nav-item
					data-value="saved"
					iconCssClass="icon-archive"
					label="publish-templates"
				/>

				<portlet:renderURL var="simplePublishRedirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
					<portlet:param name="quickPublish" value="<%= Boolean.TRUE.toString() %>" />
				</portlet:renderURL>

				<portlet:renderURL var="simplePublishURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="publishLayoutsSimple" />
					<portlet:param name="redirect" value="<%= simplePublishRedirectURL %>" />
					<portlet:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
					<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
					<portlet:param name="quickPublish" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="remoteAddress" value='<%= liveGroupTypeSettings.getProperty("remoteAddress") %>' />
					<portlet:param name="remotePort" value='<%= liveGroupTypeSettings.getProperty("remotePort") %>' />
					<portlet:param name="remotePathContext" value='<%= liveGroupTypeSettings.getProperty("remotePathContext") %>' />
					<portlet:param name="remoteGroupId" value='<%= liveGroupTypeSettings.getProperty("remoteGroupId") %>' />
					<portlet:param name="secureConnection" value='<%= liveGroupTypeSettings.getProperty("secureConnection") %>' />
					<portlet:param name="sourceGroupId" value="<%= String.valueOf(stagingGroupId) %>" />
					<portlet:param name="targetGroupId" value="<%= String.valueOf(liveGroupId) %>" />
				</portlet:renderURL>

				<aui:nav-item
					href="<%= simplePublishURL %>"
					iconCssClass="icon-rocket"
					label="switch-to-simple-publication"
				/>
			</aui:nav>
		</aui:nav-bar>
	</div>

	<div <%= publishConfigurationButtons.equals("custom") ? StringPool.BLANK : "class=\"hide\"" %> id="<portlet:namespace />customConfiguration">
		<portlet:actionURL name="editPublishConfiguration" var="updatePublishConfigurationURL">
			<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
			<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
			<portlet:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
		</portlet:actionURL>

		<aui:form action='<%= (cmd.equals(Constants.PUBLISH_TO_LIVE) || cmd.equals(Constants.PUBLISH_TO_REMOTE)) ? portletURL.toString() : updatePublishConfigurationURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="exportPagesFm" onSubmit='<%= (cmd.equals(Constants.PUBLISH_TO_LIVE) || cmd.equals(Constants.PUBLISH_TO_REMOTE)) ? "event.preventDefault(); " + renderResponse.getNamespace() + "publishPages();" : StringPool.BLANK %>'>
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
			<aui:input name="originalCmd" type="hidden" value="<%= cmd %>" />
			<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
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

			<liferay-ui:error exception="<%= DuplicateLockException.class %>" message="another-publishing-process-is-in-progress,-please-try-again-later" />

			<liferay-ui:error exception="<%= LayoutPrototypeException.class %>">

				<%
				LayoutPrototypeException lpe = (LayoutPrototypeException)errorException;
				%>

				<liferay-ui:message key="the-pages-could-not-be-published-because-one-or-more-required-page-templates-could-not-be-found-on-the-remote-system.-please-import-the-following-templates-manually" />

				<ul>

					<%
					List<Tuple> missingLayoutPrototypes = lpe.getMissingLayoutPrototypes();

					for (Tuple missingLayoutPrototype : missingLayoutPrototypes) {
						String layoutPrototypeClassName = (String)missingLayoutPrototype.getObject(0);
						String layoutPrototypeUuid = (String)missingLayoutPrototype.getObject(1);
						String layoutPrototypeName = (String)missingLayoutPrototype.getObject(2);
					%>

					<li>
						<%= ResourceActionsUtil.getModelResource(locale, layoutPrototypeClassName) %>: <strong><%= HtmlUtil.escape(layoutPrototypeName) %></strong> (<%= HtmlUtil.escape(layoutPrototypeUuid) %>)
					</li>

					<%
					}
					%>

				</ul>
			</liferay-ui:error>

			<%@ include file="/error_auth_exception.jspf" %>

			<%@ include file="/error_remote_export_exception.jspf" %>

			<%@ include file="/error_remote_options_exception.jspf" %>

			<liferay-ui:error exception="<%= SystemException.class %>">

				<%
				SystemException se = (SystemException)errorException;
				%>

				<liferay-ui:message key="<%= se.getMessage() %>" />
			</liferay-ui:error>

			<c:if test="<%= !cmd.equals(Constants.PUBLISH_TO_LIVE) && !cmd.equals(Constants.PUBLISH_TO_REMOTE) %>">
				<liferay-staging:configuration-header exportImportConfiguration="<%= exportImportConfiguration %>" label='<%= cmd.equals(Constants.ADD) ? "new-publish-template" : "edit-template" %>' />
			</c:if>

			<div id="<portlet:namespace />publishOptions">
				<div class="export-dialog-tree">

					<%
					String taskExecutorClassName = localPublishing ? BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR : BackgroundTaskExecutorNames.LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR;

					int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(stagingGroupId, taskExecutorClassName, false);

					incompleteBackgroundTaskCount += BackgroundTaskManagerUtil.getBackgroundTasksCount(liveGroupId, taskExecutorClassName, false);
					%>

					<div class="<%= incompleteBackgroundTaskCount == 0 ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
						<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
							<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
						</liferay-util:include>
					</div>

					<c:if test="<%= !cmd.equals(Constants.ADD) && !cmd.equals(Constants.UPDATE) %>">

						<%
						String scheduleCMD = StringPool.BLANK;
						String unscheduleCMD = StringPool.BLANK;

						if (cmd.equals(Constants.PUBLISH_TO_LIVE)) {
							scheduleCMD = "schedule_publish_to_live";
							unscheduleCMD = "unschedule_publish_to_live";
						}
						else if (cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
							scheduleCMD = "schedule_publish_to_remote";
							unscheduleCMD = "unschedule_publish_to_remote";
						}
						else if (cmd.equals("copy_from_live")) {
							scheduleCMD = "schedule_copy_from_live";
							unscheduleCMD = "unschedule_copy_from_live";
						}
						%>

						<aui:fieldset cssClass="options-group" label="date">
							<%@ include file="/publish_layouts_scheduler.jspf" %>
						</aui:fieldset>
					</c:if>

					<c:if test="<%= !group.isCompany() %>">
						<aui:fieldset cssClass="options-group" label="pages">

							<%
							request.setAttribute("select_pages.jsp-parameterMap", parameterMap);
							%>

							<liferay-util:include page="/select_pages.jsp" servletContext="<%= application %>">
								<liferay-util:param name="<%= Constants.CMD %>" value="<%= Constants.PUBLISH %>" />
								<liferay-util:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
								<liferay-util:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
								<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
								<liferay-util:param name="treeId" value="<%= treeId %>" />
								<liferay-util:param name="selectedLayoutIds" value="<%= StringUtil.merge(selectedLayoutIds) %>" />
							</liferay-util:include>
						</aui:fieldset>
					</c:if>

					<liferay-staging:content cmd="<%= cmd %>" parameterMap="<%= parameterMap %>" type="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />

					<c:if test="<%= !quickPublish %>">
						<liferay-staging:deletions cmd="<%= Constants.PUBLISH %>" />
					</c:if>

					<aui:fieldset cssClass="options-group" label="permissions">
						<%@ include file="/permissions.jspf" %>
					</aui:fieldset>

					<c:if test="<%= !localPublishing %>">
						<aui:fieldset cssClass="options-group" label="remote-live-connection-settings">
							<%@ include file="/publish_layouts_remote_options.jspf" %>
						</aui:fieldset>
					</c:if>
				</div>

				<aui:button-row>
					<c:choose>
						<c:when test="<%= cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE) %>">
							<aui:button type="submit" value="save" />

							<aui:button href="<%= renderURL.toString() %>" type="reset" value="cancel" />
						</c:when>
						<c:otherwise>
							<aui:button id="addButton" onClick='<%= renderResponse.getNamespace() + "schedulePublishEvent();" %>' value="add-event" />

							<aui:button id="publishButton" type="submit" value="<%= publishActionKey %>" />
						</c:otherwise>
					</c:choose>
				</aui:button-row>
			</div>
		</aui:form>
	</div>

	<div <%= publishConfigurationButtons.equals("saved") ? StringPool.BLANK : "class=\"hide\"" %> id="<portlet:namespace />savedConfigurations">
		<liferay-util:include page="/publish_layouts_configurations.jsp" servletContext="<%= application %>">
			<liferay-util:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
			<liferay-util:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
			<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
		</liferay-util:include>
	</div>
</c:if>

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
		<portlet:param name="closeRedirect" value="<%= closeRedirect %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
		<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
		<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
		<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
		<portlet:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
		<portlet:param name="quickPublish" value="<%= String.valueOf(quickPublish) %>" />
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

	var publishConfigurationButtons = A.one('#<portlet:namespace />publishConfigurationButtons');

	if (publishConfigurationButtons) {
		publishConfigurationButtons.delegate('click', clickHandler, 'li a');
	}
</aui:script>