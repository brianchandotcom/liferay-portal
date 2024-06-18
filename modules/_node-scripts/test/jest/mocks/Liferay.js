/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-env jest */

const languageMap = {
	'days-abbreviation': 'd',
	'decimal-delimiter': '.',
	'hours-abbreviation': 'h',
	'minutes-abbreviation': 'min',
	'mmm-dd': 'MMM DD',
	'mmm-dd-hh-mm': 'MMM DD, HH:mm',
	'mmm-dd-hh-mm-a': 'MMM DD, hh:mm A',
	'mmm-dd-lt': 'MMM DD, LT',
	'mmm-dd-yyyy': 'MMM DD, YYYY',
	'mmm-dd-yyyy-lt': 'MMM DD, YYYY, LT',
	'option': 'Option',
	'thousand-abbreviation': 'K',
	'x-of-x-characters': '{0}/{1} characters',
};

const after = jest.fn(() => ({
	detach: () => {},
}));

const authToken = 'default-mocked-auth-token';

const component = jest.fn((componentId) => componentId);

const destroyComponent = jest.fn();

const detach = jest.fn((name, fn) => {
	global.removeEventListener(name, fn);
});

/**
 * Event support APIs on the `Liferay` object inherited from `A.Attributes`
 *
 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/events.js#L66
 * https://yuilibrary.com/yui/docs/api/classes/Attribute.html
 */
const events = {

	/**
	 * https://clarle.github.io/yui3/yui/docs/api/files/event-custom_js_event-target.js.html#l850
	 */
	after: jest.fn(),

	/**
	 * https://yuilibrary.com/yui/docs/api/files/event-custom_js_event-target.js.html#l372
	 */
	detach: jest.fn(),

	/**
	 * https://yuilibrary.com/yui/docs/api/files/event-custom_js_event-target.js.html#l700
	 */
	fire: jest.fn(),

	/**
	 * https://yuilibrary.com/yui/docs/api/files/event-custom_js_event-target.js.html#l227
	 */
	on: jest.fn(),

	/**
	 * https://yuilibrary.com/yui/docs/api/files/event-custom_js_event-target.js.html#l136
	 */
	once: jest.fn(),
};

const namespace = jest.fn((name) => {
	global.Liferay[name] = {};

	return global.Liferay[name];
});

const FeatureFlags = {};

const Icons = {
	spritemap: '/o/icons/pack/clay.svg',
};

/**
 * Contains a fallback/dummy implementation of
 * `Liferay.Language.get`. In practice, this call is rewritten in a
 * ServerFilter, so runtime calls to `Liferay.Language.get` should not
 * be found in production code. A better match for the real behaviour
 * would be a babel plugin to rewrite calls to the API with their
 * "translated" value.
 *
 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/modules/apps/frontend-js/frontend-js-aui-web/src/main/resources/META-INF/resources/liferay/language.js
 */
const Language = {

	/**
	 * https://github.com/liferay/liferay-portal/blob/f5bc2504a4f666241363a30975b6de5d57a6f627/portal-web/docroot/html/common/themes/top_js.jspf#L153
	 */
	available: {
		ar_SA: 'Arabic (Saudi Arabia)',
		ca_ES: 'Catalan (Spain)',
		de_DE: 'German (Germany)',
		en_US: 'English (United States)',
		es_ES: 'Spanish (Spain)',
		fi_FI: 'Finnish (Finland)',
		fr_FR: 'French (France)',
		hu_HU: 'Hungarian (Hungary)',
		ja_JP: 'Japanese (Japan)',
		nl_NL: 'Dutch (Netherlands)',
		pt_BR: 'Portuguese (Brazil)',
		sv_SE: 'Swedish (Sweden)',
		zh_CN: 'Chinese (China)',
	},

	/**
	 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/modules/apps/frontend-js/frontend-js-aui-web/src/main/resources/META-INF/resources/liferay/language.js#L18
	 */
	get: jest.fn((key) => {
		if (languageMap[key]) {
			return languageMap[key];
		}

		return key;
	}),
};

/**
 * https://github.com/liferay/liferay-portal/blob/f5bc2504a4f666241363a30975b6de5d57a6f627/portal-web/docroot/html/common/themes/top_js.jspf#L161
 */
const PortletKeys = {
	DOCUMENT_LIBRARY: 'DOCUMENT_LIBRARY',
	ITEM_SELECTOR: 'ITEM_SELECTOR',
};

/**
 * https://github.com/liferay/liferay-portal/blob/f8ea9617f99238f7f5b6e4824bf71ab2e64fdfdd/portal-web/docroot/html/common/themes/top_js.jspf#L168-L170
 */
const PropsValues = {
	JAVASCRIPT_SINGLE_PAGE_APPLICATION_TIMEOUT: 0,

	NTLM_AUTH_ENABLED: false,

	UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE: 104857600,
};

/**
 * https://github.com/liferay/liferay-portal/blob/a4866af62eb89c69ee00d0e69dbe7ff092b50048/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/global.es.js#L101-L104
 */
const Session = {
	extend: jest.fn(() => {}),

	/**
	 * https://github.com/liferay/liferay-portal/blob/a4866af62eb89c69ee00d0e69dbe7ff092b50048/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/global.es.js#L102
	 */
	get: jest.fn(() => Promise.resolve({})),

	/**
	 * https://github.com/liferay/liferay-portal/blob/a4866af62eb89c69ee00d0e69dbe7ff092b50048/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/global.es.js#L103
	 */
	set: jest.fn(() => Promise.resolve({})),
};

/**
 * Contains APIs that provide information about the running context of
 * the portal. The JS ThemeDisplay object is a representation of its
 * Java counterpart simplified for JS access.
 *
 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/portal-web/docroot/html/common/themes/top_js.jspf#L147
 */
const ThemeDisplay = {

	/**
	 * https://github.com/liferay/liferay-portal/blob/a4866af62eb89c69ee00d0e69dbe7ff092b50048/portal-web/docroot/html/common/themes/top_js.jspf#L188
	 */
	getBCP47LanguageId: jest.fn(() => 'en-US'),

	getDefaultLanguageId: jest.fn(() => 'en_US'),

	/**
	 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/portal-web/docroot/html/common/themes/top_js.jspf#L217
	 */
	getDoAsUserIdEncoded: jest.fn(() => ''),

	/**
	 * https://github.com/liferay/liferay-portal/blob/a4866af62eb89c69ee00d0e69dbe7ff092b50048/portal-web/docroot/html/common/themes/top_js.jspf#L220
	 */
	getLanguageId: jest.fn(() => 'en_US'),

	getLayoutRelativeControlPanelURL: jest.fn(
		() => 'layoutRelativeControlPanelURL'
	),

	getLayoutRelativeURL: jest.fn(() => 'getLayoutRelativeURL'),

	/**
	 * https://github.com/liferay/liferay-portal/blob/a4866af62eb89c69ee00d0e69dbe7ff092b50048/portal-web/docroot/html/common/themes/top_js.jspf#L226
	 */
	getPathContext: jest.fn(() => '/'),

	/**
	 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/portal-web/docroot/html/common/themes/top_js.jspf#L235
	 */
	getPathMain: jest.fn(() => '/c'),

	/**
	 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/portal-web/docroot/html/common/themes/top_js.jspf#L238
	 */
	getPathThemeImages: jest.fn(
		() => 'http://localhost:8080/o/admin-theme/images'
	),

	getPlid: jest.fn(() => 'plid'),

	/**
	 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/portal-web/docroot/html/common/themes/top_js.jspf#L247
	 */
	getPortalURL: jest.fn(() => 'http://localhost:8080'),

	getScopeGroupId: jest.fn(() => '20126'),

	isSignedIn: jest.fn(() => true),
};

/**
 * General utilities on the `Liferay` object. Possible API sources are:
 *
 * - https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/global.es.js
 * - https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/modules/apps/frontend-js/frontend-js-aui-web/src/main/resources/META-INF/resources/liferay/util.js
 */
const Util = {
	PortletURL: {
		createResourceURL: jest.fn(() => 'http://0.0.0.0/liferay/o'),
	},

	escape: jest.fn((data) => data),

	/**
	 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/modules/apps/frontend-js/frontend-js-aui-web/src/main/resources/META-INF/resources/liferay/util.js#L442
	 */
	getGeolocation: jest.fn(),

	/**
	 * https://github.com/liferay/liferay-portal/blob/a4866af62eb89c69ee00d0e69dbe7ff092b50048/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/global.es.js#L75
	 */
	isEqual: jest.fn((a, b) => a === b),

	/**
	 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/util/navigate.es.js
	 */
	navigate: jest.fn(),

	/**
	 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/modules/apps/frontend-js/frontend-js-web/test/liferay/util/ns.es.js
	 */
	ns: jest.fn(() => ({})),

	openToast: jest.fn(() => true),

	selectEntity: jest.fn(() => {}),

	/**
	 * https://github.com/liferay/liferay-portal/blob/31073fb75fb0d3b309f9e0f921cb7a469aa2703d/modules/apps/frontend-js/frontend-js-aui-web/src/main/resources/META-INF/resources/liferay/util.js#L999
	 */
	sub: jest.fn((string, ...args) => {
		const matchX = new RegExp('(^x-)|(-x-)|(-x$)', 'gm');

		if (string.match(matchX) && args) {
			return string.replace('x', args);
		}
		else if (args.length > 1) {
			args.forEach((arg, index) => {
				string = string.replace(`{${index}}`, arg);
			});
		}

		return string;
	}),
};

const CSP = {
	nonce: '',
};

module.exports = {
	...events,
	CSP,
	FeatureFlags,
	Icons,
	Language,
	PortletKeys,
	PropsValues,
	Session,
	ThemeDisplay,
	Util,
	after,
	authToken,
	component,
	destroyComponent,
	detach,
	namespace,
};
