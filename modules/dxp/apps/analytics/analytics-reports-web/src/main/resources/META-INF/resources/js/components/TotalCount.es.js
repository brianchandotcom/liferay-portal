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

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import Popover from './Popover.es';

function TotalCount({dataProvider, label}) {
	const iconRef = React.useRef();
	const [showTooltip, setShowTooltip] = React.useState(false);
	const [value, setValue] = React.useState(null);

	React.useEffect(() => {
		dataProvider()
			.then(value => {
				setValue(value);
			})
			.catch(() => setValue('-'));
	}, [dataProvider]);

	return (
		<div className="mt-4">
			<span className="text-secondary">
				{label}
				<ClayIcon
					className="ml-1 mr-2"
					onMouseEnter={() => setShowTooltip(true)}
					onMouseLeave={() => setShowTooltip(false)}
					ref={iconRef}
					small="true"
					symbol="question-circle"
				/>

				{showTooltip && (
					<Popover
						anchor={iconRef.current}
						header={Liferay.Language.get('views')}
					>
						{Liferay.Language.get(
							'the-total-amount-of-views-since-the-content-was-published'
						)}
					</Popover>
				)}
			</span>
			<span className="font-weight-bold">{value}</span>
		</div>
	);
}

TotalCount.propTypes = {
	dataProvider: PropTypes.func.isRequired,
	label: PropTypes.string.isRequired
};

export default TotalCount;
