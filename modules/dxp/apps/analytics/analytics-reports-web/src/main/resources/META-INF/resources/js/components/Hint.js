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

import Popover from './Popover';

export default function Hint({message, title}) {
	const iconRef = React.useRef();
	const [showTooltip, setShowTooltip] = React.useState(false);

	const handleMouseEnter = () => {
		setShowTooltip(true);
	};
	const handleMouseLeave = () => {
		setShowTooltip(false);
	};

	return (
		<>
			<span
				className="p-1"
				onMouseEnter={handleMouseEnter}
				onMouseLeave={handleMouseLeave}
				ref={iconRef}
			>
				<ClayIcon
					className="mr-1"
					small="true"
					symbol="question-circle"
				/>
			</span>

			{showTooltip && (
				<Popover anchor={iconRef.current} header={title}>
					{message}
				</Popover>
			)}
		</>
	);
}

Hint.proptypes = {
	message: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};
