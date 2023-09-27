<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
long commerceOrderId = commerceOrderEditDisplayContext.getCommerceOrderId();
%>

<liferay-portlet:actionURL name="/commerce_order/edit_commerce_order" var="editCommerceOrderURL" />

<div class="sheet">
	<div class="panel-group panel-group-flush">
		<aui:form action="<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? editCommerceOrderURL : null %>" method="post" name="orderCustomFieldFm">
			<aui:fieldset>
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="customFields" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderId %>" />

				<liferay-ui:error-marker
					key="<%= WebKeys.ERROR_SECTION %>"
					value="custom-fields"
				/>

				<aui:model-context bean="<%= commerceOrderEditDisplayContext.getCommerceOrder() %>" model="<%= CommerceOrder.class %>" />

				<liferay-expando:custom-attribute-list
					className="<%= CommerceOrder.class.getName() %>"
					classPK="<%= commerceOrderId %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>

				<c:if test="<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) %>">
					<aui:button-row>
						<aui:button type="submit" />
					</aui:button-row>
				</c:if>
			</aui:fieldset>
		</aui:form>
	</div>
</div>