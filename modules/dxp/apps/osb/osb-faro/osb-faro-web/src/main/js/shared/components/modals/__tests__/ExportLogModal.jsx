import client from 'shared/apollo/client';
import ExportLogModal from '../ExportLogModal';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {
	cleanup,
	fireEvent,
	getAllByText,
	getByLabelText,
	getByTestId,
	getByText,
	render
} from '@testing-library/react';
import {MockedProvider} from '@apollo/react-testing';
import {mockPreferenceReq} from 'test/graphql-data';
import {noop} from 'lodash';
import {Provider} from 'react-redux';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '2000',
		query: {
			rangeKey: '30'
		}
	})
}));

jest.mock('shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({
		timeZoneId: 'UTC'
	})
}));

const WrapperComponent = ({children}) => (
	<ApolloProvider client={client}>
		<Provider store={mockStore()}>
			<MockedProvider mocks={[mockPreferenceReq()]}>
				{children}
			</MockedProvider>
		</Provider>
	</ApolloProvider>
);

async function assertLoadingStatesForDownload(container) {
	fireEvent.click(getByLabelText(container, 'Choose Date Range'));

	const datePickerOverlay = getByTestId(document.body, 'overlay');

	// select day 1
	fireEvent.click(getAllByText(datePickerOverlay, '1')[0]);
	// select day 2
	fireEvent.click(getAllByText(datePickerOverlay, '2')[0]);

	fireEvent.click(getByText(container, 'Download'));

	expect(
		container.querySelector('.button-root .loading-animation')
	).toBeTruthy();

	await waitForLoadingToBeRemoved(container);

	expect(
		container.querySelector('.button-root .loading-animation')
	).toBeNull();
}

describe('ExportLogModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<WrapperComponent>
				<ExportLogModal
					description='Test description'
					onClose={noop}
					title='Test Title'
				/>
			</WrapperComponent>
		);

		expect(container).toMatchSnapshot();
	});

	xit('should have a loading state when download is triggered', async () => {
		const {container, debug} = render(
			<WrapperComponent>
				<ExportLogModal
					description='Test description'
					onClose={noop}
					onSubmit={() => Promise.resolve('csv-string')}
					title='Test Title'
				/>
			</WrapperComponent>
		);

		await assertLoadingStatesForDownload(container, debug);
	});

	xit('should stop loading if the download failed', async () => {
		const {container} = render(
			<WrapperComponent>
				<ExportLogModal
					description='Test description'
					onClose={noop}
					onSubmit={() => Promise.reject('Request Error')}
					title='Test Title'
				/>
			</WrapperComponent>
		);

		await assertLoadingStatesForDownload(container);
	});
});
