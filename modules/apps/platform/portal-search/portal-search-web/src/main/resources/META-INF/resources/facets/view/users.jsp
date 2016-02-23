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

<%@ include file="/facets/init.jsp" %>

<c:if test="<%= !termCollectors.isEmpty() %>">

	<%
	int frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");
	int maxTerms = dataJSONObject.getInt("maxTerms", 10);
	boolean showAssetCount = dataJSONObject.getBoolean("showAssetCount", true);
	%>

	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="panel-title">
				<liferay-ui:message key="users" />
			</div>
		</div>
		<div class="panel-body">
			<div class="<%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" id="<%= randomNamespace %>facet">
				<aui:input name="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" type="hidden" value="<%= fieldParam %>" />

				<ul class="list-unstyled users">
					<li class="default facet-value">
						<a class="<%= Validator.isNull(fieldParam) ? "text-primary" : "text-default" %>" data-value="" href="javascript:;"><liferay-ui:message key="<%= HtmlUtil.escape(facetConfiguration.getLabel()) %>" /></a>
					</li>

					<%
					String userName = GetterUtil.getString(fieldParam);

					for (int i = 0; i < termCollectors.size(); i++) {
						TermCollector termCollector = termCollectors.get(i);

						String curUserName = GetterUtil.getString(termCollector.getTerm());

						if (((maxTerms > 0) && (i >= maxTerms)) || ((frequencyThreshold > 0) && (frequencyThreshold > termCollector.getFrequency()))) {
							break;
						}
					%>

						<li class="facet-value">
							<a class="<%= userName.equals(curUserName) ? "text-primary" : "text-default" %>" data-value="<%= curUserName %>" href="javascript:;">
								<%= HtmlUtil.escape(curUserName) %>

								<c:if test="<%= showAssetCount %>">
									<span class="frequency">(<%= termCollector.getFrequency() %>)</span>
								</c:if>
							</a>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</div>
	</div>
</c:if>