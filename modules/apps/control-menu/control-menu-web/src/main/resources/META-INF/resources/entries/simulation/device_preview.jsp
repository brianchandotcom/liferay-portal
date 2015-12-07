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

<div id="<portlet:namespace />devicePreviewContainer">
	<aui:button cssClass="close" name="closePanelPreview" value="&times;" />

	<h1><liferay-ui:message key="device" /></h1>

	<aui:nav>
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
				<div class="col-xs-5">
					<aui:input cssClass="form-control" inlineField="<%= true %>" label="" name="width" value="400" />
				</div>

				<div class="col-xs-2">
					<span> &times; </span>
				</div>

				<div class="col-xs-5">
					<aui:input cssClass="form-control" inlineField="<%= true %>" label="" name="height" value="400" />
				</div>
		</aui:nav-item>
	</aui:nav>

	<div class="alert alert-warning">
		<small><liferay-ui:message key="preview-may-not-be-accurate" /></small>
	</div>
</div>

<aui:script use="liferay-control-menu-device-preview">
	var devicePreview = new Liferay.ControlMenu.DevicePreview(
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