import client from 'shared/apollo/client';
import React from 'react';
import SessionInput from '../SessionInput';
import {ApolloProvider} from '@apollo/react-hooks';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import {MockedProvider} from '@apollo/react-testing';
import {mockPreferenceReq} from 'test/graphql-data';
import {Property} from 'shared/util/records';
import {PropertyTypes, RelationalOperators} from '../../utils/constants';

jest.unmock('react-dom');

const {EQ} = RelationalOperators;

const WrapperComponent = ({children}) => (
	<ApolloProvider client={client}>
		<MockedProvider mocks={[mockPreferenceReq()]}>
			{children}
		</MockedProvider>
	</ApolloProvider>
);

describe('SessionInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container, getAllByText, getByText} = render(
			<WrapperComponent>
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
			</WrapperComponent>
		);
		fireEvent.click(getByText('is'));
		fireEvent.click(getByText('on'));

		expect(getAllByText('is')[1]).toBeTruthy();
		expect(getByText('is not')).toBeTruthy();
		expect(getByText('contains')).toBeTruthy();
		expect(getByText('does not contain')).toBeTruthy();
		expect(getByText('is known')).toBeTruthy();
		expect(getByText('is unknown')).toBeTruthy();

		expect(getByText('since')).toBeTruthy();
		expect(getByText('after')).toBeTruthy();
		expect(getByText('before')).toBeTruthy();
		expect(getByText('between')).toBeTruthy();
		expect(getByText('ever')).toBeTruthy();
		expect(getAllByText('on')[1]).toBeTruthy();

		expect(container).toMatchSnapshot();
	});

	it('should render with "ever"', () => {
		const {container, getAllByText, getByText} = render(
			<WrapperComponent>
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
			</WrapperComponent>
		);
		fireEvent.click(getByText('is'));
		fireEvent.click(getByText('ever'));

		expect(getAllByText('is')[1]).toBeTruthy();
		expect(getByText('is not')).toBeTruthy();
		expect(getByText('contains')).toBeTruthy();
		expect(getByText('does not contain')).toBeTruthy();
		expect(getByText('is known')).toBeTruthy();
		expect(getByText('is unknown')).toBeTruthy();

		expect(getByText('since')).toBeTruthy();
		expect(getByText('after')).toBeTruthy();
		expect(getByText('before')).toBeTruthy();
		expect(getByText('between')).toBeTruthy();
		expect(getAllByText('ever')[1]).toBeTruthy();
		expect(getByText('on')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});

	it('should render a CustomNumberInput', () => {
		const {getByTestId} = render(
			<WrapperComponent>
				<SessionInput
					operatorRenderer={() => <div>{'operator'}</div>}
					property={new Property({type: PropertyTypes.SessionNumber})}
					touched={{customInput: true, dateFilter: true}}
					valid={{customInput: true, dateFilter: true}}
					value={fromJS({
						criterionGroup: {items: [{operatorName: EQ}]}
					})}
				/>
			</WrapperComponent>
		);

		expect(getByTestId('number-input')).toBeTruthy();
	});
});
