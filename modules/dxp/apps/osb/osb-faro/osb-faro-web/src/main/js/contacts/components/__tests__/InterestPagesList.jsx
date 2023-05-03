import InterestPagesList from '../InterestPagesList';
import React from 'react';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

/**
 * For ActivePagesList (active: true) validation, It's possible to order by
 * unique visits count, whereas for InactivePagesList (active: false)
 * it's not, that's why we're validating by the presence
 * of the order button on those tests.
 */
describe('InterestPagesList', () => {
	it('should render', () => {
		const {container} = render(
			<StaticRouter>
				<InterestPagesList dataSourceParams={{}} />
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render an activePages component', () => {
		const {container} = render(
			<StaticRouter>
				<InterestPagesList dataSourceParams={{active: true}} />
			</StaticRouter>
		);

		expect(
			container.querySelector('.lexicon-icon-order-arrow-down')
		).toBeTruthy();
	});

	it('should render an InactivePages component', () => {
		const {container} = render(
			<StaticRouter>
				<InterestPagesList dataSourceParams={{active: false}} />
			</StaticRouter>
		);

		expect(
			container.querySelector('.lexicon-icon-order-arrow-down')
		).toBeFalsy();
	});
});
