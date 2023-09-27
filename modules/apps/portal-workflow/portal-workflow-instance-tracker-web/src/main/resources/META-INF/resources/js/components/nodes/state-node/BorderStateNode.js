/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import classnames from 'classnames';
import React from 'react';
import {Handle} from 'react-flow-renderer';

export default function BorderStateNode({
	data: {initial = true, label, notifyVisibilityChange},
}) {
	return (
		<>
			{initial ? (
				<Handle position="right" style={{top: '50%'}} type="source" />
			) : (
				<Handle position="left" style={{top: '50%'}} type="target" />
			)}

			<ClayTooltipProvider>
				<div
					className={classnames(
						'border-state-node node text-white',
						initial ? 'start-state' : 'end-state'
					)}
					data-tooltip-align="top"
					onMouseEnter={notifyVisibilityChange(true)}
					onMouseLeave={notifyVisibilityChange(false)}
					title={label}
				>
					<span className="truncate-container">
						{Liferay.Language.get(label)}
					</span>
				</div>
			</ClayTooltipProvider>
		</>
	);
}
