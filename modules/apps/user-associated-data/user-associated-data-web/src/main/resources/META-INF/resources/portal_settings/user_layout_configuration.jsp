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
UserLayoutConfiguration userLayoutConfiguration = (UserLayoutConfiguration)request.getAttribute(UserLayoutConfiguration.class.getName());
%>

<div class="row">
	<div class="col-md-12">
		<aui:input checked="<%= GetterUtil.getBoolean(userLayoutConfiguration.userPrivateLayout()) %>" id="enablePrivateLayouts" inlineLabel="right" label="enable-private-layouts" labelCssClass="simple-toggle-switch" name="userPrivateLayout" onClick="togglePrivateLayoutsAutoCreate()" type="toggle-switch" value="<%= userLayoutConfiguration.userPrivateLayout() %>" />

		<aui:input checked="<%= GetterUtil.getBoolean(userLayoutConfiguration.userPrivateLayoutAutoCreate()) %>" id="privateLayoutsAutoCreate" inlineLabel="right" label="private-layouts-auto-create" labelCssClass="simple-toggle-switch" name="userPrivateLayoutAutoCreate" type="toggle-switch" value="<%= userLayoutConfiguration.userPrivateLayoutAutoCreate() %>" />

		<aui:input checked="<%= GetterUtil.getBoolean(userLayoutConfiguration.userPublicLayout()) %>" id="enablePublicLayouts" inlineLabel="right" label="enable-public-layouts" labelCssClass="simple-toggle-switch" name="userPublicLayout" onClick="togglePublicLayoutsAutoCreate()" type="toggle-switch" value="<%= userLayoutConfiguration.userPublicLayout() %>" />

		<aui:input checked="<%= GetterUtil.getBoolean(userLayoutConfiguration.userPublicLayoutAutoCreate()) %>" id="publicLayoutsAutoCreate" inlineLabel="right" label="public-layouts-auto-create" labelCssClass="simple-toggle-switch" name="userPublicLayoutAutoCreate" type="toggle-switch" value="<%= userLayoutConfiguration.userPublicLayoutAutoCreate() %>" />
	</div>
</div>

<script>
	function togglePrivateLayoutsAutoCreate() {
		let enablePrivateLayoutsElement = document.getElementById(
			'<portlet:namespace />enablePrivateLayouts'
		);
		let privateLayoutsAutoCreateElement = document.getElementById(
			'<portlet:namespace />privateLayoutsAutoCreate'
		);

		if (!enablePrivateLayoutsElement.checked) {
			privateLayoutsAutoCreateElement.checked = false;
			privateLayoutsAutoCreateElement.setAttribute('disabled', '');
		}
		else {
			privateLayoutsAutoCreateElement.removeAttribute('disabled');
		}
	}

	function togglePublicLayoutsAutoCreate() {
		let enablePublicLayoutsElement = document.getElementById(
			'<portlet:namespace />enablePublicLayouts'
		);
		let publicLayoutsAutoCreateElement = document.getElementById(
			'<portlet:namespace />publicLayoutsAutoCreate'
		);

		if (!enablePublicLayoutsElement.checked) {
			publicLayoutsAutoCreateElement.checked = false;
			publicLayoutsAutoCreateElement.setAttribute('disabled', '');
		}
		else {
			publicLayoutsAutoCreateElement.removeAttribute('disabled');
		}
	}
</script>