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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%@ include file="/html/portlet/layouts_admin/init_attributes.jspf" %>

<%
boolean viewTree = ParamUtil.getBoolean(request, "viewTree");
boolean viewLayout = ParamUtil.getBoolean(request, "viewLayout");

if ((renderResponse != null) && !portletName.equals(PortletKeys.GROUP_PAGES) && !portletName.equals(PortletKeys.MY_PAGES)) {
	if (group.isLayoutPrototype()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "page-template"), null);

		PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(), redirectURL.toString());
	}
	else {
		PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(), null);
	}

	if (!group.isLayoutPrototype()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, pagesName), redirectURL.toString());
	}
}
%>

<c:if test="<%= viewTree %>">
	<div id="<portlet:namespace />viewTree">
		<liferay-util:include page="/html/portlet/layouts_admin/tree_js.jsp">
			<liferay-util:param name="treeId" value="layoutsTree" />
		</liferay-util:include>
	</div>
</c:if>

<c:if test="<%= viewLayout %>">
	<div id="<portlet:namespace />viewLayout">
		<c:choose>
			<c:when test="<%= selPlid > 0 %>">
				<liferay-util:include page="/html/portlet/layouts_admin/edit_layout.jsp" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/html/portlet/layouts_admin/edit_layout_set.jsp" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>