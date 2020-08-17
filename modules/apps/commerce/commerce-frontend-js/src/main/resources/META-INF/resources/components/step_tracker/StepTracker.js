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

import ClayIcon, {ClayIconSpriteContext} from '@clayui/icon';
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
		<ClayIconSpriteContext.Provider value={props.spritemap}>
			<div className="rounded step-tracker">
				{props.steps.map((step) => (
					<Step key={step.id} {...step} />
				))}
			</div>
		</ClayIconSpriteContext.Provider>
	);
}

StepTracker.propTypes = {
	spritemap: PropTypes.string.isRequired,
	steps: PropTypes.array.isRequired,
};

export default StepTracker;
