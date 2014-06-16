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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

if (Validator.isNull(cmd)) {
	cmd = ParamUtil.getString(request, "originalCmd", Constants.PUBLISH_TO_LIVE);
}

String tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");

String closeRedirect = ParamUtil.getString(request, "closeRedirect");

String publishConfigurationButtons = ParamUtil.getString(request, "publishConfigurationButtons", "custom");

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

Group group = (Group)request.getAttribute(WebKeys.GROUP);

Group liveGroup = null;
Group stagingGroup = null;

if (group.isStagingGroup()) {
	liveGroup = group.getLiveGroup();
	stagingGroup = group;
}
else if (group.isStaged()) {
	liveGroup = group;

	if (group.isStagedRemotely()) {
		stagingGroup = group;
	}
	else {
		stagingGroup = group.getStagingGroup();
	}
}

long liveGroupId = 0;

if (liveGroup != null) {
	liveGroupId = liveGroup.getGroupId();
}

long stagingGroupId = 0;

if (stagingGroup != null) {
	stagingGroupId = stagingGroup.getGroupId();
}

long layoutSetBranchId = MapUtil.getLong(parameterMap, "layoutSetBranchId", ParamUtil.getLong(request, "layoutSetBranchId"));
String layoutSetBranchName = MapUtil.getString(parameterMap, "layoutSetBranchName", ParamUtil.getString(request, "layoutSetBranchName"));

boolean localPublishing = true;

if (liveGroup.isStaged()) {
	if (liveGroup.isStagedRemotely()) {
		localPublishing = false;
	}
}
else if (cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
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

String publishActionKey = "copy";

if (liveGroup.isStaged()) {
	publishActionKey = "publish";
}
else if (cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
	publishActionKey = "publish";
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

boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout", tabs1.equals("private-pages"));

treeId = treeId + privateLayout + layoutSetBranchId;

long[] selectedLayoutIds = GetterUtil.getLongValues(StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode"), ','));

List<Layout> selectedLayouts = new ArrayList<Layout>();

long selectedLayoutsGroupId = group.getGroupId();

if (stagingGroupId > 0) {
	selectedLayoutsGroupId = stagingGroupId;
}

for (int i = 0; i < selectedLayoutIds.length; i++) {
	try {
		selectedLayouts.add(LayoutLocalServiceUtil.getLayout(selectedLayoutsGroupId, privateLayout, selectedLayoutIds[i]));
	}
	catch (NoSuchLayoutException nsle) {
	}
}

UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

PortletURL portletURL = renderResponse.createActionURL();

if (group.isStaged() && group.isStagedRemotely()) {
	cmd = Constants.PUBLISH_TO_REMOTE;
}

portletURL.setParameter("struts_action", "/layouts_admin/publish_layouts");
portletURL.setParameter("closeRedirect", closeRedirect);
portletURL.setParameter("groupId", String.valueOf(liveGroupId));
portletURL.setParameter("stagingGroupId", String.valueOf(stagingGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));

PortletURL renderURL = renderResponse.createRenderURL();

renderURL.setParameter("struts_action", "/layouts_admin/publish_layouts");

if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
	renderURL.setParameter("tabs2", "new-publication-process");
	renderURL.setParameter("publishConfigurationButtons", "saved");
}
else {
	renderURL.setParameter("tabs2", "current-and-previous");
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

<%
String tabs2Names = StringPool.BLANK;

if (cmd.equals("view_processes")) {
	tabs2Names = "current-and-previous";
}
else {
	tabs2Names = "new-publication-process,current-and-previous,scheduled";
}
%>

<liferay-ui:trash-undo />

<liferay-ui:tabs
	names="<%= tabs2Names %>"
	param="tabs2"
	refresh="<%= false %>"
>
	<c:if test='<%= !cmd.equals("view_processes") %>'>
		<liferay-ui:section>
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
					</aui:nav>
				</aui:nav-bar>
			</div>

			<div <%= publishConfigurationButtons.equals("custom") ? StringPool.BLANK : "class=\"hide\"" %> id="<portlet:namespace />customConfiguration">
				<portlet:actionURL var="updatePublishConfigurationURL">
					<portlet:param name="struts_action" value="/layouts_admin/edit_publish_configuration" />
					<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
					<portlet:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
				</portlet:actionURL>

				<aui:form action='<%= (cmd.equals(Constants.PUBLISH_TO_LIVE) || cmd.equals(Constants.PUBLISH_TO_REMOTE)) ? portletURL.toString() : updatePublishConfigurationURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="exportPagesFm" onSubmit='<%= (cmd.equals(Constants.PUBLISH_TO_LIVE) || cmd.equals(Constants.PUBLISH_TO_REMOTE)) ? "event.preventDefault(); " + renderResponse.getNamespace() + "publishPages();" : StringPool.BLANK %>' >
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
					<aui:input name="<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>" type="hidden" value="<%= true %>"  />
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

					<%@ include file="/html/portlet/layouts_admin/error_auth_exception.jspf" %>

					<%@ include file="/html/portlet/layouts_admin/error_remote_export_exception.jspf" %>

					<%@ include file="/html/portlet/layouts_admin/error_remote_options_exception.jspf" %>

					<liferay-ui:error exception="<%= SystemException.class %>">

						<%
						SystemException se = (SystemException)errorException;
						%>

						<liferay-ui:message key="<%= se.getMessage() %>" />
					</liferay-ui:error>

					<c:if test="<%= !cmd.equals(Constants.PUBLISH_TO_LIVE) && !cmd.equals(Constants.PUBLISH_TO_REMOTE) %>">
						<aui:model-context bean="<%= exportImportConfiguration %>" model="<%= ExportImportConfiguration.class %>" />

						<aui:fieldset cssClass="options-group" label='<%= cmd.equals(Constants.ADD) ? "new-publish-template" : "edit-template" %>'>
							<aui:input label="name" name="name" showRequiredLabel="<%= false %>">
								<aui:validator name="required" />
							</aui:input>

							<aui:input label="description" name="description" />
						</aui:fieldset>
					</c:if>

					<div id="<portlet:namespace />publishOptions">
						<div class="export-dialog-tree">

							<%
							String taskExecutorClassName = localPublishing ? LayoutStagingBackgroundTaskExecutor.class.getName() : LayoutRemoteStagingBackgroundTaskExecutor.class.getName();

							int incompleteBackgroundTaskCount = BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(stagingGroupId, taskExecutorClassName, false);

							incompleteBackgroundTaskCount += BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(liveGroupId, taskExecutorClassName, false);
							%>

							<div class="<%= incompleteBackgroundTaskCount == 0 ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
								<liferay-util:include page="/html/portlet/layouts_admin/incomplete_processes_message.jsp">
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
									<%@ include file="/html/portlet/layouts_admin/publish_layouts_scheduler.jspf" %>
								</aui:fieldset>
							</c:if>

							<c:if test="<%= !group.isCompany() %>">
								<aui:fieldset cssClass="options-group" label="pages">

									<%
									request.setAttribute("select_pages.jsp-parameterMap", parameterMap);
									%>

									<liferay-util:include page="/html/portlet/layouts_admin/export_configuration/select_pages.jsp">
										<liferay-util:param name="<%= Constants.CMD %>" value="<%= Constants.PUBLISH %>" />
										<liferay-util:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
										<liferay-util:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
										<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
										<liferay-util:param name="treeId" value="<%= treeId %>" />
										<liferay-util:param name="selectedLayoutIds" value="<%= StringUtil.merge(selectedLayoutIds) %>" />
									</liferay-util:include>
								</aui:fieldset>
							</c:if>

							<%
							List<Portlet> dataSiteLevelPortlets = LayoutExporter.getDataSiteLevelPortlets(company.getCompanyId(), false);
							%>

							<c:if test="<%= !dataSiteLevelPortlets.isEmpty() %>">
								<aui:fieldset cssClass="options-group" label="content">
									<%@ include file="/html/portlet/layouts_admin/publish_layouts_portlets_data.jspf" %>
								</aui:fieldset>
							</c:if>

							<aui:fieldset cssClass="options-group" label="permissions">
								<%@ include file="/html/portlet/layouts_admin/export_configuration/permissions.jspf" %>
							</aui:fieldset>

							<c:if test="<%= !localPublishing %>">
								<aui:fieldset cssClass="options-group" label="remote-live-connection-settings">
									<%@ include file="/html/portlet/layouts_admin/publish_layouts_remote_options.jspf" %>
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
									<aui:button id="addButton" name="addButton" onClick='<%= renderResponse.getNamespace() + "schedulePublishEvent();" %>' value="add-event" />

									<aui:button id="publishButton" name="publishButton" type="submit" value="<%= publishActionKey %>" />
								</c:otherwise>
							</c:choose>
						</aui:button-row>
					</div>
				</aui:form>
			</div>

			<div <%= publishConfigurationButtons.equals("saved") ? StringPool.BLANK : "class=\"hide\"" %> id="<portlet:namespace />savedConfigurations">
				<liferay-util:include page="/html/portlet/layouts_admin/publish_layouts_configurations.jsp">
					<liferay-util:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
					<liferay-util:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
					<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				</liferay-util:include>
			</div>
		</liferay-ui:section>
	</c:if>

	<liferay-ui:section>
		<div class="process-list" id="<portlet:namespace />publishProcesses">
			<liferay-util:include page="/html/portlet/layouts_admin/publish_layouts_processes.jsp">
				<liferay-util:param name="closeRedirect" value="<%= closeRedirect %>" />
				<liferay-util:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
				<liferay-util:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
				<liferay-util:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
			</liferay-util:include>
		</div>
	</liferay-ui:section>

	<c:if test='<%= !cmd.equals("view_processes") %>'>
		<liferay-ui:section>

			<%
			long targetGroupId = liveGroupId;

			if (cmd.equals("copy_from_live")) {
				targetGroupId = stagingGroupId;
			}
			%>

			<liferay-util:include page="/html/portlet/layouts_admin/scheduled_publishing_events.jsp">
				<liferay-util:param name="groupId" value="<%= String.valueOf(targetGroupId) %>" />
				<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<liferay-util:param name="destinationName" value="<%= localPublishing ? DestinationNames.LAYOUTS_LOCAL_PUBLISHER : DestinationNames.LAYOUTS_REMOTE_PUBLISHER %>" />
			</liferay-util:include>
		</liferay-ui:section>
	</c:if>

</liferay-ui:tabs>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />publishPages',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-" + publishActionKey + "-these-pages") %>')) {
				var A = AUI();

				var allContentRadioChecked = A.one('#<portlet:namespace />allContent').attr('checked');

				if (allContentRadioChecked) {
					var selectedContents = A.one('#<portlet:namespace />selectContents');

					var portletDataControlDefault = A.one('#<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>');

					portletDataControlDefault.val(true);
				}

				submitForm(document.<portlet:namespace />exportPagesFm);
			}
		},
		['aui-base']
	);

	Liferay.Util.toggleRadio('<portlet:namespace />allApplications', '<portlet:namespace />showChangeGlobalConfiguration', ['<portlet:namespace />selectApplications']);
	Liferay.Util.toggleRadio('<portlet:namespace />allContent', '<portlet:namespace />showChangeGlobalContent', ['<portlet:namespace />selectContents']);
	Liferay.Util.toggleRadio('<portlet:namespace />chooseApplications', '<portlet:namespace />selectApplications', ['<portlet:namespace />showChangeGlobalConfiguration']);
	Liferay.Util.toggleRadio('<portlet:namespace />chooseContent', '<portlet:namespace />selectContents', ['<portlet:namespace />showChangeGlobalContent']);
	Liferay.Util.toggleRadio('<portlet:namespace />publishingEventNow', '<portlet:namespace />publishButton', ['<portlet:namespace />selectSchedule', '<portlet:namespace />addButton']);
	Liferay.Util.toggleRadio('<portlet:namespace />publishingEventSchedule', ['<portlet:namespace />selectSchedule', '<portlet:namespace />addButton'], '<portlet:namespace />publishButton');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeDateRange', '<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLastPublish', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLast', '<portlet:namespace />rangeLastInputs', ['<portlet:namespace />startEndDate']);
</aui:script>

<aui:script use="liferay-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="publishProcessesURL">
		<portlet:param name="struts_action" value="/layouts_admin/publish_layouts" />
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="closeRedirect" value="<%= closeRedirect %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
		<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
		<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
		<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
		<portlet:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport(
		{
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
			deleteMissingLayoutsNode: '#<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>',
			deletePortletDataNode: '#<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>',
			deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
			form: document.<portlet:namespace />exportPagesFm,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			layoutSetSettingsNode: '#<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>',
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
			remoteDeletePortletDataNode: '#remoteDeletePortletData',
			remotePortNode: '#<portlet:namespace />remotePort',
			remotePathContextNode: '#<portlet:namespace />remotePathContext',
			remoteGroupIdNode: '#<portlet:namespace />remoteGroupId',
			secureConnectionNode: '#secureConnection',
			setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>',
			themeReferenceNode: '#<%= PortletDataHandlerKeys.THEME_REFERENCE %>',
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

	A.one('#<portlet:namespace />publishConfigurationButtons').delegate('click', clickHandler, 'li a');
</aui:script>