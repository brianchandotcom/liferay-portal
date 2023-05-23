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
CPPriceRangeFacetsDisplayContext cpPriceRangeFacetsDisplayContext = (CPPriceRangeFacetsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= !cpPriceRangeFacetsDisplayContext.hasCommerceChannel() && !cpPriceRangeFacetsDisplayContext.isStagingEnabled() %>">
		<div class="alert alert-info mx-auto">
		<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		Facet facet = cpPriceRangeFacetsDisplayContext.getFacet();

		String max = ParamUtil.getString(PortalUtil.getOriginalServletRequest(request), "max");

		double maxDouble = ParamUtil.getDouble(PortalUtil.getOriginalServletRequest(request), "max");

		if ((maxDouble == Double.MAX_VALUE) || (maxDouble == 0)) {
			max = StringPool.BLANK;
		}

		String min = StringPool.BLANK;

		double minDouble = ParamUtil.getDouble(PortalUtil.getOriginalServletRequest(request), "min");

		if (minDouble != 0) {
			min = ParamUtil.getString(PortalUtil.getOriginalServletRequest(request), "min");
		}
		%>

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
				title="price-range"
			>
				<c:choose>
					<c:when test="<%= facet != null %>">

						<%
						FacetCollector facetCollector = facet.getFacetCollector();

						List<TermCollector> termCollectors = facetCollector.getTermCollectors();
						%>

						<c:if test="<%= !termCollectors.isEmpty() %>">
							<aui:form method="post" name='<%= "assetEntriesFacetForm_" + facet.getFieldName() %>'>
								<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= facet.getFieldName() %>" />
								<aui:input cssClass="start-parameter-name" name="start-parameter-name" type="hidden" value="<%= cpPriceRangeFacetsDisplayContext.getPaginationStartParameterName() %>" />

								<c:if test="<%= cpPriceRangeFacetsDisplayContext.isShowClear(facet.getFieldName()) %>">
									<aui:button cssClass="btn-link btn-unstyled facet-clear-btn" onClick="Liferay.Search.FacetUtil.clearSelections(event);" value="clear" />
								</c:if>

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
													<%= cpPriceRangeFacetsDisplayContext.isCPPriceRangeValueSelected(facet.getFieldName(), termCollector.getTerm()) ? "checked" : "" %>
												/>

												<span class="custom-control-label term-name <%= cpPriceRangeFacetsDisplayContext.isCPPriceRangeValueSelected(facet.getFieldName(), termCollector.getTerm()) ? "facet-term-selected" : "facet-term-unselected" %>">
													<span class="custom-control-label-text"><%= cpPriceRangeFacetsDisplayContext.getPriceRangeLabel(termCollector.getTerm()) %></span>
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
						</c:if>
					</c:when>
					<c:otherwise>
						<div class="alert alert-info">
							<liferay-ui:message key="no-facets-were-found" />
						</div>
					</c:otherwise>
				</c:choose>

				<c:if test="<%= cpPriceRangeFacetsDisplayContext.showInputRange() %>">
					<div class="mt-3 row">
						<aui:input cssClass="price-range-input" label="<%= StringPool.BLANK %>" min="0" name="minimum" prefix="<%= HtmlUtil.escape(cpPriceRangeFacetsDisplayContext.getCurrentCommerceCurrencySymbol()) %>" type="number" value="<%= min %>" wrapperCssClass="col-md-5 price-range-input-wrapper" />

						<span class="mt-auto price-range-seperator text-center">-</span>

						<aui:input cssClass="price-range-input" label="<%= StringPool.BLANK %>" name="maximum" prefix="<%= HtmlUtil.escape(cpPriceRangeFacetsDisplayContext.getCurrentCommerceCurrencySymbol()) %>" type="number" value="<%= max %>" wrapperCssClass="col-md-5 price-range-input-wrapper" />

						<div class="col-md-3 ml-2 p-0">
							<button class="btn btn-secondary price-range-btn" id="<portlet:namespace />priceRangeButton"><liferay-ui:message key="go" /></button>
						</div>
					</div>
				</c:if>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</c:otherwise>
</c:choose>

<aui:script use="liferay-search-facet-util" />

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"maxValue", Double.MAX_VALUE
		).build()
	%>'
	module="js/price_range_facets/view"
/>