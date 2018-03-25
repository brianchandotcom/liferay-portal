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
portletDisplay.setShowBackIcon(true);

LiferayPortletURL usersAdminURL = liferayPortletResponse.createLiferayPortletURL(UsersAdminPortletKeys.USERS_ADMIN, PortletRequest.RENDER_PHASE);

portletDisplay.setURLBack(usersAdminURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "export-personal-data")));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(LanguageUtil.get(request, "processes"));
					});
			}
		}
	%>'
/>

<aui:form cssClass="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="no-personal-data-export-processes-were-found"
		total="<%= 0 %>"
	>
		<liferay-ui:search-container-results
			results="<%= null %>"
		/>

		<liferay-ui:search-iterator
			markupView="lexicon"
			searchContainer="<%= null %>"
		/>
	</liferay-ui:search-container>
</aui:form>