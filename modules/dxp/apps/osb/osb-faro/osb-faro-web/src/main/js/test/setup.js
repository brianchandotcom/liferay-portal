import '@testing-library/jest-dom/extend-expect';
import * as pedantic from './pedantic';
import lang from './lang';

pedantic.enable();

jest.mock('shared/util/svg');
jest.mock('shared/api');
jest.mock('shared/components/DocumentTitle');
jest.mock('react-dom');

document.body.className = 'dxp';

global.analytics = {
	group: () => {},
	identify: () => {},
	track: () => {}
};

global.AUI = () => ({
	use: (module, callback) => callback()
});

global.Liferay = {
	Language: {
		get: lang
	}
};

global.console = {error: jest.fn(), log: console.log, warn: jest.fn()}; // eslint-disable-line no-console

global.localStorage = (() => {
	let store = {};

	return {
		/**
		 * Clear
		 */
		clear() {
			store = {};
		},

		/**
		 * Get Item
		 * @param {string} key
		 */
		getItem(key) {
			return store[key];
		},

		/**
		 * Remove Item
		 * @param {string} key
		 */
		removeItem(key) {
			delete store[key];
		},

		/**
		 * Set Item
		 * @param {string} key
		 * @param {string} value
		 */
		setItem(key, value) {
			store[key] = value.toString();
		}
	};
})();

global.pendo = {
	initialize: () => {}
};

require('jest-extended');
require('jest-canvas-mock');
