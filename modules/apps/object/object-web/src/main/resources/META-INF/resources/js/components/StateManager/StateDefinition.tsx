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

interface IOption extends PickListItem {
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
