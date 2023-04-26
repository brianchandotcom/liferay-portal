import Dropdown from '../Dropdown';
import React from 'react';
import {cleanup, fireEvent, getByTestId, render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('Dropdown', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<Dropdown />);
		expect(container).toMatchSnapshot();
	});

	it('should render without caret', () => {
		const {container} = render(<Dropdown showCaret={false} />);

		expect(container.querySelector('.caret-root')).toBeNull();
	});

	it('should render with a different button display', () => {
		const {container} = render(
			<Dropdown buttonProps={{displayType: 'link'}} />
		);
		expect(container.querySelector('.btn-link')).toBeTruthy();
	});

	it('should render as active', () => {
		const {container} = render(<Dropdown />);

		fireEvent.click(container.querySelector('.dropdown-toggle'));

		jest.runAllTimers();

		expect(getByTestId(document.body, 'overlay')).toMatchSnapshot();
	});

	it('should render items as links when href is passed to an item', () => {
		const {container} = render(
			<StaticRouter>
				<Dropdown.Item href='#foo' />
			</StaticRouter>
		);

		expect(container.querySelector('a')).toBeTruthy();
	});
});
