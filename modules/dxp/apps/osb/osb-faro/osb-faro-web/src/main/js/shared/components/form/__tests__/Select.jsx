import React from 'react';
import Select from '../Select';
import {cleanup, render} from '@testing-library/react';
import {mockForm} from 'test/data';

jest.unmock('react-dom');

describe('Select', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Select field={{name: 'test'}} form={mockForm()}>
				<Select.Item value='red'>{'red'}</Select.Item>
			</Select>
		);
		expect(container).toMatchSnapshot();
	});

	it('renders w/ label', () => {
		const {container} = render(
			<Select field={{name: 'test'}} form={mockForm()} label='foo' />
		);
		expect(container.querySelector('label')).not.toBeNull();
	});

	it('renders w/ error', () => {
		const {container} = render(
			<Select
				field={{name: 'test'}}
				form={mockForm({
					errors: {test: 'can not be test'},
					touched: {test: true}
				})}
			>
				<Select.Item value='red'>{'red'}</Select.Item>
			</Select>
		);

		expect(container.querySelector('.has-error')).not.toBeNull();
	});
});
