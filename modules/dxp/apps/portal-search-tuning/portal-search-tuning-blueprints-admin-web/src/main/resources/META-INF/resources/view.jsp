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

<liferay-ui:error key="<%= BlueprintsAdminWebKeys.ERROR %>">
	<liferay-ui:message arguments="<%= SessionErrors.get(liferayPortletRequest, BlueprintsAdminWebKeys.ERROR) %>" key="error-x" />
</liferay-ui:error>

<%
final String tabs = ParamUtil.getString(request, "tabs", BlueprintsAdminTabNames.BLUEPRINTS);

PortletURL renderURL = renderResponse.createRenderURL();
%>

<clay:navigation-bar
	inverted="<%= false %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs.equals(BlueprintsAdminTabNames.BLUEPRINTS));
						navigationItem.setHref(renderURL, "tabs", BlueprintsAdminTabNames.BLUEPRINTS);
						navigationItem.setLabel(LanguageUtil.get(request, "blueprints"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(tabs.equals(BlueprintsAdminTabNames.ELEMENTS));
						navigationItem.setHref(renderURL, "tabs", BlueprintsAdminTabNames.ELEMENTS);
						navigationItem.setLabel(LanguageUtil.get(request, "elements"));
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test="<%= tabs.equals(BlueprintsAdminTabNames.ELEMENTS) %>">
		<liferay-util:include page="/view_elements.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/view_blueprints.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>