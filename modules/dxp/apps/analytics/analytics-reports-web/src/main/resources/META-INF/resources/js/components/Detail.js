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

const TRAFFIC_CHANNELS = {
	DIRECT: 'direct',
	ORGANIC: 'organic',
	PAID: 'paid',
	REFERRAL: 'referral',
	SOCIAL: 'social',
};

export default function Detail({
	currentPage,
	languageTag,
	onCurrentPageChange,
	onTrafficSourceNameChange,
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
						languageTag={languageTag}
						trafficShareDataProvider={trafficShareDataProvider}
						trafficVolumeDataProvider={trafficVolumeDataProvider}
					/>
				)}
		</>
	);
}

Detail.proptypes = {
	currentPage: PropTypes.object.isRequired,
	languageTag: PropTypes.string.isRequired,
	onCurrentPageChange: PropTypes.func.isRequired,
	onTrafficSourceNameChange: PropTypes.func.isRequired,
	trafficShareDataProvider: PropTypes.func.isRequired,
	trafficVolumeDataProvider: PropTypes.func.isRequired,
};
