/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {ChartDispatchContext} from '../../context/ChartStateContext';
import {StoreDispatchContext} from '../../context/StoreContext';
import CountriesDetail from './CountriesDetail';
import KeywordsDetail from './KeywordsDetail';
import ReferralDetail from './ReferralDetail';
import SocialDetail from './SocialDetail';

const TRAFFIC_CHANNELS = {
	DIRECT: 'direct',
	ORGANIC: 'organic',
	PAID: 'paid',
	REFERRAL: 'referral',
	SOCIAL: 'social',
};

export default function Detail({
	currentPage,
	handleDetailPeriodChange,
	loadingData,
	onCurrentPageChange,
	onTrafficSourceNameChange,
	refProp,
	timeSpanOptions,
	trafficShareDataProvider,
	trafficSourcesDataProvider,
	trafficVolumeDataProvider,
}) {
	const chartDispatch = useContext(ChartDispatchContext);

	const storeDispatch = useContext(StoreDispatchContext);

	const currentPageMocked = {
		data: {
			countrySearch: [
				{
					countryCode: 'es',
					countryName: 'Spain',
					views: 900,
					viewsP: 40,
				},
				{
					countryCode: 'br',
					countryName: 'Brazil',
					views: 400,
					viewsP: 20,
				},
				{
					countryCode: 'us',
					countryName: 'United States',
					views: 700,
					viewsP: 20,
				},
				{
					countryCode: 'ca',
					countryName: 'Canada',
					views: 1000,
					viewsP: 20,
				},
				{
					countryCode: 'me',
					countryName: 'Mexico',
					views: 100,
					viewsP: 20,
				},
				{
					countryCode: 'fr',
					countryName: 'France',
					views: 1700,
					viewsP: 20,
				},
				{
					countryCode: 'it',
					countryName: 'Italy',
					views: 200,
					viewsP: 20,
				},
			],
		},
		view: 'paid',
	};

	return (
		<>
			{loadingData ? (
				<ClayLoadingIndicator
					className="chart-loading-indicator"
					small
				/>
			) : (
				<>
					<div className="c-pt-3 c-px-3 d-flex" ref={refProp}>
						<ClayButton
							displayType="unstyled"
							onClick={() => {
								onCurrentPageChange({view: 'main'});
								onTrafficSourceNameChange('');
								chartDispatch({type: 'SET_LOADING'});
								storeDispatch({
									selectedTrafficSourceName: '',
									type: 'SET_SELECTED_TRAFFIC_SOURCE_NAME',
								});
							}}
							small={true}
						>
							<ClayIcon symbol="angle-left-small" />
						</ClayButton>

						<div className="align-self-center flex-grow-1 mx-2">
							<strong>{currentPage.data.title}</strong>
						</div>
					</div>

					{(currentPage.view === TRAFFIC_CHANNELS.ORGANIC ||
						currentPage.view === TRAFFIC_CHANNELS.PAID) &&
						!!currentPageMocked.data.countrySearch.length &&
						(Liferay.FeatureFlags['LPS-149256'] ? (
							<CountriesDetail
								currentPage={currentPageMocked}
								handleDetailPeriodChange={
									handleDetailPeriodChange
								}
								timeSpanOptions={timeSpanOptions}
								trafficShareDataProvider={
									trafficShareDataProvider
								}
								trafficSourcesDataProvider={
									trafficSourcesDataProvider
								}
								trafficVolumeDataProvider={
									trafficVolumeDataProvider
								}
							/>
						) : (
							<KeywordsDetail
								currentPage={currentPage}
								trafficShareDataProvider={
									trafficShareDataProvider
								}
								trafficVolumeDataProvider={
									trafficVolumeDataProvider
								}
							/>
						))}

					{currentPage.view === TRAFFIC_CHANNELS.REFERRAL && (
						<ReferralDetail
							currentPage={currentPage}
							handleDetailPeriodChange={handleDetailPeriodChange}
							timeSpanOptions={timeSpanOptions}
							trafficShareDataProvider={trafficShareDataProvider}
							trafficSourcesDataProvider={
								trafficSourcesDataProvider
							}
							trafficVolumeDataProvider={
								trafficVolumeDataProvider
							}
						/>
					)}

					{currentPage.view === TRAFFIC_CHANNELS.SOCIAL && (
						<SocialDetail
							currentPage={currentPage}
							handleDetailPeriodChange={handleDetailPeriodChange}
							timeSpanOptions={timeSpanOptions}
							trafficShareDataProvider={trafficShareDataProvider}
							trafficSourcesDataProvider={
								trafficSourcesDataProvider
							}
							trafficVolumeDataProvider={
								trafficVolumeDataProvider
							}
						/>
					)}
				</>
			)}
		</>
	);
}

Detail.propTypes = {
	currentPage: PropTypes.object.isRequired,
	loadingData: PropTypes.bool,
	onCurrentPageChange: PropTypes.func.isRequired,
	onTrafficSourceNameChange: PropTypes.func.isRequired,
	refProp: PropTypes.oneOfType([
		PropTypes.shape({current: PropTypes.instanceOf(Element)}),
	]),
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	trafficShareDataProvider: PropTypes.func.isRequired,
	trafficSourcesDataProvider: PropTypes.func.isRequired,
	trafficVolumeDataProvider: PropTypes.func.isRequired,
};
