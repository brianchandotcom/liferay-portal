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
String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_asset_publisher_edit_query_rule") + StringPool.UNDERLINE;

long[] categorizableGroupIds = (long[])request.getAttribute("configuration.jsp-categorizableGroupIds");

if (categorizableGroupIds == null) {
	categorizableGroupIds = StringUtil.split(ParamUtil.getString(request, "categorizableGroupIds"), 0l);
}

int index = ParamUtil.getInteger(request, "index", GetterUtil.getInteger((String)request.getAttribute("configuration.jsp-index")));
int queryLogicIndex = GetterUtil.getInteger((String)request.getAttribute("configuration.jsp-queryLogicIndex"));

boolean queryContains = true;
boolean queryAndOperator = false;
String queryName = "assetTags";
String queryValues = null;

if (queryLogicIndex >= 0) {
	queryContains = PrefsParamUtil.getBoolean(portletPreferences, request, "queryContains" + queryLogicIndex, true);
	queryAndOperator = PrefsParamUtil.getBoolean(portletPreferences, request, "queryAndOperator" + queryLogicIndex);
	queryName = PrefsParamUtil.getString(portletPreferences, request, "queryName" + queryLogicIndex, "assetTags");
	queryValues = StringUtil.merge(portletPreferences.getValues("queryValues" + queryLogicIndex, new String[0]));

	if (Objects.equals(queryName, "assetTags")) {
		queryValues = ParamUtil.getString(request, "queryTagNames" + queryLogicIndex, queryValues);

		queryValues = AssetPublisherUtil.filterAssetTagNames(scopeGroupId, queryValues);
	}
	else {
		queryValues = ParamUtil.getString(request, "queryCategoryIds" + queryLogicIndex, queryValues);
	}
}
%>

<div class="field-row form-inline query-row">
	<aui:select inlineField="<%= true %>" label="" name='<%= "queryContains" + index %>' title="query-contains">
		<aui:option label="contains" selected="<%= queryContains %>" value="<%= true %>" />
		<aui:option label="does-not-contain" selected="<%= !queryContains %>" value="<%= false %>" />
	</aui:select>

	<aui:select inlineField="<%= true %>" label="" name='<%= "queryAndOperator" + index %>' title="and-operator">
		<aui:option label="all" selected="<%= queryAndOperator %>" value="<%= true %>" />
		<aui:option label="any" selected="<%= !queryAndOperator %>" value="<%= false %>" />
	</aui:select>

	<aui:select cssClass="asset-query-name" id='<%= randomNamespace + "selector" %>' inlineField="<%= true %>" label="of-the-following" name='<%= "queryName" + index %>'>
		<aui:option label="tags" selected='<%= Objects.equals(queryName, "assetTags") %>' value="assetTags" />
		<aui:option label="categories" selected='<%= Objects.equals(queryName, "assetCategories") %>' value="assetCategories" />
	</aui:select>

	<div class="field tags-selector <%= Objects.equals(queryName, "assetTags") ? StringPool.BLANK : "hide" %>">
		<liferay-asset:asset-tags-selector
			groupIds="<%= categorizableGroupIds %>"
			hiddenInput='<%= "queryTagNames" + index %>'
			tagNames='<%= Objects.equals(queryName, "assetTags") ? queryValues : null %>'
		/>
	</div>

	<div class="categories-selector field <%= Objects.equals(queryName, "assetCategories") ? StringPool.BLANK : "hide" %>">
		<liferay-asset:asset-categories-selector
			categoryIds='<%= Objects.equals(queryName, "assetCategories") ? queryValues : null %>'
			groupIds="<%= categorizableGroupIds %>"
			hiddenInput='<%= "queryCategoryIds" + index %>'
		/>
	</div>
</div>

<aui:script sandbox="<%= true %>">
	var select = $('#<portlet:namespace /><%= randomNamespace %>selector');

	var row = select.closest('.query-row');

	select.on(
		'change',
		function(event) {
			var categoriesSelector = row.find('.categories-selector');
			var tagsSelector = row.find('.tags-selector');

			var assetTags = (select.val() == 'assetTags');

			tagsSelector.toggleClass('hide', !assetTags);
			categoriesSelector.toggleClass('hide', assetTags);
		}
	);
</aui:script>