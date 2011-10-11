<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
String viewOrganizationsRedirect = ParamUtil.getString(request, "viewOrganizationsRedirect");
String redirect = ParamUtil.getString(request, "redirect", viewOrganizationsRedirect);

Group group = (Group)request.getAttribute(WebKeys.GROUP);

long groupId = BeanParamUtil.getLong(group, request, "groupId");

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

String[] mainSections = PropsValues.SITES_FORM_ADD_MAIN;
String[] seoSections = PropsValues.SITES_FORM_ADD_SEO;
String[] advancedSections = PropsValues.SITES_FORM_ADD_ADVANCED;

if (group != null) {
	mainSections = PropsValues.SITES_FORM_UPDATE_MAIN;
	seoSections = PropsValues.SITES_FORM_UPDATE_SEO;
	advancedSections = PropsValues.SITES_FORM_UPDATE_ADVANCED;
}

String[][] categorySections = {mainSections, seoSections, advancedSections};
%>

<c:if test="<%= portletName.equals(PortletKeys.SITES_ADMIN) %>">
	<liferay-util:include page="/html/portlet/sites_admin/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value='<%= (group == null) ? "add" : "view-all" %>' />
	</liferay-util:include>
</c:if>

<%
boolean localizeTitle = true;
String title = "new-site";

if (group != null) {
	localizeTitle= false;
	title = group.getDescriptiveName();
}
else if (layoutSetPrototype != null) {
	localizeTitle= false;
	title = layoutSetPrototype.getName(locale);
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= localizeTitle %>"
	title="<%= title %>"
/>

<portlet:actionURL var="editSiteURL">
	<portlet:param name="struts_action" value="/sites_admin/edit_site" />
</portlet:actionURL>

<aui:form action="<%= editSiteURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveGroup();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />

	<%
	request.setAttribute("site.group", group);
	request.setAttribute("site.liveGroup", liveGroup);
	request.setAttribute("site.liveGroupId", new Long(liveGroupId));
	request.setAttribute("site.stagingGroup", stagingGroup);
	request.setAttribute("site.stagingGroupId", new Long(stagingGroupId));
	request.setAttribute("site.liveGroupTypeSettings", liveGroupTypeSettings);
	request.setAttribute("site.layoutSetPrototype", layoutSetPrototype);
	%>

	<liferay-util:buffer var="htmlBottom">
		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</liferay-util:buffer>

	<liferay-ui:form-navigator
		categoryNames="<%= _CATEGORY_NAMES %>"
		categorySections="<%= categorySections %>"
		htmlBottom="<%= htmlBottom %>"
		jspPath="/html/portlet/sites_admin/site/"
		showButtons="<%= false %>"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveGroup() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (group == null) ? Constants.ADD : Constants.UPDATE %>";

		var ok = true;

		<c:if test="<%= liveGroup != null %>">
			A = AUI();

			var selectEl = A.one('#<portlet:namespace />stagingType');

			<c:choose>
				<c:when test="<%= liveGroup.isStaged() && !liveGroup.isStagedRemotely() %>">
					var oldValue = 1;
				</c:when>
				<c:when test="<%= liveGroup.isStaged() && liveGroup.isStagedRemotely() %>">
					var oldValue = 2;
				</c:when>
				<c:otherwise>
					var oldValue = 0;
				</c:otherwise>
			</c:choose>

			if (selectEl && (selectEl.val() != oldValue)) {
				var currentValue = selectEl.val();

				ok = false;

				if (0 == currentValue) {
					ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-deactivate-staging-for-x", liveGroup.getDescriptiveName()) %>');
				}
				else if (1 == currentValue) {
					ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-activate-local-staging-for-x", liveGroup.getDescriptiveName()) %>');
				}
				else if (2 == currentValue) {
					ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-activate-remote-staging-for-x", liveGroup.getDescriptiveName()) %>');
				}
			}
		</c:if>

		if (ok) {
			submitForm(document.<portlet:namespace />fm);
		}
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</aui:script>

<aui:script use="aui-base">
	var applicationAdapter = A.one('#<portlet:namespace />customJspServletContextName');

	if (applicationAdapter) {
		var publicPages = A.one('#<portlet:namespace />publicLayoutSetPrototypeId');
		var privatePages = A.one('#<portlet:namespace />privateLayoutSetPrototypeId');

		var toggleCompatibleSiteTemplates = function(event) {
			var siteTemplate = applicationAdapter.val();

			var options = A.all([]);

			if (publicPages) {
				options = options.concat(publicPages.all('option[data-servletContextName]'));
			}

			if (privatePages) {
				options = options.concat(privatePages.all('option[data-servletContextName]'));
			}

			options.attr('disabled', false);

			options.filter(':not([data-servletContextName=' + siteTemplate + '])').attr('disabled', true);
		};

		applicationAdapter.on('change', toggleCompatibleSiteTemplates);

		toggleCompatibleSiteTemplates();
	}
</aui:script>

<%
if (group != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, HtmlUtil.escape(group.getDescriptiveName()), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-site"), currentURL);
}
%>

<%!
private static String[] _CATEGORY_NAMES = {"basic-information", "search-engine-optimization", "advanced"};
%>