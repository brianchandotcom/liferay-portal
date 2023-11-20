import client from 'shared/apollo/client';
import PagePathCard from '../PagePathCard';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {CHART_COLORS, MAIN_NODE_COLOR, SECONDARY_NODE_COLOR} from '../utils';
import {cleanup, render} from '@testing-library/react';
import {MockedProvider} from '@apollo/react-testing';
import {mockPagePathReq} from 'test/graphql-data';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {StaticRouter} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		rangeKey: '30',
		title: 'Liferay DXP - Home',
		touchpoint: 'https://liferay.com/home'
	})
}));

const PREVIOUS_PATH_NODES = [
	{
		__typename: 'PagePathNode',
		canonicalUrl: 'https://www.site1.com',
		title: 'Site 1',
		views: 10000
	},
	{
		__typename: 'PagePathNode',
		canonicalUrl: 'https://www.site2.com',
		title: 'Site 2',
		views: 10000
	},
	{
		__typename: 'PagePathNode',
		canonicalUrl: 'https://www.site3.com',
		title: 'Site 3',
		views: 5000
	},
	{
		__typename: 'PagePathNode',
		canonicalUrl: 'others',
		title: 'others',
		views: 500
	},
	{
		__typename: 'PagePathNode',
		canonicalUrl: 'drop-offs',
		title: 'drop-offs',
		views: 8000
	}
];

const FOLLOWING_PATH_NODES = [
	{
		__typename: 'PagePathNode',
		canonicalUrl: 'https://www.google.com',
		title: 'Google',
		views: 10000
	},
	{
		__typename: 'PagePathNode',
		canonicalUrl: 'https://www.facebook.com',
		title: 'Facebook',
		views: 10000
	},
	{
		__typename: 'PagePathNode',
		canonicalUrl: 'https://www.instagram.com',
		title: 'Instagram',
		views: 8000
	},
	{
		__typename: 'PagePathNode',
		canonicalUrl: 'direct',
		title: 'direct',
		views: 5000
	},
	{
		__typename: 'PagePathNode',
		canonicalUrl: 'others',
		title: 'others',
		views: 1000
	}
];

const DATA = {
	pagePath: {
		__typename: 'PagePath',
		canonicalUrl: 'https://www.liferay.com',
		followingPagePathNodes: PREVIOUS_PATH_NODES,
		previousPagePathNodes: FOLLOWING_PATH_NODES,
		title: 'Liferay Home Page',
		views: 100000
	}
};

const EMPTY_STATE_DATA = {
	pagePath: {
		__typename: 'PagePath',
		canonicalUrl: 'https://www.liferay.com',
		followingPagePathNodes: [],
		previousPagePathNodes: [],
		title: 'Liferay Home Page',
		views: 100000
	}
};

const WrapperComponent = ({data}) => (
	<ApolloProvider client={client}>
		<StaticRouter>
			<MockedProvider mocks={[mockPagePathReq(data)]}>
				<PagePathCard
					rangeSelectors={{
						rangeEnd: '',
						rangeKey: RangeKeyTimeRanges.Last30Days,
						rangeStart: ''
					}}
				/>
			</MockedProvider>
		</StaticRouter>
	</ApolloProvider>
);

describe('PagePathCard', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(<WrapperComponent data={DATA} />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should render empty state', async () => {
		const {container, getByText} = render(
			<WrapperComponent data={EMPTY_STATE_DATA} />
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Path Analysis')).toBeInTheDocument();
		expect(getByText('There are no data found.')).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
		expect(getByText('Learn more about path.')).toHaveAttribute(
			'href',
			'https://learn.liferay.com/analytics-cloud/latest/en/touchpoints/pages/paths.html'
		);
	});

	it('should render node and path with correct colors', async () => {
		const {container} = render(<WrapperComponent data={DATA} />);

		await waitForLoadingToBeRemoved(container);

		const nodes = container.querySelectorAll(
			'.recharts-sankey-nodes > .recharts-layer'
		);

		/**
		 * 5 previous node +
		 * 5 following node +
		 * 1 main node
		 */

		expect(nodes).toHaveLength(11);

		nodes.forEach((node, index) => {
			// Main node is always the first one

			if (index === 0) {
				expect(node.querySelector('path')).toHaveAttribute(
					'fill',
					MAIN_NODE_COLOR
				);

				return;
			}

			const title = node.querySelector('text:nth-of-type(2)').textContent;

			if (
				title === 'Drop Offs' ||
				title === 'Other Referrals' ||
				title === 'Other Pages'
			) {
				expect(node.querySelector('path')).toHaveAttribute(
					'fill',
					SECONDARY_NODE_COLOR
				);

				return;
			}

			expect(node.querySelector('path')).toHaveAttribute(
				'fill',
				CHART_COLORS[index - 1]
			);
		});
	});
});
