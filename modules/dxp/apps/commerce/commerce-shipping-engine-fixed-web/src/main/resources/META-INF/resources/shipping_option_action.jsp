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
CommerceShippingFixedOptionsDisplayContext commerceShippingFixedOptionsDisplayContext = (CommerceShippingFixedOptionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceShippingFixedOption commerceShippingFixedOption = (CommerceShippingFixedOption)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CommercePermission.contains(permissionChecker, commerceShippingFixedOption.getGroupId(), CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS) %>">
		<liferay-portlet:renderURL var="editURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="editCommerceShippingFixedOption" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceShippingFixedOptionId" value="<%= String.valueOf(commerceShippingFixedOption.getCommerceShippingFixedOptionId()) %>" />
			<portlet:param name="commerceShippingMethodId" value="<%= String.valueOf(commerceShippingFixedOption.getCommerceShippingMethodId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url='<%= commerceShippingFixedOptionsDisplayContext.getEditURL("editCommerceShippingFixedOption", false, editURL) %>'
		/>

		<portlet:actionURL name="editCommerceShippingFixedOption" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceShippingFixedOptionId" value="<%= String.valueOf(commerceShippingFixedOption.getCommerceShippingFixedOptionId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>