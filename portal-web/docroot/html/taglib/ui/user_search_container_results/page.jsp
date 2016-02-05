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
UserSearchTerms searchTerms = (UserSearchTerms)request.getAttribute("liferay-ui:user-search-container-results:searchTerms");
boolean useIndexer = GetterUtil.getBoolean(request.getAttribute("liferay-ui:user-search-container-results:useIndexer"));
LinkedHashMap<String, Object> userParams = (LinkedHashMap<String, Object>)request.getAttribute("liferay-ui:user-search-container-results:userParams");
SearchContainer userSearchContainer = (SearchContainer)request.getAttribute("liferay-ui:user-search-container-results:searchContainer");

if (Validator.isNotNull(searchTerms.getKeywords())) {
	useIndexer = true;
}

Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);
%>

<liferay-ui:search-container id="<%= userSearchContainer.getId(request, namespace) %>" searchContainer="<%= userSearchContainer %>">
	<liferay-ui:search-container-results>
		<c:choose>
			<c:when test="<%= useIndexer && indexer.isIndexerEnabled() && PropsValues.USERS_SEARCH_WITH_INDEX %>">

				<%
				userParams.put("expandoAttributes", searchTerms.getKeywords());

				Sort sort = SortFactoryUtil.getSort(User.class, searchContainer.getOrderByCol(), searchContainer.getOrderByType());

				BaseModelSearchResult<User> baseModelSearchResult = null;

				if (searchTerms.isAdvancedSearch()) {
					baseModelSearchResult = UserLocalServiceUtil.searchUsers(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.getStatus(), userParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), sort);
				}
				else {
					baseModelSearchResult = UserLocalServiceUtil.searchUsers(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams, searchContainer.getStart(), searchContainer.getEnd(), sort);
				}

				results = baseModelSearchResult.getBaseModels();

				searchContainer.setResults(results);
				searchContainer.setTotal(baseModelSearchResult.getLength());
				%>

			</c:when>
			<c:otherwise>

				<%
				userParams.put("forceDatabase", Boolean.TRUE);

				if (searchTerms.isAdvancedSearch()) {
					total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.getStatus(), userParams, searchTerms.isAndOperator());

					results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.getStatus(), userParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
				}
				else {
					total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams);

					searchContainer.setTotal(total);

					results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
				}

				searchContainer.setTotal(total);
				searchContainer.setResults(results);
				%>

			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-results>
</liferay-ui:search-container>