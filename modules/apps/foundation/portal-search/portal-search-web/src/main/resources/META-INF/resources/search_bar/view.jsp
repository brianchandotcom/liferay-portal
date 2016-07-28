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

<%@ include file="/search_bar/init.jsp" %>

<portlet:defineObjects />

<%
SearchBarPortletDisplayContext searchBarPortletDisplayContext = (SearchBarPortletDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(com.liferay.portal.search.web.internal.searchbar.constants.SearchBarWebKeys.DISPLAY_CONTEXT));
%>

<portlet:actionURL name="redirectSearchBar" var="portletURL">
	<portlet:param name="mvcActionCommandName" value="redirectSearchBar" />
</portlet:actionURL>

<aui:form action="<%= portletURL %>" method="post" name="fm">
	<aui:fieldset id="searchContainer">
		<div class="input-group search-bar">
			<aui:field-wrapper cssClass="search-field" inlineField="<%= true %>">
				<aui:input
					autoFocus="true"
					cssClass="search-bar-input"
					label=""
					name="<%= searchBarPortletDisplayContext.getKeywordsParameterName() %>"
					placeholder="search-..."
					title="search"
					type="text"
					value="<%= searchBarPortletDisplayContext.getKeywords() %>"
				/>
			</aui:field-wrapper>

			<c:choose>
				<c:when test="<%= searchBarPortletDisplayContext.isScopePreferenceLetTheUserChoose() %>">
					<aui:field-wrapper cssClass="search-field" inlineField="<%= true %>">
						<aui:select cssClass="search-select" label="" name="scope" title="scope">
							<c:if test="<%= searchBarPortletDisplayContext.isScopePreferenceEverythingAvailable() %>">
								<aui:option label="all-sites" selected="<%= searchBarPortletDisplayContext.isScopeEverythingSelected() %>" value="everything" />
							</c:if>

							<aui:option label="this-site" selected="<%= searchBarPortletDisplayContext.isScopeSiteSelected() %>" value="this-site" />
						</aui:select>
					</aui:field-wrapper>
				</c:when>
				<c:otherwise>
					<aui:input name="scope" type="hidden" value="<%= searchBarPortletDisplayContext.getScopeParameterString() %>" />
				</c:otherwise>
			</c:choose>

			<aui:field-wrapper cssClass="input-group-btn search-field" inlineField="<%= true %>">
				<aui:button icon="icon-search" primary="<%= false %>" type="submit" value="" />
			</aui:field-wrapper>
		</div>
	</aui:fieldset>
</aui:form>