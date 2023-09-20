import React from 'react';
import SyncedStripe from '../SyncedStripe';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

/**
 * 1 site e 0 channels
 * 1 site e 1 channel
 * 0 sites e 0 channels
 * 0 sites e 1 channel
 * 2 sites e 0 channels
 * 0 sites e 2 channels
 */

describe('SyncedStripe', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<SyncedStripe />);

		expect(container).toMatchSnapshot();
	});

	it.each`
		sitesSyncedCount | channelsSyncedCount
		${1}             | ${0}
		${1}             | ${1}
		${0}             | ${0}
		${0}             | ${1}
		${2}             | ${0}
		${0}             | ${2}
	`(
		'returns correct text if sitesSyncedCount is $sitesSyncedCount and channelsSyncedCount is $channelsSyncedCount',
		({channelsSyncedCount, sitesSyncedCount}) => {
			const {queryByText} = render(
				<SyncedStripe
					channelsSyncedCount={channelsSyncedCount}
					sitesSyncedCount={sitesSyncedCount}
				/>
			);

			expect(
				queryByText(
					sitesSyncedCount === 1
						? `There is ${sitesSyncedCount} Site(s) and ${channelsSyncedCount} Channel(s) synced to this property.`
						: `There are ${sitesSyncedCount} Site(s) and ${channelsSyncedCount} Channel(s) synced to this property.`
				)
			).toBeInTheDocument();
		}
	);
});
