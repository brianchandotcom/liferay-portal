/* eslint-disable no-console */
/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	return !rtn.length ? null : rtn;
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

let menuTextFunc = undefined;
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
				let link = _a.href;
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
