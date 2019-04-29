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

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

long liveGroupId = layoutsAdminDisplayContext.getLiveGroupId();
boolean privateLayout = layoutsAdminDisplayContext.isPrivateLayout();
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();

if (selGroup.isLayoutSetPrototype()) {
	privateLayout = true;
}

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(selGroup.getLayoutRootNodeName(privateLayout, locale));
%>

<portlet:actionURL name="/layout/edit_layout_set" var="editLayoutSetURL">
	<portlet:param name="mvcRenderCommandName" value="/layout/edit_layout_set" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editLayoutSetURL %>"
	enctype="multipart/form-data"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= redirectURL.toString() %>" />
	<aui:input name="groupId" type="hidden" value="<%= selGroup.getGroupId() %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getStagingGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutSetId" type="hidden" value="<%= selLayoutSet.getLayoutSetId() %>" />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:form-navigator
			formModelBean="<%= selLayoutSet %>"
			id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_LAYOUT_SET %>"
			showButtons="<%= false %>"
		/>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, selGroup, ActionKeys.MANAGE_LAYOUTS) && SitesUtil.isLayoutSetPrototypeUpdateable(selLayoutSet) %>">
			<aui:button type="submit" value="save" />

			<c:if test="<%= Validator.isNotNull(backURL) %>">
				<aui:button href="<%= backURL %>" type="cancel" />
			</c:if>
		</c:if>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>