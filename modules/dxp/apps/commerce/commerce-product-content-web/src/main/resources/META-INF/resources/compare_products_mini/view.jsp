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
		<div id="<portlet:namespace />compareProductsMiniContainer">
			<div class="compare-products-mini-header">
				<div class="autofit-float autofit-row autofit-row-center">
					<div class="autofit-col autofit-col-expand">
						<h3 class="component-title"><liferay-ui:message arguments="<%= new Object[] {cpDefinitions.size(), cpCompareContentMiniDisplayContext.getProductsLimit()} %>" key="x-of-x-products-selected" translateArguments="<%= false %>" /></h3>
					</div>
					<div class="autofit-col">
						<a class="" href="<%= HtmlUtil.escape(cpCompareContentMiniDisplayContext.getClearCompareProductsURL()) %>"><liferay-ui:message key="clear-all" /></a>
					</div>
					<div class="autofit-col">
						<a class="lfr-compare-products-mini-header" href="javascript:;">
							<span class="expanded-text">
								<liferay-ui:message key="hide" />

								<liferay-ui:icon
									icon="angle-down"
									markupView="lexicon"
								/>
							</span>
							<span class="collapsed-text">
								<liferay-ui:message key="show" />

								<liferay-ui:icon
									icon="angle-right"
									markupView="lexicon"
								/>
							</span>
						</a>
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
					header: '#<portlet:namespace />compareProductsMiniContainer .lfr-compare-products-mini-header'
				}
			);
		</aui:script>
	</c:if>
</liferay-ddm:template-renderer>