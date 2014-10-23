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
String closeRedirect = ParamUtil.getString(request, "closeRedirect");

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group group = layoutsAdminDisplayContext.getGroup();
Group liveGroup = layoutsAdminDisplayContext.getLiveGroup();

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();

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

	for (Portlet portlet : selLayoutTypePortlet.getAllPortlets(false)) {
		if (!portletIds.contains(portlet.getPortletId())) {
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

String displayStyle = ParamUtil.getString(request, "displayStyle");
boolean showAddAction = ParamUtil.getBoolean(request, "showAddAction", true);
%>

<c:if test="<%= !portletName.equals(PortletKeys.DOCKBAR) %>">
	<div class="add-content-menu hide" id="<portlet:namespace />addLayout">
		<liferay-util:include page="/html/portlet/layouts_admin/add_layout.jsp" />
	</div>
</c:if>

<c:if test="<%= !group.isLayoutPrototype() && (selLayout != null) %>">
	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav" id="layoutsNav">
			<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, selLayout, ActionKeys.ADD_LAYOUT) && showAddAction %>">
				<aui:nav-item data-value="add-child-page" iconCssClass="icon-plus" label="add-child-page" />
			</c:if>
			<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, selLayout, ActionKeys.PERMISSIONS) %>">
				<aui:nav-item data-value="permissions" iconCssClass="icon-lock" label="permissions" />
			</c:if>
			<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, selLayout, ActionKeys.DELETE) %>">
				<aui:nav-item data-value="delete" iconCssClass="icon-remove" label="delete" />
			</c:if>
			<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, selLayout, ActionKeys.UPDATE) %>">
				<aui:nav-item data-value="copy-applications" iconCssClass="icon-list-alt" label="copy-applications" />
			</c:if>
		</aui:nav>
	</aui:nav-bar>
</c:if>

<portlet:actionURL var="editLayoutURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_layouts" />
</portlet:actionURL>

<aui:form action='<%= HttpUtil.addParameter(editLayoutURL, "refererPlid", plid) %>' cssClass="edit-layout-form" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveLayout();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value='<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", layoutsAdminDisplayContext.getSelPlid()) %>' />
	<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= selGroup.getGroupId() %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getLiveGroupId() %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getStagingGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsAdminDisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsAdminDisplayContext.getLayoutId() %>" />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<c:if test="<%= layoutRevision != null && !incomplete %>">
		<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutRevision.getLayoutSetBranchId() %>" />
	</c:if>

	<c:choose>
		<c:when test="<%= incomplete %>">
			<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(selLayout.getName(locale)), HtmlUtil.escape(layoutSetBranchName)} %>" key="the-page-x-is-not-enabled-in-x,-but-is-available-in-other-pages-variations" translateArguments="<%= false %>" />

			<aui:input name="incompleteLayoutRevisionId" type="hidden" value="<%= layoutRevision.getLayoutRevisionId() %>" />

			<%
			String taglibEnableOnClick = "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveLayout('enable');";

			String taglibDeleteOnClick = "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveLayout('" + Constants.DELETE + "');";
			%>

			<aui:button-row>
				<aui:button name="enableLayout" onClick="<%= taglibEnableOnClick %>" value='<%= LanguageUtil.format(request, "enable-in-x", HtmlUtil.escape(layoutSetBranchName), false) %>' />

				<aui:button name="deleteLayout" onClick="<%= taglibDeleteOnClick %>" value="delete-in-all-pages-variations" />
			</aui:button-row>
		</c:when>
		<c:otherwise>
			<c:if test="<%= !group.isLayoutPrototype() && (selLayout != null) %>">
				<c:if test="<%= selGroup.isStagingGroup() %>">
					<%@ include file="/html/portlet/layouts_admin/error_auth_exception.jspf" %>

					<%@ include file="/html/portlet/layouts_admin/error_remote_export_exception.jspf" %>

					<div class="alert alert-warning">
						<liferay-ui:message key="the-staging-environment-is-activated-changes-have-to-be-published-to-make-them-available-to-end-users" />
					</div>
				</c:if>

				<c:if test="<%= selGroup.hasLocalOrRemoteStagingGroup() && !selGroup.isStagingGroup() %>">
					<div class="alert alert-warning">
						<liferay-ui:message key="changes-are-immediately-available-to-end-users" />
					</div>
				</c:if>

				<%
				Group selLayoutGroup = selLayout.getGroup();
				%>

				<c:choose>
					<c:when test="<%= !SitesUtil.isLayoutUpdateable(selLayout) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="this-page-cannot-be-modified-because-it-is-associated-to-a-site-template-does-not-allow-modifications-to-it" />
						</div>
					</c:when>
					<c:when test="<%= !SitesUtil.isLayoutDeleteable(selLayout) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="this-page-cannot-be-deleted-and-cannot-have-child-pages-because-it-is-associated-to-a-site-template" />
						</div>
					</c:when>
				</c:choose>

				<c:if test="<%= (selLayout.getGroupId() != layoutsAdminDisplayContext.getGroupId()) && (selLayoutGroup.isUserGroup()) %>">

					<%
					UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(selLayoutGroup.getClassPK());
					%>

					<div class="alert alert-warning">
						<liferay-ui:message arguments="<%= HtmlUtil.escape(userGroup.getName()) %>" key="this-page-cannot-be-modified-because-it-belongs-to-the-user-group-x" translateArguments="<%= false %>" />
					</div>
				</c:if>

				<aui:script use="liferay-util-window">
					var content;
					var popup;

					var clickHandler = function(event) {
						var target = event.target;

						var dataValue = target.ancestor('li').attr('data-value') || target.attr('data-value');

						processDataValue(dataValue);
					};

					var processDataValue = function(dataValue) {
						if (dataValue === 'add-child-page') {
							content = A.one('#<portlet:namespace />addLayout');

							if (!popup) {
								popup = Liferay.Util.Window.getWindow(
									{
										dialog: {
											bodyContent: content.show(),
											cssClass: 'lfr-add-dialog',
											width: 600
										},
										title: '<%= UnicodeLanguageUtil.get(request, "add-child-page") %>'
									}
								);
							}

							popup.show();

							var cancelButton = popup.get('contentBox').one('#<portlet:namespace />cancelAddOperation');

							if (cancelButton) {
								cancelButton.on(
									'click',
									function(event) {
										popup.hide();
									}
								);
							}

							Liferay.Util.focusFormField(content.one('input:text'));
						}
						else if (dataValue === 'permissions') {
							Liferay.Util.openWindow(
								{
									cache: false,
									id: '<portlet:namespace /><%= HtmlUtil.escapeJS(selLayout.getFriendlyURL().substring(1)) %>_permissions',
									title: '<%= UnicodeLanguageUtil.get(request, "permissions") %>',

									<liferay-security:permissionsURL
										modelResource="<%= Layout.class.getName() %>"
										modelResourceDescription="<%= selLayout.getName(locale) %>"
										resourcePrimKey="<%= String.valueOf(selLayout.getPlid()) %>"
										var="permissionURL"
										windowState="<%= LiferayWindowState.POP_UP.toString() %>"
									/>

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
										bodyContent: content.show()
									},
									title: '<%= UnicodeLanguageUtil.get(request, "copy-applications") %>'
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

					<c:if test='<%= layout.isTypeControlPanel() && (SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "addError") != null) %>'>
						processDataValue('add-page');
					</c:if>
				</aui:script>
			</c:if>

			<c:if test="<%= !selGroup.hasLocalOrRemoteStagingGroup() || selGroup.isStagingGroup() %>">
				<liferay-ui:form-navigator
					categoryNames="<%= _CATEGORY_NAMES %>"
					categorySections="<%= categorySections %>"
					displayStyle="<%= displayStyle %>"
					jspPath="/html/portlet/layouts_admin/layout/"
					showButtons="<%= (selLayout.getGroupId() == layoutsAdminDisplayContext.getGroupId()) && SitesUtil.isLayoutUpdateable(selLayout) && LayoutPermissionUtil.contains(permissionChecker, selLayout, ActionKeys.UPDATE) %>"
				/>
			</c:if>
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />saveLayout(action) {
		action = action || '<%= Constants.UPDATE %>';

		var form = AUI.$(document.<portlet:namespace />fm);

		if (action == '<%= Constants.DELETE %>') {
			if (!confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
				return false;
			}

			form.fm('redirect').val('<%= HttpUtil.setParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", selLayout.getParentPlid()) %>');
		}

		form.fm('<%= Constants.CMD %>').val(action);

		submitForm(form);
	}
</aui:script>

<%!
private static final String[] _CATEGORY_NAMES = {""};
%>