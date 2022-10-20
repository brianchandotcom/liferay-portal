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

import {
	API,
	Card,
	SidePanelForm,
	saveAndReload,
} from '@liferay/object-js-components-web';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {useObjectFieldForm} from './ObjectField/useObjectFieldForm';
import StateDefinition from './StateManager/StateDefinition';

export default function EditObjectStateField({objectField, readOnly}: IProps) {
	const [pickListItems, setPickListItems] = useState<PickListItem[]>([]);

	useEffect(() => {
		if (objectField?.listTypeDefinitionId) {
			API.getPickListItems(objectField.listTypeDefinitionId).then(
				setPickListItems
			);
		}
	}, [objectField.listTypeDefinitionId, setPickListItems]);

	const isStateOptionChecked = ({
		currentKey,
		pickListItemKey,
	}: {
		currentKey: string;
		pickListItemKey: string;
	}) => {
		const stateSettings = objectField.objectFieldSettings!.find(
			({name}: ObjectFieldSetting) => name === 'stateFlow'
		);

		const stateSettingsValue = stateSettings!.value as {
			id: number;
			objectStates: ObjectState[];
		};

		const objectStates = stateSettingsValue.objectStates;

		const currentState = objectStates.find(
			(item: ObjectState) => item.key === currentKey
		);

		return (
			currentState!.objectStateTransitions.find(
				({key}: {key: string}) => key === pickListItemKey
			) !== undefined
		);
	};

	const onSubmit = async ({id, ...objectField}: ObjectField) => {
		if (Liferay.FeatureFlags['LPS-164278']) {
			delete objectField.listTypeDefinitionId;
		}

		delete objectField.system;

		try {
			await API.save(
				`/o/object-admin/v1.0/object-fields/${id}`,
				objectField
			);

			saveAndReload();
			openToast({
				message: Liferay.Language.get(
					'the-object-field-was-updated-successfully'
				),
			});
		}
		catch (error) {
			openToast({message: (error as Error).message, type: 'danger'});
		}
	};

	const {handleSubmit, setValues, values} = useObjectFieldForm({
		initialValues: objectField,
		onSubmit,
	});

	return (
		<SidePanelForm
			className="lfr-objects__edit-object-state-field"
			onSubmit={handleSubmit}
			readOnly={readOnly}
			title={`${
				objectField.label[Liferay.ThemeDisplay.getDefaultLanguageId()]
			} ${Liferay.Language.get('settings')}`}
		>
			<Card title={Liferay.Language.get('select-the-state-flow')}>
				{pickListItems?.map(({key, name}, index) => (
					<StateDefinition
						currentKey={key}
						disabled={readOnly}
						index={index}
						initialValues={pickListItems
							.filter((item) => item.name !== name)
							.map((item) => {
								return {
									...item,
									checked: isStateOptionChecked({
										currentKey: key,
										pickListItemKey: item.key,
									}),
								};
							})}
						key={index}
						setValues={setValues}
						stateName={name}
						values={values}
					/>
				))}
			</Card>
		</SidePanelForm>
	);
}

interface IProps {
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	isApproved: boolean;
	objectField: ObjectField;
	objectName: string;
	readOnly: boolean;
}
