/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const enzyme = require('enzyme');
const EnzymeAdapter = require('enzyme-adapter-react-16');

window.AUI = () => ({
	use: (module, callback) => callback()
});

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
	'thousand-abbreviation': 'K'
};

window.Liferay = {
	Language: {
		get: key => {
			if (languageMap[key]) {
				return languageMap[key];
			}

			return key;
		}
	},
	Session: {
		extend: () => {}
	},
	ThemeDisplay: {
		getBCP47LanguageId: () => 'en-US',
		getLanguageId: () => 'en_US',
		getPathThemeImages: () => 'http://localhost:8080/o/admin-theme/images',
		getUserId: () => '123',
		getUserName: () => 'Test Test'
	},
	authToken: 'auth'
};

// eslint-disable-next-line no-console
global.console = {error: jest.fn(), log: console.log, warn: console.warn};

global.localStorage = (() => {
	let store = {};

	return {
		clear() {
			store = {};
		},
		getItem(key) {
			return store[key];
		},
		setItem(key, value) {
			store[key] = value.toString();
		}
	};
})();

enzyme.configure({adapter: new EnzymeAdapter()});

global.mount = enzyme.mount;
global.shallow = enzyme.shallow;
