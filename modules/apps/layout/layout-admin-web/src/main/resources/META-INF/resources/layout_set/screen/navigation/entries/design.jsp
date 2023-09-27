<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

long liveGroupId = layoutsAdminDisplayContext.getLiveGroupId();
boolean privateLayout = layoutsAdminDisplayContext.isPrivateLayout();
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

if (selGroup.isLayoutSetPrototype()) {
	privateLayout = true;
}

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(selGroup.getLayoutRootNodeName(privateLayout, locale));
%>

<portlet:actionURL name="/layout_admin/edit_layout_set" var="editLayoutSetURL">
	<portlet:param name="mvcRenderCommandName" value="/layout_admin/edit_layout_set" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editLayoutSetURL %>"
	cssClass="c-pt-0"
	enctype="multipart/form-data"
	method="post"
	name="fm"
	wrappedFormContent="<%= false %>"
>
	<aui:input name="redirect" type="hidden" value="<%= themeDisplay.getURLCurrent() %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="groupId" type="hidden" value="<%= selGroup.getGroupId() %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getStagingGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutSetId" type="hidden" value="<%= selLayoutSet.getLayoutSetId() %>" />
	<aui:input name="screenNavigationEntryKey" type="hidden" value="<%= LayoutScreenNavigationEntryConstants.ENTRY_KEY_DESIGN %>" />

	<h2 class="c-mb-4 text-7"><liferay-ui:message key="design" /></h2>

	<liferay-frontend:edit-form-body>
		<liferay-frontend:form-navigator
			formModelBean="<%= selLayoutSet %>"
			id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_LAYOUT_SET %>"
			showButtons="<%= false %>"
			type="<%= FormNavigatorConstants.FormNavigatorType.SHEET_SECTIONS %>"
		/>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, selGroup, ActionKeys.MANAGE_LAYOUTS) && selLayoutSet.isLayoutSetPrototypeUpdateable() %>">
			<liferay-frontend:edit-form-buttons
				redirect="<%= backURL %>"
				submitLabel="save"
			/>
		</c:if>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>