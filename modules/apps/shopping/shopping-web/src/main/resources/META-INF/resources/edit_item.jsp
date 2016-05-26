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

long itemId = BeanParamUtil.getLong(item, request, "itemId");

long categoryId = BeanParamUtil.getLong(item, request, "categoryId", ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

boolean infiniteStock = BeanParamUtil.getBoolean(item, request, "infiniteStock");

// Fields

ShoppingItemField[] itemFields = null;

int fieldsCount = ParamUtil.getInteger(request, "fieldsCount");
String fieldsCountParam = request.getParameter("fieldsCount");
if ((fieldsCountParam == null) || fieldsCountParam.equals(StringPool.NULL)) {
	if (item != null) {
		itemFields = (ShoppingItemField[])ShoppingItemFieldLocalServiceUtil.getItemFields(itemId).toArray(new ShoppingItemField[0]);
		fieldsCount = itemFields.length;
	}
	else {
		itemFields = new ShoppingItemField[0];
	}
}
else {
	itemFields = new ShoppingItemField[fieldsCount];
}

int fieldId = ParamUtil.getInteger(request, "fieldId", -1);

String fieldsQuantities = "";

if (item != null) {
	fieldsQuantities = GetterUtil.getString(item.getFieldsQuantities());
}

// Prices

ShoppingItemPrice[] itemPrices = null;

int pricesCount = ParamUtil.getInteger(request, "pricesCount", 1);
String pricesCountParam = request.getParameter("pricesCount");
if ((pricesCountParam == null) || pricesCountParam.equals(StringPool.NULL)) {
	if (item != null) {
		itemPrices = (ShoppingItemPrice[])ShoppingItemPriceLocalServiceUtil.getItemPrices(itemId).toArray(new ShoppingItemPrice[0]);
		pricesCount = itemPrices.length;
	}
	else {
		itemPrices = new ShoppingItemPrice[1];
	}
}
else {
	itemPrices = new ShoppingItemPrice[pricesCount];
}

int priceId = ParamUtil.getInteger(request, "priceId", -1);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((item == null) ? LanguageUtil.get(request, "new-item") : item.getName()));
%>

<portlet:actionURL name="/shopping/edit_item" var="editItemURL">
	<portlet:param name="mvcActionCommand" value="/shopping/edit_item" />
</portlet:actionURL>

<aui:form action="<%= editItemURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="scroll" type="hidden" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (item == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="categoryId" type="hidden" value="<%= categoryId %>" />
	<aui:input name="itemId" type="hidden" value="<%= itemId %>" />
	<aui:input name="fieldsCount" type="hidden" value="<%= fieldsCount %>" />
	<aui:input name="fieldId" type="hidden" />
	<aui:input name='<%= "fieldName" + fieldsCount %>' type="hidden" />
	<aui:input name='<%= "fieldValues" + fieldsCount %>' type="hidden" />
	<aui:input name='<%= "fieldDescription" + fieldsCount %>' type="hidden" />
	<aui:input name="fieldsQuantities" type="hidden" value="<%= fieldsQuantities %>" />
	<aui:input name="pricesCount" type="hidden" value="<%= pricesCount %>" />
	<aui:input name="priceId" type="hidden" />

	<liferay-ui:error exception="<%= DuplicateItemSKUException.class %>" message="the-item-sku-you-requested-is-already-taken" />
	<liferay-ui:error exception="<%= ItemNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= ItemSKUException.class %>" message="please-enter-a-valid-item-sku" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input bean="<%= item %>" model="<%= ShoppingItem.class %>" name="sku" />

			<aui:input bean="<%= item %>" model="<%= ShoppingItem.class %>" name="name" />

			<aui:input bean="<%= item %>" model="<%= ShoppingItem.class %>" name="description" />

			<aui:input bean="<%= item %>" model="<%= ShoppingItem.class %>" name="properties" />

			<aui:input bean="<%= item %>" model="<%= ShoppingItem.class %>" name="requiresShipping" />

			<aui:input bean="<%= item %>" model="<%= ShoppingItem.class %>" name="featured" />

			<aui:input checked="<%= infiniteStock %>" helpMessage="disable-stock-checking-help" label="disable-stock-checking" name="infiniteStock" onChange='<%= renderResponse.getNamespace() + "toggleInfiniteStock(this);" %>' type="checkbox" />

			<c:if test="<%= (fieldsCount == 0) && !infiniteStock %>">
				<aui:input bean="<%= item %>" model="<%= ShoppingItem.class %>" name="stockQuantity" />
			</c:if>
		</aui:fieldset>

		<c:if test="<%= item != null %>">
			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="parent-category">

				<%
				String categoryName = "";

				if (categoryId != ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
					ShoppingCategory category = ShoppingCategoryServiceUtil.getCategory(categoryId);

					category = category.toEscapedModel();

					categoryName = category.getName();
				}
				%>

				<aui:input label="category" name="categoryName" type="resource" value="<%= categoryName %>" />

				<aui:button id="selectCategoryButton" value="select" />

				<%
				String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('categoryId', 'categoryName', this, '" + renderResponse.getNamespace() + "');";
				%>

				<aui:button onClick="<%= taglibRemoveFolder %>" value="remove" />

				<aui:script sandbox="<%= true %>">
					$('#<portlet:namespace />selectCategoryButton').on(
						'click',
						function(event) {
							Liferay.Util.selectEntity(
								{
									dialog: {
										constrain: true,
										modal: true
									},
									id: '<portlet:namespace />selectCategory',
									title: '<liferay-ui:message arguments="category" key="select-x" />',
									uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/shopping/select_category" /><portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" /></portlet:renderURL>'
								},
								function(event) {
									var form = $(document.<portlet:namespace />fm);

									form.fm('categoryId').val(event.categoryid);

									form.fm('categoryName').val(_.unescape(event.name));
								}
							);
						}
					);
				</aui:script>
			</aui:fieldset>
		</c:if>

		<c:if test="<%= item == null %>">
			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= ShoppingItem.class.getName() %>"
				/>
			</aui:fieldset>
		</c:if>

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="fields">
			<aui:input name="fields" type="hidden" />

			<liferay-ui:error exception="<%= DuplicateItemFieldNameException.class %>">

				<%
				DuplicateItemFieldNameException difne = (DuplicateItemFieldNameException)errorException;
				%>

				<liferay-ui:message key="field-names-must-be-unique.-the-following-field-names-are-duplicates" /> <%= difne.getMessage() %>.
			</liferay-ui:error>

			<div class="alert alert-info">
				<liferay-ui:message key="fields-are-added-if-you-need-to-distinguish-items-based-on-criteria-chosen-by-the-user" />
			</div>

			<table class="lfr-table">

				<%
				for (int i = 0; i < fieldsCount; i++) {
					int curFieldId = i;

					if ((fieldId > -1) && (i >= fieldId)) {
						curFieldId++;
					}

					String fieldName = ParamUtil.getString(request, "fieldName" + curFieldId);
					String fieldNameParam = request.getParameter("fieldName" + curFieldId);
					if ((fieldNameParam == null) || fieldNameParam.equals(StringPool.NULL)) {
						if (itemFields[curFieldId] != null) {
							fieldName = itemFields[curFieldId].getName();
						}
					}

					String[] fieldValues = StringUtil.split(ParamUtil.getString(request, "fieldValues" + curFieldId));
					String fieldValuesParam = request.getParameter("fieldValues" + curFieldId);
					if ((fieldValuesParam == null) || fieldValuesParam.equals(StringPool.NULL)) {
						if (itemFields[curFieldId] != null) {
							fieldValues = itemFields[curFieldId].getValuesArray();
						}
					}

					String fieldDescription = ParamUtil.getString(request, "fieldDescription" + curFieldId);
					String fieldDescriptionParam = request.getParameter("fieldDescription" + curFieldId);
					if ((fieldDescriptionParam == null) || fieldDescriptionParam.equals(StringPool.NULL)) {
						if (itemFields[curFieldId] != null) {
							fieldDescription = itemFields[curFieldId].getDescription();
						}
					}
				%>

					<tr>
						<td>
							<aui:input cssClass="lfr-input-text-container" ignoreRequestValue="<%= true %>" label="name" maxlength="<%= ModelHintsConstants.TEXT_MAX_LENGTH %>" name='<%= "fieldName" + i %>' style="width: 100px;" type="text" value="<%= fieldName %>" />
						</td>
						<td>
							<aui:input cssClass="lfr-input-text-container" ignoreRequestValue="<%= true %>" label="values" name='<%= "fieldValues" + i %>' style="width: 100px;" type="text" value='<%= StringUtil.merge(fieldValues, ", ") %>' />
						</td>
						<td>
							<aui:input cssClass="lfr-input-text-container" ignoreRequestValue="<%= true %>" label="description" name='<%= "fieldDescription" + i %>' style="width: 150px;" type="text" value="<%= fieldDescription %>" />
						</td>

						<c:if test="<%= fieldsCount > 0 %>">
							<td>
								<aui:button onClick='<%= renderResponse.getNamespace() + "deleteField(" + i + ");" %>' value="delete" />
							</td>
						</c:if>
					</tr>

				<%
				}
				%>

			</table>

			<aui:button onClick='<%= renderResponse.getNamespace() + "addField();" %>' value="add-field" />

			<c:if test="<%= (fieldsCount > 0) && !infiniteStock %>">
				<aui:button onClick='<%= renderResponse.getNamespace() + "editItemQuantities();" %>' value="edit-stock-quantity" />
			</c:if>
		</aui:fieldset>

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="prices">
			<aui:input name="prices" type="hidden" />

			<table class="lfr-table">

				<%
				for (int i = 0; i < pricesCount; i++) {
					int curPriceId = i;

					if ((priceId > -1) && (i >= priceId)) {
						curPriceId++;
					}

					int minQuantity = ParamUtil.getInteger(request, "minQuantity" + curPriceId, 0);
					String minQuantityParam = request.getParameter("minQuantity" + curPriceId);
					if ((minQuantityParam == null) || minQuantityParam.equals(StringPool.NULL)) {
						if (itemPrices[curPriceId] != null) {
							minQuantity = itemPrices[curPriceId].getMinQuantity();
						}
					}

					int maxQuantity = ParamUtil.getInteger(request, "maxQuantity" + curPriceId);
					String maxQuantityParam = request.getParameter("maxQuantity" + curPriceId);
					if ((maxQuantityParam == null) || maxQuantityParam.equals(StringPool.NULL)) {
						if (itemPrices[curPriceId] != null) {
							maxQuantity = itemPrices[curPriceId].getMaxQuantity();
						}
					}

					double price = ParamUtil.getDouble(request, "price" + curPriceId, locale);
					String priceParam = request.getParameter("price" + curPriceId);
					if ((priceParam == null) || priceParam.equals(StringPool.NULL)) {
						if (itemPrices[curPriceId] != null) {
							price = itemPrices[curPriceId].getPrice();
						}
					}

					double discount = ParamUtil.getDouble(request, "discount" + curPriceId, locale) / 100;
					String discountParam = request.getParameter("discount" + curPriceId);
					if ((discountParam == null) || discountParam.equals(StringPool.NULL)) {
						if (itemPrices[curPriceId] != null) {
							discount = itemPrices[curPriceId].getDiscount();
						}
					}

					boolean taxable = ParamUtil.getBoolean(request, "taxable" + curPriceId, true);
					String taxableParam = request.getParameter("taxable" + curPriceId);
					if ((taxableParam == null) || taxableParam.equals(StringPool.NULL)) {
						if (itemPrices[curPriceId] != null) {
							taxable = itemPrices[curPriceId].isTaxable();
						}
					}

					double shipping = ParamUtil.getDouble(request, "shipping" + curPriceId, locale);
					String shippingParam = request.getParameter("shipping" + curPriceId);
					if ((shippingParam == null) || shippingParam.equals(StringPool.NULL)) {
						if (itemPrices[curPriceId] != null) {
							shipping = itemPrices[curPriceId].getShipping();
						}
					}

					boolean useShippingFormula = ParamUtil.getBoolean(request, "useShippingFormula" + curPriceId, true);
					String useShippingFormulaParam = request.getParameter("useShippingFormula" + curPriceId);
					if ((useShippingFormulaParam == null) || useShippingFormulaParam.equals(StringPool.NULL)) {
						if (itemPrices[curPriceId] != null) {
							useShippingFormula = itemPrices[curPriceId].isUseShippingFormula();
						}
					}

					boolean active = ParamUtil.getBoolean(request, "active" + curPriceId, true);
					String activeParam = request.getParameter("active" + curPriceId);
					if ((activeParam == null) || activeParam.equals(StringPool.NULL)) {
						if (itemPrices[curPriceId] != null) {
							int status = itemPrices[curPriceId].getStatus();

							if ((status == ShoppingItemPriceConstants.STATUS_ACTIVE_DEFAULT) || (status == ShoppingItemPriceConstants.STATUS_ACTIVE)) {
								active = true;
							}
							else {
								active = false;
							}
						}
					}

					String defaultPriceParam = request.getParameter("defaultPrice");
					boolean defaultPrice = (curPriceId == 0 ? true : false);
					if (Validator.isNotNull(defaultPriceParam)) {
						if (ParamUtil.getInteger(request, "defaultPrice") == curPriceId) {
							defaultPrice = true;
						}
						else {
							defaultPrice = false;
						}
					}
					else {
						if (itemPrices[curPriceId] != null) {
							int status = itemPrices[curPriceId].getStatus();

							if (status == ShoppingItemPriceConstants.STATUS_ACTIVE_DEFAULT) {
								defaultPrice = true;
							}
							else {
								defaultPrice = false;
							}
						}
					}
				%>

					<tr>
						<td>
							<table class="lfr-table">
								<tr>
									<td>
										<aui:input field="minQuantity" fieldParam='<%= "minQuantity" + i %>' ignoreRequestValue="<%= true %>" label="min-qty" model="<%= ShoppingItemPrice.class %>" name="minQuantity" value="<%= String.valueOf(minQuantity) %>" />
									</td>
									<td>
										<aui:input field="maxQuantity" fieldParam='<%= "maxQuantity" + i %>' ignoreRequestValue="<%= true %>" label="max-qty" model="<%= ShoppingItemPrice.class %>" name="maxQuantity" value="<%= String.valueOf(maxQuantity) %>" />
									</td>
									<td>
										<aui:input field="price" fieldParam='<%= "price" + i %>' format="<%= doubleFormat %>" ignoreRequestValue="<%= true %>" label="price" model="<%= ShoppingItemPrice.class %>" name="price" value="<%= String.valueOf(price) %>" />
									</td>
									<td>
										<aui:input field="discount" fieldParam='<%= "discount" + i %>' ignoreRequestValue="<%= true %>" label="discount" model="<%= ShoppingItemPrice.class %>" name="discount" value="<%= String.valueOf(discount * 100) %>" />
									</td>
									<td>
										<aui:input ignoreRequestValue="<%= true %>" label="taxable" name='<%= "taxable" + i %>' param='<%= "taxable" + i %>' type="checkbox" value="<%= taxable %>" />
									</td>
								</tr>
							</table>

							<table class="lfr-table">
								<tr>
									<td>
										<aui:input field="shipping" fieldParam='<%= "shipping" + i %>' format="<%= doubleFormat %>" ignoreRequestValue="<%= true %>" model="<%= ShoppingItemPrice.class %>" name="shipping" value="<%= String.valueOf(shipping) %>" />
									</td>
									<td>
										<aui:input ignoreRequestValue="<%= true %>" label="use-shipping-formula" name='<%= "useShippingFormula" + i %>' type="checkbox" value="<%= useShippingFormula %>" />
									</td>
									<td>
										<aui:input ignoreRequestValue="<%= true %>" label="active" name='<%= "active" + i %>' type="checkbox" value="<%= active %>" />
									</td>
									<td>
										<aui:input checked="<%= defaultPrice %>" ignoreRequestValue="<%= true %>" label="default" name="defaultPrice" onClick='<%= "document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "active" + i + ".checked = true;" %>' type="radio" value="<% i %>" />
									</td>

									<c:if test="<%= pricesCount > 1 %>">
										<td>
											<aui:button onClick='<%= renderResponse.getNamespace() + "deletePrice(" + i + ");" %>' value="delete" />
										</td>
									</c:if>
								</tr>
							</table>

							<c:if test="<%= (i + 1) < pricesCount %>">
								<br />
							</c:if>
						</td>
					</tr>

				<%
				}
				%>

			</table>

			<aui:button onClick='<%= renderResponse.getNamespace() + "addPrice();" %>' value="add-price" />
		</aui:fieldset>

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="images">
			<liferay-ui:error exception="<%= ItemLargeImageNameException.class %>">

				<%
				String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.SHOPPING_IMAGE_EXTENSIONS, StringPool.COMMA);
				%>

				<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
			</liferay-ui:error>

			<liferay-ui:error exception="<%= ItemLargeImageSizeException.class %>">

				<%
				long imageMaxSize = PrefsPropsUtil.getLong(PropsKeys.SHOPPING_IMAGE_LARGE_MAX_SIZE);
				%>

				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(imageMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= ItemMediumImageNameException.class %>">

				<%
				String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.SHOPPING_IMAGE_EXTENSIONS, StringPool.COMMA);
				%>

				<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
			</liferay-ui:error>

			<liferay-ui:error exception="<%= ItemMediumImageSizeException.class %>">

				<%
				long imageMaxSize = PrefsPropsUtil.getLong(PropsKeys.SHOPPING_IMAGE_MEDIUM_MAX_SIZE);
				%>

				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(imageMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= ItemSmallImageNameException.class %>">

				<%
				String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.SHOPPING_IMAGE_EXTENSIONS, StringPool.COMMA);
				%>

				<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
			</liferay-ui:error>

			<liferay-ui:error exception="<%= ItemSmallImageSizeException.class %>">

				<%
				long imageMaxSize = PrefsPropsUtil.getLong(PropsKeys.SHOPPING_IMAGE_SMALL_MAX_SIZE);
				%>

				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(imageMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<aui:input label="small-image-url" name="smallImageURL" />

			<span style="font-size: xx-small;">-- <%= StringUtil.toUpperCase(LanguageUtil.get(request, "or")) %> --</span> <liferay-ui:message key="small-image" />

			<aui:input label="" name="smallFile" type="file" />

			<aui:input checked="<%= ((item != null) && item.isSmallImage()) ? true : false %>" label="use-small-image" name="smallImage" type="checkbox" />

			<aui:input label="medium-image-url" name="mediumImageURL" />

			<span style="font-size: xx-small;">-- <%= StringUtil.toUpperCase(LanguageUtil.get(request, "or")) %> --</span> <liferay-ui:message key="medium-image" />

			<aui:input label="" name="mediumFile" type="file" />

			<aui:input checked="<%= ((item != null) && item.isMediumImage()) ? true : false %>" label="use-medium-image" name="mediumImage" type="checkbox" />

			<aui:input label="large-image-url" name="largeImageURL" />

			<span style="font-size: xx-small;">-- <%= StringUtil.toUpperCase(LanguageUtil.get(request, "or")) %> --</span> <liferay-ui:message key="large-image" />

			<aui:input label="" name="largeFile" type="file" />

			<aui:input checked="<%= ((item != null) && item.isLargeImage()) ? true : false %>" label="use-large-image" name="largeImage" type="checkbox" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />addField() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('scroll').val('<portlet:namespace />fields');
		form.fm('fieldsCount').val(<%= fieldsCount + 1 %>);

		submitForm(form);
	}

	function <portlet:namespace />addPrice() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('scroll').val('<portlet:namespace />prices');
		form.fm('pricesCount').val(<%= pricesCount + 1 %>);

		submitForm(form);
	}

	function <portlet:namespace />deleteField(i) {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('scroll').val('<portlet:namespace />fields');
		form.fm('fieldsCount').val(<%= fieldsCount - 1 %>);
		form.fm('fieldId').val(i);

		submitForm(form);
	}

	function <portlet:namespace />deletePrice(i) {
		var form = AUI.$(document.<portlet:namespace />fm);

		if (form.fm('defaultPrice' + i).prop('checked')) {
			alert('<%= UnicodeLanguageUtil.get(request, "you-cannot-delete-or-deactivate-a-default-price") %>');
		}
		else if (form.fm('pricesCount').val() > 1) {
			form.fm('scroll').val('<portlet:namespace />prices');
			form.fm('pricesCount').val(<%= pricesCount - 1 %>);
			form.fm('priceId').val(i);

			submitForm(form);
		}
	}

	function <portlet:namespace />editItemQuantities() {
		var form = AUI.$(document.<portlet:namespace />fm);

		var itemQuantitiesURL = '<liferay-portlet:renderURL anchor="<%= false %>" windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/shopping/edit_item_quantities" /></liferay-portlet:renderURL>';

		<%
		for (int i = 0; i < fieldsCount; i++) {
		%>

			itemQuantitiesURL += '&<portlet:namespace />n<%= i %>=' + encodeURIComponent(form.fm('fieldName<%= i %>').val());
			itemQuantitiesURL += '&<portlet:namespace />v<%= i %>=' + encodeURIComponent(form.fm('fieldValues<%= i %>').val());

		<%
		}
		%>

		Liferay.Util.openWindow(
			{
				dialog: {
					width: 680
				},
				id: '<portlet:namespace />itemQuantities',
				refreshWindow: window,
				title: '<%= UnicodeLanguageUtil.get(request, "edit-stock-quantity") %>',
				uri: itemQuantitiesURL
			}
		);
	}

	function <portlet:namespace />toggleInfiniteStock(checkbox) {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>