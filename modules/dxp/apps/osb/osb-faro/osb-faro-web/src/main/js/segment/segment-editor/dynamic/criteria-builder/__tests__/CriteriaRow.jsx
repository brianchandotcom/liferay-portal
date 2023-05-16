import CriteriaRow from '../CriteriaRow';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {fromJS, Map} from 'immutable';
import {Provider} from 'react-redux';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('CriteriaRow', () => {
	afterEach(cleanup);

	it('should render', () => {
		const CriteriaRowContext = wrapInTestContext(CriteriaRow);

		const {container, getAllByText, getByText} = render(
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

		fireEvent.click(getByText('is not'));

		expect(getByText('is')).toBeTruthy();
		expect(getAllByText('is not')[1]).toBeTruthy();
		expect(getByText('contains')).toBeTruthy();
		expect(getByText('does not contain')).toBeTruthy();
		expect(getByText('is known')).toBeTruthy();
		expect(getByText('is unknown')).toBeTruthy();
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
