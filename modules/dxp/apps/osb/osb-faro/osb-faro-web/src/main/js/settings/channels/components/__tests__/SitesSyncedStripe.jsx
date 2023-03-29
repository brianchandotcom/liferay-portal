import React from 'react';
import SitesSyncedStriped from '../SitesSyncedStripe';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SitesSyncedStriped', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<SitesSyncedStriped />);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ count', () => {
		const {container, queryByText} = render(
			<SitesSyncedStriped sitesSyncedCount={1} />
		);

		expect(container.querySelector('.empty')).toBeNull();
		expect(queryByText('Sites Synced: 1 Site')).toBeTruthy();
	});

	it('should render w/ empty state', () => {
		const {container, queryByText} = render(
			<SitesSyncedStriped sitesSyncedCount={0} />
		);

		expect(container.querySelector('.empty')).toBeTruthy();
		expect(queryByText('Sites Synced: 0 Sites')).toBeTruthy();
	});
});
