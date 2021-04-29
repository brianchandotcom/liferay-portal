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
import PropTypes from 'prop-types';
import React from 'react';

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
	return (
		<>
			<div className="c-pt-3 c-px-3 d-flex">
				<ClayButton
					displayType="unstyled"
					onClick={() => {
						onCurrentPageChange({view: 'main'});
						onTrafficSourceNameChange('');
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
