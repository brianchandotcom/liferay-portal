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

<%@ include file="/asset_tags_selector/init.jsp" %>

<%
PortletRequest portletRequest = (PortletRequest)request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
PortletResponse portletResponse = (PortletResponse)request.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);
String namespace = AUIUtil.getNamespace(portletRequest, portletResponse);

String addCallback = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-tags-selector:addCallback"));
boolean allowAddEntry = GetterUtil.getBoolean((String)request.getAttribute("liferay-asset:asset-tags-selector:allowAddEntry"));
boolean autoFocus = GetterUtil.getBoolean((String)request.getAttribute("liferay-asset:asset-tags-selector:autoFocus"));
String eventName = (String)request.getAttribute("liferay-asset:asset-tags-selector:eventName");
long[] groupIds = (long[])request.getAttribute("liferay-asset:asset-tags-selector:groupIds");
String hiddenInput = (String)request.getAttribute("liferay-asset:asset-tags-selector:hiddenInput");
String id = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-tags-selector:id"));
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-asset:asset-tags-selector:portletURL");
String removeCallback = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-tags-selector:removeCallback"));
String tagNamesSeparatedWithCommas = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-tags-selector:tagNames"));

List<String> tagNames = Arrays.asList(StringUtil.split(tagNamesSeparatedWithCommas));

List<Object> selectedItems = new ArrayList<>();

for (String tagName : tagNames) {
	HashMap<String, String> item = new HashMap<>();

	selectedItems.add(item);

	item.put("label", tagName);
	item.put("value", tagName);
}
%>

<h4>
	<liferay-ui:message key="tags" />
</h4>

<%
HashMap<String, Object> context = new HashMap<>();

context.put("addCallback", namespace + addCallback);
context.put("eventName", eventName);
context.put("inputName", namespace + hiddenInput);
context.put("portletURL", portletURL.toString());
context.put("removeCallback", namespace + removeCallback);
context.put("selectedItems", selectedItems);
context.put("spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
%>

<input id="<%= namespace + hiddenInput %>" type="hidden" value="<%= tagNamesSeparatedWithCommas %>" />

<soy:component-renderer
	context="<%= context %>"
	module="asset_tags_selector/js/TagSelector.es"
	templateNamespace="com.liferay.asset.taglib.TagSelector.render"
/>