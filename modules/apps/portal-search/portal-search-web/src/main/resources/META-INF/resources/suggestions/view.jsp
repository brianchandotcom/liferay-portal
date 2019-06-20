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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.suggestions.display.context.SuggestionDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.suggestions.display.context.SuggestionsPortletDisplayContext" %>

<%
SuggestionsPortletDisplayContext suggestionsPortletDisplayContext = (SuggestionsPortletDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));
%>

<div class="search-suggested-spelling">
	<c:if test="<%= suggestionsPortletDisplayContext.hasSpellCheckSuggestion() %>">
		<ul class="list-inline suggested-keywords">
			<li class="label label-default">
				<liferay-ui:message key="did-you-mean" />:
			</li>
			<li>

				<%
				SuggestionDisplayContext suggestionDisplayContext = suggestionsPortletDisplayContext.getSpellCheckSuggestion();
				%>

				<aui:a href="<%= suggestionDisplayContext.getURL() %>">
					<%= suggestionDisplayContext.getSuggestedKeywordsFormatted() %>
				</aui:a>
			</li>
		</ul>
	</c:if>

	<c:if test="<%= suggestionsPortletDisplayContext.hasRelatedQueriesSuggestions() %>">
		<ul class="list-inline related-queries">
			<li class="label label-default">
				<liferay-ui:message key="related-queries" />:
			</li>

			<%
			for (SuggestionDisplayContext suggestionDisplayContext : suggestionsPortletDisplayContext.getRelatedQueriesSuggestions()) {
			%>

				<li>
					<aui:a href="<%= suggestionDisplayContext.getURL() %>">
						<%= suggestionDisplayContext.getSuggestedKeywordsFormatted() %>
					</aui:a>
				</li>

			<%
			}
			%>

		</ul>
	</c:if>
</div>