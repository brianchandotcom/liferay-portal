<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "sxpBlueprints");
%>

<clay:navigation-bar
	inverted="<%= false %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("sxpBlueprints"));
						navigationItem.setHref(renderResponse.createRenderURL(), "tabs1", "sxpBlueprints", "mvcRenderCommandName", "/sxp_blueprints_admin/view_sxp_blueprints");
						navigationItem.setLabel(LanguageUtil.get(request, "blueprints"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("sxpElements"));
						navigationItem.setHref(renderResponse.createRenderURL(), "tabs1", "sxpElements", "mvcRenderCommandName", "/sxp_blueprints_admin/view_sxp_elements", "hidden", Boolean.FALSE);
						navigationItem.setLabel(LanguageUtil.get(request, "elements"));
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test='<%= tabs1.equals("sxpElements") %>'>
		<liferay-util:include page="/view_sxp_elements.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/view_sxp_blueprints.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>