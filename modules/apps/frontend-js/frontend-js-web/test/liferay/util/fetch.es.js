'use strict';

import fetch from '../../../src/main/resources/META-INF/resources/liferay/util/fetch.es';

describe('Liferay.Util.fetch', () => {
	const sampleUrl = 'http://sampleurl.com';

	it('should apply default settings if none are given', () => {
		global.fetch = jest.fn((resource, init) => {
			expect(init).toEqual({
				credentials: 'include'
			});
		});

		fetch(sampleUrl);

		global.fetch.mockRestore();
	});

	it('should override a default setting with given setting', () => {
		global.fetch = jest.fn((resource, init) => {
			expect(init).toEqual({
				credentials: 'omit'
			});
		});

		fetch(sampleUrl, {
			credentials: 'omit'
		});

		global.fetch.mockRestore();
	});

	it('should merge default settings with given different settings', () => {
		global.fetch = jest.fn((resource, init) => {
			expect(init).toEqual({
				credentials: 'include',
				method: 'get'
			});
		});

		fetch(sampleUrl, {
			method: 'get'
		});

		global.fetch.mockRestore();
	});
});
