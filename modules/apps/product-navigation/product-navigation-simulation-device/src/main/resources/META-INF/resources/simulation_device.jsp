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

<div id="<portlet:namespace />simulationDeviceContainer">
	<div class="list-group-panel">
		<div class="container-fluid devices">
			<div class="default-devices row">
				<button class="btn btn-unstyled col-4 d-lg-block d-none lfr-device-item selected text-center" data-device="desktop" type="button">
					<aui:icon cssClass="icon icon-monospaced" image="desktop" markupView="lexicon" />

					<small><%= LanguageUtil.get(resourceBundle, "desktop") %></small>
				</button>

				<button class="btn btn-unstyled col-4 d-lg-block d-none lfr-device-item text-center" data-device="tablet" type="button">
					<aui:icon cssClass="icon icon-monospaced" image="tablet-portrait" markupView="lexicon" />

					<aui:icon cssClass="hide icon icon-monospaced icon-rotate" image="tablet-landscape" markupView="lexicon" />

					<small><%= LanguageUtil.get(resourceBundle, "tablet") %></small>
				</button>

				<button class="btn btn-unstyled col-4 lfr-device-item text-center" data-device="smartphone" type="button">
					<aui:icon cssClass="icon icon-monospaced" image="mobile-portrait" markupView="lexicon" />

					<aui:icon cssClass="hide icon icon-monospaced icon-rotate" image="mobile-landscape" markupView="lexicon" />

					<small><%= LanguageUtil.get(resourceBundle, "mobile") %></small>
				</button>

				<button class="btn btn-unstyled col-4 d-lg-block d-none lfr-device-item text-center" data-device="autosize" type="button">
					<aui:icon cssClass="icon icon-monospaced" image="autosize" markupView="lexicon" />

					<small><%= LanguageUtil.get(resourceBundle, "autosize") %></small>
				</button>

				<button class="btn btn-unstyled col-4 d-lg-block d-none lfr-device-item text-center" data-device="custom" type="button">
					<aui:icon cssClass="icon icon-monospaced" image="custom-size" markupView="lexicon" />

					<small><liferay-ui:message key="custom" /></small>
				</button>
			</div>

			<div class="custom-devices d-lg-flex d-none hide row" id="<portlet:namespace />customDeviceContainer">
				<aui:input cssClass="input-sm" inlineField="<%= true %>" label='<%= LanguageUtil.get(request, "height") + " (px):" %>' name="height" size="4" value="600" wrapperCssClass="col-6" />

				<aui:input cssClass="input-sm" inlineField="<%= true %>" label='<%= LanguageUtil.get(request, "width") + " (px):" %>' name="width" size="4" value="600" wrapperCssClass="col-6" />
			</div>
		</div>
	</div>
</div>

<aui:script use="liferay-product-navigation-simulation-device">
	var simulationDevice = new Liferay.SimulationDevice({
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
				selected: true,
				width: 1300
			},
			smartphone: {
				height: 640,
				preventTransition: true,
				rotation: true,
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
	});

	Liferay.once('screenLoad', function() {
		simulationDevice.destroy();
	});

	A.one('.devices').delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var dataDevice = currentTarget.attr('data-device');

			var customDeviceContainer = A.one(
				'#<portlet:namespace />customDeviceContainer'
			);

			if (dataDevice === 'custom') {
				customDeviceContainer.show();
			}
			else {
				customDeviceContainer.hide();
			}
		},
		'.lfr-device-item'
	);
</aui:script>