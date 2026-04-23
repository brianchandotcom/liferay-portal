import * as API from 'shared/api';
import ConnectDXP from '../ConnectDXP';
import mockStore from 'test/mock-store';
import React from 'react';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render, screen, waitFor} from '@testing-library/react';
import {MemoryRouter} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

const mockGroupId = '23';

const Wrapper = ({channelDispatch = jest.fn(), children}) => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider addTypename={false}>
				<ChannelContext.Provider
					value={{channelDispatch, selectedChannel: {id: '123'}}}
				>
					{children}
				</ChannelContext.Provider>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('ConnectDXP', () => {
	beforeEach(() => {
		API.dataSource.fetchToken.mockReturnValue(
			Promise.resolve('test-token')
		);
		API.channels.fetchAll.mockReturnValue(
			Promise.resolve({
				items: [{id: '123', name: 'Test Channel'}],
				total: 1
			})
		);
	});

	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<Wrapper>
				<ConnectDXP
					dxpConnected={false}
					groupId={mockGroupId}
					onClose={jest.fn()}
					onDxpConnected={jest.fn()}
				/>
			</Wrapper>
		);

		await waitFor(() =>
			expect(screen.getByDisplayValue('test-token')).toBeInTheDocument()
		);

		expect(container).toMatchSnapshot();
	});

	it('should render connected state', async () => {
		const {container} = render(
			<Wrapper>
				<ConnectDXP
					dxpConnected
					groupId={mockGroupId}
					onClose={jest.fn()}
					onDxpConnected={jest.fn()}
				/>
			</Wrapper>
		);

		expect(
			screen.getByText(/Your DXP instance is connected/i)
		).toBeInTheDocument();
		expect(container).toMatchSnapshot();
	});
});
