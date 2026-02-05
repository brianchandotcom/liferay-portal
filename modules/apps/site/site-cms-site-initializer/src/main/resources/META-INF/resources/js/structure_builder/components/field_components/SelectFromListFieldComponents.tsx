/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import React from 'react';

import {useSelector, useStateDispatch} from '../../contexts/StateContext';
import selectPublishedChildren from '../../selectors/selectPublishedChildren';
import {Field, SelectFromListField} from '../../utils/field';
import PicklistPicker from '../PicklistPicker';

export default function getSelectFromListFieldComponents(): {
	FirstSectionComponent?: React.FC<{disabled?: boolean; field: Field}>;
	SecondSectionComponent?: React.FC<{disabled?: boolean; field: Field}>;
} {
	return {
		FirstSectionComponent,
		SecondSectionComponent,
	};
}

function FirstSectionComponent({
	disabled,
	field,
}: {
	disabled?: boolean;
	field: Field;
}) {
	return <PicklistPicker disabled={disabled} field={field} />;
}

function SecondSectionComponent({
	disabled,
	field,
}: {
	disabled?: boolean;
	field: Field;
}) {
	const selectFromListField = field as SelectFromListField;

	const dispatch = useStateDispatch();
	const publishedChildren = useSelector(selectPublishedChildren);

	const isPublished = publishedChildren.has(field.uuid);

	return (
		<ClayForm.Group className="mb-3">
			<ClayCheckbox
				checked={selectFromListField.multiselection}
				disabled={disabled || isPublished}
				label={Liferay.Language.get('multiselection')}
				onChange={(event) => {
					dispatch({
						multiselection: event.target.checked,
						type: 'update-field',
						uuid: selectFromListField.uuid,
					});
				}}
			/>
		</ClayForm.Group>
	);
}
