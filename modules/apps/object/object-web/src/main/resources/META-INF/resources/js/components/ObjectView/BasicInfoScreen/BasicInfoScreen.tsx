/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import {
	Card,
	Input,
	REQUIRED_MSG,
	getLocalizableLabel,
	invalidateRequired,
} from '@liferay/object-js-components-web';
import React from 'react';

import {TYPES, useViewContext} from '../objectViewContext';

export default function BasicInfoScreen() {
	const [{creationLanguageId, objectView}, dispatch] = useViewContext();

	const handleChangeName = (newName: string) => {
		dispatch({
			payload: {newName},
			type: TYPES.CHANGE_OBJECT_VIEW_NAME,
		});
	};

	const handleChangeChecked = (checked: boolean) => {
		dispatch({
			payload: {checked},
			type: TYPES.SET_OBJECT_VIEW_AS_DEFAULT,
		});
	};

	let error: string | undefined;

	if (
		invalidateRequired(
			getLocalizableLabel(creationLanguageId, objectView.name)
		)
	) {
		error = REQUIRED_MSG;
	}

	return (
		<Card title={Liferay.Language.get('basic-info')}>
			<ClayForm.Group>
				<Input
					disabled={false}
					error={error}
					label={Liferay.Language.get('name')}
					name="name"
					onChange={({target: {value}}) => {
						handleChangeName(value);
					}}
					required
					value={getLocalizableLabel(
						creationLanguageId,
						objectView.name
					)}
				/>
			</ClayForm.Group>

			<ClayForm.Group className="mb-0">
				<ClayCheckbox
					checked={objectView.defaultObjectView}
					label={Liferay.Language.get('mark-as-default')}
					onChange={({target: {checked}}) => {
						handleChangeChecked(checked);
					}}
				/>
			</ClayForm.Group>
		</Card>
	);
}
