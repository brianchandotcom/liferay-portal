import BooleanInput from '../BooleanInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {Property} from 'shared/util/records';

jest.unmock('react-dom');

describe('BooleanInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<BooleanInput
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with data', () => {
		const {container} = render(
			<BooleanInput
				displayValue='Do Not Call'
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
				value='true'
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
