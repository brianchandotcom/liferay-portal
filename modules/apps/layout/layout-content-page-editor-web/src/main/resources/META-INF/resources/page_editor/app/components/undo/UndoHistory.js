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

import {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import React from 'react';

import {useSelector} from '../../store/index';

export default function UndoHistory() {
	const undoHistory = useSelector((state) => state.undoHistory);

	return (
		<>
			<ClayDropDownWithItems
				alignmentPosition={Align.BottomRight}
				className="mr-3"
				items={[]}
				trigger={
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('undo-history')}
						className="btn-monospaced"
						disabled={!undoHistory || !undoHistory.length}
						displayType="secondary"
						small
						symbol="time"
						title={Liferay.Language.get('undo-history')}
					/>
				}
			/>
		</>
	);
}
