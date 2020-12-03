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
SiteNavigationMenuItem siteNavigationMenuItem = (SiteNavigationMenuItem)request.getAttribute(SiteNavigationWebKeys.SITE_NAVIGATION_MENU_ITEM);
boolean useCustomName = GetterUtil.getBoolean(request.getAttribute(SiteNavigationMenuItemTypeLayoutWebKeys.USE_CUSTOM_NAME));

String taglibOnChange = "Liferay.Util.toggleDisabled('#" + liferayPortletResponse.getNamespace() + "nameBoundingBox input, [for=" + liferayPortletResponse.getNamespace() + "name]', !event.target.checked)";
%>

<aui:fieldset>
	<aui:input checked="<%= useCustomName %>" helpMessage="use-custom-name-help" label="use-custom-name" name="TypeSettingsProperties--useCustomName--" onChange="<%= taglibOnChange %>" type="checkbox" />
</aui:fieldset>

<aui:input autoFocus="<%= true %>" disabled="<%= !useCustomName %>" label="name" localized="<%= true %>" maxlength='<%= ModelHintsUtil.getMaxLength(SiteNavigationMenuItem.class.getName(), "name") %>' name="name" placeholder="name" type="text" value='<%= SiteNavigationMenuItemUtil.getSiteNavigationMenuItemXML(siteNavigationMenuItem, "name") %>'>
	<aui:validator name="required" />
</aui:input>

<aui:input id="groupId" name="TypeSettingsProperties--groupId--" type="hidden" value="<%= (selLayout != null) ? selLayout.getGroupId() : StringPool.BLANK %>">
	<aui:validator name="required" />
</aui:input>

<aui:input id="privateLayout" name="TypeSettingsProperties--privateLayout--" type="hidden" value="<%= (selLayout != null) ? selLayout.isPrivateLayout() : StringPool.BLANK %>">
	<aui:validator name="required" />
</aui:input>

<div class="form-group input-text-wrapper mb-2 text-default">
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

<clay:button
	cssClass="mb-4"
	displayType="secondary"
	id='<%= liferayPortletResponse.getNamespace() + "chooseLayout" %>'
	label='<%= LanguageUtil.get(resourceBundle, "choose") %>'
	small="<%= true %>"
/>

<%
String eventName = liferayPortletResponse.getNamespace() + "selectLayout";

ItemSelector itemSelector = (ItemSelector)request.getAttribute(SiteNavigationMenuItemTypeLayoutWebKeys.ITEM_SELECTOR);

ItemSelectorCriterion itemSelectorCriterion = new LayoutItemSelectorCriterion();

itemSelectorCriterion.setDesiredItemSelectorReturnTypes(new UUIDItemSelectorReturnType());

PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(renderRequest), eventName, itemSelectorCriterion);

if (selLayout != null) {
	itemSelectorURL.setParameter("layoutUuid", selLayout.getUuid());
}
%>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "editLayout" %>'
	context='<%=
		HashMapBuilder.<String, Object>put(
			"eventName", eventName
		).put(
			"itemSelectorURL", itemSelectorURL.toString()
		).build()
	%>'
	module="js/EditLayout"
	servletContext="<%= application %>"
/>