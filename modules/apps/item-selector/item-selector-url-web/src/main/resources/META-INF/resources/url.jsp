<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ItemSelectorURLViewDisplayContext itemSelectorURLViewDisplayContext = (ItemSelectorURLViewDisplayContext)request.getAttribute(ItemSelectorURLView.ITEM_SELECTOR_URL_VIEW_DISPLAY_CONTEXT);
%>

<div class="lfr-form-content">
	<clay:sheet>
		<div class="panel-group panel-group-flush">
			<react:component
				module="js/ItemSelectorUrl.es"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"eventName", itemSelectorURLViewDisplayContext.getItemSelectedEventName()
					).build()
				%>'
			/>
		</div>
	</clay:sheet>
</div>