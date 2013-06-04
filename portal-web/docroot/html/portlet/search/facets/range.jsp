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

<%@ include file="/html/portlet/search/facets/init.jsp" %>

<%
if (termCollectors.isEmpty()) {
	return;
}

int frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");
JSONArray rangesJSONArray = dataJSONObject.getJSONArray("ranges");
%>

<div class="<%= cssClass %>" data-facetFieldName="<%= facet.getFieldName() %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />

	<ul class="range unstyled">
		<li class="facet-value default <%= Validator.isNull(fieldParam) ? "current-term" : StringPool.BLANK %>">
			<a data-value="" href="javascript:;"><liferay-ui:message key="any-range" /></a>
		</li>

		<%
		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

			String label = rangeJSONObject.getString("label");
			String range = rangeJSONObject.getString("range");

			TermCollector termCollector = facetCollector.getTermCollector(range);
		%>

			<c:if test="<%= fieldParam.equals(range) %>">
				<aui:script use="liferay-token-list">
					Liferay.Search.tokenList.add(
						{
							clearFields: '<%= renderResponse.getNamespace() + facet.getFieldName() %>',
							text: '<%= UnicodeLanguageUtil.get(pageContext, HtmlUtil.escape(label)) %>'
						}
					);
				</aui:script>
			</c:if>

		<%
			int frequency = 0;

			if (termCollector != null) {
				frequency = termCollector.getFrequency();
			}

			if (frequencyThreshold > frequency) {
				continue;
			}
		%>

			<li class="facet-value <%= fieldParam.equals(range) ? "current-term" : StringPool.BLANK %>">
				<a data-value="<%= HtmlUtil.escapeAttribute(range) %>" href="javascript:;"><liferay-ui:message key="<%= label %>" /></a><aui:spacer /><span class="frequency">(<%= frequency %>)</span>
			</li>

		<%
		}
		%>

	</ul>
</div>