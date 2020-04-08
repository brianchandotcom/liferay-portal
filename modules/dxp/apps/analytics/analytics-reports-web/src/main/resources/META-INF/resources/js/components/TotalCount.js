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

import StateContext from '../state/context';
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
	const {validAnalyticsCloudConnection} = useContext(StateContext);

	const [value, setValue] = useState('-');
	const isMounted = useIsMounted();

	useEffect(() => {
		if (validAnalyticsCloudConnection) {
			dataProvider()
				.then(value => {
					if (isMounted()) {
						setValue(value);
					}
				})
				.catch(() => {
					if (isMounted()) {
						setValue('-');
					}
				});
		}
		else {
			setValue('-');
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataProvider, validAnalyticsCloudConnection]);

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
			<span className="font-weight-bold">
				{percentage && value !== '-' ? (
					<span>{`${value}%`}</span>
				) : (
					value
				)}
			</span>
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
