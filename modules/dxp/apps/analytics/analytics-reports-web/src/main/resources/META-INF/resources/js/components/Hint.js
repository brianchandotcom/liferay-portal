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
import ClayPopover from '@clayui/popover';
import className from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

export default function Hint({message, position = 'top', secondary, title}) {
	const [showPopover, setShowPopover] = useState(false);

	return (
		<ClayPopover
			alignPosition={position}
			header={title}
			onShowChange={setShowPopover}
			show={showPopover}
			trigger={
				<span
					className={className('p-1 small', {
						'text-secondary': secondary,
					})}
					onMouseEnter={() => setShowPopover(true)}
					onMouseLeave={() => setShowPopover(false)}
				>
					<ClayIcon small="true" symbol="question-circle" />
				</span>
			}
		>
			{message}
		</ClayPopover>
	);
}

Hint.propTypes = {
	message: PropTypes.string.isRequired,
	position: PropTypes.string,
	secondary: PropTypes.bool,
	title: PropTypes.string.isRequired,
};
