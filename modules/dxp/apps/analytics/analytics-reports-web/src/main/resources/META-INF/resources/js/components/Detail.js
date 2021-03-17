/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {ChartDispatchContext} from '../context/ChartStateContext';
import {StoreDispatchContext} from '../context/StoreContext';
import KeywordsDetail from './detail/KeywordsDetail';
import ReferralDetail from './detail/ReferralDetail';
import SocialDetail from './detail/SocialDetail';

const TRAFFIC_CHANNELS = {
	DIRECT: 'direct',
	ORGANIC: 'organic',
	PAID: 'paid',
	REFERRAL: 'referral',
	SOCIAL: 'social',
};

export default function Detail({
	currentPage,
	onCurrentPageChange,
	onTrafficSourceNameChange,
	timeSpanOptions,
	trafficShareDataProvider,
	trafficVolumeDataProvider,
}) {
	const chartDispatch = useContext(ChartDispatchContext);

	const storeDispatch = useContext(StoreDispatchContext);

	return (
		<>
			<div className="c-pt-3 c-px-3 d-flex">
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
				currentPage.data.countryKeywords.length > 0 && (
					<KeywordsDetail
						currentPage={currentPage}
						trafficShareDataProvider={trafficShareDataProvider}
						trafficVolumeDataProvider={trafficVolumeDataProvider}
					/>
				)}

			{currentPage.view === TRAFFIC_CHANNELS.REFERRAL && (
				<ReferralDetail
					currentPage={currentPage}
					timeSpanOptions={timeSpanOptions}
					trafficShareDataProvider={trafficShareDataProvider}
					trafficVolumeDataProvider={trafficVolumeDataProvider}
				/>
			)}

			{currentPage.view === TRAFFIC_CHANNELS.SOCIAL && (
				<SocialDetail
					currentPage={currentPage}
					timeSpanOptions={timeSpanOptions}
					trafficShareDataProvider={trafficShareDataProvider}
					trafficVolumeDataProvider={trafficVolumeDataProvider}
				/>
			)}
		</>
	);
}

Detail.propTypes = {
	currentPage: PropTypes.object.isRequired,
	onCurrentPageChange: PropTypes.func.isRequired,
	onTrafficSourceNameChange: PropTypes.func.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	trafficShareDataProvider: PropTypes.func.isRequired,
	trafficVolumeDataProvider: PropTypes.func.isRequired,
};
