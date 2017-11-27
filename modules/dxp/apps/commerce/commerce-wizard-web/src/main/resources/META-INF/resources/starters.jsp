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
String selectedMethod = ParamUtil.getString(request, "selectedMethod");

CommerceStarterDisplayContext commerceStarterDisplayContext = (CommerceStarterDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceStarter> commerceStarters = commerceStarterDisplayContext.getCommerStarters();
%>

<div class="container-fluid-1280">
	<div class="col-md-8 commerce-wizard-container offset-md-2">
		<div class="commerce-wizard-header">
			<h1><liferay-ui:message key="choose-a-theme" /></h1>
		</div>

		<portlet:actionURL name="applyCommerceStarter" var="applyCommerceStarterURL" />

		<aui:form action="<%= applyCommerceStarterURL %>" method="post" name="fm">
			<c:if test="<%= !commerceStarters.isEmpty() %>">
				<div id="carousel-container">
					<div class="col-md-8 offset-md-2">
						<div class="carousel slide w100" data-interval="false" id="carousel-commerce-starters">
							<div class="carousel-inner carousel-starters" role="listbox">
								<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
								<aui:input name="commerceStarterKey" type="hidden" />
								<aui:input name="selectedMethod" type="hidden" value="<%= selectedMethod %>" />

								<%
								int carouselItemsCount = 1;

								for (CommerceStarter commerceStarter : commerceStarters) {
									String carouselItemCssClass = "carousel-item ";

									if (carouselItemsCount == 1) {
										carouselItemCssClass += "active";
									}
								%>

									<div class="<%= carouselItemCssClass %>" data-value="<%= commerceStarter.getKey() %>">

										<%
										commerceStarterDisplayContext.renderPreview(commerceStarter);
										%>

									</div>

								<%
									carouselItemsCount++;
								}
								%>

							</div>
						</div>
					</div>

					<ol class="carousel-indicators">

						<%
						int carouselIndicatorsCount = 1;

						for (int i = 0; i < commerceStarters.size(); i++) {
							String carouselIndicatorsCssClass = StringPool.BLANK;

							if (carouselIndicatorsCount == 1) {
								carouselIndicatorsCssClass += "active";
							}
						%>

							<li class="<%= carouselIndicatorsCssClass %>" data-slide-to="<%= i %>" data-target="#carousel-commerce-starters">
								<%= carouselIndicatorsCount %>
							</li>

						<%
						}
						%>

					</ol>

					<a class="carousel-control carousel-control-left left" data-slide="prev" href="#carousel-commerce-starters" role="button">
						<liferay-ui:icon
							cssClass="commerce-wizard-icon-angle-left"
							icon="angle-left"
							markupView="lexicon"
						/>

						<span class="sr-only">Previous</span>
					</a>

					<a class="carousel-control carousel-control-right right" data-slide="next" href="#carousel-commerce-starters" role="button">
						<liferay-ui:icon
							cssClass="commerce-wizard-icon-angle-right"
							icon="angle-right"
							markupView="lexicon"
						/>

						<span class="sr-only">Next</span>
					</a>
				</div>
			</c:if>

			<div class="commerce-wizard-button-row">
				<a class="btn btn-left btn-secondary" href="<%= redirect %>" id="previousButton">
					<liferay-ui:icon
						cssClass="commerce-wizard-icon-angle-left"
						icon="angle-left"
						markupView="lexicon"
					/>

					<liferay-ui:message key="previous" />
				</a>
			</div>
		</aui:form>
	</div>
</div>