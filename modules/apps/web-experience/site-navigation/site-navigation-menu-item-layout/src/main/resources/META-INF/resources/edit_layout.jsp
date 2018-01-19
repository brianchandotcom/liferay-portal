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
Layout selLayout = (Layout)request.getAttribute(WebKeys.SEL_LAYOUT);

String title = GetterUtil.getString(request.getAttribute(WebKeys.TITLE));
%>

<aui:input label="title" name="TypeSettingsProperties--title--" type="text" value="<%= title %>" />

<aui:input id="groupId" name="TypeSettingsProperties--groupId--" type="hidden" value="<%= (selLayout != null) ? selLayout.getGroupId() : StringPool.BLANK %>">
	<aui:validator name="required" />
</aui:input>

<aui:input id="layoutUuid" name="TypeSettingsProperties--layoutUuid--" type="hidden" value="<%= (selLayout != null) ? selLayout.getUuid() : StringPool.BLANK %>">
	<aui:validator name="required" />
</aui:input>

<aui:input id="privateLayout" name="TypeSettingsProperties--privateLayout--" type="hidden" value="<%= (selLayout != null) ? selLayout.isPrivateLayout() : StringPool.BLANK %>">
	<aui:validator name="required" />
</aui:input>

<p class="text-default">
	<span id="<portlet:namespace />layoutItemRemove" role="button">
		<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
	</span>
	<span id="<portlet:namespace />layoutNameInput">
		<c:choose>
			<c:when test="<%= selLayout != null %>">
				<%= selLayout.getName(locale) %>
			</c:when>
			<c:otherwise>
				<span class="text-muted"><liferay-ui:message key="none" /></span>
			</c:otherwise>
		</c:choose>
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

if (selLayout != null) {
	itemSelectorURL.setParameter("layoutUuid", selLayout.getUuid());
}
%>

<aui:script use="liferay-item-selector-dialog">
	var groupId = $('#<portlet:namespace />groupId');
	var layoutItemRemove = $('#<portlet:namespace />layoutItemRemove');
	var layoutNameInput = $('#<portlet:namespace />layoutNameInput');
	var layoutUuid = $('#<portlet:namespace />layoutUuid');
	var privateLayout = $('#<portlet:namespace />privateLayout');

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
								groupId.val(selectedItem.groupId);

								layoutUuid.val(selectedItem.id);

								privateLayout.val(selectedItem.privateLayout);

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