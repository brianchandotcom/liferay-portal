/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	Card,
	SidePanelForm,
	saveAndReload,
} from '@liferay/object-js-components-web';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../utils/constants';
import {useObjectFieldForm} from './ObjectField/useObjectFieldForm';
import StateDefinition from './StateManager/StateDefinition';

export default function EditObjectStateField({objectField, readOnly}: IProps) {
	const [listTypeEntries, setListTypeEntries] = useState<ListTypeEntry[]>([]);

	useEffect(() => {
		if (objectField?.listTypeDefinitionId) {
			API.getListTypeDefinitionListTypeEntries(
				objectField.listTypeDefinitionId
			).then(setListTypeEntries);
		}
	}, [
		objectField.listTypeDefinitionId,
		objectField.listTypeDefinitionExternalReferenceCode,
		setListTypeEntries,
	]);

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
		delete objectField.listTypeDefinitionId;
		delete objectField.system;

		try {
			await API.save({
				item: objectField,
				url: `/o/object-admin/v1.0/object-fields/${id}`,
			});

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
				objectField.label[defaultLanguageId]
			} ${Liferay.Language.get('settings')}`}
		>
			<Card title={Liferay.Language.get('select-the-state-flow')}>
				{listTypeEntries?.map(({key, name}, index) => (
					<StateDefinition
						currentKey={key}
						disabled={readOnly}
						index={index}
						initialValues={listTypeEntries
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
