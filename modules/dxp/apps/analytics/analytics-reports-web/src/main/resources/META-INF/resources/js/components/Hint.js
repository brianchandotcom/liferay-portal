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
import React, {useRef, useState} from 'react';

import Popover from './Popover';

export default function Hint({align, message, position, title}) {
	const iconRef = useRef();
	const [showTooltip, setShowTooltip] = useState(false);

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
				<Popover
					align={align}
					anchor={iconRef.current}
					header={title}
					position={position}
				>
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
