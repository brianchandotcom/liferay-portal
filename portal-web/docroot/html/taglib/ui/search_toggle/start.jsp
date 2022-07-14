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

<%@ include file="/html/taglib/ui/search_toggle/init.jsp" %>

<%
boolean advancedSearch = displayTerms.isAdvancedSearch();
%>

<div class="input-group lfr-search-toggle">
	<div class="input-group-item input-group-prepend" id="<%= id %>simple">
		<input class="form-control search-query" <%= advancedSearch ? "disabled" : StringPool.BLANK %> id="<%= id + DisplayTerms.KEYWORDS %>" name="<portlet:namespace /><%= DisplayTerms.KEYWORDS %>" placeholder="<liferay-ui:message key="search" />..." title="search" type="text" value="<%= HtmlUtil.escapeAttribute(displayTerms.getKeywords()) %>" />
	</div>

	<div class="input-group-append input-group-item input-group-item-shrink">
		<button class="btn btn-monospaced btn-secondary" type="submit">
			<aui:icon image="search" markupView="lexicon" />
		</button>
	</div>

	<div class="input-group-append input-group-item input-group-item-shrink">
		<button class="btn btn-monospaced btn-secondary toggle-advanced" id="<%= id %>toggleAdvanced" type="button">
			<aui:icon image="caret-bottom" markupView="lexicon" />
		</button>
	</div>
</div>

<div class="taglib-search-toggle-advanced-wrapper">
	<div class="taglib-search-toggle-advanced <%= advancedSearch ? "toggler-content-expanded" : "toggler-content-collapsed" %>" id="<%= id %>advanced">
		<input id="<%= id + DisplayTerms.ADVANCED_SEARCH %>" name="<portlet:namespace /><%= DisplayTerms.ADVANCED_SEARCH %>" type="hidden" value="<%= advancedSearch %>" />

		<aui:button cssClass="close" name="closeAdvancedSearch" value="&times;" />

		<div class="taglib-search-toggle-advanced-content" id="<%= id %>advancedContent">
			<div class="form-group form-group-inline">
				<aui:select label="match" name="<%= DisplayTerms.AND_OPERATOR %>" wrapperCssClass="match-fields">
					<aui:option label="all" selected="<%= displayTerms.isAndOperator() %>" value="<%= true %>" />
					<aui:option label="any" selected="<%= !displayTerms.isAndOperator() %>" value="<%= false %>" />
				</aui:select>

				<span class="match-fields-legend">
					<liferay-ui:message key="of-the-following-fields" />
				</span>
			</div>