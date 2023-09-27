<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/search_container/init.jsp" %>

<%
String cssClass = GetterUtil.getString(request.getAttribute("liferay-ui:search-container-column-user:cssClass"));
Date date = GetterUtil.getDate(request.getAttribute("liferay-ui:search-container-column-user:date"), DateFormatFactoryUtil.getDate(locale), null);
boolean showDetails = GetterUtil.getBoolean(request.getAttribute("liferay-ui:search-container-column-user:showDetails"));

long userId = GetterUtil.getLong(request.getAttribute("liferay-ui:search-container-column-user:userId"));

User user2 = UserLocalServiceUtil.fetchUser(userId);
%>

<div class="user-info">
	<liferay-ui:user-portrait
		cssClass="<%= cssClass %>"
		user="<%= user2 %>"
	/>

	<c:if test="<%= showDetails %>">
		<div class="user-details">
			<div class="user-name">
				<%= HtmlUtil.escape((user2 != null) ? user2.getFullName() : LanguageUtil.get(resourceBundle, "anonymous")) %>
			</div>

			<c:if test="<%= date != null %>">
				<div class="date-info">
					<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - date.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
				</div>
			</c:if>
		</div>
	</c:if>
</div>