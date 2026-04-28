jest.unmock('react-dom');

import ConnectorEntities from '../ConnectorEntities';
import React from 'react';
import {ConnectorEntityDescriptor} from '../types';
import {render} from '@testing-library/react';

const buildEntity = (
	overrides: Partial<ConnectorEntityDescriptor> = {}
): ConnectorEntityDescriptor => ({
	accessor: 'contacts',
	description: 'Contact records',
	icon: 'users',
	label: 'Contacts',
	...overrides
});

describe('ConnectorEntities', () => {
	it('renders one item per entity with its label and description', () => {
		const entities = [
			buildEntity({accessor: 'a', description: 'desc-a', label: 'A'}),
			buildEntity({accessor: 'b', description: 'desc-b', label: 'B'})
		];

		const {getByText} = render(
			<ConnectorEntities
				connectionStatus='connected'
				entities={entities}
				syncedCounts={{}}
			/>
		);

		expect(getByText('A')).toBeTruthy();
		expect(getByText('B')).toBeTruthy();
		expect(getByText('desc-a')).toBeTruthy();
		expect(getByText('desc-b')).toBeTruthy();
	});

	it('renders the synced count when it is a non-negative number', () => {
		const {getByText} = render(
			<ConnectorEntities
				connectionStatus='connected'
				entities={[buildEntity()]}
				syncedCounts={{contacts: 42}}
			/>
		);

		expect(getByText(/42/)).toBeTruthy();
	});

	it('renders the synced count when it is zero', () => {
		const {getByText} = render(
			<ConnectorEntities
				connectionStatus='connected'
				entities={[buildEntity()]}
				syncedCounts={{contacts: 0}}
			/>
		);

		expect(getByText(/0/)).toBeTruthy();
	});

	it('hides the synced count when no value is provided', () => {
		const {queryByText} = render(
			<ConnectorEntities
				connectionStatus='connected'
				entities={[buildEntity()]}
				syncedCounts={{}}
			/>
		);

		expect(queryByText(/Items Synced/i)).toBeNull();
	});

	it('renders the connected label with success display when status is connected', () => {
		const {getByText} = render(
			<ConnectorEntities
				connectionStatus='connected'
				entities={[buildEntity()]}
				syncedCounts={{}}
			/>
		);

		expect(getByText('Connected')).toBeTruthy();
	});

	it('renders the disconnected label when status is disconnected', () => {
		const {getByText} = render(
			<ConnectorEntities
				connectionStatus='disconnected'
				entities={[buildEntity()]}
				syncedCounts={{}}
			/>
		);

		expect(getByText('Disconnected')).toBeTruthy();
	});

	it('renders nothing inside the list when no entities are provided', () => {
		const {container} = render(
			<ConnectorEntities
				connectionStatus='connected'
				entities={[]}
				syncedCounts={{}}
			/>
		);

		expect(container.querySelectorAll('.list-group-item')).toHaveLength(0);
	});
});
