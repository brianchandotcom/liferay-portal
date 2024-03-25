<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/search_results/init.jsp" %>

<%
long commerceAccountId = GetterUtil.getLong(request.getAttribute("liferay-commerce-ui:search-results:commerceAccountId"));
long groupId = GetterUtil.getLong(request.getAttribute("liferay-commerce-ui:search-results:groupId"));
long plid = GetterUtil.getLong(request.getAttribute("liferay-commerce-ui:search-results:plid"));
String searchURL = (String)request.getAttribute("liferay-commerce-ui:search-results:searchURL");
%>

<div>
	<react:component
		module="{searchResults} from commerce-frontend-taglib"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"commerceAccountId", commerceAccountId
			).put(
				"groupId", groupId
			).put(
				"plid", plid
			).put(
				"searchURL", searchURL
			).build()
		%>'
	/>
</div>