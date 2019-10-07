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

<%@ include file="/html/taglib/init.jsp" %>

<%
UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)request.getAttribute("liferay-ui:user-group-search-container-results:searchTerms");
LinkedHashMap<String, Object> userGroupParams = (LinkedHashMap<String, Object>)request.getAttribute("liferay-ui:user-group-search-container-results:userGroupParams");
SearchContainer userGroupSearchContainer = (SearchContainer)request.getAttribute("liferay-ui:user-group-search-container-results:searchContainer");
%>

<liferay-ui:search-container
	id="<%= userGroupSearchContainer.getId(request, namespace) %>"
	searchContainer="<%= userGroupSearchContainer %>"
>
	<liferay-ui:search-container-results>

		<%
		String keywords = searchTerms.getKeywords();

		if (Validator.isNotNull(keywords)) {
			userGroupParams.put("expandoAttributes", keywords);
		}

		total = UserGroupServiceUtil.searchCount(company.getCompanyId(), keywords, userGroupParams);

		searchContainer.setTotal(total);

		results = UserGroupServiceUtil.search(company.getCompanyId(), keywords, userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

		searchContainer.setResults(results);
		%>

	</liferay-ui:search-container-results>
</liferay-ui:search-container>