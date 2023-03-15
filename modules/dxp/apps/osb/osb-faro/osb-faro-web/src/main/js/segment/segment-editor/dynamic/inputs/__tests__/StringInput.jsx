import React from 'react';
import StringInput from '../StringInput';
import {cleanup, render} from '@testing-library/react';
import {Property} from 'shared/util/records';

jest.unmock('react-dom');

describe('StringInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<StringInput
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
				touched={false}
				valid={false}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with data', () => {
		const {container} = render(
			<StringInput
				displayValue='Name'
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
				touched={false}
				valid
				value='Test Test'
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/o value input when value is null', () => {
		const {queryByTestId} = render(
			<StringInput
				displayValue='Name'
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
				touched={false}
				valid
				value={null}
			/>
		);

		expect(queryByTestId('value-input')).toBeNull();
	});

	it('should render w/ has-error when touched and not valid', () => {
		const {container} = render(
			<StringInput
				displayValue='Name'
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
				touched
				valid={false}
				value=''
			/>
		);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});
});
