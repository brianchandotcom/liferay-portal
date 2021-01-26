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

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useState} from 'react';

import togglePermission from '../actions/togglePermission';
import selectCanSwitchEditMode from '../selectors/selectCanSwitchEditMode';
import {useDispatch, useSelector} from '../store/index';

const EDIT_MODES = {
	contentEditing: Liferay.Language.get('content-editing'),
	pageDesign: Liferay.Language.get('page-design'),
};

export default function EditModeSelector() {
	const canSwitchEditMode = useSelector(selectCanSwitchEditMode);
	const dispatch = useDispatch();

	const [active, setActive] = useState(false);
	const [editMode, setEditMode] = useState(
		canSwitchEditMode ? EDIT_MODES.pageDesign : EDIT_MODES.contentEditing
	);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomLeft}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="form-control-select page-editor__edit-mode-selector text-left"
					disabled={!canSwitchEditMode}
					displayType="secondary"
					small
					type="button"
				>
					<span>{editMode}</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				<ClayDropDown.Item
					onClick={() => {
						setActive(false);
						setEditMode(EDIT_MODES.pageDesign);

						dispatch(togglePermission('UPDATE', true));
					}}
				>
					{EDIT_MODES.pageDesign}
				</ClayDropDown.Item>
				<ClayDropDown.Item
					onClick={() => {
						setActive(false);
						setEditMode(EDIT_MODES.contentEditing);

						dispatch(togglePermission('UPDATE', false));
					}}
				>
					{EDIT_MODES.contentEditing}
				</ClayDropDown.Item>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
