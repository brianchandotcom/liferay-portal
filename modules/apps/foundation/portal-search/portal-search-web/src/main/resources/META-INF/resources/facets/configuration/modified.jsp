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
SearchFacet searchFacet = (SearchFacet)request.getAttribute("facet_configuration.jsp-searchFacet");

JSONObject dataJSONObject = searchFacet.getData();

JSONArray rangesJSONArray = dataJSONObject.getJSONArray("ranges");
%>

<aui:fieldset id='<%= renderResponse.getNamespace() + "rangesId" %>'>

	<%
	int[] rangesIndexes = new int[rangesJSONArray.length()];

	for (int i = 0; i < rangesJSONArray.length(); i++) {
		rangesIndexes[i] = i;

		JSONObject jsonObject = rangesJSONArray.getJSONObject(i);
	%>

		<div class="lfr-form-row lfr-form-row-inline">
			<div class="row-fields">
				<aui:input label="label" name='<%= searchFacet.getClassName() + "label_" + i %>' value='<%= jsonObject.getString("label") %>' />

				<aui:input label="range" name='<%= searchFacet.getClassName() + "range_" + i %>' value='<%= jsonObject.getString("range") %>' />
			</div>
		</div>

	<%
	}
	%>

	<aui:input name='<%= searchFacet.getClassName() + "rangesIndexes" %>' type="hidden" value="<%= StringUtil.merge(rangesIndexes) %>" />
</aui:fieldset>

<aui:script use="liferay-auto-fields">
	var autoFields = new Liferay.AutoFields(
		{
			contentBox: 'fieldset#<portlet:namespace />rangesId',
			fieldIndexes: '<portlet:namespace /><%= searchFacet.getClassName() %>rangesIndexes',
			namespace: '<portlet:namespace />'
		}
	).render();
</aui:script>