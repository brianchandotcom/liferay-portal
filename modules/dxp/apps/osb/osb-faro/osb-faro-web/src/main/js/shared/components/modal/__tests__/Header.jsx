import Header from '../Header';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Modal Header', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Header title='Foo' />);

		expect(container).toMatchSnapshot();
	});

	it('renders a modal header with an icon', () => {
		const {container} = render(
			<Header iconSymbol='warning-full' title='Foo' />
		);

		expect(container.querySelector('.icon-root')).not.toBeNull();
	});

	it('renders a modal header w/ a border', () => {
		const {container} = render(<Header border title='Foo' />);

		expect(container.querySelector('.border')).not.toBeNull();
	});
});
