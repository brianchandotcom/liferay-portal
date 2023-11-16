import * as API from 'shared/api';
import DeleteChannelModal from '../DeleteChannelModal';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import {StaticRouter} from 'react-router-dom';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

describe('DeleteChannelModal', () => {
	afterEach(cleanup);

	it('renders without data source alert message', async () => {
		API.dataSource.fetchChannels.mockReturnValueOnce(
			Promise.resolve({items: [], total: 0})
		);

		const {container} = render(
			<StaticRouter>
				<DeleteChannelModal onClose={noop} onSubmit={noop} />
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should render with data source alert message', async () => {
		const {container, getByText} = render(
			<StaticRouter>
				<DeleteChannelModal onClose={noop} onSubmit={noop} />
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(getByText(/To reconnect to Analytics Cloud/)).toBeTruthy();
	});
});
