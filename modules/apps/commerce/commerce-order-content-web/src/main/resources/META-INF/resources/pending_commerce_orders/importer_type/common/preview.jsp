<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String commerceOrderImporterTypeKey = ParamUtil.getString(request, "commerceOrderImporterTypeKey");

CommerceOrderImporterType commerceOrderImporterType = commerceOrderContentDisplayContext.getCommerceOrderImporterType(commerceOrderImporterTypeKey);
%>

<c:if test="<%= commerceOrderImporterType != null %>">

	<%
	String commerceOrderImporterItemParamName = ParamUtil.getString(request, commerceOrderImporterType.getCommerceOrderImporterItemParamName());
	%>

	<portlet:actionURL name="/commerce_open_order_content/import_commerce_order_items" var="importCommerceOrderItemsActionURL">
		<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order" />
	</portlet:actionURL>

	<aui:form action="<%= importCommerceOrderItemsActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.IMPORT %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderContentDisplayContext.getCommerceOrderId() %>" />
		<aui:input name="commerceOrderImporterTypeKey" type="hidden" value="<%= commerceOrderImporterTypeKey %>" />
		<aui:input name="<%= commerceOrderImporterType.getCommerceOrderImporterItemParamName() %>" type="hidden" value="<%= commerceOrderImporterItemParamName %>" />

		<div class="pb-6">
			<frontend-data-set:classic-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"commerceOrderId", String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId())
					).put(
						"commerceOrderImporterTypeKey", commerceOrderImporterTypeKey
					).put(
						commerceOrderImporterType.getCommerceOrderImporterItemParamName(), commerceOrderImporterItemParamName
					).build()
				%>'
				dataProviderKey="<%= CommerceOrderFDSNames.PREVIEW_ORDER_ITEMS %>"
				id="<%= CommerceOrderFDSNames.PREVIEW_ORDER_ITEMS %>"
				itemsPerPage="<%= 10 %>"
				showManagementBar="<%= false %>"
				showSearch="<%= false %>"
				style="fluid"
			/>
		</div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" name="importButton" primary="<%= true %>" type="submit" value='<%= LanguageUtil.get(request, "import") %>' />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>

	<liferay-frontend:component
		module="js/preview"
	/>
</c:if>