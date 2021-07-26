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
		<clay:container-fluid
			cssClass="devices"
		>
			<clay:row
				cssClass="default-devices mb-2"
			>
				<button class="btn btn-unstyled col-4 d-lg-block d-none lfr-device-item mb-3 selected text-center" data-device="desktop" type="button">
					<div class="c-inner px-0" tabindex="-1">
						<span class="icon icon-monospaced">
							<clay:icon
								symbol="desktop"
							/>
						</span>
						<span class="d-block mb-3 mt-1"><liferay-ui:message key="desktop" /></span>
					</div>
				</button>

				<button class="btn btn-unstyled col-4 d-lg-block d-none lfr-device-item mb-3 text-center" data-device="tablet" type="button">
					<div class="c-inner px-0" tabindex="-1">
						<span class="icon icon-monospaced">
							<clay:icon
								symbol="tablet-portrait"
							/>
						</span>
						<span class="hide icon icon-monospaced icon-rotate">
							<clay:icon
								symbol="tablet-landscape"
							/>
						</span>
						<span class="d-block mb-3 mt-1"><liferay-ui:message key="tablet" /></span>
					</div>
				</button>

				<button class="btn btn-unstyled col-4 lfr-device-item mb-3 text-center" data-device="smartphone" type="button">
					<div class="c-inner px-0" tabindex="-1">
						<span class="icon icon-monospaced">
							<clay:icon
								symbol="mobile-portrait"
							/>
						</span>
						<span class="hide icon icon-monospaced icon-rotate">
							<clay:icon
								symbol="mobile-landscape"
							/>
						</span>
						<span class="d-block mb-3 mt-1"><liferay-ui:message key="mobile" /></span>
					</div>
				</button>

				<button class="btn btn-unstyled col-4 d-lg-block d-none lfr-device-item text-center" data-device="autosize" type="button">
					<div class="c-inner px-0" tabindex="-1">
						<span class="icon icon-monospaced">
							<clay:icon
								symbol="autosize"
							/>
						</span>
						<span class="d-block mb-3 mt-1"><liferay-ui:message key="autosize" /></span>
					</div>
				</button>

				<button class="btn btn-unstyled col-4 d-lg-block d-none lfr-device-item text-center" data-device="custom" type="button">
					<div class="c-inner px-0" tabindex="-1">
						<span class="icon icon-monospaced">
							<clay:icon
								symbol="custom-size"
							/>
						</span>
						<span class="d-block mb-3 mt-1"><liferay-ui:message key="custom" /></span>
					</div>
				</button>
			</clay:row>

			<clay:row
				cssClass="custom-devices hide mt-3"
				hidden="hidden"
				id='<%= liferayPortletResponse.getNamespace() + "customDeviceContainer" %>'
			>
				<aui:input cssClass="input-sm" inlineField="<%= true %>" label='<%= LanguageUtil.get(request, "height") + " (px):" %>' name="height" size="4" value="600" wrapperCssClass="flex-grow-1 mr-3" />

				<aui:input cssClass="input-sm" inlineField="<%= true %>" label='<%= LanguageUtil.get(request, "width") + " (px):" %>' name="width" size="4" value="600" wrapperCssClass="flex-grow-1" />
			</clay:row>
		</clay:container-fluid>
	</div>
</div>

<aui:script use="liferay-product-navigation-simulation-device">
	var simulationDevice = new Liferay.SimulationDevice({
		devices: {
			autosize: {
				skin: 'autosize',
			},
			custom: {
				height: '#<portlet:namespace />height',
				resizable: true,
				width: '#<portlet:namespace />width',
			},
			desktop: {
				height: 1050,
				selected: true,
				width: 1300,
			},
			smartphone: {
				height: 640,
				preventTransition: true,
				rotation: true,
				skin: 'smartphone',
				width: 400,
			},
			tablet: {
				height: 900,
				preventTransition: true,
				rotation: true,
				skin: 'tablet',
				width: 760,
			},
		},
		inputHeight: '#<portlet:namespace />height',
		inputWidth: '#<portlet:namespace />width',
		namespace: '<portlet:namespace />',
	});

	Liferay.once('screenLoad', () => {
		simulationDevice.destroy();
	});

	A.one('.devices').delegate(
		'click',
		(event) => {
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