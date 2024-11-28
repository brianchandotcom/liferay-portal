<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
portletDisplay.setBeta(true);
%>

<liferay-ui:error embed="<%= false %>" exception="<%= NoSuchObjectEntryException.class %>" message="the-object-could-not-be-found" />
<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />

<c:choose>
	<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPD-37531") %>'>

		<%
		String datasets = ParamUtil.getString(request, "datasets", "custom");
		%>

		<clay:navigation-bar
			navigationItems='<%=
				new JSPNavigationItemList(pageContext) {
					{
						add(
							navigationItem -> {
								navigationItem.setActive(datasets.equals("custom"));
								navigationItem.setHref(renderResponse.createRenderURL());
								navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "custom-data-sets"));
							});

						add(
							navigationItem -> {
								navigationItem.setActive(datasets.equals("system"));
								navigationItem.setHref(renderResponse.createRenderURL(), "datasets", "system");
								navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "system-data-sets"));
							});
					}
				}
			%>'
		/>

		<c:choose>
			<c:when test='<%= datasets.equals("custom") %>'>
				<liferay-util:include page="/custom_data_sets.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/system_data_sets.jsp" servletContext="<%= application %>" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/custom_data_sets.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>