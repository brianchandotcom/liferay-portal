/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

function Step(props) {
	return (
		<div className={classnames(`step`, props.state || 'inactive')}>
			<span className="step-label">
				{props.label}

				{props.state === 'completed' && (
					<ClayIcon className="ml-3" symbol="check" />
				)}
			</span>
		</div>
	);
}

Step.propTypes = {
	label: PropTypes.string.isRequired,
	state: PropTypes.oneOf(['completed', 'active', 'inactive']),
};

function StepTracker(props) {
	return (
		<div className="rounded step-tracker">
			{props.steps.map((step) => (
				<Step key={step.id} {...step} />
			))}
		</div>
	);
}

StepTracker.propTypes = {
	steps: PropTypes.array.isRequired,
};

export default StepTracker;
