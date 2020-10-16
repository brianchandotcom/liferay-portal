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

<style>
	.facet-checkbox-label {
		display: block;
	}
</style>

<%
CPOptionFacetsDisplayContext cpOptionFacetsDisplayContext = (CPOptionFacetsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= !cpOptionFacetsDisplayContext.hasCommerceChannel() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		List<Facet> facets = cpOptionFacetsDisplayContext.getFacets();

		long companyId = company.getCompanyId();
		%>

		<c:choose>
			<c:when test="<%= !facets.isEmpty() %>">

				<%
				for (Facet facet : facets) {
					FacetCollector facetCollector = facet.getFacetCollector();

					List<TermCollector> termCollectors = facetCollector.getTermCollectors();
				%>

					<c:if test="<%= !termCollectors.isEmpty() %>">

				<liferay-ui:panel-container
					extended="<%= true %>"
					markupView="lexicon"
					persistState="<%= true %>"
				>
					<liferay-ui:panel
						collapsible="<%= true %>"
						cssClass="search-facet"
						markupView="lexicon"
						persistState="<%= true %>"
						title="<%= HtmlUtil.escape(cpOptionFacetsDisplayContext.getCPOptionName(companyId, facet.getFieldId())) %>"
					>
						<aui:form method="post" name='<%= "assetEntriesFacetForm_" + facet.getFieldName() %>'>
							<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= cpOptionFacetsDisplayContext.getCPOptionKey(companyId, facet.getFieldName()) %>" />
							<aui:input cssClass="start-parameter-name" name="start-parameter-name" type="hidden" value="<%= cpOptionFacetsDisplayContext.getPaginationStartParameterName() %>" />

							<aui:fieldset>
								<ul class="list-unstyled">

								<%
								int i = 0;

								for (TermCollector termCollector : termCollectors) {
									i++;
								%>

								<li class="facet-value">
									<div class="custom-checkbox custom-control">
										<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= facet.getFieldName() + i %>">
											<input
												class="custom-control-input facet-term"
												data-term-id="<%= HtmlUtil.escapeAttribute(termCollector.getTerm()) %>"
												id="<portlet:namespace />term_<%= facet.getFieldName() + i %>"
												name="<portlet:namespace />term_<%= facet.getFieldName() + i %>"
												onChange="Liferay.Search.FacetUtil.changeSelection(event);"
												type="checkbox"
												<%= cpOptionFacetsDisplayContext.isCPOptionValueSelected(companyId, facet.getFieldName(), termCollector.getTerm()) ? "checked" : "" %>
											/>

											<span class="custom-control-label term-name <%= cpOptionFacetsDisplayContext.isCPOptionValueSelected(companyId, facet.getFieldName(), termCollector.getTerm()) ? "facet-term-selected" : "facet-term-unselected" %>">
												<span class="custom-control-label-text"><%= HtmlUtil.escape(termCollector.getTerm()) %></span>
											</span>

											<small class="term-count">
												(<%= termCollector.getFrequency() %>)
											</small>
										</label>
									</div>
								</li>

								<%
								}
								%>

							</aui:fieldset>
						</aui:form>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

					</c:if>

				<%
				}
				%>

			</c:when>
			<c:otherwise>
				<div class="alert alert-info">
					<liferay-ui:message key="no-facets-were-found" />
				</div>
			</c:otherwise>
		</c:choose>

		<aui:script use="liferay-search-facet-util"></aui:script>
	</c:otherwise>
</c:choose>