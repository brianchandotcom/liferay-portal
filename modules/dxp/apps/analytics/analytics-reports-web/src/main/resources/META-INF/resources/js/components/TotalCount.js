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

import {useStateSafe} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect} from 'react';

import ConnectionContext from '../context/ConnectionContext';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import {numberFormat} from '../utils/numberFormat';
import Hint from './Hint';

function TotalCount({
	className,
	dataProvider,
	label,
	percentage = false,
	popoverHeader,
	popoverMessage,
	popoverPosition,
}) {
	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [value, setValue] = useStateSafe('-');

	const dispatch = useContext(StoreDispatchContext);

	const {languageTag, publishedToday} = useContext(StoreStateContext);

	useEffect(() => {
		if (validAnalyticsConnection) {
			dataProvider()
				.then(setValue)
				.catch(() => {
					setValue('-');
					dispatch({type: 'ADD_WARNING'});
				});
		}
	}, [dispatch, dataProvider, setValue, validAnalyticsConnection]);

	let displayValue = '-';

	if (validAnalyticsConnection && !publishedToday) {
		displayValue =
			value !== '-' ? (
				percentage ? (
					<span>{`${value}%`}</span>
				) : (
					numberFormat(languageTag, value)
				)
			) : (
				value
			);
	}

	return (
		<div className={className}>
			<span className="text-secondary">{label}</span>
			<span className="text-secondary">
				<Hint
					message={popoverMessage}
					position={popoverPosition}
					title={popoverHeader}
				/>
			</span>
			<span className="font-weight-bold inline-item-after">
				{displayValue}
			</span>
		</div>
	);
}

TotalCount.propTypes = {
	dataProvider: PropTypes.func.isRequired,
	label: PropTypes.string.isRequired,
	percentage: PropTypes.bool,
	popoverHeader: PropTypes.string.isRequired,
	popoverMessage: PropTypes.string.isRequired,
};

export default TotalCount;
