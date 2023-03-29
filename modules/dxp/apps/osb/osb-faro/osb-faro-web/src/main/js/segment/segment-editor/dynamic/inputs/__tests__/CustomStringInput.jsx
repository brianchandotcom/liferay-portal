import CustomStringInput from '../CustomStringInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {
	createCustomValueMap,
	setPropertyValue
} from '../../utils/custom-inputs';
import {Property} from 'shared/util/records';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{key: 'criterionGroup', value: [{operatorName: 'eq', value: ''}]}
]);

const defaultProps = {
	displayValue: 'Address',
	operatorRenderer: () => <div>{'operator'}</div>,
	property: new Property(),
	touched: false,
	valid: true,
	value: mockValue
};

const DefaultComponent = props => (
	<CustomStringInput {...defaultProps} {...props} />
);

describe('CustomStringInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent valid={false} />);

		expect(container).toMatchSnapshot();
	});

	it('should render with data', () => {
		const {container} = render(
			<DefaultComponent
				value={setPropertyValue(mockValue, 'value', 0, '123 West Road')}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/o value input when value is null', () => {
		const {queryByTestId} = render(
			<DefaultComponent
				value={setPropertyValue(mockValue, 'value', 0, null)}
			/>
		);

		expect(queryByTestId('string-input')).toBeNull();
	});

	it('should render w/ has-error when touched and not valid', () => {
		const {container} = render(<DefaultComponent touched valid={false} />);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});

	it('should render as a regular input if autocomplete is false', () => {
		const {container} = render(<DefaultComponent autocomplete={false} />);

		expect(container.querySelector('.base-select-input-root')).toBeNull();
	});
});
