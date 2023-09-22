import React from 'react';
import SyncedStripe, {getTitle} from '../SyncedStripe';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SyncedStripe', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<SyncedStripe channelsSyncedCount={0} sitesSyncedCount={0} />
		);

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

			if (sitesSyncedCount === 1) {
				if (channelsSyncedCount === 1) {
					expect(
						queryByText(
							`There is ${sitesSyncedCount} Site and ${channelsSyncedCount} Channel synced to this property.`
						)
					).toBeInTheDocument();
				} else {
					expect(
						queryByText(
							`There is ${sitesSyncedCount} Site and ${channelsSyncedCount} Channels synced to this property.`
						)
					).toBeInTheDocument();
				}
			} else {
				if (channelsSyncedCount === 1) {
					expect(
						queryByText(
							`There are ${sitesSyncedCount} Sites and ${channelsSyncedCount} Channel synced to this property.`
						)
					).toBeInTheDocument();
				} else {
					expect(
						queryByText(
							`There are ${sitesSyncedCount} Sites and ${channelsSyncedCount} Channels synced to this property.`
						)
					).toBeInTheDocument();
				}
			}
		}
	);
});

describe('getTitle', () => {
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
			const title = getTitle(sitesSyncedCount, channelsSyncedCount);

			if (sitesSyncedCount === 1) {
				if (channelsSyncedCount === 1) {
					expect(title).toEqual(
						`There is 1 Site and ${channelsSyncedCount} Channel synced to this property.`
					);
				} else {
					expect(title).toEqual(
						`There is 1 Site and ${channelsSyncedCount} Channels synced to this property.`
					);
				}
			} else {
				if (channelsSyncedCount === 1) {
					expect(title).toEqual(
						`There are ${sitesSyncedCount} Sites and 1 Channel synced to this property.`
					);
				} else {
					expect(title).toEqual(
						`There are ${sitesSyncedCount} Sites and ${channelsSyncedCount} Channels synced to this property.`
					);
				}
			}
		}
	);
});
