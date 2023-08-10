/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import className from 'classnames';
import React, {useState} from 'react';

export type Position =
	| 'top'
	| 'top-left'
	| 'top-right'
	| 'bottom'
	| 'bottom-left'
	| 'bottom-right'
	| 'left'
	| 'left-top'
	| 'left-bottom'
	| 'right'
	| 'right-top'
	| 'right-bottom';

interface Props {
	message: string;
	position?: Position;
	secondary?: boolean;
	title: string;
}

export function Hint({message, position = 'top', secondary, title}: Props) {
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
					<ClayIcon symbol="question-circle" />
				</span>
			}
		>
			{message}
		</ClayPopover>
	);
}

export default Hint;
