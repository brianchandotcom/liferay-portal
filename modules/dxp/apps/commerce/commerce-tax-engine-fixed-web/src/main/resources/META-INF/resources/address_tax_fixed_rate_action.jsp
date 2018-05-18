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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = (CommerceTaxFixedRateAddressRel)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CommercePermission.contains(permissionChecker, commerceTaxFixedRateAddressRel.getGroupId(), CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="editCommerceTaxFixedRateAddressRel" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceTaxMethodId" value="<%= String.valueOf(commerceTaxFixedRateAddressRel.getCommerceTaxMethodId()) %>" />
			<portlet:param name="commerceTaxFixedRateAddressRelId" value="<%= String.valueOf(commerceTaxFixedRateAddressRel.getCommerceTaxFixedRateAddressRelId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:actionURL name="editCommerceTaxFixedRateAddressRel" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceTaxFixedRateAddressRelId" value="<%= String.valueOf(commerceTaxFixedRateAddressRel.getCommerceTaxFixedRateAddressRelId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>