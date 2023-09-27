<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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