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
String redirect = ParamUtil.getString(request, "redirect");

ShoppingItem item = (ShoppingItem)request.getAttribute(WebKeys.SHOPPING_ITEM);

item = item.toEscapedModel();

ShoppingItemField[] itemFields = (ShoppingItemField[])ShoppingItemFieldLocalServiceUtil.getItemFields(item.getItemId()).toArray(new ShoppingItemField[0]);
ShoppingItemPrice[] itemPrices = (ShoppingItemPrice[])ShoppingItemPriceLocalServiceUtil.getItemPrices(item.getItemId()).toArray(new ShoppingItemPrice[0]);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(item.getName());
%>

<portlet:actionURL name="/shopping/cart" var="cartURL">
	<portlet:param name="mvcActionCommand" value="/shopping/cart" />
</portlet:actionURL>

<portlet:renderURL var="redirectURL">
	<portlet:param name="mvcRenderCommandName" value="/shopping/cart" />
	<portlet:param name="tabs1" value="cart" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<aui:form action="<%= cartURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
	<aui:input name="itemId" type="hidden" value="<%= item.getItemId() %>" />
	<aui:input name="fields" type="hidden" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<h4><strong><%= item.getSku() %></strong></h4>

			<c:if test="<%= item.isMediumImage() %>">
				<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="image" />" src="<%= Validator.isNotNull(item.getMediumImageURL()) ? item.getMediumImageURL() : themeDisplay.getPathImage() + "/shopping/item?img_id=" + item.getMediumImageId() + "&t=" + WebServerServletTokenUtil.getToken(item.getMediumImageId()) %>" vspace="0" />
			</c:if>

			<c:if test="<%= item.isLargeImage() %>">
				<aui:a href='<%= Validator.isNotNull(item.getLargeImageURL()) ? item.getLargeImageURL() : themeDisplay.getPathImage() + "/shopping/item?img_id=" + item.getLargeImageId() + "&t=" + WebServerServletTokenUtil.getToken(item.getLargeImageId()) %>' style="font-size: xx-small;" target="_blank"><liferay-ui:message key="see-large-photo" /></aui:a>
			</c:if>

			<h3><strong><%= item.getName() %></strong></h3>

			<c:if test="<%= Validator.isNotNull(item.getDescription()) %>">
				<h6 class="text-default"><%= item.getDescription() %></h6>
			</c:if>

			<%
			Properties props = new OrderedProperties();

			PropertiesUtil.load(props, item.getProperties());

			Enumeration enu = props.propertyNames();

			while (enu.hasMoreElements()) {
				String propsKey = (String)enu.nextElement();
				String propsValue = props.getProperty(propsKey, StringPool.BLANK);
			%>

				<h6 class="text-default"><%= propsKey %>: <%= propsValue %></h6>

			<%
			}
			%>

			<%
			for (ShoppingItemPrice itemPrice : itemPrices) {
				if (itemPrice.getStatus() == ShoppingItemPriceConstants.STATUS_INACTIVE) {
					continue;
				}
			%>

				<h6 class="text-default">
					<c:choose>
						<c:when test="<%= (itemPrice.getMinQuantity()) == 0 && (itemPrice.getMaxQuantity() == 0) %>">
							<liferay-ui:message key="price" />:
						</c:when>
						<c:when test="<%= itemPrice.getMaxQuantity() != 0 %>">
							<liferay-ui:message arguments='<%= new Object[] {"<strong>" + itemPrice.getMinQuantity() + "</strong>", "<strong>" + itemPrice.getMaxQuantity() + "</strong>"} %>' key="price-for-x-to-x-items" translateArguments="<%= false %>" />
						</c:when>
						<c:when test="<%= itemPrice.getMaxQuantity() == 0 %>">
							<liferay-ui:message arguments='<%= "<strong>" + itemPrice.getMinQuantity() + "</strong>" %>' key="price-for-x-items-and-above" translateArguments="<%= false %>" />
						</c:when>
					</c:choose>

					<c:choose>
						<c:when test="<%= itemPrice.getDiscount() <= 0 %>">
							<%= currencyFormat.format(itemPrice.getPrice()) %><br />
						</c:when>
						<c:otherwise>
							<del><%= currencyFormat.format(itemPrice.getPrice()) %></del> <div class="alert alert-success"><%= currencyFormat.format(ShoppingUtil.calculateActualPrice(itemPrice)) %></div> / <liferay-ui:message key="you-save" />: <div class="alert alert-danger"><%= currencyFormat.format(ShoppingUtil.calculateDiscountPrice(itemPrice)) %> (<%= percentFormat.format(itemPrice.getDiscount()) %>)</div><br />
						</c:otherwise>
					</c:choose>
				</h6>

			<%
			}
			%>

			<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SHOPPING_ITEM_SHOW_AVAILABILITY) %>">
				<c:choose>
					<c:when test="<%= ShoppingUtil.isInStock(item) %>">
						<div class="alert alert-success">
							<liferay-ui:message key="availability" />: <liferay-ui:message key="in-stock" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-danger">
							<liferay-ui:message key="availability" />: <liferay-ui:message key="out-of-stock" />
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<%
			for (ShoppingItemField itemField : itemFields) {
			%>

				<c:if test="<%= Validator.isNotNull(itemField.getDescription()) %>">
					<div class="alert alert-info">
						<%= HtmlUtil.escape(itemField.getDescription()) %>
					</div>
				</c:if>

				<aui:select id='<%= "fieldId" + itemField.getItemFieldId() %>' label="<%= HtmlUtil.escape(itemField.getName()) %>" name='<%= "fieldName" + HtmlUtil.escapeAttribute(itemField.getName()) %>'>
					<aui:option label="select-option" value="" />

					<%
					for (String fieldValue : itemField.getValuesArray()) {
					%>

						<aui:option label="<%= HtmlUtil.escape(fieldValue) %>" />

					<%
					}
					%>

				</aui:select>

			<%
			}
			%>

		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" onClick='<%= renderResponse.getNamespace() + "addToCart();" %>' primary="<%= true %>" value="add-to-shopping-cart" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />addToCart() {
		document.<portlet:namespace />fm.<portlet:namespace />fields.value = '';

		<%
		for (ShoppingItemField itemField : itemFields) {
			String fieldName = itemField.getName();
		%>

			if (document.<portlet:namespace />fm['<portlet:namespace />fieldName<%= HtmlUtil.escapeJS(fieldName) %>'].value == '') {
				alert('<liferay-ui:message key="please-select-all-options" />');

				return;
			}

			document.<portlet:namespace />fm.<portlet:namespace />fields.value = document.<portlet:namespace />fm.<portlet:namespace />fields.value + '<%= HtmlUtil.escapeJS(fieldName) %>=' + document.<portlet:namespace />fm['<portlet:namespace />fieldName<%= HtmlUtil.escapeJS(fieldName) %>'].value + '&';

		<%
		}
		%>

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>