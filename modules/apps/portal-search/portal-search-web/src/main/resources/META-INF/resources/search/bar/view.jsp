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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletDisplayContext" %>

<liferay-theme:defineObjects />

<%
SearchBarPortletDisplayContext searchBarPortletDisplayContext = (SearchBarPortletDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));
%>

<c:choose>
	<c:when test="<%= searchBarPortletDisplayContext.isDestinationUnreachable() %>">
		<div class="alert alert-info text-center">
			<liferay-ui:message key="this-search-bar-is-not-visible-to-users-yet" />

			<aui:a href="javascript:;" onClick="<%= portletDisplay.getURLConfigurationJS() %>"><liferay-ui:message key="set-up-its-destination-to-make-it-visible" /></aui:a>
		</div>
	</c:when>
	<c:otherwise>
		<aui:form action="<%= searchBarPortletDisplayContext.getSearchURL() %>" method="get" name="fm">
			<c:if test="<%= !Validator.isBlank(searchBarPortletDisplayContext.getPaginationStartParameterName()) %>">
				<input class="search-bar-reset-start-page" name="<%= searchBarPortletDisplayContext.getPaginationStartParameterName() %>" type="hidden" value="0" />
			</c:if>

			<aui:fieldset cssClass="search-bar">
				<aui:input cssClass="search-bar-empty-search-input" name="emptySearchEnabled" type="hidden" value="<%= searchBarPortletDisplayContext.isEmptySearchEnabled() %>" />

				<div class="input-group <%= searchBarPortletDisplayContext.isLetTheUserChooseTheSearchScope() ? "search-bar-scope" : "search-bar-simple" %>">
					<c:choose>
						<c:when test="<%= searchBarPortletDisplayContext.isLetTheUserChooseTheSearchScope() %>">
							<aui:input autoFocus="<%= true %>" cssClass="search-bar-keywords-input" data-qa-id="searchInput" label="" name="<%= HtmlUtil.escapeAttribute(searchBarPortletDisplayContext.getKeywordsParameterName()) %>" placeholder='<%= LanguageUtil.get(request, "search-...") %>' title="search" type="text" useNamespace="<%= false %>" value="<%= searchBarPortletDisplayContext.getKeywords() %>" wrapperCssClass="input-group-item input-group-prepend search-bar-keywords-input-wrapper" />

							<aui:select cssClass="search-bar-scope-select" label="" name="<%= HtmlUtil.escapeAttribute(searchBarPortletDisplayContext.getScopeParameterName()) %>" title="scope" useNamespace="<%= false %>" wrapperCssClass="input-group-item input-group-item-shrink input-group-prepend search-bar-search-select-wrapper">
								<aui:option label="this-site" selected="<%= searchBarPortletDisplayContext.isSelectedCurrentSiteSearchScope() %>" value="<%= searchBarPortletDisplayContext.getCurrentSiteSearchScopeParameterString() %>" />

								<c:if test="<%= searchBarPortletDisplayContext.isAvailableEverythingSearchScope() %>">
									<aui:option label="everything" selected="<%= searchBarPortletDisplayContext.isSelectedEverythingSearchScope() %>" value="<%= searchBarPortletDisplayContext.getEverythingSearchScopeParameterString() %>" />
								</c:if>
							</aui:select>

							<div class="input-group-append input-group-item input-group-item-shrink search-bar-search-button-wrapper">
								<clay:button
									ariaLabel='<%= LanguageUtil.get(request, "submit") %>'
									elementClasses="search-bar-search-button"
									icon="search"
									style="secondary"
									type="submit"
								/>
							</div>
						</c:when>
						<c:otherwise>
							<div class="input-group-item search-bar-keywords-input-wrapper">
								<input class="form-control input-group-inset input-group-inset-after search-bar-keywords-input" data-qa-id="searchInput" id="<portlet:namespace /><%= StringUtil.randomId() %>" name="<%= HtmlUtil.escapeAttribute(searchBarPortletDisplayContext.getKeywordsParameterName()) %>" placeholder="<%= LanguageUtil.get(request, "search-...") %>" title="<%= LanguageUtil.get(request, "search") %>" type="text" value="<%= HtmlUtil.escapeAttribute(searchBarPortletDisplayContext.getKeywords()) %>" />

								<aui:input name="<%= HtmlUtil.escapeAttribute(searchBarPortletDisplayContext.getScopeParameterName()) %>" type="hidden" value="<%= searchBarPortletDisplayContext.getScopeParameterValue() %>" />

								<div class="input-group-inset-item input-group-inset-item-after search-bar-search-button-wrapper">
									<clay:button
										ariaLabel='<%= LanguageUtil.get(request, "submit") %>'
										elementClasses="search-bar-search-button"
										icon="search"
										style="unstyled"
										type="submit"
									/>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</aui:fieldset>
		</aui:form>

		<aui:script use="liferay-search-bar">
			new Liferay.Search.SearchBar(A.one('#<portlet:namespace/>fm'));
		</aui:script>
	</c:otherwise>
</c:choose>