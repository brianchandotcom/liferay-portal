/* eslint-disable no-console */
/* eslint-disable no-undef */
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

if (!fragmentNamespace) {
	return;
}

if (layoutMode === 'edit') {
	return;
}

const queryInnerTextAll = function (root, selector, regex) {
	if (typeof regex === 'string') {
		regex = new RegExp(regex, 'i');
	}
	const elements = [...root.querySelectorAll(selector)];
	const rtn = elements.filter((element) => {
		return element.innerText.match(regex);
	});

	return rtn.length === 0 ? null : rtn;
};

const queryInnerText = function (root, selector, text) {
	try {
		const result = queryInnerTextAll(root, selector, text);
		if (Array.isArray(result)) {
			return result[0];
		}
		else {
			return result;
		}
	}
	catch (error) {
		console.log(error);

		return null;
	}
};

const enableDebug = configuration.enableDebug;
const enableMenuText = configuration.enableMenuText;
const runMenuTextOnload = configuration.runMenuTextOnload;

var menuTextFunc = undefined;
if (enableMenuText) {
	menuTextFunc = () => {
		const menuText = configuration.menuText;
		const pageLink = configuration.pageLink;
		const pageLocation = configuration.pageLocation;
		const _navbarMenu = document.querySelector('div.navbar-menu');
		const _registerSpan = queryInnerText(_navbarMenu, 'span', menuText);

		if (_registerSpan) {
			const _a = _registerSpan.closest('a');
			if (_a) {
				var link = _a.href;
				const regex = /\/(group|web)\//i;
				link = link.replace(regex, `/${pageLocation}/`);
				const friendlyUrlIndex = link.lastIndexOf('/');
				link =
					link.substring(0, friendlyUrlIndex) +
					(pageLink.startsWith('/') ? pageLink : `/${pageLink}`);
				console.log('Changing' + _a.href + ' to ' + link);
				_a.href = link;
			}
		}
	};
}

if (!menuTextFunc) {
	if (enableDebug) {
		console.debug('No functions enabled');
	}

	return;
}

if (runMenuTextOnload) {
	document.addEventListener('DOMContentLoaded', () => {
		if (runMenuTextOnload && menuTextFunc) {
			if (enableDebug) {
				console.debug('Running menu-text on load');
			}
			menuTextFunc();
		}
	});
}

if (!runMenuTextOnload && menuTextFunc) {
	if (enableDebug) {
		console.debug('Running menu-text immediately');
	}
	menuTextFunc();
}
