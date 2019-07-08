require('whatwg-fetch');

const enzyme = require('enzyme');
const EnzymeAdapter = require('enzyme-adapter-react-16');

window.AUI = () => ({
	use: (module, callback) => callback()
});

const languageMap = {
	'dd-mmm': 'DD MMM',
	'dd-mmm-hh-a': 'DD MMM, hh A',
	'dd-mmm-yyyy': 'DD MMM, YYYY',
	'decimal-delimiter': '.',
	'mmm-dd-lt': 'MMM DD, LT',
	'thousand-abbreviation': 'K'
};

window.Liferay = {
	authToken: 'auth',
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
	}
};

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
