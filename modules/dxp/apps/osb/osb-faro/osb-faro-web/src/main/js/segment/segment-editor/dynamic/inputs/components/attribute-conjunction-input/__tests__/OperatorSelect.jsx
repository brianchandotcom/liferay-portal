import OperatorSelect from '../OperatorSelect';
import React from 'react';
import {DataTypes} from 'event-analysis/utils/types';
import {FunctionalOperators} from '../../../../utils/constants';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('OperatorSelect', () => {
	it('should render', () => {
		const {container} = render(
			<OperatorSelect
				dataType={DataTypes.Number}
				onChange={jest.fn()}
				operatorsName={FunctionalOperators.Between}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
