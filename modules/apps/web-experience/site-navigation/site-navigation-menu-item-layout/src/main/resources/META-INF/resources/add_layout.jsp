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

<aui:fieldset-group markupView="lexicon">
	<aui:fieldset>
		<aui:input id="layoutUuid" name="TypeSettingsProperties--layoutUuid--" type="hidden">
			<aui:validator name="required" />
		</aui:input>

		<p class="text-default">
			<span class="hide" id="<portlet:namespace />layoutItemRemove" role="button">
				<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
			</span>
			<span id="<portlet:namespace />layoutNameInput">
				<span class="text-muted"><liferay-ui:message key="none" /></span>
			</span>
		</p>

		<aui:button name="chooseLayout" value="choose" />

		<%
		String eventName = renderResponse.getNamespace() + "selectLayout";

		ItemSelector itemSelector = (ItemSelector)request.getAttribute(SiteNavigationMenuItemTypeLayoutWebKeys.ITEM_SELECTOR);

		LayoutItemSelectorCriterion layoutItemSelectorCriterion = new LayoutItemSelectorCriterion();

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes = new ArrayList<ItemSelectorReturnType>();

		desiredItemSelectorReturnTypes.add(new UUIDItemSelectorReturnType());

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(desiredItemSelectorReturnTypes);

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(renderRequest), eventName, layoutItemSelectorCriterion);
		%>

		<aui:script use="liferay-item-selector-dialog">
			var layoutItemRemove = $('#<portlet:namespace />layoutItemRemove');
			var layoutNameInput = $('#<portlet:namespace />layoutNameInput');
			var layoutUuid = $('#<portlet:namespace />layoutUuid');

			$('#<portlet:namespace />chooseLayout').on(
				'click',
				function(event) {
					var itemSelectorDialog = new A.LiferayItemSelectorDialog(
						{
							eventName: '<%= eventName %>',
							on: {
								selectedItemChange: function(event) {
									var selectedItem = event.newVal;

									if (selectedItem) {
										layoutUuid.val(selectedItem.id);

										layoutNameInput.html(selectedItem.name);

										layoutItemRemove.removeClass('hide');
									}
								}
							},
							'strings.add': '<liferay-ui:message key="done" />',
							title: '<liferay-ui:message key="select-layout" />',
							url: '<%= itemSelectorURL.toString() %>'
						}
					);

					itemSelectorDialog.open();
				}
			);

			layoutItemRemove.on(
				'click',
				function(event) {
					layoutNameInput.html('<liferay-ui:message key="none" />');

					layoutUuid.val('');

					layoutItemRemove.addClass('hide');
				}
			);
		</aui:script>
	</aui:fieldset>
</aui:fieldset-group>