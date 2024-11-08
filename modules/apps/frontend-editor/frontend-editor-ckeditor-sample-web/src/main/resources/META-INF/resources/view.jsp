<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "ckeditor5");
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("ckeditor5"));
						navigationItem.setHref(renderResponse.createRenderURL());
						navigationItem.setLabel("CKEditor 5");
					});
				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("ckeditor4"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor4");
						navigationItem.setLabel("CKEditor 4");
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test='<%= StringUtil.equals(navigation, "ckeditor5") %>'>
		<liferay-util:include page="/ckeditor5/view.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/ckeditor4/view.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>