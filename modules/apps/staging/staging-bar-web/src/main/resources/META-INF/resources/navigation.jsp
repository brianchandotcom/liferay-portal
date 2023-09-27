<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String navigationName = ParamUtil.getString(request, "navigationName");
%>

<c:if test="<%= Validator.isNotNull(navigationName) %>">
	<clay:navigation-bar
		navigationItems="<%=
			new JSPNavigationItemList(pageContext) {
				{
					add(
						navigationItem -> {
							navigationItem.setActive(true);
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, navigationName));
						});
				}
			}
		%>"
	/>
</c:if>