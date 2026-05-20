import {DataSourceTypes} from 'shared/util/constants';
import {
	getConnectorConfig,
	listAvailableConnectors,
	listConnectors
} from '../registry';

describe('connector registry', () => {
	describe('getConnectorConfig', () => {
		it('returns the demandbase config when looked up by enum value', () => {
			const config = getConnectorConfig(DataSourceTypes.Demandbase);

			expect(config).toBeDefined();
			expect(config?.slug).toBe('demandbase');
			expect(config?.type).toBe(DataSourceTypes.Demandbase);
		});

		it('returns the hubspot config when looked up by enum value', () => {
			const config = getConnectorConfig(DataSourceTypes.Hubspot);

			expect(config).toBeDefined();
			expect(config?.slug).toBe('hubspot');
			expect(config?.type).toBe(DataSourceTypes.Hubspot);
		});

		it('returns the marketo config when looked up by enum value', () => {
			const config = getConnectorConfig(DataSourceTypes.Marketo);

			expect(config).toBeDefined();
			expect(config?.slug).toBe('marketo');
			expect(config?.type).toBe(DataSourceTypes.Marketo);
		});

		it('is case-insensitive on the lookup key', () => {
			expect(getConnectorConfig('demandbase')?.slug).toBe('demandbase');
			expect(getConnectorConfig('HuBsPoT')?.slug).toBe('hubspot');
		});

		it('returns undefined for unknown types', () => {
			expect(getConnectorConfig('UNKNOWN')).toBeUndefined();
		});

		it('returns undefined when given empty input', () => {
			expect(getConnectorConfig('')).toBeUndefined();
			expect(getConnectorConfig(undefined)).toBeUndefined();
		});
	});

	describe('listConnectors', () => {
		it('returns every registered connector config', () => {
			const slugs = listConnectors()
				.map(c => c.slug)
				.sort();

			expect(slugs).toEqual(['demandbase', 'hubspot', 'marketo']);
		});
	});

	describe('listAvailableConnectors', () => {
		it('hides singleton connectors that already exist on the data source list', () => {
			const existing = new Set([DataSourceTypes.Demandbase]);

			const slugs = listAvailableConnectors(existing).map(c => c.slug);

			expect(slugs).not.toContain('demandbase');
			expect(slugs).toContain('hubspot');
		});

		it('returns all connectors when none are present yet', () => {
			const slugs = listAvailableConnectors(new Set())
				.map(c => c.slug)
				.sort();

			expect(slugs).toEqual(['demandbase', 'hubspot', 'marketo']);
		});
	});
});
