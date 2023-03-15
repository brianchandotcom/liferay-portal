import * as API from 'shared/api';
import ConnectDXP from '../ConnectDXP';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router-dom';

jest.unmock('react-dom');

describe('ConnectDXP', () => {
	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<ConnectDXP groupId='123' onClose={noop} onNext={noop} />
				</StaticRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders "Connected" when dxpConnected is true', () => {
		const {queryByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<ConnectDXP
						dxpConnected
						groupId='123'
						onClose={noop}
						onNext={noop}
					/>
				</StaticRouter>
			</Provider>
		);

		expect(queryByText('Back')).toBeNull();
		expect(
			queryByText('Your DXP Instance Is Connected to Analytics Cloud')
		).toBeInTheDocument();
		expect(queryByText('Sites')).toBeInTheDocument();
		expect(queryByText('Contacts')).toBeInTheDocument();
	});

	it('renders Download button', () => {
		const {queryByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<ConnectDXP groupId='123' onClose={noop} onNext={noop} />
				</StaticRouter>
			</Provider>
		);

		expect(queryByText('Download')).toBeTruthy();
	});

	it('change Download link when change DXP version', () => {
		const {container, queryByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<ConnectDXP groupId='123' onClose={noop} onNext={noop} />
				</StaticRouter>
			</Provider>
		);

		const select = container.querySelector('.select-root');

		fireEvent.change(select, {
			target: {value: 'dxp-71-fixpack-22'}
		});

		expect(queryByText('Download').href).toMatch(/7-1-fix-pack-22/);
	});

	it('fires "setDxpConnected" when the token value changes', () => {
		const spy = jest.fn();

		render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<ConnectDXP
						groupId='123'
						onboarding
						onClose={noop}
						onDxpConnected={spy}
						onNext={noop}
					/>
				</StaticRouter>
			</Provider>
		);

		expect(spy).not.toBeCalled();

		jest.runOnlyPendingTimers();

		API.dataSource.fetchToken.mockReturnValue(
			Promise.resolve('New Token Value')
		);

		jest.runOnlyPendingTimers();
		jest.runOnlyPendingTimers();

		expect(spy).toBeCalled();
	});
});
