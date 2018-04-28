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
CommerceDiscountRelDisplayContext commerceDiscountRelDisplayContext = (CommerceDiscountRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String assetCategoryIds = commerceDiscountRelDisplayContext.getAssetCategoryIds();
%>

<portlet:actionURL name="editCommerceDiscountRel" var="editCommerceDiscountRelActionURL" />

<aui:form action="<%= editCommerceDiscountRelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="className" type="hidden" value="<%= AssetCategory.class.getName() %>" />
	<aui:input name="commerceDiscountId" type="hidden" value="<%= commerceDiscountRelDisplayContext.getCommerceDiscountId() %>" />

	<div class="lfr-form-content">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<liferay-ui:asset-categories-error />

				<h4><liferay-ui:message key="select-categories" /></h4>

				<liferay-asset:asset-categories-selector
					categoryIds="<%= assetCategoryIds %>"
					hiddenInput="addClassPKs"
				/>
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>