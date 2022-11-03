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

<%
long searchScopeGroupId = searchDisplayContext.getSearchScopeGroupId();

if (Validator.isNull(fieldParam)) {
	fieldParam = String.valueOf(searchScopeGroupId);
}

ScopeSearchFacetDisplayContextBuilder scopeSearchFacetDisplayContextBuilder = new ScopeSearchFacetDisplayContextBuilder(renderRequest);

scopeSearchFacetDisplayContextBuilder.setFacet(facet);

if (searchScopeGroupId != 0) {
	scopeSearchFacetDisplayContextBuilder.setFilteredGroupIds(new long[] {searchScopeGroupId});
}

scopeSearchFacetDisplayContextBuilder.setFrequenciesVisible(dataJSONObject.getBoolean("showAssetCount", true));
scopeSearchFacetDisplayContextBuilder.setFrequencyThreshold(dataJSONObject.getInt("frequencyThreshold"));
scopeSearchFacetDisplayContextBuilder.setGroupLocalService(GroupLocalServiceUtil.getService());
scopeSearchFacetDisplayContextBuilder.setLanguage(LanguageUtil.getLanguage());
scopeSearchFacetDisplayContextBuilder.setLocale(locale);
scopeSearchFacetDisplayContextBuilder.setMaxTerms(dataJSONObject.getInt("maxTerms"));
scopeSearchFacetDisplayContextBuilder.setParameterName(facet.getFieldId());
scopeSearchFacetDisplayContextBuilder.setParameterValue(fieldParam);

ScopeSearchFacetDisplayContext scopeSearchFacetDisplayContext = scopeSearchFacetDisplayContextBuilder.build();
%>

<c:choose>
	<c:when test="<%= scopeSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(scopeSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= scopeSearchFacetDisplayContext.getParameterValue() %>" />
	</c:when>
	<c:otherwise>
		<div class="panel panel-secondary">
			<div class="panel-heading">
				<div class="panel-title">
					<liferay-ui:message key="sites" />
				</div>
			</div>

			<div class="panel-body">
				<div class="<%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" id="<%= randomNamespace %>facet">
					<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(scopeSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= scopeSearchFacetDisplayContext.getParameterValue() %>" />

					<ul class="list-unstyled scopes">
						<li class="default facet-value">
							<a class="<%= scopeSearchFacetDisplayContext.isNothingSelected() ? "facet-term-selected" : "facet-term-unselected" %>" data-value="0" href="javascript:void(0);"><liferay-ui:message key="<%= HtmlUtil.escape(facetConfiguration.getLabel()) %>" /></a>
						</li>

						<%
						List<BucketDisplayContext> bucketDisplayContexts = scopeSearchFacetDisplayContext.getBucketDisplayContexts();

						for (BucketDisplayContext bucketDisplayContext : bucketDisplayContexts) {
						%>

							<li class="facet-value">
								<a class="<%= bucketDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>" data-value="<%= bucketDisplayContext.getFilterValue() %>" href="javascript:void(0);">
									<%= HtmlUtil.escape(bucketDisplayContext.getBucketText()) %>

									<c:if test="<%= bucketDisplayContext.isFrequencyVisible() %>">
										<span class="frequency">(<%= bucketDisplayContext.getFrequency() %>)</span>
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
	</c:otherwise>
</c:choose>