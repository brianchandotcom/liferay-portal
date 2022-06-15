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
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

long commerceAccountId = CommerceUtil.getCommerceAccountId(commerceContext);

GroupedCPTypeHelper groupedCPTypeHelper = (GroupedCPTypeHelper)request.getAttribute(GroupedCPTypeWebKeys.GROUPED_CP_TYPE_HELPER);

CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);
%>

<c:if test="<%= GroupedCPTypeConstants.NAME.equals(cpCatalogEntry.getProductTypeName()) %>">
	<div class="grouped-products-container mt-3 row">
		<div class="col-lg-12">

			<%
			for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : groupedCPTypeHelper.getCPDefinitionGroupedEntry(commerceAccountId, commerceContext.getCommerceChannelGroupId(), cpCatalogEntry.getCPDefinitionId())) {
				CProduct cProduct = cpDefinitionGroupedEntry.getEntryCProduct();

				CPDefinition cProductCPDefinition = CPDefinitionLocalServiceUtil.getCPDefinition(cProduct.getPublishedCPDefinitionId());
			%>

				<div class="mt-1 row">
					<div class="col-md-4">
						<img class="img-fluid" src="<%= cProductCPDefinition.getDefaultImageThumbnailSrc(commerceAccountId) %>" />
					</div>

					<div class="col-md-8">
						<h5>
							<%= HtmlUtil.escape(cProductCPDefinition.getName(LocaleUtil.toLanguageId(locale))) %>
						</h5>

						<p>
							<%= cProductCPDefinition.getShortDescription(LocaleUtil.toLanguageId(locale)) %>
						</p>
					</div>
				</div>

			<%
			}
			%>

		</div>
	</div>
</c:if>