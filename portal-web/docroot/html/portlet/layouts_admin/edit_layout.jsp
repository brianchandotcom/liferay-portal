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
String closeRedirect = ParamUtil.getString(request, "closeRedirect");

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group group = (Group)request.getAttribute("edit_pages.jsp-group");
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");

long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
long stagingGroupId = ((Long)request.getAttribute("edit_pages.jsp-stagingGroupId")).longValue();

Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
long layoutId = ((Long)request.getAttribute("edit_pages.jsp-layoutId")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");
PortletURL redirectURL = (PortletURL)request.getAttribute("edit_pages.jsp-redirectURL");

long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);

Set<Long> parentPlids = new HashSet<Long>();

long parentPlid = refererPlid;

while (parentPlid > 0) {
	try {
		Layout parentLayout = LayoutLocalServiceUtil.getLayout(parentPlid);

		if (parentLayout.isRootLayout()) {
			break;
		}

		parentPlid = parentLayout.getParentPlid();

		parentPlids.add(parentPlid);
	}
	catch (Exception e) {
		break;
	}
}

LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(selLayout);

String layoutSetBranchName = StringPool.BLANK;

boolean incomplete = false;

if (layoutRevision != null) {
	long layoutSetBranchId = layoutRevision.getLayoutSetBranchId();

	incomplete = StagingUtil.isIncomplete(selLayout, layoutSetBranchId);

	if (incomplete) {
		LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);

		layoutSetBranchName = layoutSetBranch.getName();
	}
}

String[] mainSections = PropsValues.LAYOUT_FORM_UPDATE;

if (selLayout.isSupportsEmbeddedPortlets()) {
	List<Portlet> embeddedPortlets = new ArrayList<Portlet>();

	LayoutTypePortlet selLayoutTypePortlet = (LayoutTypePortlet)selLayout.getLayoutType();

	List<String> portletIds = selLayoutTypePortlet.getPortletIds();

	for (Portlet portlet : selLayoutTypePortlet.getAllPortlets()) {
		if (!portlet.isSystem() && !portletIds.contains(portlet.getPortletId())) {
			embeddedPortlets.add(portlet);
		}
	}

	if (!embeddedPortlets.isEmpty()) {
		request.setAttribute("edit_pages.jsp-embeddedPortlets", embeddedPortlets);

		mainSections = ArrayUtil.append(mainSections, "embedded-portlets");
	}
}

if (!group.isUser() && selLayout.isTypePortlet()) {
	mainSections = ArrayUtil.append(mainSections, "customization-settings");
}

String[][] categorySections = {mainSections};
%>

<liferay-util:include page="/html/portlet/layouts_admin/add_layout.jsp" />

<aui:nav-bar>
	<aui:nav id="layoutsNav">
		<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, selPlid, ActionKeys.ADD_LAYOUT) && PortalUtil.isLayoutParentable(selLayout.getType()) %>">
			<aui:nav-item data-value="add-child-page" iconClass="icon-plus" label="add-child-page" />
		</c:if>
		<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, selPlid, ActionKeys.PERMISSIONS) %>">
			<aui:nav-item data-value="permissions" iconClass="icon-lock" label="permissions" />
		</c:if>
		<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, selPlid, ActionKeys.DELETE) %>">
			<aui:nav-item data-value="delete" iconClass="icon-remove" label="delete" />
		</c:if>
		<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, selLayout, ActionKeys.UPDATE) %>">
			<aui:nav-item data-value="copy-applications" iconClass="icon-list-alt" label="copy-applications" />
		</c:if>
	</aui:nav>
</aui:nav-bar>

<portlet:actionURL var="editLayoutURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_layouts" />
</portlet:actionURL>

<aui:form action="<%= editLayoutURL %>" cssClass="edit-layout-form" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveLayout();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value='<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", selPlid) %>' />
	<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
	<aui:input name="selPlid" type="hidden" value="<%= selPlid %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutId %>" />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<c:if test="<%= layoutRevision != null && !incomplete %>">
		<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutRevision.getLayoutSetBranchId() %>" />
	</c:if>

	<c:choose>
		<c:when test="<%= incomplete %>">
			<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(selLayout.getName(locale)), HtmlUtil.escape(layoutSetBranchName)} %>" key="the-page-x-is-not-enabled-in-x,-but-is-available-in-other-pages-variations" />

			<aui:input name="incompleteLayoutRevisionId" type="hidden" value="<%= layoutRevision.getLayoutRevisionId() %>" />

			<%
			String taglibEnableOnClick = "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveLayout('enable');";

			String taglibDeleteOnClick = "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveLayout('" + Constants.DELETE + "');";
			%>

			<aui:button-row>
				<aui:button name="enableLayout" onClick="<%= taglibEnableOnClick %>" value='<%= LanguageUtil.format(pageContext, "enable-in-x", HtmlUtil.escape(layoutSetBranchName)) %>' />

				<aui:button name="deleteLayout" onClick="<%= taglibDeleteOnClick %>" value="delete-in-all-pages-variations" />
			</aui:button-row>
		</c:when>
		<c:otherwise>
			<c:if test="<%= !group.isLayoutPrototype() && (selLayout != null) %>">
				<c:if test="<%= liveGroup.isStaged() %>">
					<liferay-ui:error exception="<%= RemoteExportException.class %>">

						<%
						RemoteExportException ree = (RemoteExportException)errorException;
						%>

						<c:if test="<%= ree.getType() == RemoteExportException.BAD_CONNECTION %>">
							<%= LanguageUtil.format(pageContext, "could-not-connect-to-address-x.-please-verify-that-the-specified-port-is-correct-and-that-the-remote-server-is-configured-to-accept-requests-from-this-server", "<em>" + ree.getURL() + "</em>") %>
						</c:if>

						<c:if test="<%= ree.getType() == RemoteExportException.NO_GROUP %>">
							<%= LanguageUtil.format(pageContext, "remote-group-with-id-x-does-not-exist", ree.getGroupId()) %>
						</c:if>

						<c:if test="<%= ree.getType() == RemoteExportException.NO_LAYOUTS %>">
							<liferay-ui:message key="no-pages-are-selected-for-export" />
						</c:if>

						<c:if test="<%= ree.getType() == RemoteExportException.NO_PERMISSIONS %>">
							<liferay-ui:message arguments="<%= ree.getGroupId() %>" key="you-do-not-have-permissions-to-edit-the-site-with-id-x-on-the-remote-server" />
						</c:if>
					</liferay-ui:error>

					<div class="alert alert-block">
						<liferay-ui:message key="the-staging-environment-is-activated-changes-have-to-be-published-to-make-them-available-to-end-users" />
					</div>
				</c:if>

				<liferay-security:permissionsURL
					modelResource="<%= Layout.class.getName() %>"
					modelResourceDescription="<%= selLayout.getName(locale) %>"
					resourcePrimKey="<%= String.valueOf(selLayout.getPlid()) %>"
					var="permissionURL"
					windowState="<%= LiferayWindowState.POP_UP.toString() %>"
				/>

				<%
				Group selLayoutGroup = selLayout.getGroup();
				%>

				<c:choose>
					<c:when test="<%= !SitesUtil.isLayoutUpdateable(selLayout) %>">
						<div class="alert alert-block">
							<liferay-ui:message key="this-page-cannot-be-modified-because-it-is-associated-to-a-site-template-does-not-allow-modifications-to-it" />
						</div>
					</c:when>
					<c:when test="<%= !SitesUtil.isLayoutDeleteable(selLayout) %>">
						<div class="alert alert-block">
							<liferay-ui:message key="this-page-cannot-be-deleted-because-it-is-associated-to-a-site-template" />
						</div>
					</c:when>
				</c:choose>

				<c:if test="<%= (selLayout.getGroupId() != groupId) && (selLayoutGroup.isUserGroup()) %>">

					<%
					UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(selLayoutGroup.getClassPK());
					%>

					<div class="alert alert-block">
						<liferay-ui:message arguments="<%= HtmlUtil.escape(userGroup.getName()) %>" key="this-page-cannot-be-modified-because-it-belongs-to-the-user-group-x" />
					</div>
				</c:if>

				<aui:script use="liferay-util-window">
					var content;
					var popup;

					var clickHandler = function(event) {
						var dataValue = event.target.ancestor().attr('data-value');

						if (dataValue === 'add-child-page') {
							content = A.one('#<portlet:namespace />addLayout');

							if (!popup) {
								popup = Liferay.Util.Window.getWindow(
									{
										dialog: {
											bodyContent: content.show(),
											zIndex: Liferay.zIndex.WINDOW + 2
										},
										title: '<%= UnicodeLanguageUtil.get(pageContext, "add-child-page") %>'
									}
								);
							}

							popup.show();

							Liferay.Util.focusFormField(content.one('input:text'));
						}
						else if (dataValue === 'permissions') {
							<liferay-security:permissionsURL
								modelResource="<%= Layout.class.getName() %>"
								modelResourceDescription="<%= selLayout.getName(locale) %>"
								resourcePrimKey="<%= String.valueOf(selLayout.getPlid()) %>"
								var="permissionURL"
								windowState="<%= LiferayWindowState.POP_UP.toString() %>"
							/>

							Liferay.Util.openWindow(
								{
									cache: false,
									dialog: {
										zIndex: Liferay.zIndex.WINDOW + 2
									},
									id: '<portlet:namespace /><%= selLayout.getFriendlyURL().substring(1) %>_permissions',
									title: '<%= UnicodeLanguageUtil.get(pageContext, "permissions") %>',
									uri: '<%= permissionURL %>'
								}
							);
						}
						else if (dataValue === 'delete') {
							<portlet:namespace />saveLayout('<%= Constants.DELETE %>');
						}
						else if (dataValue == 'copy-applications') {
							content = A.one('#<portlet:namespace />copyPortletsFromPage');

							popUp = Liferay.Util.Window.getWindow(
								{
									dialog: {
										bodyContent: content.show(),
										destroyOnHide: true,
										zIndex: Liferay.zIndex.WINDOW + 2
									},
									title: '<%= UnicodeLanguageUtil.get(pageContext, "copy-applications") %>'
								}
							);

							popUp.show();

							var submitButton = popUp.get('contentBox').one('#<portlet:namespace />copySubmitButton');

							if (submitButton) {
								submitButton.on(
									'click',
									function(event) {
										popUp.hide();

										var form = A.one('#<portlet:namespace />fm');

										if (form) {
											form.append(content);
										}

										<portlet:namespace />saveLayout();
									}
								);
							}
						}
					};

					A.one('#<portlet:namespace />layoutsNav').delegate('click', clickHandler, 'li a');
				</aui:script>

			</c:if>

			<liferay-ui:form-navigator
				categoryNames="<%= _CATEGORY_NAMES %>"
				categorySections="<%= categorySections %>"
				jspPath="/html/portlet/layouts_admin/layout/"
				showButtons="<%= (selLayout.getGroupId() == groupId) && SitesUtil.isLayoutUpdateable(selLayout) && LayoutPermissionUtil.contains(permissionChecker, selPlid, ActionKeys.UPDATE) %>"
			/>
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveLayout',
		function(action) {
			var A = AUI();

			action = action || '<%= Constants.UPDATE %>';

			if (action == '<%= Constants.DELETE %>') {
				if (!confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
					return false;
				}

				document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= HttpUtil.setParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", selLayout.getParentPlid()) %>';
			}

			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = action;

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);
</aui:script>

<%!
private static final String[] _CATEGORY_NAMES = {""};
%>