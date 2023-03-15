import React from 'react';
import SessionInput from '../SessionInput';
import {cleanup, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import {Property} from 'shared/util/records';
import {PropertyTypes, RelationalOperators} from '../../utils/constants';

jest.unmock('react-dom');

const {EQ} = RelationalOperators;
describe('SessionInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<SessionInput
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
				touched={{customInput: true, dateFilter: true}}
				valid={{customInput: true, dateFilter: true}}
				value={fromJS({
					criterionGroup: {
						items: [
							{operatorName: EQ},
							{
								operatorName: EQ,
								propertyName: 'completeDate',
								value: '2021-01-01'
							}
						]
					}
				})}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with "ever"', () => {
		const {container} = render(
			<SessionInput
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
				touched={{customInput: true, dateFilter: true}}
				valid={{customInput: true, dateFilter: true}}
				value={fromJS({
					criterionGroup: {
						items: [{operatorName: EQ}]
					}
				})}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render a CustomNumberInput', () => {
		const {getByTestId} = render(
			<SessionInput
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property({type: PropertyTypes.SessionNumber})}
				touched={{customInput: true, dateFilter: true}}
				valid={{customInput: true, dateFilter: true}}
				value={fromJS({
					criterionGroup: {items: [{operatorName: EQ}]}
				})}
			/>
		);

		expect(getByTestId('number-input')).toBeTruthy();
	});
});
