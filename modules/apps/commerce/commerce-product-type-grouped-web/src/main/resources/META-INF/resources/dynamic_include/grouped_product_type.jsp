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
GroupedCPTypeHelper groupedCPTypeHelper = (GroupedCPTypeHelper)request.getAttribute(GroupedCPTypeWebKeys.GROUPED_CP_TYPE_HELPER);

CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);
%>

<c:if test="<%= GroupedCPTypeConstants.NAME.equals(cpCatalogEntry.getProductTypeName()) %>">
	<div class="grouped-products-container mt-3 row">
		<div class="col-lg-12">

			<%
			for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : groupedCPTypeHelper.getCPDefinitionGroupedEntry(cpCatalogEntry.getCPDefinitionId())) {
				CProduct cProduct = cpDefinitionGroupedEntry.getEntryCProduct();

				CPDefinition cProductCPDefinition = CPDefinitionLocalServiceUtil.getCPDefinition(cProduct.getPublishedCPDefinitionId());
			%>

				<div class="row">
					<div class="col-md-4">
						<img class="img-fluid" src="<%= cProductCPDefinition.getDefaultImageThumbnailSrc(CommerceUtil.getCommerceAccountId((CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT))) %>" />
					</div>

					<div class="col-md-8">
						<h5>
							<%= HtmlUtil.escape(cProductCPDefinition.getName(LocaleUtil.toLanguageId(locale))) %>
						</h5>

						<h6>
							<liferay-ui:message arguments="<%= cpDefinitionGroupedEntry.getQuantity() %>" key="quantity-x" />
						</h6>
					</div>
				</div>

			<%
			}
			%>

		</div>
	</div>
</c:if>