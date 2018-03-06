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
CPCompareContentMiniDisplayContext cpCompareContentMiniDisplayContext = (CPCompareContentMiniDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String languageId = LocaleUtil.toLanguageId(locale);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("cpCompareContentMiniDisplayContext", cpCompareContentMiniDisplayContext);

List<CPDefinition> cpDefinitions = cpCompareContentMiniDisplayContext.getCPDefinitions();
%>

<liferay-ddm:template-renderer
	className="<%= CPCompareContentMiniPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= cpCompareContentMiniDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= cpCompareContentMiniDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= cpDefinitions %>"
>
	<c:if test="<%= cpDefinitions.size() > 0 %>">
		<liferay-util:buffer var="showCompareProductsIcon">
			<liferay-ui:icon
				cssClass="align-middle d-inline-block"
				icon="angle-right"
				markupView="lexicon"
				message="show"
			/>
		</liferay-util:buffer>

		<liferay-util:buffer var="hideCompareProductsIcon">
			<liferay-ui:icon
				cssClass="align-middle d-inline-block"
				icon="angle-down"
				markupView="lexicon"
				message="hide"
			/>
		</liferay-util:buffer>

		<div id="<portlet:namespace />compareProductsMiniContainer">
			<div class="compare-products-mini-header mb-5 row">
				<div class="col-md-12">
					<div class="bg-light">
						<h3 class="align-middle d-inline-block m-0 p-3"><liferay-ui:message arguments="<%= new Object[] {cpDefinitions.size(), cpCompareContentMiniDisplayContext.getProductsLimit()} %>" key="x-of-x-products-selected" translateArguments="<%= false %>" /></h3>

						<div class="align-middle d-inline-block float-right p-3">
							<a class="align-middle d-inline-block px-2 text-dark" href="<%= HtmlUtil.escape(cpCompareContentMiniDisplayContext.getClearCompareProductsURL()) %>"><liferay-ui:message key="clear-all" /></a>

							<div class="align-middle d-inline-block lfr-compare-products-mini-header">
								<p class="align-middle d-inline-block m-0 px-2"><liferay-ui:message key="hide" /></p>

								<%= hideCompareProductsIcon %>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="lfr-compare-products-mini-content row">

				<%
				for (CPDefinition cpDefinition : cpDefinitions) {
				%>

					<div class="col-md-2">
						<div class="card">
							<div class="float-right">
								<liferay-ui:icon
									icon="times"
									markupView="lexicon"
									message="remove"
									url="<%= cpCompareContentMiniDisplayContext.getDeleteCompareProductURL(cpDefinition.getCPDefinitionId()) %>"
								/>
							</div>

							<a class="aspect-ratio" href="<%= HtmlUtil.escape(cpCompareContentMiniDisplayContext.getProductFriendlyURL(cpDefinition.getCPDefinitionId())) %>">

								<%
								String img = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
								%>

								<c:if test="<%= Validator.isNotNull(img) %>">
									<img class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= img %>">
								</c:if>
							</a>

							<div class="card-row card-row-padded card-row-valign-top">
								<div class="card-col-content text-nowrap">
									<a class="align-middle truncate-text" href="<%= HtmlUtil.escape(cpCompareContentMiniDisplayContext.getProductFriendlyURL(cpDefinition.getCPDefinitionId())) %>">
										<%= cpDefinition.getTitle(languageId) %>
									</a>
								</div>
							</div>
						</div>
					</div>

				<%
				}
				%>

				<c:if test="<%= (cpDefinitions.size() < cpCompareContentMiniDisplayContext.getProductsLimit()) %>">
					<div class="col-md-2">
						<div class="card">
							<div class="aspect-ratio border">
								<liferay-ui:icon
									cssClass="aspect-ratio-item-center-middle aspect-ratio-item-fluid"
									icon="plus"
									localizeMessage="<%= true %>"
									markupView="lexicon"
									message="add-more-products-to-compare-list"
								/>
							</div>
						</div>
					</div>
				</c:if>

				<div class="col-md-2">
					<div class="card">
						<div class="aspect-ratio">
							<aui:button cssClass="aspect-ratio-item-center-middle aspect-ratio-item-fluid" href="<%= HtmlUtil.escape(cpCompareContentMiniDisplayContext.getCompareProductsURL()) %>" value="compare" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<aui:script use="aui-toggler">
			new A.Toggler(
				{
					animated: true,
					content: '#<portlet:namespace />compareProductsMiniContainer .lfr-compare-products-mini-content',
					expanded: true,
					header: '#<portlet:namespace />compareProductsMiniContainer .lfr-compare-products-mini-header',
					on: {
						animatingChange: function(event) {
							var instance = this;

							var header = instance.get('header');

							var expanded = !instance.get('expanded');

							if (expanded) {
								header.html('<p class="align-middle d-inline-block m-0 px-2"><liferay-ui:message key="hide" /></p><%= UnicodeFormatter.toString(hideCompareProductsIcon) %>');
							}
							else {
								header.html('<p class="align-middle d-inline-block m-0 px-2"><liferay-ui:message key="show" /></p><%= UnicodeFormatter.toString(showCompareProductsIcon) %>');
							}
						}
					}
				}
			);
		</aui:script>
	</c:if>
</liferay-ddm:template-renderer>