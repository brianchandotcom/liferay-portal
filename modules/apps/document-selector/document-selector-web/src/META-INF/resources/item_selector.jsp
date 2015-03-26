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
String itemSelectedCallback = liferayPortletResponse.getNamespace() + "itemSelectedCallback";

ItemSelectorViewRenderers itemSelectorViewRenderers = ItemSelectorViewRenderers.get(liferayPortletRequest);
%>

<aui:script>
	function <%= itemSelectedCallback %>(type, item) {
	}
</aui:script>

<liferay-ui:tabs names="<%= StringUtil.merge(itemSelectorViewRenderers.getTitles()) %>" param="tabs1" refresh="<%= false %>" type="pills">

	<%
	for (String title : itemSelectorViewRenderers.getTitles()) {
		ItemSelectorViewRenderer<?> itemSelectorViewRenderer = itemSelectorViewRenderers.getItemSelectorViewRenderer(title);
	%>

		<liferay-ui:section>
			<div class="tab-body">
				<%= itemSelectorViewRenderer.getHTML(itemSelectedCallback) %>
			</div>
		</liferay-ui:section>

	<%
	}
	%>

</liferay-ui:tabs>