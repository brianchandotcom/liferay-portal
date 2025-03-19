/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectPublishedFields from '../selectors/selectPublishedFields';
import ListTypeService from '../services/ListTypeService';
import {Field, MultiselectField, SingleSelectField} from '../utils/field';

type Picklist = {
	id: React.Key;
	name: string;
};

export default function PicklistPicker({field}: {field: Field}) {
	const selectField = field as SingleSelectField | MultiselectField;

	const [picklists, setPicklists] = useState<Picklist[]>([]);
	const [selectedKey, setSelectedKey] = useState<React.Key>(
		selectField.listTypeDefinitionId
	);

	const dispatch = useStateDispatch();
	const publishedFields = useSelector(selectPublishedFields);

	const isPublished = publishedFields.has(field.uuid);

	const id = useId();

	useEffect(() => {
		const getPicklists = async () => {
			const listTypeDefinitions =
				await ListTypeService.getListTypeDefinitions();

			setPicklists(listTypeDefinitions.items);
		};

		getPicklists();
	}, []);

	return (
		<ClayForm.Group className="mb-2">
			<ClayInput.Group className="align-items-end">
				<ClayInput.GroupItem>
					<label htmlFor={id}>
						{Liferay.Language.get('picklist')}

						<ClayIcon
							className="ml-1 reference-mark"
							symbol="asterisk"
						/>
					</label>

					<Picker
						aria-label={sub(
							Liferay.Language.get('select-x'),
							Liferay.Language.get('picklist')
						)}
						disabled={isPublished}
						id={id}
						items={picklists}
						onSelectionChange={(selectedKey: React.Key) => {
							dispatch({
								listTypeDefinitionId: selectedKey as string,
								type: 'update-field',
								uuid: field.uuid,
							});

							setSelectedKey(selectedKey);
						}}
						selectedKey={selectedKey}
					>
						{(item) => <Option key={item.id}>{item.name}</Option>}
					</Picker>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem shrink>
					{selectedKey ? (
						<ClayDropDownWithItems
							items={[
								{
									label: Liferay.Language.get('edit'),
									symbolLeft: 'pencil',
								},
								{type: 'divider'},
								{
									label: Liferay.Language.get('new-picklist'),
									symbolRight: 'shortcut',
								},
							]}
							trigger={
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'more-actions'
									)}
									displayType="secondary"
									symbol="ellipsis-v"
									title={Liferay.Language.get('more-actions')}
								/>
							}
						/>
					) : (
						<ClayButton displayType="secondary">
							{Liferay.Language.get('new-picklist')}

							<ClayIcon className="ml-2" symbol="shortcut" />
						</ClayButton>
					)}
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}
