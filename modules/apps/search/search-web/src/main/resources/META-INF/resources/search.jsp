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

long groupId = ParamUtil.getLong(request, "groupId");

String keywords = ParamUtil.getString(request, "keywords");

String format = ParamUtil.getString(request, "format");

PortletURL portletURL = PortletURLUtil.getCurrent(renderRequest, renderResponse);

request.setAttribute("search.jsp-portletURL", portletURL);
request.setAttribute("search.jsp-returnToFullPageURL", portletDisplay.getURLBack());
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="mvcPath" value="/search.jsp" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm" onSubmit="event.preventDefault();">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" type="hidden" value="<%= ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_CUR) %>" />
	<aui:input name="format" type="hidden" value="<%= format %>" />

	<aui:fieldset id="searchContainer">
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" inlineField="<%= true %>" label="" name="keywords" size="30" title="search" value="<%= HtmlUtil.escape(keywords) %>" />

		<aui:field-wrapper inlineField="<%= true %>">
			<aui:button icon="icon-search" onClick='<%= renderResponse.getNamespace() + "search();" %>' value="search" />
			<aui:button icon="icon-remove" onClick='<%= renderResponse.getNamespace() + "clearSearch();" %>' value="clear" />
		</aui:field-wrapper>
	</aui:fieldset>

	<div class="lfr-token-list" id="<portlet:namespace />searchTokens">
		<div class="lfr-token-list-content" id="<portlet:namespace />searchTokensContent"></div>
	</div>

	<aui:script use="liferay-token-list">
		Liferay.namespace('Search').tokenList = new Liferay.TokenList(
			{
				after: {
					close: function(event) {
						var item = event.item;

						var fieldValues = item.attr('data-fieldValues').split();

						var form = A.one('#<portlet:namespace />fm');

						fieldValues.forEach(
							function(item, index) {
								var values = item.split('|');

								var field = form.one('#' + values[0]);

								if (field) {
									field.val(values[1]);
								}
							}
						);

						var clearFields = A.all('#' + event.item.attr('data-clearFields').split().join(',#'));

						clearFields.remove();

						if (fieldValues.length || clearFields.size()) {
							submitForm(document.<portlet:namespace />fm);
						}
					}
				},
				boundingBox: '#<portlet:namespace />searchTokens',
				contentBox: '#<portlet:namespace />searchTokensContent'
			}
		).render();
	</aui:script>

	<%@ include file="/main_search.jspf" %>

	<c:if test="<%= searchDisplayContext.isDisplayOpenSearchResults() %>">
		<liferay-ui:panel collapsible="<%= true %>" cssClass="open-search-panel" extended="<%= true %>" id="searchOpenSearchPanelContainer" persistState="<%= true %>" title="open-search">
			<%@ include file="/open_search.jspf" %>
		</liferay-ui:panel>
	</c:if>
</aui:form>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />keywords').on(
		'keydown',
		function(event) {
			if (event.keyCode === 13) {
				<portlet:namespace />search();
			}
		}
	);

	$('.portlet-search .result .lfr-search-container').on(
		'click',
		'.table-cell .asset-entry .toggle-details',
		function(event) {
			var handle = $(event.currentTarget);
			var rowTD = handle.parentsUntil('.table-data', '.table-cell');

			var documentFields = rowTD.find('.asset-entry .asset-entry-fields');

			if (handle.text() == '[+]') {
				documentFields.removeClass('hide');

				handle.text('[-]');
			}
			else if (handle.text() == '[-]') {
				documentFields.addClass('hide');

				handle.text('[+]');
			}
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />addSearchProvider() {
		<portlet:resourceURL var="openSearchDescriptionXMLURL">
			<portlet:param name="mvcPath" value="/open_search_description.jsp" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</portlet:resourceURL>

		window.external.AddSearchProvider('<%= openSearchDescriptionXMLURL.toString() %>');
	}

	function <portlet:namespace />clearSearch() {
		<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="clearSearchURL">
			<portlet:param name="groupId" value="0" />
		</portlet:renderURL>

		window.location.href = '<%= clearSearchURL %>';
	}

	function <portlet:namespace />search() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= SearchContainer.DEFAULT_CUR_PARAM %>').val(1);

		var keywords = form.fm('keywords').val();

		keywords = keywords.replace(/^\s+|\s+$/, '');

		if (keywords != '') {
			submitForm(form);
		}
	}
</aui:script>

<%
String pageSubtitle = LanguageUtil.get(request, "search-results");
String pageKeywords = LanguageUtil.get(request, "search");

if (Validator.isNotNull(keywords)) {
	pageKeywords = keywords;

	if (StringUtil.startsWith(pageKeywords, Field.ASSET_TAG_NAMES + StringPool.COLON)) {
		pageKeywords = StringUtil.replace(pageKeywords, Field.ASSET_TAG_NAMES + StringPool.COLON, StringPool.BLANK);
	}
}

PortalUtil.setPageSubtitle(pageSubtitle, request);
PortalUtil.setPageKeywords(pageKeywords, request);
%>