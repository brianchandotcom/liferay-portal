import Input from '../Input';
import React from 'react';
import {mockForm} from 'test/data';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<Input
		field={{name: 'foo'}}
		form={mockForm()}
		label='Input label'
		mask={[]}
		{...props}
	/>
);

describe('Input', () => {
	it('should render', () => {
		const {container} = render(
			<Input field={{name: 'foo'}} form={mockForm()} inline />
		);

		expect(container).toMatchSnapshot();
	});

	it('should render a labelled input', () => {
		const {queryByText} = render(<DefaultComponent />);

		expect(queryByText('Input label')).toBeTruthy();
	});

	it('should render a required input', () => {
		const {queryByText} = render(<DefaultComponent required />);

		expect(queryByText('Input label').closest('label')).toHaveClass(
			'required'
		);
	});
});
