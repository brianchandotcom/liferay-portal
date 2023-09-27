/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			className="position-fixed"
			header={title}
			onShowChange={setShowPopover}
			show={showPopover}
			trigger={
				<span
					className={className('p-1', {
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
