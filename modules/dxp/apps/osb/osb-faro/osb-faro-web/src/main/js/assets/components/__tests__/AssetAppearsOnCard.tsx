import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {Accessor, AssetAppearsOnCard} from '../AssetAppearsOnCard';
import {ApolloProvider} from '@apollo/react-hooks';
import {AssetTypes} from 'shared/util/constants';
import {cleanup, render} from '@testing-library/react';
import {EmptyStateLink, EmptyStateText} from '../AssetAppearsOnCard';
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

const WrappedComponent = ({
	accessors,
	assetType,
	empty = false,
	emptyStateLink,
	emptyStateText
}) => (
	<Provider store={mockStore()}>
		<ApolloProvider client={client}>
			<StaticRouter>
				<MockedProvider
					mocks={[
						mockTimeRangeReq(),
						mockPreferenceReq(),
						mockAssetAppearsOnReq(
							{
								assetType: assetType.toUpperCase(),
								selectedMetrics: accessors
							},
							empty
						)
					]}
				>
					<AssetAppearsOnCard
						accessors={accessors}
						assetType={assetType}
						emptyStateLink={emptyStateLink}
						emptyStateText={emptyStateText}
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
				emptyStateLink={EmptyStateLink.Blog}
				emptyStateText={EmptyStateText.Blog}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should have a Views column for Blog', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ViewsMetric]}
				assetType={AssetTypes.Blog}
				emptyStateLink={EmptyStateLink.Blog}
				emptyStateText={EmptyStateText.Blog}
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
				emptyStateLink={EmptyStateLink.Document}
				emptyStateText={EmptyStateText.Document}
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
				emptyStateLink={EmptyStateLink.Form}
				emptyStateText={EmptyStateText.Form}
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
				emptyStateLink={EmptyStateLink.Journal}
				emptyStateText={EmptyStateText.Journal}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Views')).toBeInTheDocument();
	});

	it('should render empty state for Blog', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.PreviewsMetric]}
				assetType={AssetTypes.Blog}
				empty
				emptyStateLink={EmptyStateLink.Blog}
				emptyStateText={EmptyStateText.Blog}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const linkText = getByText('Learn more about blogs.');

		expect(
			getByText('There are no assets on the selected period.')
		).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
		expect(linkText).toBeInTheDocument();
		expect(linkText).toHaveAttribute('href', EmptyStateLink.Blog);
	});

	it('should render empty state for Documents and Media', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.PreviewsMetric]}
				assetType={AssetTypes.Document}
				empty
				emptyStateLink={EmptyStateLink.Document}
				emptyStateText={EmptyStateText.Document}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const linkText = getByText('Learn more about documents and media.');

		expect(
			getByText('There are no assets on the selected period.')
		).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
		expect(linkText).toBeInTheDocument();
		expect(linkText).toHaveAttribute('href', EmptyStateLink.Document);
	});

	it('should render empty state for Forms', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.PreviewsMetric]}
				assetType={AssetTypes.Form}
				empty
				emptyStateLink={EmptyStateLink.Form}
				emptyStateText={EmptyStateText.Form}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const linkText = getByText('Learn more about forms.');

		expect(
			getByText('There are no assets on the selected period.')
		).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
		expect(linkText).toBeInTheDocument();
		expect(linkText).toHaveAttribute('href', EmptyStateLink.Form);
	});

	it('should render empty state for Web content', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.PreviewsMetric]}
				assetType={AssetTypes.Journal}
				empty
				emptyStateLink={EmptyStateLink.Journal}
				emptyStateText={EmptyStateText.Journal}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const linkText = getByText('Learn more about web content.');

		expect(
			getByText('There are no assets on the selected period.')
		).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
		expect(linkText).toBeInTheDocument();
		expect(linkText).toHaveAttribute('href', EmptyStateLink.Journal);
	});
});
