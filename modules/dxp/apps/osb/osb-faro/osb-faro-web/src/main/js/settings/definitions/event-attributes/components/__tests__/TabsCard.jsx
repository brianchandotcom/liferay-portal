import mockStore from 'test/mock-store';
import React from 'react';
import TabsCard from '../TabsCard';
import {MemoryRouter} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render, screen} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('../GlobalAttributeList', () => ({
	__esModule: true,
	default: () => <div>{'GLOBAL_ATTRIBUTE_LIST'}</div>
}));

jest.mock('../AttributeList', () => ({
	__esModule: true,
	default: () => <div>{'LOCAL_ATTRIBUTE_LIST'}</div>
}));

const BASE = '/workspace/23/settings/definitions/event-attributes';

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

	it('renders the global attributes list on the global tab route', async () => {
		renderAt(`${BASE}/global`);

		expect(
			await screen.findByText('GLOBAL_ATTRIBUTE_LIST')
		).toBeInTheDocument();
		expect(screen.queryByText('LOCAL_ATTRIBUTE_LIST')).toBeNull();
	});

	it('renders the local attributes list on the local tab route', async () => {
		renderAt(`${BASE}/local`);

		expect(
			await screen.findByText('LOCAL_ATTRIBUTE_LIST')
		).toBeInTheDocument();
		expect(screen.queryByText('GLOBAL_ATTRIBUTE_LIST')).toBeNull();
	});
});
