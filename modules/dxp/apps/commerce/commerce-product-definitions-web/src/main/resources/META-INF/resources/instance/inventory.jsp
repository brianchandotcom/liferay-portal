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
CPInstance cpInstance = (CPInstance)request.getAttribute(CPWebKeys.CP_INSTANCE);

boolean overrideInventory = BeanParamUtil.getBoolean(cpInstance, request, "overrideInventory");
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="inventory" />

<aui:model-context bean="<%= cpInstance %>" model="<%= CPInstance.class %>" />

<div id="<portlet:namespace />lfr-commerce-product-istance-inventory-header">
	<aui:input name="overrideInventory" />
</div>

<div class="toggler-content-collapsed" id="<portlet:namespace />lfr-commerce-product-istance-inventory-content">
	<aui:fieldset>
		<aui:input name="minCartQuantity" />

		<aui:input name="maxCartQuantity" />

		<aui:input name="allowedCartQuantities" />

		<aui:input name="multipleCartQuantity" />
	</aui:fieldset>
</div>

<aui:script use="aui-toggler">
	new A.Toggler(
		{
			animated: true,
			content: '#<portlet:namespace />lfr-commerce-product-istance-inventory-content',
			expanded: <%= overrideInventory %>,
			header: '#<portlet:namespace />lfr-commerce-product-istance-inventory-header',
			on: {
				animatingChange: function(event) {
					var instance = this;

					var expanded = !instance.get('expanded');

					A.one('#<portlet:namespace />overrideInventory').attr('checked', expanded);
				}
			}
		}
	);
</aui:script>