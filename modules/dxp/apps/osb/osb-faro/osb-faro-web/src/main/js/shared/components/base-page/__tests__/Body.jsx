import Body from '../Body';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('BasePage.Body', () => {
	afterEach(cleanup);

	it('renders Body', () => {
		const {container} = render(<Body>{'Test Test'}</Body>);

		expect(container).toMatchSnapshot();
	});

	it('renders Body w/ disabled', () => {
		const {container} = render(<Body>{'Test Test'}</Body>);

		expect(container).toMatchSnapshot();
	});

	it('renders Body w/o pageContainer', () => {
		const {container} = render(
			<Body pageContainer={false}>{'Test Test'}</Body>
		);

		expect(container).toMatchSnapshot();
	});
});
