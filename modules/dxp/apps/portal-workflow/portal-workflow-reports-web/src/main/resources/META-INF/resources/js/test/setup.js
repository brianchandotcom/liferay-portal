const fs = require('fs');
const path = require('path');
const properties = require('properties');

const LANG_KEY_PATH = path.resolve(
	'src',
	'main',
	'resources',
	'content',
	'Language.properties'
);

const languageProperties = fs.readFileSync(LANG_KEY_PATH);

const TRANSLATIONS =
	properties.parse(languageProperties.toString('utf8')) || {};

window.AUI = () => ({
	use: (module, callback) => callback()
});

window.Liferay = {
	authToken: 'auth',
	Language: {
		get: key => TRANSLATIONS[key] || key
	},
	Session: {
		extend: () => {}
	},
	ThemeDisplay: {
		getUserId: () => '123',
		getUserName: () => 'Test Test'
	}
};

global.console = {error: jest.fn(), log: console.log};

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

require('jest-extended');
require('jest-canvas-mock');