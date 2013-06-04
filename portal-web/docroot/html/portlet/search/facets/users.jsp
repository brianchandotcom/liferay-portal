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
int maxTerms = dataJSONObject.getInt("maxTerms", 10);
boolean showAssetCount = dataJSONObject.getBoolean("showAssetCount", true);
%>

<div class="<%= cssClass %>" data-facetFieldName="<%= facet.getFieldName() %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />

	<ul class="users unstyled">
		<li class="facet-value default <%= Validator.isNull(fieldParam) ? "current-term" : StringPool.BLANK %>">
			<a data-value="" href="javascript:;"><img alt="" src='<%= themeDisplay.getPathThemeImages() + "/common/user_icon.png" %>' /><liferay-ui:message key="any" /><aui:spacer /><liferay-ui:message key="<%= facetConfiguration.getLabel() %>" /></a>
		</li>

		<%
		long userId = GetterUtil.getLong(fieldParam);

		for (int i = 0; i < termCollectors.size(); i++) {
			TermCollector termCollector = termCollectors.get(i);

			long curUserId = GetterUtil.getLong(termCollector.getTerm());

			User curUser = UserLocalServiceUtil.getUser(curUserId);
		%>

			<c:if test="<%= userId == curUserId %>">
				<aui:script use="liferay-token-list">
					Liferay.Search.tokenList.add(
						{
							clearFields: '<%= renderResponse.getNamespace() + facet.getFieldName() %>',
							text: '<%= HtmlUtil.escapeJS(curUser.getFullName()) %>'
						}
					);
				</aui:script>
			</c:if>

			<%
			if (((maxTerms > 0) && (i >= maxTerms)) || ((frequencyThreshold > 0) && (frequencyThreshold > termCollector.getFrequency()))) {
				break;
			}
			%>

			<li class="facet-value <%= (userId == curUserId) ? "current-term" : StringPool.BLANK %>">
				<a data-value="<%= curUserId %>" href="javascript:;"><%= HtmlUtil.escape(curUser.getFullName()) %></a><c:if test="<%= showAssetCount %>"><aui:spacer /><span class="frequency">(<%= termCollector.getFrequency() %>)</span></c:if>
			</li>

		<%
		}
		%>

	</ul>
</div>