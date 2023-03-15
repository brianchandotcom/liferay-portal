import CriteriaRow from '../CriteriaRow';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {fromJS, Map} from 'immutable';
import {Provider} from 'react-redux';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('CriteriaRow', () => {
	afterEach(cleanup);

	it('should render', () => {
		const CriteriaRowContext = wrapInTestContext(CriteriaRow);

		const {container} = render(
			<Provider store={mockStore()}>
				<CriteriaRowContext
					criterion={{
						operatorName: 'sessions-filter',
						propertyName: 'context/referrer',
						rowId: 'row_0',
						touched: false,
						valid: true,
						value: fromJS({
							criterionGroup: {
								conjunctionName: 'and',
								criteriaGroupId: 'group_0',
								items: [
									{
										operatorName: 'ne',
										propertyName: 'context/referrer',
										rowId: 'row_1',
										touched: false,
										valid: true
									},
									{
										operatorName: 'gt',
										propertyName: 'completeDate',
										rowId: 'row_2',
										touched: false,
										valid: true
									}
								]
							}
						})
					}}
					referencedAssetsIMap={new Map()}
					referencedPropertiesIMap={new Map()}
				/>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ Non-Existent Property message', () => {
		const CriteriaRowContext = wrapInTestContext(CriteriaRow);

		const {queryByText} = render(
			<Provider store={mockStore()}>
				<CriteriaRowContext
					referencedAssetsIMap={new Map()}
					referencedPropertiesIMap={new Map()}
				/>
			</Provider>
		);

		expect(queryByText('Attribute no longer exists.')).toBeTruthy();
	});
});
