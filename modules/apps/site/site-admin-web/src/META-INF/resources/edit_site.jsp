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

long groupId = ParamUtil.getLong(request, "groupId", portletName.equals(SiteAdminPortletKeys.SITE_SETTINGS) ? themeDisplay.getSiteGroupId() : 0);

Group group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}

long parentGroupId = ParamUtil.getLong(request, "parentGroupSearchContainerPrimaryKeys", GroupConstants.DEFAULT_PARENT_GROUP_ID);

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

if (!portletName.equals(SiteAdminPortletKeys.SITE_SETTINGS)) {
	String title = StringPool.BLANK;

	if (group != null) {
		title = group.getDescriptiveName(locale);
	}
	else if (layoutSetPrototype != null) {
		title = layoutSetPrototype.getName(locale);
	}
	else if (parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
		title = LanguageUtil.get(request, "new-child-site");
	}
	else {
		title = LanguageUtil.get(request, "new-site");
	}

	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL.toString());

	renderResponse.setTitle(HtmlUtil.escape(title));
}
%>

<liferay-ui:success key='<%= SiteAdminPortletKeys.SITE_SETTINGS + "requestProcessed" %>' message="site-was-added" />

<c:if test="<%= !portletName.equals(SiteAdminPortletKeys.SITE_SETTINGS) %>">

	<%
	if (group != null) {
		PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(locale), null);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
	}
	else if (parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
		Group parentGroup = GroupLocalServiceUtil.getGroup(parentGroupId);

		PortalUtil.addPortletBreadcrumbEntry(request, parentGroup.getDescriptiveName(locale), null);
	}
	%>

	<div id="breadcrumb">
		<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
	</div>
</c:if>

<portlet:actionURL name="editGroup" var="editGroupURL">
	<portlet:param name="mvcPath" value="/edit_site.jsp" />
</portlet:actionURL>

<aui:form action="<%= editGroupURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveGroup();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />

	<%
	request.setAttribute("site.group", group);
	request.setAttribute("site.liveGroup", liveGroup);
	request.setAttribute("site.liveGroupId", Long.valueOf(liveGroupId));
	request.setAttribute("site.stagingGroup", stagingGroup);
	request.setAttribute("site.stagingGroupId", Long.valueOf(stagingGroupId));
	request.setAttribute("site.liveGroupTypeSettings", liveGroupTypeSettings);
	request.setAttribute("site.layoutSetPrototype", layoutSetPrototype);
	%>

	<liferay-ui:form-navigator
		backURL="<%= backURL %>"
		formModelBean="<%= group %>"
		id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_SITES %>"
		showButtons="<%= true %>"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveGroup(forceDisable) {
		var $ = AUI.$;

		var form = $(document.<portlet:namespace />fm);

		<c:if test="<%= (group != null) && !group.isCompany() %>">
			<portlet:namespace />saveLocales();
		</c:if>

		submitForm(form);
	}
</aui:script>