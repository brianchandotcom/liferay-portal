<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_asset_publisher_edit_query_rule") + StringPool.UNDERLINE;

long[] categorizableGroupIds = (long[])request.getAttribute("configuration.jsp-categorizableGroupIds");
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

	if (Validator.equals(queryName, "assetTags")) {
		queryValues = ParamUtil.getString(request, "queryTagNames" + queryLogicIndex, queryValues);
	}
	else {
		queryValues = ParamUtil.getString(request, "queryCategoryIds" + queryLogicIndex, queryValues);
	}
}
%>

<div class="field-row form-inline query-row">
	<aui:select inlineField="<%= true %>" label="" name='<%= "queryContains" + index %>'>
		<aui:option label="contains" selected="<%= queryContains %>" value="true" />
		<aui:option label="does-not-contain" selected="<%= !queryContains %>" value="false" />
	</aui:select>

	<aui:select inlineField="<%= true %>" label="" name='<%= "queryAndOperator" + index %>'>
		<aui:option label="all" selected="<%= queryAndOperator %>" value="true" />
		<aui:option label="any" selected="<%= !queryAndOperator %>" value="false" />
	</aui:select>

	<aui:select cssClass="asset-query-name" id='<%= randomNamespace + "selector" %>' inlineField="<%= true %>" label="of-the-following" name='<%= "queryName" + index %>'>
		<aui:option label="tags" selected='<%= Validator.equals(queryName, "assetTags") %>' value="assetTags" />
		<aui:option label="categories" selected='<%= Validator.equals(queryName, "assetCategories") %>' value="assetCategories" />
	</aui:select>

	<div class="field tags-selector <%= Validator.equals(queryName, "assetTags") ? StringPool.BLANK : "hide" %>">
		<liferay-ui:asset-tags-selector
			curTags='<%= Validator.equals(queryName, "assetTags") ? queryValues : null %>'
			focus="<%= false %>"
			groupIds="<%= categorizableGroupIds %>"
			hiddenInput='<%= "queryTagNames" + index %>'
		/>
	</div>

	<div class="field categories-selector <%= Validator.equals(queryName, "assetCategories") ? StringPool.BLANK : "hide" %>">
		<liferay-ui:asset-categories-selector
			curCategoryIds='<%= Validator.equals(queryName, "assetCategories") ? queryValues : null %>'
			focus="<%= false %>"
			hiddenInput='<%= "queryCategoryIds" + index %>'
		/>
	</div>
</div>

<aui:script use="aui-base">
	var select = A.one('#<portlet:namespace /><%= randomNamespace %>selector');

	if (select) {
		var row = select.ancestor('.query-row');

		if (row) {
			select.on(
				'change',
				function(event) {
					var tagsSelector = row.one('.tags-selector');
					var categoriesSelector = row.one('.categories-selector');

					if (select.val() == 'assetTags') {
						if (tagsSelector) {
							tagsSelector.show();
						}

						if (categoriesSelector) {
							categoriesSelector.hide();
						}
					}
					else {
						if (tagsSelector) {
							tagsSelector.hide();
						}

						if (categoriesSelector) {
							categoriesSelector.show();
						}
					}
				}
			);
		}
	}
</aui:script>