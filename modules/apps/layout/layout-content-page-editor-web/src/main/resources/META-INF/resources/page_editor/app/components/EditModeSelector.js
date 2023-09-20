/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import Form from '@clayui/form';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import togglePermission from '../actions/togglePermission';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectCanSwitchEditMode from '../selectors/selectCanSwitchEditMode';

const EDIT_MODES = [
	{
		label: Liferay.Language.get('content-editing'),
		value: 'content-editing',
	},
	{label: Liferay.Language.get('page-design'), value: 'page-design'},
];

const TriggerLabel = React.forwardRef(
	({children, className, ...otherProps}, ref) => (
		<ClayButton
			className={classNames('form-control-select', {
				show: className.includes('show'),
			})}
			displayType="secondary"
			ref={ref}
			size="sm"
			{...otherProps}
		>
			{children}
		</ClayButton>
	)
);

export default function EditModeSelector() {
	const canSwitchEditMode = useSelector(selectCanSwitchEditMode);
	const dispatch = useDispatch();

	const [editMode, setEditMode] = useState(
		canSwitchEditMode
			? EDIT_MODES.find(({value}) => value === 'page-design')
			: EDIT_MODES.find(({value}) => value === 'content-editing')
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
		<Form.Group className="mb-0">
			<Picker
				UNSAFE_menuClassName="cadmin"
				aria-label={sub(
					Liferay.Language.get('page-edition-mode'),
					editMode.label
				)}
				as={TriggerLabel}
				className="btn-secondary"
				defaultSelectedKey={editMode.value}
				disabled={!canSwitchEditMode}
				items={EDIT_MODES}
				onSelectionChange={(key) => {
					const selectedOption = EDIT_MODES.find(
						({value}) => value === key
					);

					setEditMode(selectedOption);

					dispatch(
						togglePermission(
							higherUpdatePermissionRef.current,
							selectedOption.value === 'page-design'
						)
					);
				}}
			>
				{({label, value}) => (
					<Option key={value} textValue={label}>
						{label}
					</Option>
				)}
			</Picker>
		</Form.Group>
	);
}
