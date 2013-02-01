<#--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
-->

<@aui["script"] use="aui-base">
	Liferay.provide(
		window,
		'<@portlet["namespace"] />switchType',
		function() {
			A.one('#<@portlet["namespace"] />importBundle').ancestor('.aui-field-text').toggle();
			A.one('#<@portlet["namespace"] />location').ancestor('.aui-field-text').toggle();
		},
		['aui-base']
	);
</@>

<@aui["script"] use="module-admin">
	Liferay.ModuleAdmin.init(
		{
			namespace: '<@portlet["namespace"] />',
			redirectURL: '<@portlet["renderURL"] copyCurrentRenderParameters=(false) />',
			serviceURL: '<@portlet["resourceURL"] copyCurrentRenderParameters=(false) />'
		}
	);
</@>