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

String pagesRedirect = ParamUtil.getString(request, "pagesRedirect");

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
portletURL.setParameter("pagesRedirect", currentURL);
portletURL.setParameter("groupId", String.valueOf(liveGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));

PortletURL selectURL = renderResponse.createRenderURL();

selectURL.setParameter("struts_action", "/layouts_admin/publish_layouts");
selectURL.setParameter(Constants.CMD, cmd);
selectURL.setParameter("pagesRedirect", pagesRedirect);
selectURL.setParameter("groupId", String.valueOf(stagingGroupId));
selectURL.setParameter("selPlid", String.valueOf(selPlid));
selectURL.setParameter("privateLayout", String.valueOf(privateLayout));
selectURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
selectURL.setParameter("selectPages", String.valueOf(!selectPages));
selectURL.setParameter("schedule", String.valueOf(schedule));
selectURL.setWindowState(LiferayWindowState.EXCLUSIVE);

request.setAttribute("edit_pages.jsp-groupId", new Long(stagingGroupId));
request.setAttribute("edit_pages.jsp-selPlid", new Long(selPlid));
request.setAttribute("edit_pages.jsp-privateLayout", new Boolean(privateLayout));

request.setAttribute("edit_pages.jsp-rootNodeName", rootNodeName);

request.setAttribute("edit_pages.jsp-portletURL", portletURL);

response.setHeader("Ajax-ID", request.getHeader("Ajax-ID"));
%>

<c:if test='<%= SessionMessages.contains(renderRequest, "requestProcessed") %>'>
	<div class="alert alert-success">

		<%
		String successMessage = (String)SessionMessages.get(renderRequest, "requestProcessed");
		%>

		<c:choose>
			<c:when test='<%= Validator.isNotNull(successMessage) && !successMessage.equals("request_processed") %>'>
				<%= HtmlUtil.escape(successMessage) %>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="your-request-completed-successfully" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<style type="text/css">
	.tree-node-content .incomplete-layout {
		color: #CCC;
	}

	#<portlet:namespace />pane td.first {
		padding-top: 5px;
	}

	#<portlet:namespace />exportPagesFm .export-pages-panel-container {
		margin: 1em 0;
	}

	#<portlet:namespace />exportPagesFm .export-pages-panel-container .lfr-panel-content{
		padding: 1em;
	}

	#<portlet:namespace />exportPagesFm .selected-pages-option .field-content {
		display: inline;
	}

	#<portlet:namespace />exportPagesFm .layout-variation-name {
		color: #999;
	}

	#<portlet:namespace />exportPagesFm .page-not-exportable {
		color: #933;
	}

	#<portlet:namespace />exportPagesFm .portlet-data-section legend {
		font-size: 110%;
	}

	#<portlet:namespace />exportPagesFm .portlet-data-section .portlet-type-data-section .legend {
		border-width: 0;
	}
</style>

<aui:form action='<%= portletURL.toString() + "&etag=0&strip=0" %>' method="post" name="exportPagesFm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "refreshDialog();" %>' >
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

			<div id="<portlet:namespace />pane">
				<liferay-util:include page="/html/portlet/layouts_admin/tree_js.jsp">
					<liferay-util:param name="selectableTree" value="1" />
					<liferay-util:param name="treeId" value="<%= treeKey %>" />
					<liferay-util:param name="incomplete" value="<%= String.valueOf(false) %>" />
					<liferay-util:param name="tabs1" value='<%= (privateLayout) ? "private-pages" : "public-pages" %>' />
				</liferay-util:include>
			</div>

			<aui:button-row>

				<%
				String taglibOnClick = "Liferay.Util.Window.refreshByChild('#" + renderResponse.getNamespace() + "exportPagesFm');";
				%>

				<aui:button onClick="<%= taglibOnClick %>" value="select" />
			</aui:button-row>
		</c:when>
		<c:otherwise>
			<c:if test="<%= schedule %>">
				<aui:nav-bar>
					<aui:nav>
						<aui:nav-item label="view-all" />
						<aui:nav-item label="add" />
					</aui:nav>
				</aui:nav-bar>

				<div class="hide" id="<portlet:namespace />publishedEvents">
					<liferay-ui:header
						title="scheduled-events"
					/>

					<div id="<portlet:namespace />scheduledPublishEventsDiv"></div>
				</div>
			</c:if>

			<div id="<portlet:namespace />publishOptions">
				<c:if test="<%= schedule %>">
					<liferay-ui:header
						title="new-event"
					/>

					<aui:input label="title" name="description" type="text" />
				</c:if>

				<c:choose>
					<c:when test="<%= layoutSetBranchId > 0 %>">
						<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutSetBranchId %>" />
					</c:when>
					<c:otherwise>
						<c:if test="<%= LayoutStagingUtil.isBranchingLayoutSet(selGroup, privateLayout) %>">

							<%
							List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), privateLayout);
							%>

							<aui:select label="site-pages-variation" name="layoutSetBranchId">

								<%
								for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
									boolean selected = false;

									if (layoutSetBranch.isMaster()) {
										selected = true;
									}
								%>

									<aui:option label="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>" selected="<%= selected %>" value="<%= layoutSetBranch.getLayoutSetBranchId() %>" />

								<%
								}
								%>

							</aui:select>
						</c:if>
					</c:otherwise>
				</c:choose>

				<liferay-ui:panel-container cssClass="export-pages-panel-container" extended="<%= true %>" id="layoutsAdminExportPagesPanelContainer" persistState="<%= true %>">
					<c:if test="<%= !selGroup.isCompany() %>">
						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="layoutsAdminExportPagesPagesPanel" persistState="<%= true %>" title="pages">
							<%@ include file="/html/portlet/layouts_admin/publish_layouts_select_pages.jspf" %>
						</liferay-ui:panel>
					</c:if>

					<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= true %>" id="layoutsAdminExportPagesPortletsPanel" persistState="<%= true %>" title="applications">
						<%@ include file="/html/portlet/layouts_admin/publish_layouts_portlets.jspf" %>
					</liferay-ui:panel>

					<c:if test="<%= !selGroup.isCompany() %>">
						<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= true %>" id="layoutsAdminExportPagesOptionsPanel" persistState="<%= true %>" title="other">
							<%@ include file="/html/portlet/layouts_admin/publish_layouts_other.jspf" %>
						</liferay-ui:panel>
					</c:if>

					<c:if test="<%= !localPublishing %>">
						<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= true %>" id="layoutsAdminExportPagesConnectionPanel" persistState="<%= true %>" title="remote-live-connection-settings">
							<%@ include file="/html/portlet/layouts_admin/publish_layouts_remote_options.jspf" %>
						</liferay-ui:panel>
					</c:if>

					<c:if test="<%= schedule %>">
						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="layoutsAdminExportPagesSchedulePanel" persistState="<%= true %>" title="schedule">
							<%@ include file="/html/portlet/layouts_admin/publish_layouts_scheduler.jspf" %>
						</liferay-ui:panel>
					</c:if>
				</liferay-ui:panel-container>

				<c:choose>
					<c:when test="<%= schedule %>">
						<aui:button-row>
							<aui:button name="addButton" value="add-event" />
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
		'<portlet:namespace />refreshDialog',
		function() {
			var A = AUI();

			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-" + publishActionKey + "-these-pages") %>')) {
				var dialog = Liferay.Util.Window.getByChild('#<portlet:namespace />exportPagesFm');

				if (dialog) {
					dialog.io.set('uri', '<%= portletURL.toString() + "&etag=0&strip=0" %>');

					dialog.io.set(
						'form',
						{
							id: '<portlet:namespace />exportPagesFm'
						}
					);

					dialog.io.start();
				}
			}
		},
		['liferay-util-window']
	);
</aui:script>

<aui:script use="aui-base,aui-modal">
	var dialog = Liferay.Util.Window.getByChild('#<portlet:namespace />exportPagesFm');

	if (dialog) {
		dialog.io.set('uri', '<%= selectURL %>');

		<c:if test="<%= schedule %>">
			var toolbarViewButton = A.one('#<portlet:namespace />exportPagesFm .view-button');
			var toolbarAddButton = A.one('#<portlet:namespace />exportPagesFm .add-button');
			var addEventButton = A.one('#<portlet:namespace />addButton');

			var allEvents = A.one('#<portlet:namespace />publishedEvents');
			var publishOptions = A.one('#<portlet:namespace />publishOptions');

			var viewEvents = function() {
				toolbarAddButton.removeClass('current');
				toolbarViewButton.addClass('current');

				allEvents.show();
				publishOptions.hide();
			};

			addEventButton.on(
				'click',
				function(event) {
					<portlet:namespace />schedulePublishEvent();

					viewEvents();
				}
			);

			toolbarAddButton.one('a').on(
				'click',
				function(event) {
					toolbarAddButton.addClass('current');
					toolbarViewButton.removeClass('current');

					allEvents.hide();
					publishOptions.show();
				}
			);

			toolbarViewButton.one('a').on(
				'click',
				function(event) {
					viewEvents();
				}
			);
		</c:if>
	}
</aui:script>