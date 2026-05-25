/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {EActionType, IActionGroup} from '../types';
import BalloonGroup from './BalloonGroup';

import '../../../css/ckeditor5/balloon.scss';

function WritingAssistantBalloon({
	actionsGroup,
	handleActionClick,
}: {
	actionsGroup: IActionGroup[];
	handleActionClick: (type: EActionType) => Promise<void>;
}) {
	return (
		<div className="balloon-container">
			{actionsGroup.map((group: IActionGroup, index: number) => (
				<React.Fragment key={group.name}>
					{index > 0 && <span className="balloon-group-separator" />}

					<BalloonGroup
						handleItemClick={handleActionClick}
						header={group.name}
					>
						{group.children}
					</BalloonGroup>
				</React.Fragment>
			))}
		</div>
	);
}

export default WritingAssistantBalloon;
