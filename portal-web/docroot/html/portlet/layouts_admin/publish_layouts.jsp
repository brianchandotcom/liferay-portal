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
String cmd = ParamUtil.getString(request, "cmd", "publish_to_live");

String tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");

String closeRedirect = ParamUtil.getString(request, "closeRedirect");

boolean selectPages = ParamUtil.getBoolean(request, "selectPages");
boolean schedule = ParamUtil.getBoolean(request, "schedule");

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group liveGroup = null;
Group stagingGroup = null;

int pagesCount = 0;

if (selGroup.isCompany()) {
	stagingGroup = selGroup;
	liveGroup = selGroup;
}
else if (selGroup.isStagingGroup()) {
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

long selGroupId = selGroup.getGroupId();

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
else if (cmd.equals("publish_to_remote") || selGroup.isCompany()) {
	localPublishing = false;
}

String treeKey = "liveLayoutsTree";

if (liveGroup.isStaged()) {
	if (!liveGroup.isStagedRemotely()) {
		treeKey = "stageLayoutsTree";
	}
	else {
		treeKey = "remoteLayoutsTree";
	}
}

treeKey = treeKey + selGroupId;

String publishActionKey = "copy";

if (liveGroup.isStaged()) {
	publishActionKey = "publish";
}
else if (cmd.equals("publish_to_remote") || selGroup.isCompany()) {
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

long[] selectedLayoutIds = new long[0];

boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout", tabs1.equals("private-pages"));

treeKey = treeKey + privateLayout + layoutSetBranchId;

selectedLayoutIds = GetterUtil.getLongValues(StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeKey + "SelectedNode"), ','));

List results = new ArrayList();

for (int i = 0; i < selectedLayoutIds.length; i++) {
	try {
		results.add(LayoutLocalServiceUtil.getLayout(selGroupId, privateLayout, selectedLayoutIds[i]));
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

portletURL.setParameter("struts_action", "/layouts_admin/edit_layouts");
portletURL.setParameter("closeRedirect", closeRedirect);
portletURL.setParameter("groupId", String.valueOf(liveGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));

PortletURL selectURL = renderResponse.createRenderURL();

selectURL.setParameter("struts_action", "/layouts_admin/publish_layouts");
selectURL.setParameter(Constants.CMD, cmd);
selectURL.setParameter("closeRedirect", closeRedirect);
selectURL.setParameter("groupId", String.valueOf(stagingGroupId));
selectURL.setParameter("selPlid", String.valueOf(selPlid));
selectURL.setParameter("privateLayout", String.valueOf(privateLayout));
selectURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
selectURL.setParameter("selectPages", String.valueOf(!selectPages));
selectURL.setParameter("schedule", String.valueOf(schedule));
selectURL.setWindowState(LiferayWindowState.POP_UP);

request.setAttribute("edit_pages.jsp-groupId", new Long(stagingGroupId));
request.setAttribute("edit_pages.jsp-selPlid", new Long(selPlid));
request.setAttribute("edit_pages.jsp-privateLayout", new Boolean(privateLayout));

request.setAttribute("edit_pages.jsp-rootNodeName", rootNodeName);

request.setAttribute("edit_pages.jsp-portletURL", portletURL);

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

<aui:form action='<%= portletURL.toString() + "&etag=0&strip=0" %>' method="post" name="exportPagesFm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "publishPages();" %>' >
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
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
				<%= ResourceActionsUtil.getModelResource(locale, layoutPrototypeClassName) %>: <strong><%= HtmlUtil.escape(layoutPrototypeName) %></strong> (<%= layoutPrototypeUuid %>)
			</li>

			<%
			}
			%>

		</ul>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= RemoteExportException.class %>">

		<%
		RemoteExportException ree = (RemoteExportException)errorException;
		%>

		<c:if test="<%= ree.getType() == RemoteExportException.BAD_CONNECTION %>">
			<liferay-ui:message arguments="<%= ree.getURL() %>" key="there-was-a-bad-connection-with-the-remote-server-at-x" />
		</c:if>

		<c:if test="<%= ree.getType() == RemoteExportException.NO_GROUP %>">
			<liferay-ui:message arguments="<%= ree.getGroupId() %>" key="no-site-exists-on-the-remote-server-with-site-id-x" />
		</c:if>

		<c:if test="<%= ree.getType() == RemoteExportException.NO_LAYOUTS %>">
			<liferay-ui:message key="there-are-no-layouts-in-the-exported-data" />
		</c:if>

		<c:if test="<%= ree.getType() == RemoteExportException.NO_PERMISSIONS %>">
			<liferay-ui:message arguments="<%= ree.getGroupId() %>" key="you-do-not-have-permissions-to-edit-the-site-with-id-x-on-the-remote-server" />
		</c:if>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= RemoteOptionsException.class %>">

		<%
		RemoteOptionsException roe = (RemoteOptionsException)errorException;
		%>

		<c:if test="<%= roe.getType() == RemoteOptionsException.REMOTE_ADDRESS %>">
			<liferay-ui:message arguments="<%= roe.getRemoteAddress() %>" key="the-remote-address-x-is-not-valid" />
		</c:if>

		<c:if test="<%= roe.getType() == RemoteOptionsException.REMOTE_GROUP_ID %>">
			<liferay-ui:message arguments="<%= roe.getRemoteGroupId() %>" key="the-remote-site-id-x-is-not-valid" />
		</c:if>

		<c:if test="<%= roe.getType() == RemoteOptionsException.REMOTE_PATH_CONTEXT %>">
			<liferay-ui:message arguments="<%= roe.getRemotePathContext() %>" key="the-remote-path-context-x-is-not-valid" />
		</c:if>

		<c:if test="<%= roe.getType() == RemoteOptionsException.REMOTE_PORT %>">
			<liferay-ui:message arguments="<%= roe.getRemotePort() %>" key="the-remote-port-x-is-not-valid" />
		</c:if>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= SystemException.class %>">

		<%
		SystemException se = (SystemException)errorException;
		%>

		<liferay-ui:message key="<%= se.getMessage() %>" />
	</liferay-ui:error>

	<c:choose>
		<c:when test="<%= selectPages %>">
			<div class="alert alert-info">
				<liferay-ui:message key="note-that-selecting-no-pages-from-tree-reverts-to-implicit-selection-of-all-pages" />
			</div>

			<div class="selected-pages" id="<portlet:namespace />pane">
				<liferay-util:include page="/html/portlet/layouts_admin/tree_js.jsp">
					<liferay-util:param name="selectableTree" value="1" />
					<liferay-util:param name="treeId" value="<%= treeKey %>" />
					<liferay-util:param name="incomplete" value="<%= String.valueOf(false) %>" />
					<liferay-util:param name="tabs1" value='<%= (privateLayout) ? "private-pages" : "public-pages" %>' />
				</liferay-util:include>
			</div>

			<aui:button-row>

				<%
				String selectPagesURL = HttpUtil.setParameter(currentURL, "selectPages", false);
				%>

				<aui:button href="<%= selectPagesURL %>" value="select" />
			</aui:button-row>
		</c:when>
		<c:otherwise>
			<div id="<portlet:namespace />publishOptions">
				<div class="export-dialog-tree">
					<aui:input label="title" name="description" type="text" />

					<c:if test="<%= schedule %>">
						<aui:fieldset cssClass="options-group" label="schedule">
							<%@ include file="/html/portlet/layouts_admin/publish_layouts_scheduler.jspf" %>
						</aui:fieldset>
					</c:if>

					<c:if test="<%= !selGroup.isCompany() %>">
						<aui:fieldset cssClass="options-group" label="pages">
							<%@ include file="/html/portlet/layouts_admin/publish_layouts_select_pages.jspf" %>
						</aui:fieldset>
					</c:if>

					<%@ include file="/html/portlet/layouts_admin/publish_layouts_portlets.jspf" %>

					<aui:fieldset cssClass="options-group" label="application-configuration">
						<%@ include file="/html/portlet/layouts_admin/publish_layouts_portlets_setup.jspf" %>
					</aui:fieldset>

					<aui:fieldset cssClass="options-group" label="content">
						<%@ include file="/html/portlet/layouts_admin/publish_layouts_portlets_data.jspf" %>
					</aui:fieldset>

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

				<c:choose>
					<c:when test="<%= schedule %>">
						<aui:button-row>
							<aui:button name="addButton" onClick='<%= renderResponse.getNamespace() + "schedulePublishEvent();" %>' value="add-event" />
						</aui:button-row>
					</c:when>
					<c:otherwise>
						<aui:button-row>
							<aui:button name="publishButton" type="submit" value="<%= publishActionKey %>" />
						</aui:button-row>
					</c:otherwise>
				</c:choose>
			</div>
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />publishPages',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-" + publishActionKey + "-these-pages") %>')) {
				submitForm(document.<portlet:namespace />exportPagesFm);
			}
		}
	);
</aui:script>

<aui:script use="liferay-export-import">
	new Liferay.ExportImport(
		{
			categoriesNode: '#<%= PortletDataHandlerKeys.CATEGORIES %>Checkbox',
			deleteMissingLayoutsNode: '#<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>Checkbox',
			deletePortletDataNode: '#<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>Checkbox',
			dialogTitle: '<%= UnicodeLanguageUtil.get(pageContext, "content-to-publish") %>',
			form: document.<portlet:namespace />exportPagesFm,
			layoutSetSettingsNode: '#<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>Checkbox',
			logoNode: '#<%= PortletDataHandlerKeys.LOGO %>Checkbox',
			namespace: '<portlet:namespace />',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			rangeLastPublishNode: '#rangeLastPublish',
			remoteAddressNode: '#<portlet:namespace />remoteAddress',
			remoteDeletePortletDataNode: '#remoteDeletePortletDataCheckbox',
			remotePortNode: '#<portlet:namespace />remotePort',
			remotePathContextNode: '#<portlet:namespace />remotePathContext',
			remoteGroupIdNode: '#<portlet:namespace />remoteGroupId',
			secureConnectionNode: '#secureConnectionCheckbox',
			themeNode: '#<%= PortletDataHandlerKeys.THEME %>Checkbox',
			themeReferenceNode: '#<%= PortletDataHandlerKeys.THEME_REFERENCE %>Checkbox',
			userPreferencesNode: '#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES %>Checkbox'
		}
	);
</aui:script>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />chooseApplications', '<portlet:namespace />selectApplications', ['<portlet:namespace />showChangeGlobalConfiguration']);
	Liferay.Util.toggleRadio('<portlet:namespace />allApplications', '<portlet:namespace />showChangeGlobalConfiguration', ['<portlet:namespace />selectApplications']);

	Liferay.Util.toggleRadio('<portlet:namespace />rangeDateRange','<portlet:namespace />startEndDate');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeAll','', ['<portlet:namespace />startEndDate']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLastPublish','', ['<portlet:namespace />startEndDate']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLast','', ['<portlet:namespace />startEndDate']);

	Liferay.Util.toggleRadio('<portlet:namespace />chooseContent', '<portlet:namespace />selectContents', ['<portlet:namespace />showChangeGlobalContent']);
	Liferay.Util.toggleRadio('<portlet:namespace />allContent', '<portlet:namespace />showChangeGlobalContent', ['<portlet:namespace />selectContents']);
</aui:script>