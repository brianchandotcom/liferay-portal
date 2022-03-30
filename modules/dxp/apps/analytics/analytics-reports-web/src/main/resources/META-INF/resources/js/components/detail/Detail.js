/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {ChartDispatchContext} from '../../context/ChartStateContext';
import {StoreDispatchContext} from '../../context/StoreContext';
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
						currentPage.data.countrySearchKeywords.length > 0 && (
							<KeywordsDetail
								currentPage={currentPage}
								trafficShareDataProvider={
									trafficShareDataProvider
								}
								trafficVolumeDataProvider={
									trafficVolumeDataProvider
								}
							/>
						)}

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
