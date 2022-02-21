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

<%@ include file="/search/init.jsp" %>

<div class="kb-search-header">
	<liferay-util:include page="/search/view.jsp" servletContext="<%= application %>" />
</div>

<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="mvcPath" value="/search/search.jsp" />
	<portlet:param name="keywords" value='<%= ParamUtil.getString(request, "keywords") %>' />
</liferay-portlet:renderURL>

<%
KBSearchDisplayContext kbSearchDisplayContext = new KBSearchDisplayContext(request, iteratorURL);
%>

<liferay-ui:search-container
	searchContainer="<%= kbSearchDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.util.Tuple"
		modelVar="tuple"
	>
		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="mvcPath" value="/search/view_article.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="resourcePrimKey" value="<%= (String)tuple.getObject(0) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="title"
			orderable="<%= true %>"
			value="<%= HtmlUtil.escape((String)tuple.getObject(1)) %>"
		/>

		<c:if test="<%= kbSearchPortletInstanceConfiguration.showKBArticleAuthorColumn() %>">
			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="author"
				orderable="<%= true %>"
				orderableProperty="user-name"
				value="<%= HtmlUtil.escape((String)tuple.getObject(2)) %>"
			/>
		</c:if>

		<c:if test="<%= kbSearchPortletInstanceConfiguration.showKBArticleCreateDateColumn() %>">
			<liferay-ui:search-container-column-date
				cssClass="kb-column-no-wrap"
				href="<%= rowURL %>"
				name="create-date"
				orderable="<%= true %>"
				value="<%= (Date)tuple.getObject(3) %>"
			/>
		</c:if>

		<c:if test="<%= kbSearchPortletInstanceConfiguration.showKBArticleModifiedDateColumn() %>">
			<liferay-ui:search-container-column-date
				cssClass="kb-column-no-wrap"
				href="<%= rowURL %>"
				name="modified-date"
				orderable="<%= true %>"
				value="<%= (Date)tuple.getObject(4) %>"
			/>
		</c:if>

		<c:if test="<%= kbSearchPortletInstanceConfiguration.showKBArticleViewsColumn() %>">
			<liferay-ui:search-container-column-text
				buffer="buffer"
				cssClass="kb-column-no-wrap"
				href="<%= rowURL %>"
			>

				<%
				KBArticle kbArticle = KBArticleLocalServiceUtil.fetchLatestKBArticle(GetterUtil.getLong((String)tuple.getObject(0)), WorkflowConstants.STATUS_APPROVED);

				long viewCount = (kbArticle != null) ? kbArticle.getViewCount() : 0;

				buffer.append(viewCount);

				buffer.append(StringPool.SPACE);
				buffer.append((viewCount == 1) ? LanguageUtil.get(request, "view") : LanguageUtil.get(request, "views"));
				%>

			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>