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
boolean setCustomName = GetterUtil.getBoolean(request.getAttribute(SiteNavigationMenuItemTypeLayoutWebKeys.SET_CUSTOM_NAME));
SiteNavigationMenuItem siteNavigationMenuItem = (SiteNavigationMenuItem)request.getAttribute(SiteNavigationWebKeys.SITE_NAVIGATION_MENU_ITEM);

String taglibOnChange = "Liferay.Util.toggleDisabled('#" + renderResponse.getNamespace() + "nameBoundingBox input, [for=" + renderResponse.getNamespace() + "name]', !event.target.checked)";
%>

<aui:fieldset>
	<aui:input checked="<%= setCustomName %>" helpMessage="set-custom-name-help" label="set-custom-name" name="TypeSettingsProperties--setCustomName--" onChange="<%= taglibOnChange %>" type="checkbox" />
</aui:fieldset>

<aui:input autoFocus="<%= true %>" disabled="<%= !setCustomName %>" label="name" localized="<%= true %>" maxlength='<%= ModelHintsUtil.getMaxLength(SiteNavigationMenuItem.class.getName(), "name") %>' name="name" placeholder="name" type="text" value='<%= SiteNavigationMenuItemUtil.getSiteNavigationMenuItemXML(siteNavigationMenuItem, "name") %>'>
	<aui:validator name="required" />
</aui:input>

<aui:input id="groupId" name="TypeSettingsProperties--groupId--" type="hidden" value="<%= (selLayout != null) ? selLayout.getGroupId() : StringPool.BLANK %>">
	<aui:validator name="required" />
</aui:input>

<aui:input id="privateLayout" name="TypeSettingsProperties--privateLayout--" type="hidden" value="<%= (selLayout != null) ? selLayout.isPrivateLayout() : StringPool.BLANK %>">
	<aui:validator name="required" />
</aui:input>

<div class="form-group input-text-wrapper text-default">
	<div class="d-inline-block" id="<portlet:namespace />layoutItemRemove" role="button">
		<aui:icon cssClass="icon-monospaced" image="times-circle" markupView="lexicon" />
	</div>

	<div class="d-inline-block">
		<span id="<portlet:namespace />layoutNameInput">
			<c:choose>
				<c:when test="<%= selLayout != null %>">
					<%= HtmlUtil.escape(selLayout.getName(locale)) %>
				</c:when>
				<c:otherwise>
					<span class="text-muted"><liferay-ui:message key="none" /></span>
				</c:otherwise>
			</c:choose>
		</span>
	</div>

	<aui:input id="layoutUuid" name="TypeSettingsProperties--layoutUuid--" type="hidden" value="<%= (selLayout != null) ? selLayout.getUuid() : StringPool.BLANK %>">
		<aui:validator name="required" />
	</aui:input>
</div>

<aui:button name="chooseLayout" value="choose" />

<%
String eventName = renderResponse.getNamespace() + "selectLayout";

ItemSelector itemSelector = (ItemSelector)request.getAttribute(SiteNavigationMenuItemTypeLayoutWebKeys.ITEM_SELECTOR);

ItemSelectorCriterion itemSelectorCriterion = new LayoutItemSelectorCriterion();

itemSelectorCriterion.setDesiredItemSelectorReturnTypes(new UUIDItemSelectorReturnType());

PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(renderRequest), eventName, itemSelectorCriterion);

if (selLayout != null) {
	itemSelectorURL.setParameter("layoutUuid", selLayout.getUuid());
}
%>

<aui:script use="aui-base,liferay-item-selector-dialog,node-event-simulate">
	var groupId = A.one('#<portlet:namespace />groupId');
	var layoutItemRemove = A.one('#<portlet:namespace />layoutItemRemove');
	var layoutNameInput = A.one('#<portlet:namespace />layoutNameInput');
	var layoutUuid = A.one('#<portlet:namespace />layoutUuid');
	var privateLayout = A.one('#<portlet:namespace />privateLayout');

	A.one('#<portlet:namespace />chooseLayout').on('click', function(event) {
		Liferay.Loader.require(
			'frontend-js-web/liferay/ItemSelectorDialog.es',
			function(ItemSelectorDialog) {
				const itemSelectorDialog = new ItemSelectorDialog.default({
					buttonAddLabel: '<liferay-ui:message key="done" />',
					eventName:'<%= eventName %>',
					title: '<liferay-ui:message key="select-layout" />',
					url: '<%= itemSelectorURL.toString() %>'
				});

				itemSelectorDialog.on('selectedItemChange', function(event) {
					var assetEntryIds = event.selectedItem;

					if (selectedItem) {
							groupId.val(selectedItem.groupId);

							layoutUuid.val(selectedItem.id);

							privateLayout.val(selectedItem.privateLayout);

							layoutNameInput.html(selectedItem.name);
							layoutNameInput.simulate('change');

							layoutItemRemove.removeClass('hide');
						}
				});

				itemSelectorDialog.open();
			}
		);
	});

	layoutItemRemove.on('click', function(event) {
		layoutNameInput.html('<liferay-ui:message key="none" />');

		layoutUuid.val('');

		layoutItemRemove.addClass('hide');
	});
</aui:script>