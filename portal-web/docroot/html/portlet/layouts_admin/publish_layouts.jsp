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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

if (Validator.isNull(cmd)) {
	cmd = ParamUtil.getString(request, "originalCmd", "publish_to_live");
}

String tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");

String closeRedirect = ParamUtil.getString(request, "closeRedirect");

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group liveGroup = null;
Group stagingGroup = null;

int pagesCount = 0;

if (selGroup.isStagingGroup()) {
	liveGroup = selGroup.getLiveGroup();
	stagingGroup = selGroup;
}
else if (selGroup.isStaged()) {
	liveGroup = selGroup;

	if (selGroup.isStagedRemotely()) {
		stagingGroup = selGroup;
	}
	else {
		stagingGroup = selGroup.getStagingGroup();
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

long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");
String layoutSetBranchName = ParamUtil.getString(request, "layoutSetBranchName");

boolean localPublishing = true;

if (liveGroup.isStaged()) {
	if (liveGroup.isStagedRemotely()) {
		localPublishing = false;
	}
}
else if (cmd.equals("publish_to_remote")) {
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
else if (cmd.equals("publish_to_remote")) {
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

long selectedLayoutsGroupId = selGroup.getGroupId();

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

if (privateLayout) {
	pagesCount = selGroup.getPrivateLayoutsPageCount();
}
else {
	pagesCount = selGroup.getPublicLayoutsPageCount();
}

UnicodeProperties groupTypeSettings = selGroup.getTypeSettingsProperties();
UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

Organization organization = null;
User user2 = null;

if (liveGroup.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(liveGroup.getOrganizationId());
}
else if (liveGroup.isUser()) {
	user2 = UserLocalServiceUtil.getUserById(liveGroup.getClassPK());
}

String rootNodeName = liveGroup.getDescriptiveName(locale);

if (liveGroup.isOrganization()) {
	rootNodeName = organization.getName();
}
else if (liveGroup.isUser()) {
	rootNodeName = user2.getFullName();
}

PortletURL portletURL = renderResponse.createActionURL();

if (selGroup.isStaged() && selGroup.isStagedRemotely()) {
	cmd = "publish_to_remote";
}

portletURL.setParameter("struts_action", "/layouts_admin/publish_layouts");
portletURL.setParameter("closeRedirect", closeRedirect);
portletURL.setParameter("groupId", String.valueOf(liveGroupId));
portletURL.setParameter("stagingGroupId", String.valueOf(stagingGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));

PortletURL renderURL = renderResponse.createRenderURL();

renderURL.setParameter("struts_action", "/layouts_admin/publish_layouts");
renderURL.setParameter("tabs2", "current-and-previous");
renderURL.setParameter("closeRedirect", closeRedirect);
renderURL.setParameter("groupId", String.valueOf(stagingGroupId));
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

<liferay-ui:tabs
	names="<%= tabs2Names %>"
	param="tabs2"
	refresh="<%= false %>"
>
	<c:if test='<%= !cmd.equals("view_processes") %>'>
		<liferay-ui:section>
			<aui:form action='<%= portletURL.toString() + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="exportPagesFm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "publishPages();" %>' >
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
				<aui:input name="originalCmd" type="hidden" value="<%= cmd %>" />
				<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
				<aui:input name="redirect" type="hidden" value="<%= renderURL.toString() %>" />
				<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
				<aui:input name="layoutSetBranchName" type="hidden" value="<%= layoutSetBranchName %>" />
				<aui:input name="lastImportUserName" type="hidden" value="<%= user.getFullName() %>" />
				<aui:input name="lastImportUserUuid" type="hidden" value="<%= String.valueOf(user.getUserUuid()) %>" />

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

				<div id="<portlet:namespace />publishOptions">
					<div class="export-dialog-tree">

						<%
						String scheduleCMD = StringPool.BLANK;
						String unscheduleCMD = StringPool.BLANK;

						if (cmd.equals("copy_from_live")) {
							scheduleCMD = "schedule_copy_from_live";
							unscheduleCMD = "unschedule_copy_from_live";
						}
						else if (cmd.equals("publish_to_live")) {
							scheduleCMD = "schedule_publish_to_live";
							unscheduleCMD = "unschedule_publish_to_live";
						}
						else if (cmd.equals("publish_to_remote")) {
							scheduleCMD = "schedule_publish_to_remote";
							unscheduleCMD = "unschedule_publish_to_remote";
						}

						String taskExecutorClassName = localPublishing ? LayoutStagingBackgroundTaskExecutor.class.getName() : LayoutRemoteStagingBackgroundTaskExecutor.class.getName();

						int incompleteBackgroundTaskCount = BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(stagingGroupId, taskExecutorClassName, false);

						incompleteBackgroundTaskCount += BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(liveGroupId, taskExecutorClassName, false);
						%>

						<div class="<%= incompleteBackgroundTaskCount == 0 ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
							<liferay-util:include page="/html/portlet/layouts_admin/incomplete_processes_message.jsp">
								<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
							</liferay-util:include>
						</div>

						<aui:fieldset cssClass="options-group" label="date">
							<%@ include file="/html/portlet/layouts_admin/publish_layouts_scheduler.jspf" %>
						</aui:fieldset>

						<c:if test="<%= !selGroup.isCompany() %>">
							<aui:fieldset cssClass="options-group" label="pages">
								<%@ include file="/html/portlet/layouts_admin/publish_layouts_select_pages.jspf" %>
							</aui:fieldset>
						</c:if>

						<%
						List<Layout> exportLayouts = new ArrayList<Layout>();

						if (selLayout != null) {
							exportLayouts.add(selLayout);
						}
						else if (!selectedLayouts.isEmpty()) {
							exportLayouts = selectedLayouts;
						}
						else {
							exportLayouts = LayoutLocalServiceUtil.getLayouts(selGroup.getGroupId(), privateLayout);
						}

						List<Portlet> portletDataHandlerPortlets = LayoutExporter.getPortletDataHandlerPortlets(exportLayouts);
						%>

						<c:if test="<%= !portletDataHandlerPortlets.isEmpty() %>">
							<aui:fieldset cssClass="options-group" label="application-configuration">
								<%@ include file="/html/portlet/layouts_admin/publish_layouts_portlets_setup.jspf" %>
							</aui:fieldset>
						</c:if>

						<%
						List<Portlet> dataSiteLevelPortlets = LayoutExporter.getDataSiteLevelPortlets(company.getCompanyId());
						%>

						<c:if test="<%= !dataSiteLevelPortlets.isEmpty() %>">
							<aui:fieldset cssClass="options-group" label="content">
								<%@ include file="/html/portlet/layouts_admin/publish_layouts_portlets_data.jspf" %>
							</aui:fieldset>
						</c:if>

						<c:if test="<%= !selGroup.isCompany() %>">
							<aui:fieldset cssClass="options-group" label="permissions">
								<%@ include file="/html/portlet/layouts_admin/publish_layouts_permissions.jspf" %>
							</aui:fieldset>
						</c:if>

						<c:if test="<%= !localPublishing %>">
							<aui:fieldset cssClass="options-group" label="remote-live-connection-settings">
								<%@ include file="/html/portlet/layouts_admin/publish_layouts_remote_options.jspf" %>
							</aui:fieldset>
						</c:if>
					</div>

					<aui:button-row>
						<aui:button id="addButton" name="addButton" onClick='<%= renderResponse.getNamespace() + "schedulePublishEvent();" %>' value="add-event" />

						<aui:button id="publishButton" name="publishButton" type="submit" value="<%= publishActionKey %>" />
					</aui:button-row>
				</div>
			</aui:form>
		</liferay-ui:section>
	</c:if>

	<liferay-ui:section>
		<div class="process-list" id="<portlet:namespace />publishProcesses">
			<liferay-util:include page="/html/portlet/layouts_admin/publish_layouts_processes.jsp">
				<liferay-util:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
				<liferay-util:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
				<liferay-util:param name="closeRedirect" value="<%= closeRedirect %>" />
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

					var checkedNodes = selectedContents.all('input[type=checkbox]');

					checkedNodes.each(
						function(item, index, collection) {
							if (!item.attr('checked')) {
								item.attr('checked', true);

								Liferay.Util.updateCheckboxValue(item);
							}
						}
					);

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
		<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
		<portlet:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport(
		{
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>Checkbox',
			deleteMissingLayoutsNode: '#<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>Checkbox',
			deletePortletDataNode: '#<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>Checkbox',
			deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>Checkbox',
			form: document.<portlet:namespace />exportPagesFm,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			layoutSetSettingsNode: '#<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>Checkbox',
			logoNode: '#<%= PortletDataHandlerKeys.LOGO %>Checkbox',
			namespace: '<portlet:namespace />',
			pageTreeId: '<%= treeId %>',
			processesNode: '#publishProcesses',
			processesResourceURL: '<%= publishProcessesURL.toString() %>',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			rangeLastPublishNode: '#rangeLastPublish',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>Checkbox',
			remoteAddressNode: '#<portlet:namespace />remoteAddress',
			remoteDeletePortletDataNode: '#remoteDeletePortletDataCheckbox',
			remotePortNode: '#<portlet:namespace />remotePort',
			remotePathContextNode: '#<portlet:namespace />remotePathContext',
			remoteGroupIdNode: '#<portlet:namespace />remoteGroupId',
			secureConnectionNode: '#secureConnectionCheckbox',
			setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>Checkbox',
			themeReferenceNode: '#<%= PortletDataHandlerKeys.THEME_REFERENCE %>Checkbox',
			userPreferencesNode: '#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>Checkbox'
		}
	);
</aui:script>