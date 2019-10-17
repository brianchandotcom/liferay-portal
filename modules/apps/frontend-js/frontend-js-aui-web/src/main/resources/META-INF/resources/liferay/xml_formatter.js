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

/**
 * The XML Formatter Utility
 *
 * @deprecated As of Athanasius(7.3.x), replaced by Liferay.Util.formatXML
 * @module liferay-xml-formatter
 */

AUI.add(
	'liferay-xml-formatter',
	A => {
		var Lang = A.Lang;

		var XMLFormatter = A.Component.create({
			ATTRS: {
				lineIndent: {
					validator: Lang.isString,
					value: '\r\n'
				},

				tagIndent: {
					validator: Lang.isString,
					value: '\t'
				}
			},

			EXTENDS: A.Base,

			NAME: 'liferayxmlformatter',

			prototype: {
				format(content) {
					var instance = this;

					var tagIndent = instance.get('tagIndent');

					var lineIndent = instance.get('lineIndent');

					return Liferay.Util.formatXML(content, {
						lineIndent,
						tagIndent
					});
				}
			}
		});

		Liferay.XMLFormatter = XMLFormatter;
	},
	'',
	{
		requires: ['aui-base']
	}
);
