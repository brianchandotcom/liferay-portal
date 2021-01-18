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
String viewOrganizationsRedirect = ParamUtil.getString(request, "viewOrganizationsRedirect");

String redirect = ParamUtil.getString(request, "redirect", viewOrganizationsRedirect);

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

String backURL = ParamUtil.getString(request, "backURL", redirect);

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());

Group group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}

Group liveGroup = null;

long liveGroupId = 0;

Group stagingGroup = null;

long stagingGroupId = 0;

UnicodeProperties liveGroupTypeSettings = null;

if (group != null) {
	if (group.isStagingGroup()) {
		liveGroup = group.getLiveGroup();

		stagingGroup = group;
	}
	else {
		liveGroup = group;

		if (group.hasStagingGroup()) {
			stagingGroup = group.getStagingGroup();
		}
	}

	liveGroupId = liveGroup.getGroupId();

	if (stagingGroup != null) {
		stagingGroupId = stagingGroup.getGroupId();
	}

	liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	liveGroupTypeSettings = new UnicodeProperties();
}

LayoutSetPrototype layoutSetPrototype = null;

long layoutSetPrototypeId = ParamUtil.getLong(request, "layoutSetPrototypeId");

if (layoutSetPrototypeId > 0) {
	layoutSetPrototype = LayoutSetPrototypeServiceUtil.getLayoutSetPrototype(layoutSetPrototypeId);
}
%>

<liferay-ui:success key='<%= SiteAdminPortletKeys.SITE_SETTINGS + "requestProcessed" %>' message="site-was-added" />

<portlet:actionURL name="/site_admin/edit_group" var="editGroupURL">
	<portlet:param name="mvcPath" value="/edit_site.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editGroupURL %>"
	cssClass="container-form-lg"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveGroup();" %>'
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />

	<%
	request.setAttribute("site.group", group);
	request.setAttribute("site.layoutSetPrototype", layoutSetPrototype);
	request.setAttribute("site.liveGroup", liveGroup);
	request.setAttribute("site.liveGroupId", Long.valueOf(liveGroupId));
	request.setAttribute("site.liveGroupTypeSettings", liveGroupTypeSettings);
	request.setAttribute("site.stagingGroup", stagingGroup);
	request.setAttribute("site.stagingGroupId", Long.valueOf(stagingGroupId));
	%>

	<liferay-frontend:edit-form-body>
		<liferay-frontend:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= group %>"
			id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_SITES %>"
			showButtons="<%= false %>"
		/>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	function <portlet:namespace />saveGroup(forceDisable) {
		submitForm(document.<portlet:namespace />fm);
	}
</script>