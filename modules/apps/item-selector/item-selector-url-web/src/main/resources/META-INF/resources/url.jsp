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

Map<String, Object> data = HashMapBuilder.<String, Object>put(
	"eventName", itemSelectorURLViewDisplayContext.getItemSelectedEventName()
).build();
%>

<div class="lfr-form-content">
	<div class="sheet sheet-lg">
		<div class="panel-group panel-group-flush">
			<react:component
				data="<%= data %>"
				module="js/ItemSelectorUrl.es"
			/>
		</div>
	</div>
</div>