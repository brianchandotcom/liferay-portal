import OperatorSelect from '../OperatorSelect';
import React from 'react';
import {DataTypes} from 'event-analysis/utils/types';
import {fireEvent, render} from '@testing-library/react';
import {FunctionalOperators} from '../../../../utils/constants';

jest.unmock('react-dom');

describe('OperatorSelect', () => {
	it('should render', () => {
		const {container, getByText} = render(
			<OperatorSelect
				dataType={DataTypes.Number}
				onChange={jest.fn()}
				operatorsName={FunctionalOperators.Between}
			/>
		);
		fireEvent.click(getByText('Select an option'));

		expect(getByText('is greater than')).toBeTruthy();
		expect(getByText('is less than')).toBeTruthy();
		expect(getByText('between')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
