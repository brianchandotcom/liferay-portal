/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {useState} from 'react';

export function BetaButton() {
	const [showPopover, setShowPopover] = useState(false);

	return (
		<ClayPopover
			alignPosition="top"
			disableScroll
			header={Liferay.Language.get('beta-feature')}
			show={showPopover}
			trigger={
				<ClayButton
					className="rounded-circle"
					displayType="beta"
					onMouseOut={() => setShowPopover(false)}
					onMouseOver={() => setShowPopover(true)}
					size="xs"
				>
					<span className="inline-item">Beta</span>

					<span className="inline-item inline-item-after">
						<ClayIcon symbol="info-panel-open" />
					</span>
				</ClayButton>
			}
		>
			{Liferay.Language.get('this-feature-is-in-testing')}
		</ClayPopover>
	);
}
