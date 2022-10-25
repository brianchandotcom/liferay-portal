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

<liferay-util:buffer
	var="onClickFn"
>
	Liferay.Util.openModal({
		onClose: function(event) {
			window.location.reload();
		},
		title: '<liferay-ui:message key="import" />',
		url: '<%=
			PortletURLBuilder.create(
				PortalUtil.getControlPanelPortletURL(liferayPortletRequest, StyleBookPortletKeys.STYLE_BOOK, PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/style_book/view_import"
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		%>'
	});
</liferay-util:buffer>

<liferay-ui:icon
	iconCssClass="download"
	message="import"
	onClick="<%= onClickFn %>"
	url="javascript:void(0);"
/>