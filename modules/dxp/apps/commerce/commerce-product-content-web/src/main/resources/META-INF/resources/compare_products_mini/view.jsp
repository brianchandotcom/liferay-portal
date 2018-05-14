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
				<a class="collapse-icon lfr-compare-products-mini-header" href="javascript:;">
					<span class="component-title"><liferay-ui:message arguments="<%= new Object[] {cpDefinitions.size(), cpCompareContentMiniDisplayContext.getProductsLimit()} %>" key="x-of-x-products-selected" translateArguments="<%= false %>" /></span>

					<span class="collapse-icon-open">
						<liferay-ui:icon
							icon="angle-down"
							markupView="lexicon"
						/>
					</span>
					<span class="collapse-icon-closed">
						<liferay-ui:icon
							icon="angle-up"
							markupView="lexicon"
						/>
					</span>
				</a>
			</div>

			<div class="lfr-compare-products-mini-content">
				<ul class="card-page">

					<%
					for (CPDefinition cpDefinition : cpDefinitions) {
					%>

						<li class="card-page-item">
							<div class="autofit-row autofit-row-end">
								<div class="autofit-col">
									<div class="card">
										<div class="sticker sticker-top-right">
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
									</div>
								</div>

								<div class="autofit-col autofit-col-expand">
									<a class="compare-link" href="<%= HtmlUtil.escape(cpCompareContentMiniDisplayContext.getProductFriendlyURL(cpDefinition.getCPDefinitionId())) %>">
										<%= cpDefinition.getName(languageId) %>
									</a>
								</div>
						</li>

					<%
					}
					%>

					<li class="card-page-item card-page-item-shrink">
						<a class="btn btn-link" href="<%= HtmlUtil.escape(cpCompareContentMiniDisplayContext.getClearCompareProductsURL()) %>"><liferay-ui:message key="clear-all" /></a>
					</li>
					<li class="card-page-item card-page-item-shrink">
						<aui:button cssClass="btn-primary" href="<%= HtmlUtil.escape(cpCompareContentMiniDisplayContext.getCompareProductsURL()) %>" value="compare" />
					</li>
				</ul>
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