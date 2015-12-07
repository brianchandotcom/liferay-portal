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
boolean useIndexer = GetterUtil.getBoolean(request.getAttribute("liferay-ui:user-group-search-container-results:useIndexer"));
LinkedHashMap<String, Object> userGroupParams = (LinkedHashMap<String, Object>)request.getAttribute("liferay-ui:user-group-search-container-results:userGroupParams");
SearchContainer userGroupSearchContainer = (SearchContainer)request.getAttribute("liferay-ui:user-group-search-container-results:searchContainer");

Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(UserGroup.class);
%>

<liferay-ui:search-container id="<%= userGroupSearchContainer.getId(request, namespace) %>" searchContainer="<%= userGroupSearchContainer %>">
	<liferay-ui:search-container-results>
		<c:choose>
			<c:when test="<%= useIndexer && indexer.isIndexerEnabled() && PropsValues.USER_GROUPS_SEARCH_WITH_INDEX %>">

				<%
				BaseModelSearchResult<UserGroup> baseModelSearchResult = null;

				userGroupParams.put("expandoAttributes", searchTerms.getKeywords());

				Sort sort = SortFactoryUtil.getSort(UserGroup.class, searchContainer.getOrderByCol(), searchContainer.getOrderByType());

				baseModelSearchResult = UserGroupLocalServiceUtil.searchUserGroups(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), sort);

				results = baseModelSearchResult.getBaseModels();

				searchContainer.setResults(results);
				searchContainer.setTotal(baseModelSearchResult.getLength());
				%>

			</c:when>
			<c:otherwise>

				<%
				total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams);

				searchContainer.setTotal(total);

				results = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

				searchContainer.setResults(results);
				%>

			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-results>
</liferay-ui:search-container>