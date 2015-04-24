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

<script>
	var PATH_PORTLET = Liferay.ThemeDisplay.getPathContext() + '/o/comliferayportletcssweb';

	AUI().applyConfig(
			{
				groups: {
					journal: {
						base: PATH_PORTLET + '/js/',
						modules: {
							'liferay-look-and-feel': {
								path: 'look_and_feel.js',
								requires: [
									'aui-color-picker-popover',
									'aui-io-plugin-deprecated',
									'aui-io-request',
									'aui-tabview',
									'liferay-portlet-url',
									'liferay-util-window',
									'liferay-widget-zindex'
								]
							}
						},
						root: PATH_PORTLET + '/js/'
					}
				}
			}
	);

	Liferay.provide(
		Liferay.Portlet,
		'loadCSSEditor',
		function(portletId, baseActionURL, baseRenderURL, baseResourceURL) {
			Liferay.PortletCSS.init(portletId, baseActionURL, baseRenderURL, baseResourceURL);
		},
		['liferay-look-and-feel']
	);
</script>