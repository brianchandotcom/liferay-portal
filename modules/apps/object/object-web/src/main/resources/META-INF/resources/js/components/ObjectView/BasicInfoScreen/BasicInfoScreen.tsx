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

import ClayForm, {ClayCheckbox} from '@clayui/form';
import React, {useContext} from 'react';

import {invalidateRequired} from '../../../hooks/useForm';
import {defaultLanguageId} from '../../../utils/locale';
import Card from '../../Card/Card';
import Input from '../../Form/Input';
import ViewContext, {TYPES} from '../context';

export default function BasicInfoScreen() {
	const [{objectView}, dispatch] = useContext(ViewContext);

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

	if (invalidateRequired(objectView.name[defaultLanguageId])) {
		error = Liferay.Language.get('required');
	}

	return (
		<Card title={Liferay.Language.get('basic-info')}>
			<ClayForm.Group>
				<Input
					disabled={false}
					error={error}
					label={Liferay.Language.get('name')}
					name="name"
					onChange={({target: {value}}: any) => {
						handleChangeName(value);
					}}
					required
					value={objectView.name[defaultLanguageId]}
				/>
			</ClayForm.Group>

			<ClayForm.Group className="mb-0">
				<ClayCheckbox
					checked={objectView.defaultObjectView}
					disabled={false}
					label={Liferay.Language.get('mark-as-default')}
					onChange={({target: {checked}}) => {
						handleChangeChecked(checked);
					}}
				/>
			</ClayForm.Group>
		</Card>
	);
}
