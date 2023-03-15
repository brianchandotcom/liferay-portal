import DateInput from '../DateInput';
import React from 'react';
import {mockForm} from 'test/data';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<DateInput field={{name: 'foo'}} form={mockForm()} {...props} />
);

describe('DateInput', () => {
	const labelContent = 'Foo Date';

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render with label', () => {
		const {queryByText} = render(<DefaultComponent label={labelContent} />);

		expect(queryByText(labelContent)).toBeTruthy();
	});

	it('should render as required', () => {
		const {queryByText} = render(
			<DefaultComponent label={labelContent} required />
		);

		expect(queryByText(labelContent).closest('label')).toHaveClass(
			'required'
		);
	});
});
