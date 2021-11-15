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
DisplayPageSiteNavigationMenuTypeDisplayContext displayPageSiteNavigationMenuTypeDisplayContext = new DisplayPageSiteNavigationMenuTypeDisplayContext(request);
%>

<aui:input id="classNameId" name="TypeSettingsProperties--classNameId--" type="hidden" value="<%= displayPageSiteNavigationMenuTypeDisplayContext.getClassNameId() %>">
	<aui:validator name="required" />
</aui:input>

<aui:input id="classPK" name="TypeSettingsProperties--classPK--" type="hidden" value="<%= displayPageSiteNavigationMenuTypeDisplayContext.getClassPK() %>">
	<aui:validator name="required" />
</aui:input>

<aui:input id="classTypeId" name="TypeSettingsProperties--classTypeId--" type="hidden" value="<%= displayPageSiteNavigationMenuTypeDisplayContext.getClassTypeId() %>" />

<aui:input id="title" name="TypeSettingsProperties--title--" type="hidden" value="<%= displayPageSiteNavigationMenuTypeDisplayContext.getTitle() %>" />

<aui:input id="type" name="TypeSettingsProperties--type--" type="hidden" value="<%= displayPageSiteNavigationMenuTypeDisplayContext.getType() %>" />

<aui:input autoFocus="<%= true %>" disabled="<%= true %>" label="title" localized="<%= false %>" name="originalTitle" placeholder="title" type="text" value="<%= displayPageSiteNavigationMenuTypeDisplayContext.getOriginalTitle() %>" />

<div>
	<p class="list-group-title">
		<liferay-ui:message key="item-type" />
	</p>

	<p class="small" id="<portlet:namespace />itemTypeLabel">
		<%= HtmlUtil.escape(displayPageSiteNavigationMenuTypeDisplayContext.getItemType()) %>
	</p>
</div>

<%
String itemSubtype = displayPageSiteNavigationMenuTypeDisplayContext.getItemSubtype();
%>

<div class="<%= Validator.isNull(itemSubtype) ? "d-none" : "" %>" id="<portlet:namespace />itemSubtype">
	<div>
		<p class="list-group-title">
			<liferay-ui:message key="item-subtype" />
		</p>

		<p class="small" id="<portlet:namespace />itemSubtypeLabel">
			<%= HtmlUtil.escape(itemSubtype) %>
		</p>
	</div>
</div>

<%
String eventName = liferayPortletResponse.getNamespace() + "selectInfoItem";

ItemSelector itemSelector = (ItemSelector)request.getAttribute(SiteNavigationMenuItemTypeDisplayPageWebKeys.ITEM_SELECTOR);

InfoItemItemSelectorCriterion itemSelectorCriterion = new InfoItemItemSelectorCriterion();

itemSelectorCriterion.setDesiredItemSelectorReturnTypes(new InfoItemItemSelectorReturnType());

PortletURL infoItemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(request), liferayPortletResponse.getNamespace() + "selectInfoItem", itemSelectorCriterion);
%>

<clay:button
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"eventName", eventName
		).put(
			"getItemTypeURL", displayPageSiteNavigationMenuTypeDisplayContext.getItemTypeURL(liferayPortletResponse)
		).put(
			"itemSelectorURL", infoItemSelectorURL.toString()
		).build()
	%>'
	cssClass="mb-4"
	displayType="secondary"
	id='<%= liferayPortletResponse.getNamespace() + "chooseInfoItem" %>'
	label='<%= LanguageUtil.get(resourceBundle, "choose") %>'
	propsTransformer="js/ChooseInfoItemButtonPropsTransformer"
	small="<%= true %>"
/>