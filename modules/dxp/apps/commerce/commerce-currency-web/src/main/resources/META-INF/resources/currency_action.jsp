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

CommerceCurrency commerceCurrency = (CommerceCurrency)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CommerceCurrencyPermission.contains(permissionChecker, scopeGroupId, CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="editCommerceCurrency" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:actionURL name="editCommerceCurrency" var="setPrimaryURL">
			<portlet:param name="<%= Constants.CMD %>" value="setPrimary" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
			<portlet:param name="primary" value="<%= String.valueOf(!commerceCurrency.getPrimary()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= (commerceCurrency.getPrimary()) ? LanguageUtil.get(request, "unset-as-primary") : LanguageUtil.get(request, "set-as-primary") %>'
			url="<%= setPrimaryURL %>"
		/>

		<portlet:actionURL name="editCommerceCurrency" var="setActiveURL">
			<portlet:param name="<%= Constants.CMD %>" value="setActive" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
			<portlet:param name="active" value="<%= String.valueOf(!commerceCurrency.getActive()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= (commerceCurrency.getActive()) ? LanguageUtil.get(request, "unset-as-active") : LanguageUtil.get(request, "set-as-active") %>'
			url="<%= setActiveURL %>"
		/>

		<portlet:actionURL name="editCommerceCurrency" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>