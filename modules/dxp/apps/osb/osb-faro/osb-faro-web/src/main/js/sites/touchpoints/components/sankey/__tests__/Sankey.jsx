import client from 'shared/apollo/client';
import React from 'react';
import Sankey from '../Sankey';
import {ApolloProvider} from '@apollo/react-components';
import {MockedProvider} from '@apollo/react-testing';
import {mockTouchpointsPath} from 'test/graphql-data';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router-dom';

jest.unmock('react-dom');

const ASSET_TITLE = 'Home - Liferay DXP';
const ASSET_URL = 'http://127.0.0.1:8080/';

const MOCK_PAGE = {
	__typename: 'PageMetric',
	assetTitle: ASSET_TITLE,
	directAccessMetric: {
		__typename: 'Metric',
		value: 8.0
	},
	indirectAccessMetric: {
		__typename: 'Metric',
		value: 29.0
	},
	pageReferrerMetrics: [
		{
			__typename: 'PageReferrerMetric',
			accessMetric: {
				__typename: 'Metric',
				value: 7.0
			},
			assetTitle: null,
			external: true,
			referrer: 'http://127.0.0.1/'
		},
		{
			__typename: 'PageReferrerMetric',
			accessMetric: {
				__typename: 'Metric',
				value: 6.0
			},
			assetTitle: null,
			external: false,
			referrer:
				'http://127.0.0.1:8080/web/guest/home?p_p_id=com_liferay_login_web_portlet_LoginPortlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&saveLastPath=false&_com_liferay_login_web_portlet_LoginPortlet_mvcRenderCommandName=%2Flogin%2Flogin'
		},
		{
			__typename: 'PageReferrerMetric',
			accessMetric: {
				__typename: 'Metric',
				value: 2.0
			},
			assetTitle: ASSET_TITLE,
			external: false,
			referrer: ASSET_URL
		},
		{
			__typename: 'PageReferrerMetric',
			accessMetric: {
				__typename: 'Metric',
				value: 14.0
			},
			assetTitle: null,
			external: true,
			referrer: 'others'
		}
	],
	viewsMetric: {
		__typename: 'Metric',
		value: 37.0
	}
};

const defaultProps = {
	filters: {
		devices: ['Any'],
		location: ['Any']
	},
	rangeSelectors: {
		rangeKey: 30
	},
	router: {
		params: {
			channelId: '123',
			groupId: '456',
			title: encodeURIComponent(ASSET_TITLE),
			touchpoint: ASSET_URL
		}
	},
	touchpoint: ASSET_URL
};

const variables = {
	title: ASSET_TITLE,
	touchpoint: ASSET_URL
};

const DefaultComponent = props => (
	<ApolloProvider client={client}>
		<StaticRouter>
			<MockedProvider mocks={[mockTouchpointsPath(MOCK_PAGE, variables)]}>
				<Sankey {...defaultProps} {...props} />
			</MockedProvider>
		</StaticRouter>
	</ApolloProvider>
);

describe('Sankey', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
