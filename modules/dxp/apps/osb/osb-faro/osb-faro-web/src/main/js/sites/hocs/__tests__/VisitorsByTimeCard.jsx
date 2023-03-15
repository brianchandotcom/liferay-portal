import BasePage from 'shared/components/base-page';
import client from 'shared/apollo/client';
import React from 'react';
import VisitorsByTimeCard, {
	formatHour,
	renderTooltip
} from '../VisitorsByTimeCard';
import {ApolloProvider} from '@apollo/react-components';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router-dom';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

const MOCK_CONTEXT = {
	rangeKey: {defaultValue: '30'},
	router: {
		params: {
			channelId: '123',
			groupId: '2000'
		},
		query: {
			rangeKey: '30'
		}
	}
};

const WrappedComponent = props => (
	<ApolloProvider client={client}>
		<BasePage.Context.Provider value={MOCK_CONTEXT}>
			<StaticRouter>
				<VisitorsByTimeCard {...props} />
			</StaticRouter>
		</BasePage.Context.Provider>
	</ApolloProvider>
);

describe('VisitorsByTimeCard', () => {
	it('render', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoading(container);

		expect(container).toMatchSnapshot();
	});
});

describe('renderTooltip', () => {
	it('render', () => {
		const {container} = render(
			renderTooltip({column: 'Sunday', row: 12, value: 98})
		);

		expect(container).toMatchSnapshot();
	});
});

describe('formatHour', () => {
	it.each`
		hour  | retVal
		${0}  | ${'12 AM'}
		${6}  | ${'6 AM'}
		${11} | ${'11 AM'}
		${12} | ${'12 PM'}
		${18} | ${'6 PM'}
	`('return $retVal when formatting $hour', ({hour, retVal}) => {
		expect(formatHour(hour)).toBe(retVal);
	});
});
