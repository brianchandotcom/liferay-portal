/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const setAttribute = (node, attributes) => {
	attributes.map(({key, value}) => node.setAttribute(key, value));

	return node;
};

const createElement = (object) => {
	const {attributes, tagname} = object;

	return setAttribute(document.createElement(tagname), attributes);
};

export default createElement;
