import hubspotConfig from '../../configs/hubspot';
import {DataSourceTypes} from 'shared/util/constants';
import {fetchConnectorEntityCount} from 'shared/api/connector';

jest.mock('shared/api/connector', () => ({
	fetchConnectorEntityCount: jest.fn(() => Promise.resolve(123))
}));

describe('hubspot config', () => {
	beforeEach(() => {
		(fetchConnectorEntityCount as jest.Mock).mockClear();
	});

	it('uses the hubspot slug', () => {
		expect(hubspotConfig.slug).toBe('hubspot');
	});

	it('uses the hubspot data source type enum', () => {
		expect(hubspotConfig.type).toBe(DataSourceTypes.Hubspot);
	});

	it('targets the hubspot webhooks endpoint', () => {
		expect(hubspotConfig.endpointPath).toBe('/api/hubspot_webhooks');
	});

	it('is not flagged as singleton (multiple instances allowed)', () => {
		expect(hubspotConfig.singleton).toBe(true);
	});

	it('exposes a contacts entity with the expected metadata', () => {
		expect(hubspotConfig.entities).toHaveLength(1);

		const [contacts] = hubspotConfig.entities;

		expect(contacts).toEqual(
			expect.objectContaining({
				accessor: 'contacts',
				icon: 'users'
			})
		);
		expect(typeof contacts.fetchCount).toBe('function');
	});

	it('delegates fetchCount to fetchConnectorEntityCount with the slug and entity', async () => {
		await hubspotConfig.entities[0].fetchCount!({
			groupId: '23',
			id: 'data-source-1'
		});

		expect(fetchConnectorEntityCount).toHaveBeenCalledWith(
			'hubspot',
			'contacts',
			{groupId: '23', id: 'data-source-1'}
		);
	});

	it('builds the language strings from the configured display name', () => {
		expect(hubspotConfig.languages.connectTitle).toContain(
			hubspotConfig.displayName
		);
		expect(hubspotConfig.languages.disconnectedAlert).toContain(
			hubspotConfig.displayName
		);
	});
});
