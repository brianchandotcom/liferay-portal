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
Map<String, Object> data = new HashMap<String, Object>();

PortletURL addURL = PortletURLFactoryUtil.create(request, ControlMenuPortletKeys.CONTROL_MENU, plid, PortletRequest.RENDER_PHASE);

addURL.setParameter("mvcPath", "/add_panel.jsp");
addURL.setParameter("stateMaximized", String.valueOf(themeDisplay.isStateMaximized()));
addURL.setParameter("viewAssetEntries", Boolean.TRUE.toString());
addURL.setWindowState(LiferayWindowState.EXCLUSIVE);

data.put("panelURL", addURL);

data.put("qa-id", "add");
%>

<liferay-ui:icon
	data="<%= data %>"
	icon="plus"
	id="addPanel"
	label="<%= false %>"
	linkCssClass="control-menu-icon"
	markupView="lexicon"
	message="add"
	url="javascript:;"
/>