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
UserSearchFacetDisplayContextBuilder userSearchFacetDisplayContextBuilder = new UserSearchFacetDisplayContextBuilder(renderRequest);

userSearchFacetDisplayContextBuilder.setFacet(facet);
userSearchFacetDisplayContextBuilder.setFrequenciesVisible(dataJSONObject.getBoolean("showAssetCount", true));
userSearchFacetDisplayContextBuilder.setFrequencyThreshold(dataJSONObject.getInt("frequencyThreshold"));
userSearchFacetDisplayContextBuilder.setMaxTerms(dataJSONObject.getInt("maxTerms", 10));
userSearchFacetDisplayContextBuilder.setParamName(facet.getFieldId());
userSearchFacetDisplayContextBuilder.setParamValue(fieldParam);

UserSearchFacetDisplayContext userSearchFacetDisplayContext = userSearchFacetDisplayContextBuilder.build();
%>

<c:choose>
	<c:when test="<%= userSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(userSearchFacetDisplayContext.getParamName()) %>" type="hidden" value="<%= userSearchFacetDisplayContext.getParamValue() %>" />
	</c:when>
	<c:otherwise>
		<div class="panel panel-secondary">
			<div class="panel-heading">
				<div class="panel-title">
					<liferay-ui:message key="users" />
				</div>
			</div>

			<div class="panel-body">
				<div class="<%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(userSearchFacetDisplayContext.getParamName()) %>" id="<%= randomNamespace %>facet">
					<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(userSearchFacetDisplayContext.getParamName()) %>" type="hidden" value="<%= userSearchFacetDisplayContext.getParamValue() %>" />

					<ul class="list-unstyled users">
						<li class="default facet-value">
							<a class="<%= userSearchFacetDisplayContext.isNothingSelected() ? "facet-term-selected" : "facet-term-unselected" %>" data-value="" href="javascript:void(0);"><liferay-ui:message key="<%= HtmlUtil.escape(facetConfiguration.getLabel()) %>" /></a>
						</li>

						<%
						java.util.List<BucketDisplayContext> bucketDisplayContexts = userSearchFacetDisplayContext.getBucketDisplayContexts();

						for (BucketDisplayContext bucketDisplayContext : bucketDisplayContexts) {
						%>

							<li class="facet-value">
								<a class="<%= bucketDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>" data-value="<%= HtmlUtil.escapeAttribute(bucketDisplayContext.getBucketText()) %>" href="javascript:void(0);">
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