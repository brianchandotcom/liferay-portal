import BasePage from '../index';
import {cleanup, render} from '@testing-library/react';
import {renderWithStore} from 'test/mock-store';
import {withStaticRouter} from 'test/mock-router';

const WrappedComponent = withStaticRouter(BasePage);

jest.unmock('react-dom');

describe('BasePage', () => {
	afterEach(cleanup);

	it('renders BasePage', () => {
		const {container} = render(
			renderWithStore(WrappedComponent, {
				children: 'Test test',
				documentTitle: 'Test title'
			})
		);

		expect(container).toMatchSnapshot();
	});
});
