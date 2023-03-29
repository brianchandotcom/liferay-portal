import BreakdownTable from '../index';
import React from 'react';
import {
	AttributesContext,
	withAttributesProvider
} from '../../context/attributes';
import {MockedProvider} from '@apollo/react-testing';
import {mockEventAnalysisResultReq} from 'test/graphql-data';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';
import {waitForTable} from 'test/helpers';

const initialAttributes = {
	attributes: {
		1: {
			defaultDataType: 'boolean',
			displayName: 'Boolean Name',
			id: '1',
			name: 'booleanName'
		}
	},
	breakdownOrder: ['111'],
	breakdowns: {
		111: {
			attributeId: '1',
			attributeType: 'EVENT',
			dataType: 'BOOLEAN'
		}
	},
	filterOrder: ['123'],
	filters: {
		123: {
			attributeId: '1',
			attributeType: 'EVENT',
			dataType: 'BOOLEAN',
			operator: 'eq',
			values: ['true']
		}
	}
};

const breakdownItems = [
	{
		__typename: 'BreakdownItem',
		breakdownItems: [
			{
				__typename: 'BreakdownItem',
				breakdownItems: [
					{
						__typename: 'BreakdownItem',
						breakdownItems: [],
						leafNode: false,
						name: 'All Individuals',
						previousValue: 2633,
						value: 1717
					}
				],
				leafNode: true,
				name: 'View Article',
				previousValue: 5033,
				value: 3367
			}
		],
		leafNode: false,
		name: 'articleTitle [0]',
		previousValue: 5033,
		value: 3367
	}
];

const eventAnalysisResult = {
	__typename: 'EventAnalysis',
	breakdownItems,
	count: 1,
	page: 0,
	previousValue: 1234,
	value: 5033
};

jest.unmock('react-dom');

describe('BreakdownTable', () => {
	const event = {id: '1', name: 'View Article'};

	const WrappedComponent = props => (
		<StaticRouter>
			<AttributesContext.Provider value={initialAttributes}>
				<MockedProvider
					mocks={[
						mockEventAnalysisResultReq(eventAnalysisResult, {
							eventAnalysisBreakdowns: Object.values(
								initialAttributes.breakdowns
							),
							eventAnalysisFilters: Object.values(
								initialAttributes.filters
							)
						})
					]}
				>
					<BreakdownTable
						channelId='123'
						compareToPrevious
						event={event}
						rangeSelectors={{
							rangeKey: '30'
						}}
						type='TOTAL'
						{...props}
					/>
				</MockedProvider>
			</AttributesContext.Provider>
		</StaticRouter>
	);

	it('render', async () => {
		const {container} = render(<WrappedComponent />);
		jest.runAllTimers();

		await waitForTable(container);

		expect(container).toMatchSnapshot();
	});

	it('render with single event', async () => {
		const BreakdownWithProvider = withAttributesProvider(BreakdownTable);

		const {container} = render(
			<StaticRouter>
				<MockedProvider
					mocks={[
						mockEventAnalysisResultReq(eventAnalysisResult, {
							eventAnalysisBreakdowns: [],
							eventAnalysisFilters: []
						})
					]}
				>
					<BreakdownWithProvider
						channelId='123'
						compareToPrevious
						event={event}
						rangeSelectors={{
							rangeKey: '30'
						}}
						type='TOTAL'
					/>
				</MockedProvider>
			</StaticRouter>
		);
		jest.runAllTimers();

		await waitForTable(container);

		expect(container).toMatchSnapshot();
	});

	it('render with empty state', () => {
		const {queryByText} = render(
			<BreakdownTable
				compareToPrevious={false}
				event={null}
				rangeSelectors={{
					rangeKey: '30'
				}}
			/>
		);

		expect(queryByText('Add an event to analyze.')).toBeTruthy();
	});
});
