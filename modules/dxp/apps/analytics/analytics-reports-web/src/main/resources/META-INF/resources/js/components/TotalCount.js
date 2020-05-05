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

import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import ConnectionContext from '../context/ConnectionContext';
import {StoreContext, useWarning} from '../context/store';
import Hint from './Hint';

function TotalCount({
	className,
	dataProvider,
	label,
	percentage = false,
	popoverAlign,
	popoverHeader,
	popoverMessage,
	popoverPosition,
}) {
	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [value, setValue] = useState('-');

	const isMounted = useIsMounted();

	const [, addWarning] = useWarning();

	const [{publishedToday}] = useContext(StoreContext);

	useEffect(() => {
		if (validAnalyticsConnection) {
			dataProvider()
				.then((value) => {
					if (isMounted()) {
						setValue(value);
					}
				})
				.catch(() => {
					if (isMounted()) {
						setValue('-');
						addWarning();
					}
				});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataProvider, validAnalyticsConnection]);

	let displayValue = '-';

	if (validAnalyticsConnection && !publishedToday) {
		displayValue =
			percentage && value !== '-' ? <span>{`${value}%`}</span> : value;
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
	dataProvider: PropTypes.func.isRequired,
	label: PropTypes.string.isRequired,
	popoverHeader: PropTypes.string.isRequired,
	popoverMessage: PropTypes.string.isRequired,
};

export default TotalCount;
