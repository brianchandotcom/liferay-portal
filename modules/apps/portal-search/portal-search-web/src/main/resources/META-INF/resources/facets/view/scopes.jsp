<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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