import mockStore from 'test/mock-store';
import React from 'react';
import TabsCard from '../TabsCard';
import {MemoryRouter} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render, screen} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('../EventList', () => ({
	__esModule: true,
	default: () => <div>{'DEFAULT_EVENT_LIST'}</div>
}));

jest.mock('../CustomEventList', () => ({
	__esModule: true,
	default: () => <div>{'CUSTOM_EVENT_LIST'}</div>
}));

const BASE = '/workspace/23/settings/definitions/events';

const renderAt = path => {
	window.history.pushState({}, '', path);

	return render(
		<Provider store={mockStore()}>
			<MemoryRouter initialEntries={[path]}>
				<TabsCard groupId='23' />
			</MemoryRouter>
		</Provider>
	);
};

describe('TabsCard', () => {
	afterEach(() => window.history.pushState({}, '', '/'));

	it('renders the default events list on the default tab route', async () => {
		renderAt(`${BASE}/default`);

		expect(
			await screen.findByText('DEFAULT_EVENT_LIST')
		).toBeInTheDocument();
		expect(screen.queryByText('CUSTOM_EVENT_LIST')).toBeNull();
	});

	it('renders the custom events list on the custom tab route', async () => {
		renderAt(`${BASE}/custom`);

		expect(
			await screen.findByText('CUSTOM_EVENT_LIST')
		).toBeInTheDocument();
		expect(screen.queryByText('DEFAULT_EVENT_LIST')).toBeNull();
	});
});
