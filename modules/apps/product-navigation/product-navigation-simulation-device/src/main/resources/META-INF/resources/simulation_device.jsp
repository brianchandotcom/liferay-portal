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

<li id="<portlet:namespace />simulationDeviceContainer">
	<h3 class="list-group-heading"><liferay-ui:message key="device" /></h3>

	<div class="list-group-panel">
		<div class="devices">
			<div class="container-fluid default-devices">
				<div class="col-xs-3 lfr-device-item selected text-center" data-device="smartphone">
					<aui:icon cssClass="icon icon-monospaced" image="mobile-portrait" markupView="lexicon" />

					<aui:icon cssClass="hide icon icon-monospaced icon-rotate" image="mobile-landscape" markupView="lexicon" />

					<h5 class="text-default"><%= LanguageUtil.get(resourceBundle, "mobile") %></h5>

					<h5 class="text-default">768px</h5>
				</div>

				<div class="col-xs-3 lfr-device-item text-center" data-device="tablet">
					<aui:icon cssClass="icon icon-monospaced" image="tablet-portrait" markupView="lexicon" />

					<aui:icon cssClass="hide icon icon-monospaced icon-rotate" image="tablet-landscape" markupView="lexicon" />

					<h5 class="text-default"><%= LanguageUtil.get(resourceBundle, "tablet") %></h5>

					<h5 class="text-default">1024px</h5>
				</div>

				<div class="col-xs-3 lfr-device-item text-center" data-device="desktop">
					<aui:icon cssClass="icon icon-monospaced" image="desktop" markupView="lexicon" />

					<h5 class="text-default"><%= LanguageUtil.get(resourceBundle, "desktop") %></h5>

					<h5 class="text-default">1280px</h5>
				</div>

				<div class="col-xs-3 lfr-device-item text-center" data-device="autosize">
					<aui:icon cssClass="icon icon-monospaced" image="full-size" markupView="lexicon" />

					<h5 class="text-default"><%= LanguageUtil.get(resourceBundle, "autosize") %></h5>

					<h5 class="text-default">100%</h5>
				</div>
			</div>

			<div class="container-fluid custom-devices">
				<aui:input inlineField="<%= true %>" inlineLabel="left" name="width" size="4" value="400" wrapperCssClass="col-xs-5" />

				<aui:input inlineField="<%= true %>" inlineLabel="left" name="height" size="4" value="400" wrapperCssClass="col-xs-5" />

				<span class="col-xs-2 lfr-device-item" data-device="custom">
					<aui:icon cssClass="icon icon-monospaced" image="cog" markupView="lexicon" />
				</span>
			</div>
		</div>

		<div class="preview-info-message">
			<h5 class="text-default"><%= LanguageUtil.get(resourceBundle, "preview-may-not-be-accurate") %></h5>
		</div>
	</div>
</li>

<aui:script use="liferay-product-navigation-simulation-device">
	var simulationDevice = new Liferay.SimulationDevice(
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

	Liferay.once('dockbarHidePanel', A.bind('destroy', simulationDevice));
</aui:script>