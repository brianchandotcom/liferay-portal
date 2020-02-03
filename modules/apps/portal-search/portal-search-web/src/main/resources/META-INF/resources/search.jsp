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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNotNull(redirect)) {
	portletDisplay.setURLBack(redirect);
}

long groupId = ParamUtil.getLong(request, SearchPortletParameterNames.GROUP_ID);

String format = ParamUtil.getString(request, SearchPortletParameterNames.FORMAT);
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="mvcPath" value="/search.jsp" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm" onSubmit="event.preventDefault();">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" type="hidden" value="<%= ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_CUR) %>" />
	<aui:input name="format" type="hidden" value="<%= format %>" />

	<aui:fieldset id='<%= renderResponse.getNamespace() + "searchContainer" %>'>
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" inlineField="<%= true %>" label="" name="keywords" size="30" title="search" value="<%= HtmlUtil.escape(searchDisplayContext.getKeywords()) %>" />
		<aui:input name="scope" type="hidden" value="<%= searchDisplayContext.getSearchScopeParameterString() %>" />
		<aui:input name="useAdvancedSearchSyntax" type="hidden" value="<%= searchDisplayContext.isUseAdvancedSearchSyntax() %>" />

		<aui:field-wrapper inlineField="<%= true %>">
			<aui:button icon="icon-search" onClick='<%= renderResponse.getNamespace() + "search();" %>' type="submit" value="search" />
		</aui:field-wrapper>
	</aui:fieldset>

	<%@ include file="/main_search.jspf" %>

	<c:if test="<%= searchDisplayContext.isDisplayOpenSearchResults() %>">
		<liferay-ui:panel
			collapsible="<%= true %>"
			cssClass="open-search-panel"
			extended="<%= true %>"
			id="searchOpenSearchPanelContainer"
			persistState="<%= true %>"
			title="open-search"
		>
			<%@ include file="/open_search.jspf" %>
		</liferay-ui:panel>
	</c:if>
</aui:form>

<%
String pageSubtitle = LanguageUtil.get(request, "search-results");
String pageKeywords = LanguageUtil.get(request, "search");

if (Validator.isNotNull(searchDisplayContext.getKeywords())) {
	pageKeywords = searchDisplayContext.getKeywords();

	if (StringUtil.startsWith(pageKeywords, Field.ASSET_TAG_NAMES + StringPool.COLON)) {
		pageKeywords = StringUtil.removeSubstring(pageKeywords, Field.ASSET_TAG_NAMES + StringPool.COLON);
	}
}

PortalUtil.setPageSubtitle(pageSubtitle, request);
PortalUtil.setPageKeywords(pageKeywords, request);
%>

<script>
	var keywordsInput = document.getElementById('<portlet:namespace />keywords');

	if (keywordsInput) {
		keywordsInput.addEventListener('keydown', function(event) {
			if (event.keyCode === 13) {
				<portlet:namespace />search();
			}
		});
	}

	function <portlet:namespace />addSearchProvider() {
		<portlet:resourceURL var="openSearchDescriptionXMLURL">
			<portlet:param name="mvcPath" value="/open_search_description.jsp" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</portlet:resourceURL>

		window.external.AddSearchProvider(
			'<%= openSearchDescriptionXMLURL.toString() %>'
		);
	}

	function <portlet:namespace />search() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.setFormValues(form, {
			<%= SearchContainer.DEFAULT_CUR_PARAM %>: 1
		});

		var keywordsInput = Liferay.Util.getFormElement(form, 'keywords');

		if (keywordsInput) {
			var keywords = keywordsInput.value;

			keywords = keywords.replace(/^\s+|\s+$/, '');

			if (keywords != '') {
				submitForm(form);
			}
		}
	}
</script>