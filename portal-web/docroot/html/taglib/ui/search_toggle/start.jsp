<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<div class="taglib-search-toggle">
	<aui:input id="<%= id + displayTerms.ADVANCED_SEARCH %>" name="<%= displayTerms.ADVANCED_SEARCH %>" type="hidden" value='<%= clickValue.equals("basic") ? false : true %>' />

	<div class="<%= basicFormCssClass %>" id="<%= id %>basic">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(buttonLabel) %>">
				<span class="aui-search-bar">
					<aui:input id="<%= id + displayTerms.KEYWORDS %>" inlineField="<%= true %>" label="" name="<%= displayTerms.KEYWORDS %>" size="30" title="search" value="<%= displayTerms.getKeywords() %>" />

					<aui:button type="submit" value="<%= buttonLabel %>" />
				</span>

				<div class="<%= id %>toggle-link">
					<aui:a href="javascript:;" tabindex="-1"><liferay-ui:message key="advanced" /> &raquo;</aui:a>
				</div>
			</c:when>
			<c:otherwise>
				<aui:input id="<%= id + displayTerms.KEYWORDS %>" label="search" name="<%= displayTerms.KEYWORDS %>" size="30" value="<%= displayTerms.getKeywords() %>" />

				&nbsp;<aui:a href="javascript:;" tabindex="-1"><liferay-ui:message key="advanced" /> &raquo;</aui:a>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="<%= advancedFormCssClass %>" id="<%= id %>advanced">
		<liferay-util:buffer var="andOperator">
			<aui:select cssClass="inline-control" inlineField="<%= true %>" label="" name="<%= displayTerms.AND_OPERATOR %>">
				<aui:option label="all" selected="<%= displayTerms.isAndOperator() %>" value="1" />
				<aui:option label="any" selected="<%= !displayTerms.isAndOperator() %>" value="0" />
			</aui:select>
		</liferay-util:buffer>

		<liferay-ui:message arguments="<%= andOperator %>" key="match-x-of-the-following-fields" />