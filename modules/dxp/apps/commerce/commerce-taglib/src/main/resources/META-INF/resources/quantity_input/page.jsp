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

<%@ include file="/quantity_input/init.jsp" %>

<%
String allowedOrderQuantity = (String)request.getAttribute("liferay-commerce:quantity-input:allowedOrderQuantity");
CPDefinition cpDefinition = (CPDefinition)request.getAttribute("liferay-commerce:quantity-input:cpDefinition");
int maxOrderQuantity = (int)request.getAttribute("liferay-commerce:quantity-input:maxOrderQuantity");
int minOrderQuantity = (int)request.getAttribute("liferay-commerce:quantity-input:minOrderQuantity");
int multipleOrderQuantity = (int)request.getAttribute("liferay-commerce:quantity-input:multipleOrderQuantity");
String name = (String)request.getAttribute("liferay-commerce:quantity-input:name");
boolean useSelect = (boolean)request.getAttribute("liferay-commerce:quantity-input:useSelect");
int value = (int)request.getAttribute("liferay-commerce:quantity-input:value");

long cpDefinitionId = cpDefinition.getCPDefinitionId();

if (Validator.isNull(name)) {
	name = cpDefinitionId + "Quantity";
}

int[] allowedOrderQuantities = null;

if (Validator.isNotNull(allowedOrderQuantity)) {
	allowedOrderQuantities = StringUtil.split(allowedOrderQuantity, 0);

	Arrays.sort(allowedOrderQuantities);
}
%>

<div class="commerce-quantity-container">
	<c:choose>
		<c:when test="<%= !useSelect %>">
			<aui:input ignoreRequestValue="<%= true %>" label="quantity" name="<%= name %>" type="number" value="<%= value %>">
				<aui:validator name="number" />
				<aui:validator name="min"><%= minOrderQuantity %></aui:validator>
				<aui:validator name="max"><%= maxOrderQuantity %></aui:validator>
			</aui:input>
		</c:when>
		<c:when test="<%= allowedOrderQuantities != null %>">
			<aui:select ignoreRequestValue="<%= true %>" label="quantity" name="<%= name %>">

				<%
				for (int curQuantity : allowedOrderQuantities) {
				%>

					<aui:option label="<%= curQuantity %>" selected="<%= curQuantity == value %>" value="<%= curQuantity %>" />

				<%
				}
				%>

			</aui:select>
		</c:when>
		<c:otherwise>
			<aui:select ignoreRequestValue="<%= true %>" label="quantity" name="<%= name %>">

				<%
				int quantity = 1;

				if (minOrderQuantity > 1) {
					quantity = minOrderQuantity;
				}

				if (multipleOrderQuantity > 1) {
					quantity = multipleOrderQuantity;
				}

				for (int i = 1; i < 10; i++) {
				%>

					<aui:option label="<%= quantity %>" selected="<%= quantity == value %>" value="<%= quantity %>" />

				<%
					if ((maxOrderQuantity > 0) && (quantity == maxOrderQuantity)) {
						break;
					}

					if (multipleOrderQuantity > 1) {
						quantity = quantity + multipleOrderQuantity;
					}
					else {
						quantity++;
					}
				}
				%>

			</aui:select>
		</c:otherwise>
	</c:choose>
</div>