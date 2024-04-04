<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/returns/init.jsp" %>

<%
CommerceReturnContentDisplayContext commerceReturnContentDisplayContext = (CommerceReturnContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceReturnItem commerceReturnItem = commerceReturnContentDisplayContext.getCommerceReturnItem();
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.format(request, "edit-x", commerceReturnContentDisplayContext.getCommerceReturnItemId()) %>'
>
	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "details") %>'
	>
		<aui:form name="commerceReturnItemsFm" onSubmit="event.preventDefault();">
			<aui:input name="commerceReturnItemId" type="hidden" value="<%= commerceReturnContentDisplayContext.getCommerceReturnItemId() %>" />

			<aui:input ignoreRequestValue="<%= true %>" name="quantity" type="text" value="<%= commerceReturnContentDisplayContext.getFormattedQuantity() %>">
				<aui:validator name="min">0</aui:validator>
				<aui:validator name="number" />
			</aui:input>

			<aui:field-wrapper label='<%= LanguageUtil.get(resourceBundle, "return-reason") %>' name="returnReasonFieldWrapper">
				<div id="autocomplete-root"></div>
			</aui:field-wrapper>

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" value="save" />

				<aui:button cssClass="btn-lg" type="cancel" />
			</aui:button-row>
		</aui:form>
	</commerce-ui:panel>
</liferay-frontend:side-panel-content>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"autocompleteAPIURL", commerceReturnContentDisplayContext.getListTypeEntriesByExternalReferenceCodeURL()
		).put(
			"autocompleteInitialLabel", commerceReturnContentDisplayContext.getReturnReasonName()
		).put(
			"autocompleteInitialValue", (commerceReturnItem == null) ? StringPool.BLANK : commerceReturnItem.getReturnReason()
		).put(
			"commerceReturnItemId", commerceReturnContentDisplayContext.getCommerceReturnItemId()
		).put(
			"dataSetId", CommerceOrderFDSNames.RETURN_ITEMS
		).build()
	%>'
	module="js/edit_commerce_return_item"
/>