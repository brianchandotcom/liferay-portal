import BehaviorInput, {AssetItem} from '../BehaviorInput';
import mockStore from 'test/mock-store';
import React from 'react';
import {ACTIVITY_KEY, RelationalOperators} from '../../utils/constants';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {createCustomValueMap} from '../../utils/custom-inputs';
import {Map} from 'immutable';
import {Property} from 'shared/util/records';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: RelationalOperators.EQ,
				propertyName: ACTIVITY_KEY,
				value: 'test#test#123123123'
			}
		]
	},
	{key: 'operator', value: RelationalOperators.GE},
	{key: 'value', value: ''}
]);

const defaultProps = {
	onChange: jest.fn(),
	operatorRenderer: () => <div>{'test'}</div>,
	property: new Property(),
	referencedAssetsIMap: new Map(),
	touched: {asset: false, occurenceCount: false},
	valid: {asset: false, occurenceCount: false},
	value: mockValue
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<BehaviorInput {...defaultProps} {...props} />
	</Provider>
);

describe('BehaviorInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container, getAllByText, getByText} = render(
			<DefaultComponent />
		);

		fireEvent.click(getByText('at least'));
		fireEvent.click(getByText('ever'));

		expect(getAllByText('at least')[1]).toBeTruthy();
		expect(getByText('at most')).toBeTruthy();

		expect(getByText('since')).toBeTruthy();
		expect(getByText('before')).toBeTruthy();
		expect(getByText('between')).toBeTruthy();
		expect(getAllByText('ever')[1]).toBeTruthy();
		expect(getByText('on')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});

	it('should render w/ data', () => {
		const {container} = render(
			<DefaultComponent
				referencedAssetsIMap={new Map({123123123: new Map()})}
				valid={{asset: true, occurenceCount: true}}
				value={mockValue.set('value', 123)}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with has-error for asset', () => {
		const {container} = render(
			<DefaultComponent
				touched={{asset: true, occurenceCount: false}}
				valid={{asset: false, occurenceCount: true}}
				value={mockValue.set('value', 123)}
			/>
		);

		expect(
			container.querySelector('.form-group-item-shrink.has-error')
		).toBeNull();

		expect(container.querySelector('.has-error')).toBeTruthy();
	});

	it('should render with has-error for occurenceCount', () => {
		const {container} = render(
			<DefaultComponent touched={{asset: false, occurenceCount: true}} />
		);

		expect(
			container.querySelector('.form-group-item-shrink.has-error')
		).toBeTruthy();
	});

	describe('AssetItem', () => {
		afterEach(cleanup);

		it('should render', () => {
			const {container} = render(<AssetItem />);

			expect(container).toMatchSnapshot();
		});
	});
});
