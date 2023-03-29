import OrganizationTextInput from '../OrganizationTextInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {createCustomValueMap} from '../../utils/custom-inputs';
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
	valid: false,
	value: mockValue
};

describe('OrganizationTextInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<OrganizationTextInput {...defaultProps} />);

		expect(container).toMatchSnapshot();
	});
});
