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
List<CPDefinition> cpDefinitions = (List<CPDefinition>)request.getAttribute(CPWebKeys.CP_DEFINITIONS);

if (cpDefinitions == null) {
	cpDefinitions = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= !cpDefinitions.isEmpty() %>">
		<div class="carousel slide w100" data-interval="false" id="carousel-product-definitions">
			<div class="carousel-inner" role="listbox">

				<%
				int carouselItemsCount = 1;

				for (CPDefinition cpDefinition : cpDefinitions) {
					String carouselItemCssClass = "carousel-item ";

					if (carouselItemsCount == 1) {
						carouselItemCssClass += "active";
					}
				%>

					<div class="<%= carouselItemCssClass %>">
						<div class="sidebar-header">
							<h1><%= HtmlUtil.escape(cpDefinition.getName(languageId)) %></h1>

							<div class="lfr-asset-categories sidebar-block">
								<liferay-asset:asset-categories-summary
									className="<%= cpDefinition.getModelClassName() %>"
									classPK="<%= cpDefinition.getCPDefinitionId() %>"
								/>
							</div>
						</div>

						<div class="carousel-label">
							<p><liferay-ui:message arguments="<%= new Object[] {carouselItemsCount, cpDefinitions.size()} %>" key="product-x-of-x" /></p>
						</div>

						<liferay-ui:tabs
							names="details,specs,skus"
							param="<%= String.valueOf(cpDefinition.getCPDefinitionId()) %>"
							refresh="<%= false %>"
							type="tabs nav-tabs-default"
						>
							<liferay-ui:section>
								<div class="sidebar-body">
									<dl class="sidebar-block">

										<%
										String defaultImageThumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
										%>

										<c:if test="<%= Validator.isNotNull(defaultImageThumbnailSrc) %>">
											<div class="default-image" style="background-image: url(<%= defaultImageThumbnailSrc %>); height: 300px; background-size: cover;"></div>
										</c:if>

										<%
										String description = cpDefinition.getDescription(languageId);
										%>

										<c:if test="<%= Validator.isNotNull(description) %>">
											<dt class="h5 uppercase">
												<liferay-ui:message key="description" />
											</dt>
											<dd class="h6 sidebar-caption">
												<%= HtmlUtil.escape(description) %>
											</dd>
										</c:if>

										<dt class="h5 uppercase">
											<liferay-ui:message key="created" />
										</dt>
										<dd class="h6 sidebar-caption">
											<%= HtmlUtil.escape(cpDefinition.getUserName()) %>
										</dd>
									</dl>

									<div class="lfr-asset-tags sidebar-block">
										<liferay-asset:asset-tags-summary
											className="<%= cpDefinition.getModelClassName() %>"
											classPK="<%= cpDefinition.getCPDefinitionId() %>"
											message="tags"
										/>
									</div>
								</div>
							</liferay-ui:section>

							<liferay-ui:section>
								<div class="sidebar-body">
									<dl class="sidebar-block">

										<%
										List<CPDefinitionSpecificationOptionValue> cpDefinitionSpecificationOptionValues = cpDefinition.getCPDefinitionSpecificationOptionValues();

										for (CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue : cpDefinitionSpecificationOptionValues) {
											CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();
										%>

											<dt class="h5 uppercase">
												<%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %>
											</dt>
											<dd class="h6 sidebar-caption">
												<%= HtmlUtil.escape(cpDefinitionSpecificationOptionValue.getValue(languageId)) %>
											</dd>

										<%
										}
										%>

									</dl>
								</div>
							</liferay-ui:section>

							<liferay-ui:section>
								<div class="sidebar-body">

									<%
									List<CPInstance> cpInstances = cpDefinition.getCPInstances();

									for (CPInstance cpInstance : cpInstances) {
									%>

										<dt class="h5">
											<%= HtmlUtil.escape(cpInstance.getSku()) %>
										</dt>

									<%
									}
									%>

								</div>
							</liferay-ui:section>
						</liferay-ui:tabs>
					</div>

				<%
					carouselItemsCount++;
				}
				%>

			</div>

			<a class="carousel-control carousel-control-left left" data-slide="prev" href="#carousel-product-definitions" role="button">
				<span aria-hidden="true" class="glyphicon glyphicon-chevron-left"></span>
				<span class="sr-only">Previous</span>
			</a>

			<a class="carousel-control carousel-control-right right" data-slide="next" href="#carousel-product-definitions" role="button">
				<span aria-hidden="true" class="glyphicon glyphicon-chevron-right"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= cpDefinitions.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>