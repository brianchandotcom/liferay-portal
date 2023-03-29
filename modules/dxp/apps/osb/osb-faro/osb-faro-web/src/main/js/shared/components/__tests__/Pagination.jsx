import Pagination from '../Pagination';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {withStaticRouter} from 'test/mock-router';

jest.unmock('react-dom');

const DefaultComponent = withStaticRouter(Pagination);

describe('Pagination', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DefaultComponent href='' page={1} total={1} />
		);
		expect(container).toMatchSnapshot();
	});

	it('should render with buttons if passed an onChange handler', () => {
		const {container} = render(
			<DefaultComponent onChange={jest.fn()} page={1} total={1} />
		);
		expect(container).toMatchSnapshot();
	});

	it('should render with a lot of pages', () => {
		const {container} = render(
			<DefaultComponent href='' page={1} total={100} />
		);
		expect(container).toMatchSnapshot();
	});

	it('should render a lot of pages with a middle page active', () => {
		const {container} = render(
			<DefaultComponent href='' page={50} total={100} />
		);
		expect(container).toMatchSnapshot();
	});

	it('should render a lot of pages with the end page active', () => {
		const {container} = render(
			<DefaultComponent href='' page={100} total={100} />
		);
		expect(container).toMatchSnapshot();
	});
});
