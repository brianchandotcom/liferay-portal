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
String cmd = ParamUtil.getString(request, Constants.CMD, Constants.PUBLISH_TO_LIVE);

String tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");

String closeRedirect = ParamUtil.getString(request, "closeRedirect");

long exportImportConfigurationId = ParamUtil.getLong(request, "exportImportConfigurationId");

ExportImportConfiguration exportImportConfiguration = null;

Map<String, Serializable> exportImportConfigurationSettingsMap = Collections.emptyMap();

Map<String, String[]> parameterMap = Collections.emptyMap();

if (exportImportConfigurationId > 0) {
	exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

	exportImportConfigurationSettingsMap = exportImportConfiguration.getSettingsMap();

	parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
}

boolean configuredPublish = (exportImportConfiguration == null) ? false : true;

long layoutSetBranchId = MapUtil.getLong(parameterMap, "layoutSetBranchId", ParamUtil.getLong(request, "layoutSetBranchId"));
String layoutSetBranchName = MapUtil.getString(parameterMap, "layoutSetBranchName", ParamUtil.getString(request, "layoutSetBranchName"));

boolean localPublishing = ParamUtil.getBoolean(request, "localPublishing");

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

String publishMessageKey = "publish-to-live";

if (cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
	publishMessageKey = "publish-to-remote-live";
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

if (configuredPublish) {
	privateLayout = MapUtil.getBoolean(exportImportConfigurationSettingsMap, "privateLayout", privateLayout);
}

treeId = treeId + privateLayout + layoutSetBranchId;

PortletURL portletURL = renderResponse.createActionURL();

if (group.isStaged() && group.isStagedRemotely()) {
	cmd = Constants.PUBLISH_TO_REMOTE;
}

portletURL.setParameter(ActionRequest.ACTION_NAME, "publishLayouts");
portletURL.setParameter("mvcRenderCommandName", "publishLayouts");
portletURL.setParameter("closeRedirect", closeRedirect);
portletURL.setParameter("stagingGroupId", String.valueOf(stagingGroupId));

PortletURL redirectURL = renderResponse.createRenderURL();

redirectURL.setParameter("mvcRenderCommandName", "publishLayouts");
redirectURL.setParameter("closeRedirect", closeRedirect);
redirectURL.setParameter("groupId", String.valueOf(stagingGroupId));
redirectURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
redirectURL.setParameter("layoutSetBranchName", layoutSetBranchName);
redirectURL.setParameter("privateLayout", String.valueOf(privateLayout));

response.setHeader("Ajax-ID", request.getHeader("Ajax-ID"));
%>

<div class="container-fluid-1280">
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

	<aui:form action='<%= portletURL.toString() + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="publishPagesFm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "publishPages();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
		<aui:input name="originalCmd" type="hidden" value="<%= cmd %>" />
		<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirectURL.toString() %>" />
		<aui:input name="exportImportConfigurationId" type="hidden" value="<%= exportImportConfigurationId %>" />
		<aui:input name="groupId" type="hidden" value="<%= stagingGroupId %>" />
		<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
		<aui:input name="layoutSetBranchName" type="hidden" value="<%= layoutSetBranchName %>" />
		<aui:input name="lastImportUserName" type="hidden" value="<%= user.getFullName() %>" />
		<aui:input name="lastImportUserUuid" type="hidden" value="<%= String.valueOf(user.getUserUuid()) %>" />
		<aui:input name="treeId" type="hidden" value="<%= treeId %>" />
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

		<%@ include file="/publish/error/error_auth_exception.jspf" %>

		<%@ include file="/publish/error/error_remote_export_exception.jspf" %>

		<%@ include file="/publish/error/error_remote_options_exception.jspf" %>

		<liferay-ui:error exception="<%= SystemException.class %>">

			<%
			SystemException se = (SystemException)errorException;
			%>

			<liferay-ui:message key="<%= se.getMessage() %>" />
		</liferay-ui:error>

		<div id="<portlet:namespace />publishOptions">
			<div class="export-dialog-tree">
				<div class="container-fluid-1280">

					<%
					String taskExecutorClassName = localPublishing ? BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR : BackgroundTaskExecutorNames.LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR;

					int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(stagingGroupId, taskExecutorClassName, false);

					if (localPublishing) {
						incompleteBackgroundTaskCount += BackgroundTaskManagerUtil.getBackgroundTasksCount(liveGroupId, taskExecutorClassName, false);
					}
					%>

					<div class="<%= (incompleteBackgroundTaskCount == 0) ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
						<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
							<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
						</liferay-util:include>
					</div>

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

					<aui:fieldset-group markupView="lexicon">
						<aui:fieldset>
							<c:choose>
								<c:when test="<%= exportImportConfiguration == null %>">
									<aui:input maxlength='<%= ModelHintsUtil.getMaxLength(ExportImportConfiguration.class.getName(), "name") %>' name="name" placeholder="process-name-placeholder" />
								</c:when>
								<c:otherwise>
									<aui:input maxlength='<%= ModelHintsUtil.getMaxLength(ExportImportConfiguration.class.getName(), "name") %>' name="name" value="<%= exportImportConfiguration.getName() %>" />
								</c:otherwise>
							</c:choose>
						</aui:fieldset>

						<aui:fieldset cssClass="options-group">
							<div class="sheet-section">
								<h3 class="sheet-subtitle"><liferay-ui:message key="date" /></h3>

								<%@ include file="/publish/publish_layouts_scheduler.jspf" %>
							</div>
						</aui:fieldset>

						<liferay-staging:deletions
							cmd="<%= Constants.PUBLISH %>"
							disableInputs="<%= configuredPublish %>"
							exportImportConfigurationId="<%= exportImportConfigurationId %>"
						/>

						<c:if test="<%= !group.isCompany() %>">
							<liferay-staging:select-pages
								action="<%= Constants.PUBLISH %>"
								disableInputs="<%= configuredPublish %>"
								exportImportConfigurationId="<%= exportImportConfigurationId %>"
								groupId="<%= groupId %>"
								privateLayout="<%= privateLayout %>"
								treeId="<%= treeId %>"
							/>
						</c:if>

						<liferay-staging:content
							cmd="<%= cmd %>"
							disableInputs="<%= configuredPublish %>"
							exportImportConfigurationId="<%= exportImportConfigurationId %>"
							type="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>"
						/>

						<liferay-staging:permissions
							action="<%= Constants.PUBLISH %>"
							descriptionCSSClass="permissions-description"
							disableInputs="<%= configuredPublish %>"
							exportImportConfigurationId="<%= exportImportConfigurationId %>"
							global="<%= group.isCompany() %>"
							labelCSSClass="permissions-label"
						/>

						<c:if test="<%= !localPublishing %>">
							<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" cssClass="options-group" label="remote-live-connection-settings">
								<liferay-staging:remote-options
									disableInputs="<%= configuredPublish %>"
									exportImportConfigurationId="<%= exportImportConfigurationId %>"
									privateLayout="<%= privateLayout %>"
								/>
							</aui:fieldset>
						</c:if>
					</aui:fieldset-group>
				</div>
			</div>

			<aui:button-row>
				<aui:button id="addButton" onClick='<%= renderResponse.getNamespace() + "schedulePublishEvent();" %>' value="add-event" />

				<aui:button id="publishButton" type="submit" value="<%= LanguageUtil.get(request, publishMessageKey) %>" />

				<c:if test="<%= configuredPublish %>">
					<aui:button cssClass="btn btn-link" href="<%= redirectURL.toString() %>" id="cancelButton" value="cancel" />
				</c:if>
			</aui:button-row>
		</div>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />publishPages() {
		var exportImport = Liferay.component(
			'<portlet:namespace />ExportImportComponent'
		);

		var dateChecker = exportImport.getDateRangeChecker();

		if (dateChecker.validRange) {
			submitForm(document.<portlet:namespace />publishPagesFm);
		} else {
			exportImport.showNotification(dateChecker);
		}
	}

	Liferay.Util.toggleRadio(
		'<portlet:namespace />allApplications',
		'<portlet:namespace />showChangeGlobalConfiguration',
		['<portlet:namespace />selectApplications']
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />allContent',
		'<portlet:namespace />showChangeGlobalContent',
		['<portlet:namespace />selectContents']
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />publishingEventNow',
		'<portlet:namespace />publishButton',
		['<portlet:namespace />selectSchedule', '<portlet:namespace />addButton']
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />publishingEventSchedule',
		['<portlet:namespace />selectSchedule', '<portlet:namespace />addButton'],
		'<portlet:namespace />publishButton'
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
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLastPublish', '', [
		'<portlet:namespace />startEndDate',
		'<portlet:namespace />rangeLastInputs'
	]);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />rangeLast',
		'<portlet:namespace />rangeLastInputs',
		['<portlet:namespace />startEndDate']
	);
</aui:script>

<aui:script use="liferay-export-import-export-import">
	var exportImport = new Liferay.ExportImport({
		commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
		deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
		form: document.<portlet:namespace />publishPagesFm,
		incompleteProcessMessageNode:
			'#<portlet:namespace />incompleteProcessMessage',
		locale: '<%= locale.toLanguageTag() %>',
		namespace: '<portlet:namespace />',
		pageTreeId: '<%= treeId %>',
		processesNode: '#publishProcesses',
		rangeAllNode: '#rangeAll',
		rangeDateRangeNode: '#rangeDateRange',
		rangeLastNode: '#rangeLast',
		rangeLastPublishNode: '#rangeLastPublish',
		ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
		setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>',
		timeZoneOffset: <%= timeZoneOffset %>,
		userPreferencesNode:
			'#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>'
	});

	Liferay.component('<portlet:namespace />ExportImportComponent', exportImport);
</aui:script>