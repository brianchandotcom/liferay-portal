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

import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import ConnectionContext from '../context/ConnectionContext';
import {StoreStateContext} from '../context/StoreContext';
import {numberFormat} from '../utils/numberFormat';
import Hint from './Hint';

export default function TotalCount({
	className,
	label,
	percentage = false,
	popoverAlign,
	popoverHeader,
	popoverMessage,
	popoverPosition,
	value,
}) {
	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const {languageTag, publishedToday} = useContext(StoreStateContext);

	let displayValue = '-';

	if (validAnalyticsConnection && !publishedToday && value >= 0) {
		displayValue = percentage ? (
			<span>{`${value}%`}</span>
		) : (
			numberFormat(languageTag, value)
		);
	}

	return (
		<div className={className}>
			<span className="text-secondary">{label}</span>
			<span className="text-secondary">
				<Hint
					align={popoverAlign}
					message={popoverMessage}
					position={popoverPosition}
					title={popoverHeader}
				/>
			</span>
			<span className="font-weight-bold">{displayValue}</span>
		</div>
	);
}

TotalCount.propTypes = {
	label: PropTypes.string.isRequired,
	percentage: PropTypes.bool,
	popoverHeader: PropTypes.string.isRequired,
	popoverMessage: PropTypes.string.isRequired,
	value: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
};
