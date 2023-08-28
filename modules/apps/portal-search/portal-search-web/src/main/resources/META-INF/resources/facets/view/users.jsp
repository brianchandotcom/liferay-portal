<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
userSearchFacetDisplayContextBuilder.setUserLocalService(UserLocalServiceUtil.getService());

UserSearchFacetDisplayContext userSearchFacetDisplayContext = userSearchFacetDisplayContextBuilder.build();
%>

<c:choose>
	<c:when test="<%= userSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(userSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= userSearchFacetDisplayContext.getParameterValue() %>" />
	</c:when>
	<c:otherwise>
		<div class="panel panel-secondary">
			<div class="panel-heading">
				<div class="panel-title">
					<liferay-ui:message key="users" />
				</div>
			</div>

			<div class="panel-body">
				<div class="<%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(userSearchFacetDisplayContext.getParameterName()) %>" id="<%= randomNamespace %>facet">
					<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(userSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= userSearchFacetDisplayContext.getParameterValue() %>" />

					<ul class="list-unstyled users">
						<li class="default facet-value">
							<a class="<%= userSearchFacetDisplayContext.isNothingSelected() ? "facet-term-selected" : "facet-term-unselected" %>" data-value="" href="javascript:void(0);"><liferay-ui:message key="<%= HtmlUtil.escape(facetConfiguration.getLabel()) %>" /></a>
						</li>

						<%
						java.util.List<BucketDisplayContext> bucketDisplayContexts = userSearchFacetDisplayContext.getBucketDisplayContexts();

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