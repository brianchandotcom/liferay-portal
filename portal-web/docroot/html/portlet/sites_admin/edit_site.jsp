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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String viewOrganizationsRedirect = ParamUtil.getString(request, "viewOrganizationsRedirect", themeDisplay.getURLControlPanel());
String redirect = ParamUtil.getString(request, "redirect", viewOrganizationsRedirect);

String backURL = ParamUtil.getString(request, "backURL", redirect);
boolean showBackURL = ParamUtil.getBoolean(request, "showBackURL", true);

long groupId = ParamUtil.getLong(request, "groupId", portletName.equals(SitesAdminPortletKeys.SITE_SETTINGS) ? themeDisplay.getSiteGroupId() : 0);

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

boolean showPrototypes = ParamUtil.getBoolean(request, "showPrototypes", true);

if (!portletName.equals(PortletKeys.SITE_SETTINGS)) {
	if (group != null) {
		PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(locale), null);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
	}
	else if (parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
		Group parentGroup = GroupLocalServiceUtil.getGroup(parentGroupId);

		PortalUtil.addPortletBreadcrumbEntry(request, parentGroup.getDescriptiveName(locale), null);
	}
}
%>

<liferay-ui:success key='<%= PortletKeys.SITE_SETTINGS + "requestProcessed" %>' message="site-was-added" />

<c:if test="<%= (group == null) || !layout.isTypeControlPanel() %>">

	<%
	boolean localizeTitle = true;
	String title = "new-site";

	if (group != null) {
		localizeTitle= false;
		title = group.getDescriptiveName(locale);
	}
	else if (layoutSetPrototype != null) {
		localizeTitle= false;
		title = layoutSetPrototype.getName(locale);
	}
	else if (parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
		title = "new-child-site";
	%>

		<div id="breadcrumb">
			<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
		</div>

	<%
	}
	%>

	<liferay-ui:header
		backURL="<%= backURL %>"
		escapeXml="<%= false %>"
		localizeTitle="<%= localizeTitle %>"
		showBackURL="<%= showBackURL %>"
		title="<%= HtmlUtil.escape(title) %>"
	/>
</c:if>

<portlet:actionURL name="editGroup" var="editGroupURL" />

<aui:form action="<%= editGroupURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveGroup();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
	<aui:input name="forceDisable" type="hidden" value="<%= false %>" />

	<%
	request.setAttribute("site.group", group);
	request.setAttribute("site.liveGroup", liveGroup);
	request.setAttribute("site.liveGroupId", new Long(liveGroupId));
	request.setAttribute("site.stagingGroup", stagingGroup);
	request.setAttribute("site.stagingGroupId", new Long(stagingGroupId));
	request.setAttribute("site.liveGroupTypeSettings", liveGroupTypeSettings);
	request.setAttribute("site.layoutSetPrototype", layoutSetPrototype);
	request.setAttribute("site.showPrototypes", String.valueOf(showPrototypes));
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

		var ok = true;

		<c:if test="<%= liveGroup != null %>">
			var stagingTypeEl = $('input[name=<portlet:namespace />stagingType]:checked');

			var oldValue;

			<c:choose>
				<c:when test="<%= liveGroup.isStaged() && !liveGroup.isStagedRemotely() %>">
					oldValue = 1;
				</c:when>
				<c:when test="<%= liveGroup.isStaged() && liveGroup.isStagedRemotely() %>">
					oldValue = 2;
				</c:when>
				<c:otherwise>
					oldValue = 0;
				</c:otherwise>
			</c:choose>

			var currentValue = stagingTypeEl.val();

			if (stagingTypeEl.length && (currentValue != oldValue)) {
				ok = false;

				if (currentValue == 0) {
					ok = confirm('<%= UnicodeLanguageUtil.format(request, "are-you-sure-you-want-to-deactivate-staging-for-x", liveGroup.getDescriptiveName(locale), false) %>');
				}
				else if (currentValue == 1) {
					ok = confirm('<%= UnicodeLanguageUtil.format(request, "are-you-sure-you-want-to-activate-local-staging-for-x", liveGroup.getDescriptiveName(locale), false) %>');
				}
				else if (currentValue == 2) {
					ok = confirm('<%= UnicodeLanguageUtil.format(request, "are-you-sure-you-want-to-activate-remote-staging-for-x", liveGroup.getDescriptiveName(locale), false) %>');
				}
			}
		</c:if>

		if (ok) {
			if (forceDisable) {
				form.fm('forceDisable').val(true);
				form.fm('local').prop('checked', false);
				form.fm('none').prop('checked', true);
				form.fm('redirect').val('<portlet:renderURL><portlet:param name="mvcPath" value="/html/portlet/sites_admin/edit_site.jsp" /><portlet:param name="historyKey" value='<%= renderResponse.getNamespace() + "staging" %>' /></portlet:renderURL>');
				form.fm('remote').prop('checked', false);
			}

			<c:if test="<%= (group != null) && !group.isCompany() %>">
				<portlet:namespace />saveLocales();
			</c:if>

			submitForm(form);
		}
	}
</aui:script>

<aui:script sandbox="<%= true %>">
	var applicationAdapter = $('#<portlet:namespace />customJspServletContextName');

	if (applicationAdapter.length) {
		var publicPages = $('#<portlet:namespace />publicLayoutSetPrototypeId');
		var privatePages = $('#<portlet:namespace />privateLayoutSetPrototypeId');

		var toggleCompatibleSiteTemplates = function(event) {
			var siteTemplate = applicationAdapter.val();

			var options = $();

			options = options.add(publicPages.find('option[data-servletContextName]'));
			options = options.add(privatePages.find('option[data-servletContextName]'));

			options.prop('disabled', false);

			options.filter(':not([data-servletContextName=' + siteTemplate + '])').prop('disabled', true);
		};

		applicationAdapter.on('change', toggleCompatibleSiteTemplates);

		toggleCompatibleSiteTemplates();
	}
</aui:script>