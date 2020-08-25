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
AssetTagsSearchFacetDisplayBuilder assetTagsSearchFacetDisplayBuilder = new AssetTagsSearchFacetDisplayBuilder();

assetTagsSearchFacetDisplayBuilder.setDisplayStyle(dataJSONObject.getString("displayStyle", "cloud"));
assetTagsSearchFacetDisplayBuilder.setFacet(facet);
assetTagsSearchFacetDisplayBuilder.setFrequenciesVisible(dataJSONObject.getBoolean("showAssetCount", true));
assetTagsSearchFacetDisplayBuilder.setFrequencyThreshold(dataJSONObject.getInt("frequencyThreshold"));
assetTagsSearchFacetDisplayBuilder.setMaxTerms(dataJSONObject.getInt("maxTerms", 10));
assetTagsSearchFacetDisplayBuilder.setParameterName(facet.getFieldId());
assetTagsSearchFacetDisplayBuilder.setParameterValue(fieldParam);

AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext = assetTagsSearchFacetDisplayBuilder.build();
%>

<c:choose>
	<c:when test="<%= assetTagsSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetTagsSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetTagsSearchFacetDisplayContext.getParameterValue() %>" />
	</c:when>
	<c:otherwise>
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="panel-title">
					<liferay-ui:message key="tags" />
				</div>
			</div>

			<div class="panel-body">
				<div class="asset-tags <%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" id="<%= randomNamespace %>facet">
					<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetTagsSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetTagsSearchFacetDisplayContext.getParameterValue() %>" />

					<ul class="<%= assetTagsSearchFacetDisplayContext.isCloudWithCount() ? "tag-cloud" : "tag-list" %> list-unstyled">
						<li class="default facet-value">
							<a class="<%= assetTagsSearchFacetDisplayContext.isNothingSelected() ? "facet-term-selected" : "facet-term-unselected" %>" data-value="" href="javascript:;"><liferay-ui:message key="<%= HtmlUtil.escape(facetConfiguration.getLabel()) %>" /></a>
						</li>

						<%
						java.util.List<AssetTagsSearchFacetTermDisplayContext> assetTagsSearchFacetTermDisplayContexts = assetTagsSearchFacetDisplayContext.getTermDisplayContexts();

						for (AssetTagsSearchFacetTermDisplayContext assetTagsSearchFacetTermDisplayContext : assetTagsSearchFacetTermDisplayContexts) {
						%>

							<li class="facet-value tag-popularity-<%= assetTagsSearchFacetTermDisplayContext.getPopularity() %>">
								<a class="<%= assetTagsSearchFacetTermDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>" data-value="<%= HtmlUtil.escapeAttribute(assetTagsSearchFacetTermDisplayContext.getValue()) %>" href="javascript:;">
									<%= HtmlUtil.escape(assetTagsSearchFacetTermDisplayContext.getDisplayName()) %>

									<c:if test="<%= assetTagsSearchFacetTermDisplayContext.isFrequencyVisible() %>">
										<span class="frequency">(<%= assetTagsSearchFacetTermDisplayContext.getFrequency() %>)</span>
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