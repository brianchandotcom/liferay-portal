import {buildLanguages} from '../buildLanguages';

describe('buildLanguages', () => {
	it('returns every key declared in the Languages contract', () => {
		const languages = buildLanguages('Acme');

		expect(Object.keys(languages).sort()).toEqual(
			[
				'connectDescription',
				'connectTitle',
				'disconnectedAlert',
				'endpointHelper',
				'endpointLabel',
				'reconnectHelper',
				'successAlert',
				'syncHelper',
				'tokenLabel'
			].sort()
		);
	});

	it('substitutes the display name in connect-related strings', () => {
		const languages = buildLanguages('Acme');

		expect(languages.connectTitle).toContain('Acme');
		expect(languages.connectDescription).toContain('Acme');
	});

	it('substitutes the display name in disconnected and reconnect strings', () => {
		const languages = buildLanguages('Acme');

		expect(languages.disconnectedAlert).toContain('Acme');
		expect(languages.reconnectHelper).toContain('Acme');
	});

	it('substitutes the display name in endpoint and token labels', () => {
		const languages = buildLanguages('Acme');

		expect(languages.endpointHelper).toContain('Acme');
		expect(languages.endpointLabel).toContain('Acme');
		expect(languages.tokenLabel).toContain('Acme');
	});

	it('substitutes the display name in the success alert', () => {
		const languages = buildLanguages('Acme');

		expect(languages.successAlert).toContain('Acme');
	});

	it('substitutes the display name multiple times in syncHelper', () => {
		const languages = buildLanguages('Acme');
		const occurrences = languages.syncHelper.match(/Acme/g) ?? [];

		expect(occurrences.length).toBeGreaterThanOrEqual(2);
	});

	it('returns string values for every key', () => {
		const languages = buildLanguages('Acme');

		Object.values(languages).forEach(value => {
			expect(typeof value).toBe('string');
		});
	});
});
