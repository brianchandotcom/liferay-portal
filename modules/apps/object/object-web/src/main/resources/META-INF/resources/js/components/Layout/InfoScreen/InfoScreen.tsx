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
import LayoutContext, {TYPES} from '../context';

const InfoScreen: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [{isViewOnly, objectLayout}, dispatch] = useContext(LayoutContext);

	let error: string | undefined;

	if (invalidateRequired(objectLayout.name[defaultLanguageId])) {
		error = Liferay.Language.get('required');
	}

	return (
		<Card title={Liferay.Language.get('basic-info')}>
			<Input
				disabled={isViewOnly}
				error={error}
				id="objectLayoutName"
				label={Liferay.Language.get('name')}
				name="name"
				onChange={({target: {value}}) => {
					dispatch({
						payload: {name: {[defaultLanguageId]: value}},
						type: TYPES.CHANGE_OBJECT_LAYOUT_NAME,
					});
				}}
				required
				value={objectLayout.name[defaultLanguageId]}
			/>

			<ClayForm.Group className="mb-0">
				<ClayCheckbox
					checked={objectLayout.defaultObjectLayout}
					disabled={isViewOnly}
					label={Liferay.Language.get('mark-as-default')}
					onChange={({target: {checked}}) => {
						dispatch({
							payload: {checked},
							type: TYPES.SET_OBJECT_LAYOUT_AS_DEFAULT,
						});
					}}
				/>
			</ClayForm.Group>
		</Card>
	);
};

export default InfoScreen;
