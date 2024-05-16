import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {Accessor, AssetAppearsOnCard} from '../AssetAppearsOnCard';
import {ApolloProvider} from '@apollo/react-hooks';
import {AssetTypes} from 'shared/util/constants';
import {cleanup, render} from '@testing-library/react';
import {
	mockAssetAppearsOnReq,
	mockPreferenceReq,
	mockTimeRangeReq
} from 'test/graphql-data';
import {MockedProvider} from '@apollo/react-testing';
import {Provider} from 'react-redux';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {StaticRouter} from 'react-router-dom';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		assetId: 'myBlogId',
		channelId: '123',
		groupId: '456',
		query: {
			rangeKey: RangeKeyTimeRanges.Last30Days
		},
		title: 'Blog Title'
	})
}));

const WrappedComponent = ({accessors, assetType}) => (
	<Provider store={mockStore()}>
		<ApolloProvider client={client}>
			<StaticRouter>
				<MockedProvider
					mocks={[
						mockTimeRangeReq(),
						mockPreferenceReq(),
						mockAssetAppearsOnReq({
							assetType: assetType.toUpperCase(),
							selectedMetrics: accessors
						})
					]}
				>
					<AssetAppearsOnCard
						accessors={accessors}
						assetType={assetType}
					/>
				</MockedProvider>
			</StaticRouter>
		</ApolloProvider>
	</Provider>
);

describe('AssetAppearsOnCard', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<WrappedComponent
				accessors={[Accessor.ViewsMetric]}
				assetType={AssetTypes.Blog}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should have a Views column for blog', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ViewsMetric]}
				assetType={AssetTypes.Blog}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Views')).toBeInTheDocument();
	});

	it('should have a Views column for Blog', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ViewsMetric]}
				assetType={AssetTypes.Blog}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Views')).toBeInTheDocument();
	});

	it('should have [Downloads, Previews] columns for Document', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.DownloadsMetric, Accessor.PreviewsMetric]}
				assetType={AssetTypes.Document}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Downloads')).toBeInTheDocument();
		expect(getByText('Previews')).toBeInTheDocument();
	});

	it('should have a [Submissions, Views] column for Forms', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.SubmissionsMetric, Accessor.ViewsMetric]}
				assetType={AssetTypes.Form}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Submissions')).toBeInTheDocument();
		expect(getByText('Views')).toBeInTheDocument();
	});

	it('should have a Views column for WebContent', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ViewsMetric]}
				assetType={AssetTypes.Journal}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Views')).toBeInTheDocument();
	});
});
