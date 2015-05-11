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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "browse");

long groupId = ParamUtil.getLong(request, "groupId", GroupConstants.DEFAULT_PARENT_GROUP_ID);
String sitesListView = ParamUtil.get(request, "sitesListView", SiteConstants.LIST_VIEW_TREE);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("sitesListView", sitesListView);

String portletURLString = portletURL.toString();

PortletURL searchURL = renderResponse.createRenderURL();

searchURL.setParameter("sitesListView", SiteConstants.LIST_VIEW_FLAT_SITES);
searchURL.setParameter("toolbarItem", "view-all-sites");

pageContext.setAttribute("searchURL", searchURL);

String searchURLString = searchURL.toString();
%>

<aui:form action="<%= searchURLString %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= portletURLString %>" />
	<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />

	<liferay-ui:error exception="<%= NoSuchLayoutSetException.class %>">

		<%
		NoSuchLayoutSetException nslse = (NoSuchLayoutSetException)errorException;

		PKParser pkParser = new PKParser(nslse.getMessage());

		Group group = GroupLocalServiceUtil.getGroup(pkParser.getLong("groupId"));
		%>

		<liferay-ui:message arguments="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>" key="site-x-does-not-have-any-private-pages" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteCurrentGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-you-are-accessing-the-site" />
	<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteGroupThatHasChild.class %>" message="you-cannot-delete-sites-that-have-subsites" />
	<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteSystemGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />

	<c:choose>
		<c:when test="<%= sitesListView.equals(SiteConstants.LIST_VIEW_FLAT_SITES) %>">
			<%@ include file="/view_flat_sites.jspf" %>
		</c:when>
		<c:otherwise>
			<%@ include file="/view_tree.jspf" %>
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

	function <portlet:namespace />deleteSites() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('redirect').val(form.fm('sitesRedirect').val());
			form.fm('deleteGroupIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteGroups" />');
		}
	}
</aui:script>