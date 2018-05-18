<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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