<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = commercePriceListDisplayContext.getCommercePriceList();

String headerTitle = LanguageUtil.get(request, "add-price-list");

if (commercePriceList != null) {
	headerTitle = commercePriceList.getName();
}

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCommercePriceListExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_price_list/edit_commerce_price_list_external_reference_code" />
	<portlet:param name="commercePriceListId" value="<%= String.valueOf(commercePriceListDisplayContext.getCommercePriceListId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commercePriceListDisplayContext.getHeaderActionModels() %>"
	bean="<%= commercePriceList %>"
	beanIdLabel="id"
	externalReferenceCode="<%= (commercePriceList == null) ? StringPool.BLANK : commercePriceList.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= (commercePriceList == null) ? StringPool.BLANK : editCommercePriceListExternalReferenceCodeURL %>"
	model="<%= CommercePriceList.class %>"
	title="<%= headerTitle %>"
	wrapperCssClasses="side-panel-top-anchor"
/>

<liferay-frontend:screen-navigation
	containerWrapperCssClass="container"
	key="<%= CommercePriceListScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_PRICE_LIST_GENERAL %>"
	modelBean="<%= commercePriceListDisplayContext.getCommercePriceList() %>"
	portletURL="<%= currentURLObj %>"
/>

<aui:script>
	document
		.getElementById('<portlet:namespace />publishButton')
		.addEventListener('click', (e) => {
			e.preventDefault();

			var form = document.getElementById('<portlet:namespace />fm');

			if (!form) {
				throw new Error('Form with id: <portlet:namespace />fm not found!');
			}

			var workflowActionInput = document.getElementById(
				'<portlet:namespace />workflowAction'
			);

			if (workflowActionInput) {
				workflowActionInput.value =
					'<%= WorkflowConstants.ACTION_PUBLISH %>';
			}

			submitForm(form);
		});
</aui:script>