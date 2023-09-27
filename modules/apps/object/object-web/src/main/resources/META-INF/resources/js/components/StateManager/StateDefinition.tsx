/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Card,
	CustomItem,
	MultipleSelect,
} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import './StateDefinition.scss';

export default function StateDefinition({
	currentKey,
	disabled,
	index,
	initialValues,
	setValues,
	stateName,
	values,
}: IProps) {
	const [items, setItems] = useState<CustomItem[]>(
		initialValues.map(({checked, key, name}) => {
			return {checked, label: name, value: key};
		})
	);

	useEffect(() => {
		const stateSettings = values.objectFieldSettings?.find(
			({name}: ObjectFieldSetting) => name === 'stateFlow'
		);

		const stateSettingsIndex = values.objectFieldSettings?.indexOf(
			stateSettings!
		);

		const stateSettingsValue = stateSettings!.value as {
			id: number;
			objectStates: ObjectState[];
		};

		const objectStates = stateSettingsValue.objectStates;

		const currentState = objectStates.find(
			(item: ObjectState) => item.key === currentKey
		)!;

		const currentStateIndex = objectStates.indexOf(currentState!);

		const newObjectStateTransitions = items
			.filter((item) => item.checked)
			.map(({value}) => {
				return {key: value!};
			});

		const newObjectStates = [...objectStates];

		newObjectStates[currentStateIndex] = {
			...currentState,
			objectStateTransitions: newObjectStateTransitions,
		};

		stateSettingsValue.objectStates = newObjectStates;

		const newObjectFieldSettings = values.objectFieldSettings;

		newObjectFieldSettings![stateSettingsIndex!].value = stateSettingsValue;

		setValues({
			objectFieldSettings: newObjectFieldSettings,
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items]);

	return (
		<div className="lfr-objects__state-definition-card-state">
			<div className="lfr-objects__state-definition-card-state-name">
				{index === 0 && (
					<label>{Liferay.Language.get('state-name')}</label>
				)}

				<Card title={stateName} viewMode="no-children" />
			</div>

			<div className="lfr-objects__state-definition-card-state-next-status">
				{index === 0 && (
					<label>{Liferay.Language.get('next-status')}</label>
				)}

				<MultipleSelect
					disabled={disabled}
					options={items}
					selectAllOption
					setOptions={setItems}
				/>
			</div>
		</div>
	);
}

interface IOption extends ListTypeEntry {
	checked: boolean;
}

interface IProps {
	currentKey: string;
	disabled: boolean;
	index: number;
	initialValues: IOption[];
	setValues: (values: Partial<ObjectField>) => void;
	stateName: string;
	values: Partial<ObjectField>;
}
