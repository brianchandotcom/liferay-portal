/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import Form from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import togglePermission, {PermissionKey} from '../actions/togglePermission';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectCanSwitchEditMode from '../selectors/selectCanSwitchEditMode';

interface EditModePickerProps {
	disabled: boolean;
	symbol?: string;
	title?: string;
}

interface EditMode {
	label: string;
	value: string;
}

const EDIT_MODES = [
	{
		label: Liferay.Language.get('content-editing'),
		value: 'content-editing',
	},
	{label: Liferay.Language.get('page-design'), value: 'page-design'},
];

const Trigger = React.forwardRef<HTMLButtonElement, any>(
	({children, symbol, ...otherProps}, ref) => (
		<ClayButton
			{...otherProps}
			className={classNames('form-control-select', {
				'd-lg-block d-none':
					Liferay.FeatureFlags['LPD-10988'] && !symbol,
				'd-lg-none': symbol,
			})}
			displayType="secondary"
			ref={ref}
			size="sm"
		>
			{symbol ? <ClayIcon symbol="format" /> : children}
		</ClayButton>
	)
);

const EditModePicker = (props: EditModePickerProps) => (
	<Picker UNSAFE_menuClassName="cadmin" as={Trigger} {...props}>
		{({label, value}: EditMode) => (
			<Option key={value} textValue={label}>
				{label}
			</Option>
		)}
	</Picker>
);

export default function EditModeSelector() {
	const canSwitchEditMode = useSelector(selectCanSwitchEditMode);
	const dispatch = useDispatch();

	const [editMode, setEditMode] = useState<EditMode>(
		canSwitchEditMode
			? EDIT_MODES.find(({value}) => value === 'page-design')!
			: EDIT_MODES.find(({value}) => value === 'content-editing')!
	);

	const permissions = useSelector((state) => state.permissions);

	const higherUpdatePermissionRef = useRef<PermissionKey>(
		'UPDATE_LAYOUT_LIMITED'
	);

	useEffect(() => {
		if (permissions.UPDATE) {
			higherUpdatePermissionRef.current = 'UPDATE';
		}
		else if (permissions.UPDATE_LAYOUT_BASIC) {
			higherUpdatePermissionRef.current = 'UPDATE_LAYOUT_BASIC';
		}

		/* eslint-disable-next-line react-hooks/exhaustive-deps */
	}, []);

	const props = {
		['aria-label']: sub(
			Liferay.Language.get('select-edit-mode.-current-edit-mode-x'),
			editMode.label
		),
		disabled: !canSwitchEditMode,
		items: EDIT_MODES,
		onSelectionChange: (key: React.Key) => {
			const selectedOption = EDIT_MODES.find(({value}) => value === key)!;

			setEditMode(selectedOption);

			dispatch(
				togglePermission(
					higherUpdatePermissionRef.current,
					selectedOption.value === 'page-design'
				)
			);
		},
		selectedKey: editMode.value,
	};

	return (
		<Form.Group className="mb-0">
			<EditModePicker {...props} />

			{Liferay.FeatureFlags['LPD-10988'] ? (
				<EditModePicker
					symbol="format"
					title={sub(
						Liferay.Language.get('select-x'),
						Liferay.Language.get('edit-mode')
					)}
					{...props}
				/>
			) : null}
		</Form.Group>
	);
}
