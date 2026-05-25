import {ConnectorStatus} from '../types';
import {
	getInitialLogEntries,
	getTransitionEntry
} from '../getConnectorStatusItems';

describe('getInitialLogEntries', () => {
	it('seeds a single Token generated entry for Inactive with no data', () => {
		const entries = getInitialLogEntries(ConnectorStatus.Inactive, 0);

		expect(entries).toHaveLength(1);
		expect(entries[0].title).toBe('Token Generated');
	});

	it('seeds Listening and Token generated for Active with no data', () => {
		const entries = getInitialLogEntries(ConnectorStatus.Active, 0);

		expect(entries).toHaveLength(2);
		expect(entries[0].title).toBe('Listening...');
		expect(entries[1].title).toBe('Token Generated');
	});

	it('seeds Data flow, Listening, Token generated for Active with data', () => {
		const entries = getInitialLogEntries(ConnectorStatus.Active, 5);

		expect(entries).toHaveLength(3);
		expect(entries[0].title).toBe('Data Flow Established');
		expect(entries[0].iconDisplayType).toBe('success');
		expect(entries[1].title).toBe('Listening...');
		expect(entries[2].title).toBe('Token Generated');
	});

	it('seeds Inactive data flow and prior history for Inactive with data', () => {
		const entries = getInitialLogEntries(ConnectorStatus.Inactive, 10);

		expect(entries).toHaveLength(4);
		expect(entries[0].title).toBe('Inactive Data Flow');
		expect(entries[1].title).toBe('Data Flow Established');
		expect(entries[2].title).toBe('Listening...');
		expect(entries[3].title).toBe('Token Generated');
	});

	it('seeds disconnected history for Disconnected status', () => {
		const entries = getInitialLogEntries(ConnectorStatus.Disconnected, 0);

		expect(entries).toHaveLength(4);
		expect(entries[0].title).toBe('Data Source Disconnected');
		expect(entries[1].title).toBe('Inactive Data Flow');
		expect(entries[2].title).toBe('Data Flow Established');
		expect(entries[3].title).toBe('Listening...');
	});
});

describe('getTransitionEntry', () => {
	it('returns Token generated for Inactive with no data', () => {
		const entry = getTransitionEntry(ConnectorStatus.Inactive, 0);

		expect(entry.title).toBe('Token Generated');
		expect(entry.iconDisplayType).toBe('success');
	});

	it('returns Inactive data flow for Inactive with data', () => {
		const entry = getTransitionEntry(ConnectorStatus.Inactive, 3);

		expect(entry.title).toBe('Inactive Data Flow');
		expect(entry.iconDisplayType).toBe('secondary');
	});

	it('returns Listening for Active with no data', () => {
		const entry = getTransitionEntry(ConnectorStatus.Active, 0);

		expect(entry.title).toBe('Listening...');
		expect(entry.iconDisplayType).toBe('success');
	});

	it('returns Data flow established for Active with data', () => {
		const entry = getTransitionEntry(ConnectorStatus.Active, 7);

		expect(entry.title).toBe('Data Flow Established');
		expect(entry.iconDisplayType).toBe('success');
		expect(entry.bold).toBe(true);
	});

	it('returns Data source disconnected for Disconnected', () => {
		const entry = getTransitionEntry(ConnectorStatus.Disconnected, 0);

		expect(entry.title).toBe('Data Source Disconnected');
		expect(entry.iconDisplayType).toBe('secondary');
	});
});

describe('item shape', () => {
	it('every entry from getInitialLogEntries has all required fields', () => {
		const allSeeds = [
			getInitialLogEntries(ConnectorStatus.Inactive, 0),
			getInitialLogEntries(ConnectorStatus.Active, 0),
			getInitialLogEntries(ConnectorStatus.Active, 1),
			getInitialLogEntries(ConnectorStatus.Inactive, 1),
			getInitialLogEntries(ConnectorStatus.Disconnected, 0)
		];

		allSeeds.forEach(entries => {
			entries.forEach(entry => {
				expect(typeof entry.bold).toBe('boolean');
				expect(typeof entry.icon).toBe('string');
				expect(['secondary', 'success']).toContain(
					entry.iconDisplayType
				);
				expect(typeof entry.title).toBe('string');
				expect(typeof entry.secondaryText).toBe('string');
			});
		});
	});
});
