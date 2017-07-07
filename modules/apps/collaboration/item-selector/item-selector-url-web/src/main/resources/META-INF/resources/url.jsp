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
ItemSelectorURLViewDisplayContext itemSelectorURLViewDisplayContext = (ItemSelectorURLViewDisplayContext)request.getAttribute(ItemSelectorURLView.ITEM_SELECTOR_URL_VIEW_DISPLAY_CONTEXT);
%>

<aui:row cssClass="container-fluid-1280 lfr-item-viewer" id="itemSelectorUrlContainer">
	<aui:col cssClass="url-view" width="<%= 60 %>">
		<aui:input helpMessage='<%= LanguageUtil.format(request, "for-example-x", "http://www.liferay.com/liferay.png", false) %>' label='<%= LanguageUtil.get(resourceBundle, "image-url") %>' name="urlInput" placeholder="http://" />

		<aui:button disabled="<%= true %>" name="previewBtn" value='<%= LanguageUtil.get(resourceBundle, "enter") %>' />
	</aui:col>
</aui:row>

<aui:script use="liferay-item-selector-url">
	new Liferay.ItemSelectorUrl(
		{
			closeCaption: '<%= itemSelectorURLViewDisplayContext.getTitle(locale) %>',
			namespace: '<portlet:namespace />',
			on: {
				selectedItem: function(event) {
					Liferay.Util.getOpener().Liferay.fire('<%= itemSelectorURLViewDisplayContext.getItemSelectedEventName() %>', event);
				}
			},
			rootNode: '#<portlet:namespace />itemSelectorUrlContainer'
		}
	);
</aui:script>