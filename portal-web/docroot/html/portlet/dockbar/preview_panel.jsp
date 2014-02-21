<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<div id="<portlet:namespace />devicePreviewContainer">
	<aui:button cssClass="close pull-right" name="closePanelPreview" value="&times;" />

	<h1><liferay-ui:message key="preview" /></h1>

	<aui:nav cssClass="nav-list">
		<aui:nav-item cssClass="autosize lfr-device-item" data-device="autosize">
			<div class="device-info">
				<span class="device-name"><liferay-ui:message key="autosize" /></span>
				<span class="device-dimensions">100%</span>
			</div>
		</aui:nav-item>

		<aui:nav-item cssClass="lfr-device-item selected smartphone" data-device="smartphone">
			<div class="device-info">
				<span class="device-name"><liferay-ui:message key="smartphone" /></span>
				<span class="device-dimensions">768px</span>
			</div>
		</aui:nav-item>

		<aui:nav-item cssClass="lfr-device-item tablet" data-device="tablet">
			<div class="device-info">
				<span class="device-name"><liferay-ui:message key="tablet" /></span>
				<span class="device-dimensions">1024px</span>
			</div>
		</aui:nav-item>

		<aui:nav-item cssClass="desktop lfr-device-item" data-device="desktop">
			<div class="device-info">
				<span class="device-name"><liferay-ui:message key="desktop" /></span>
				<span class="device-dimensions">1280px</span>
			</div>
		</aui:nav-item>

		<aui:nav-item cssClass="lfr-device-item" data-device="custom">
			<p><liferay-ui:message key="custom" /> (px)</p>

			<aui:input cssClass="input-mini" inlineField="<%= true %>" label="" name="width" value="400" /><span> &times; </span><aui:input cssClass="input-mini" inlineField="<%= true %>" label="" name="height" value="400" />
		</aui:nav-item>
	</aui:nav>

	<div class="alert">
		<small><liferay-ui:message key="preview-may-not-be-accurate" /></small>
	</div>
</div>

<aui:script use="liferay-dockbar-device-preview">
	var devicePreview = new Liferay.Dockbar.DevicePreview(
		{
			devices: {
				autosize: {
					skin: 'autosize'
				},
				custom: {
					height: '#<portlet:namespace />height',
					resizable: true,
					width: '#<portlet:namespace />width'
				},
				desktop: {
					height: 1050,
					width: 1300
				},
				smartphone: {
					height: 640,
					preventTransition: true,
					rotation: true,
					selected: true,
					skin: 'smartphone',
					width: 400
				},
				tablet: {
					height: 900,
					preventTransition: true,
					rotation: true,
					skin: 'tablet',
					width: 760
				}
			},
			inputHeight: '#<portlet:namespace />height',
			inputWidth: '#<portlet:namespace />width',
			namespace: '<portlet:namespace />'
		}
	);

	Liferay.once('dockbarHidePanel', A.bind('destroy', devicePreview));
</aui:script>