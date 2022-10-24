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
import {sub} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import togglePermission from '../actions/togglePermission';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectCanSwitchEditMode from '../selectors/selectCanSwitchEditMode';

const EDIT_MODES = {
	contentEditing: Liferay.Language.get('content-editing'),
	pageDesign: Liferay.Language.get('page-design'),
};

export default function EditModeSelector() {
	const canSwitchEditMode = useSelector(selectCanSwitchEditMode);
	const dispatch = useDispatch();

	const [editMode, setEditMode] = useState(
		canSwitchEditMode ? EDIT_MODES.pageDesign : EDIT_MODES.contentEditing
	);

	const permissions = useSelector((state) => state.permissions);

	const higherUpdatePermissionRef = useRef();

	useEffect(() => {
		if (permissions.UPDATE) {
			higherUpdatePermissionRef.current = 'UPDATE';
		}
		else if (permissions.UPDATE_LAYOUT_BASIC) {
			higherUpdatePermissionRef.current = 'UPDATE_LAYOUT_BASIC';
		}
		else {
			higherUpdatePermissionRef.current = 'UPDATE_LAYOUT_LIMITED';
		}

		/* eslint-disable-next-line react-hooks/exhaustive-deps */
	}, []);

	return (
		<ClayDropDown
			alignmentPosition={Align.BottomLeft}
			closeOnClick
			menuElementAttrs={{
				className: 'page-editor__edit-mode-dropdown-menu',
				containerProps: {
					className: 'cadmin',
				},
			}}
			trigger={
				<ClayButton
					aria-label={sub(
						Liferay.Language.get('page-edition-mode-x'),
						editMode
					)}
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
						setEditMode(EDIT_MODES.pageDesign);

						dispatch(
							togglePermission(
								higherUpdatePermissionRef.current,
								true
							)
						);
					}}
				>
					{EDIT_MODES.pageDesign}
				</ClayDropDown.Item>

				<ClayDropDown.Item
					onClick={() => {
						setEditMode(EDIT_MODES.contentEditing);

						dispatch(
							togglePermission(
								higherUpdatePermissionRef.current,
								false
							)
						);
					}}
				>
					{EDIT_MODES.contentEditing}
				</ClayDropDown.Item>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
