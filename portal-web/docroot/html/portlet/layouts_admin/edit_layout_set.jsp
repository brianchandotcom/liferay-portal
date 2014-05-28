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

Group group = (Group)request.getAttribute("edit_pages.jsp-group");
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");
long groupId = (Long)request.getAttribute("edit_pages.jsp-groupId");
long liveGroupId = (Long)request.getAttribute("edit_pages.jsp-liveGroupId");
long stagingGroupId = (Long)request.getAttribute("edit_pages.jsp-stagingGroupId");
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
UnicodeProperties liveGroupTypeSettings = (UnicodeProperties)request.getAttribute("edit_pages.jsp-liveGroupTypeSettings");
LayoutSet selLayoutSet = ((LayoutSet)request.getAttribute("edit_pages.jsp-selLayoutSet"));

String rootNodeName = (String)request.getAttribute("edit_pages.jsp-rootNodeName");

PortletURL redirectURL = (PortletURL)request.getAttribute("edit_pages.jsp-redirectURL");

int pagesCount = 0;

if (selGroup.isLayoutSetPrototype()) {
	privateLayout = true;
}

if (privateLayout) {
	if (group != null) {
		pagesCount = group.getPrivateLayoutsPageCount();
	}
}
else {
	if (group != null) {
		pagesCount = group.getPublicLayoutsPageCount();
	}
}

String[] mainSections = PropsValues.LAYOUT_SET_FORM_UPDATE;

if (!company.isSiteLogo()) {
	mainSections = ArrayUtil.remove(mainSections, "logo");
}

if (group.isGuest()) {
	mainSections = ArrayUtil.remove(mainSections, "advanced");
}

String[][] categorySections = {mainSections};

boolean hasExportImportLayoutsPermission = GroupPermissionUtil.contains(permissionChecker, liveGroup, ActionKeys.EXPORT_IMPORT_LAYOUTS);

boolean hasAddPageLayoutsPermission = GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ADD_LAYOUT);

boolean hasViewPagesPermission = (pagesCount > 0) && (liveGroup.isStaged() || selGroup.isLayoutSetPrototype() || selGroup.isStagingGroup() || portletName.equals(PortletKeys.MY_SITES) || portletName.equals(PortletKeys.GROUP_PAGES) || portletName.equals(PortletKeys.SITES_ADMIN) || portletName.equals(PortletKeys.USERS_ADMIN));
%>

<div class="add-content-menu hide" id="<portlet:namespace />addLayout">
	<liferay-util:include page="/html/portlet/layouts_admin/add_layout.jsp" />
</div>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav" id="layoutsNav">
		<c:if test="<%= hasViewPagesPermission %>">
			<aui:nav-item data-value="view-pages" iconCssClass="icon-file" label="view-pages" />
		</c:if>
		<c:if test="<%= hasAddPageLayoutsPermission %>">
			<aui:nav-item data-value="add-page" iconCssClass="icon-plus" label="add-page" />
		</c:if>
		<c:if test="<%= hasExportImportLayoutsPermission %>">
			<aui:nav-item data-value="export" iconCssClass="icon-arrow-down" label="export" />
			<aui:nav-item data-value="import" iconCssClass="icon-arrow-up" label="import" />
		</c:if>
	</aui:nav>
</aui:nav-bar>

<c:if test="<%= liveGroup.isStaged() %>">
	<%@ include file="/html/portlet/layouts_admin/error_auth_exception.jspf" %>

	<%@ include file="/html/portlet/layouts_admin/error_remote_export_exception.jspf" %>

	<div class="alert alert-block">
		<liferay-ui:message key="the-staging-environment-is-activated-changes-have-to-be-published-to-make-them-available-to-end-users" />
	</div>
</c:if>

<portlet:actionURL var="editLayoutSetURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_layout_set" />
</portlet:actionURL>

<aui:form action="<%= editLayoutSetURL %>" cssClass="edit-layoutset-form" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveLayoutset();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirectURL.toString() %>" />
	<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
	<aui:input name="selPlid" type="hidden" value="<%= selPlid %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutSetId" type="hidden" value="<%= selLayoutSet.getLayoutSetId() %>" />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<liferay-ui:form-navigator
		categoryNames="<%= _CATEGORY_NAMES %>"
		categorySections="<%= categorySections %>"
		jspPath="/html/portlet/layouts_admin/layout_set/"
		showButtons="<%= GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.MANAGE_LAYOUTS) && SitesUtil.isLayoutSetPrototypeUpdateable(selLayoutSet) %>"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveLayoutset(action) {
		document.<portlet:namespace />fm.encoding = 'multipart/form-data';

		if (action) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = action;
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'update';
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateLogo() {
		document.<portlet:namespace />fm.encoding = 'multipart/form-data';
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'logo';

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateRobots() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'robots';

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />removePage',
		function(box) {
			var A = AUI();

			var selectEl = A.one(box);

			var currentValue = selectEl.val() || null;

			Liferay.Util.removeItem(box);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateDisplayOrder',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'display_order';
			document.<portlet:namespace />fm.<portlet:namespace />layoutIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateStaging',
		function() {
			var A = AUI();

			var selectEl = A.one('#<portlet:namespace />stagingType');

			var currentValue = selectEl.val() || null;

			var ok = false;

			if (currentValue == 0) {
				ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-deactivate-staging-for-x", liveGroup.getDescriptiveName(locale), false) %>');
			}
			else if (currentValue == 1) {
				ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-activate-local-staging-for-x", liveGroup.getDescriptiveName(locale), false) %>');
			}
			else if (currentValue == 2) {
				ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-activate-remote-staging-for-x", liveGroup.getDescriptiveName(locale), false) %>');
			}

			if (ok) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'staging';

				submitForm(document.<portlet:namespace />fm);
			}
		},
		['aui-base']
	);
</aui:script>

<aui:script use="liferay-util-window">
	var popup;

	var clickHandler = function(event) {
		var dataValue = event.target.ancestor('li').attr('data-value');

		processDataValue(dataValue);
	};

	var processDataValue = function(dataValue) {
		if (dataValue === 'add-page' || dataValue === 'add-child-page') {
			var content = A.one('#<portlet:namespace />addLayout');

			if (!popup) {
				popup = Liferay.Util.Window.getWindow(
					{
						dialog: {
							bodyContent: content.show(),
							cssClass: 'lfr-add-dialog',
							width: 600
						},
						title: '<%= UnicodeLanguageUtil.get(pageContext, "add-page") %>'
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
		else if (dataValue === 'view-pages') {
			<liferay-portlet:actionURL plid="<%= selPlid %>" portletName="<%= PortletKeys.SITE_REDIRECTOR %>" var="viewPagesURL">
				<portlet:param name="struts_action" value="/my_sites/view" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			</liferay-portlet:actionURL>

			window.open('<%= viewPagesURL %>').focus();
		}
		else if (dataValue === 'import') {
			<portlet:renderURL var="importPagesURL">
				<portlet:param name="struts_action" value="/layouts_admin/import_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VALIDATE %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
			</portlet:renderURL>

			location.href = '<%= importPagesURL %>';
		}
		else if (dataValue === 'export') {
			<portlet:renderURL var="exportPagesURL">
				<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
			</portlet:renderURL>

			location.href = '<%= exportPagesURL %>';
		}
	};

	A.one('#<portlet:namespace />layoutsNav').delegate('click', clickHandler, 'li a');

	<c:if test='<%= layout.isTypeControlPanel() && (SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "addError") != null) %>'>
		processDataValue('add-page');
	</c:if>
</aui:script>

<%!
private static final String[] _CATEGORY_NAMES = {""};
%>