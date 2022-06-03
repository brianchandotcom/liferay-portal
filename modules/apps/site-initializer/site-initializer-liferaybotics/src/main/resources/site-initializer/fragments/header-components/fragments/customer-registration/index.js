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
const enableRegisterPage = configuration.enableRegisterPage;
const runRegisterPageOnload = configuration.runRegisterPageOnload;

var menuTextFunc = undefined;
if (enableMenuText) {
	menuTextFunc = () => {
		const menuText = configuration.menuText;
		const _navbarMenu = document.querySelector('div.navbar-menu');
		const _registerSpan = queryInnerText(_navbarMenu, 'span', menuText);

		if (_registerSpan) {
			const _li = _registerSpan.closest('li');
			if (_li) {
				if (themeDisplay.isSignedIn()) {
					_li.style.display = 'none';
				}
				else {
					_li.style.display = '';
				}
			}
		}
	};
}

var registerPageFunc = undefined;
if (enableRegisterPage) {
	registerPageFunc = () => {
		const registerPageUrl = configuration.registerPageUrl;
		const _loginContainer = document.querySelector('div.login-container');
		if (_loginContainer) {
			const _createAccount = document.querySelector(
				"div.navigation a[href*='create_account']",
				_loginContainer
			);
			if (_createAccount) {
				var getUrl = window.location;
				_createAccount.setAttribute(
					'href',
					getUrl.protocol + '//' + getUrl.host + registerPageUrl
				);
			}
		}
	};
}

if (!menuTextFunc && !registerPageFunc) {
	if (enableDebug) {
		console.debug('No functions enabled');
	}

	return;
}

if (runMenuTextOnload || runRegisterPageOnload) {
	document.addEventListener('DOMContentLoaded', () => {
		if (runMenuTextOnload && menuTextFunc) {
			if (enableDebug) {
				console.debug('Running menu-text on load');
			}
			menuTextFunc();
		}

		if (runRegisterPageOnload && registerPageFunc) {
			if (enableDebug) {
				console.debug('Running register-page on load');
			}
			registerPageFunc();
		}
	});
}

if (!runMenuTextOnload && menuTextFunc) {
	if (enableDebug) {
		console.debug('Running menu-text immediately');
	}
	menuTextFunc();
}

if (!runRegisterPageOnload && registerPageFunc) {
	if (enableDebug) {
		console.debug('Running register-page immediately');
	}
	registerPageFunc();
}
