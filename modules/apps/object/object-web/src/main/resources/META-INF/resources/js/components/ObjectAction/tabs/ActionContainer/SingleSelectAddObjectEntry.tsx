/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import {SingleSelect} from '@liferay/object-js-components-web';
import React from 'react';

import {ActionError} from '../../ObjectActionContainer';
import {ObjectOptionsListItem, ObjectsOptionsList} from '../../fetchUtil';

interface SingleSelectAddObjectEntryProps {
	errors: ActionError;
	objectsOptions: ObjectsOptionsList;
	updateObjectDefinitionParameters: (
		value: ObjectOptionsListItem
	) => Promise<void>;
	values: Partial<ObjectAction>;
}

export function SingleSelectAddObjectEntry({
	errors,
	objectsOptions,
	updateObjectDefinitionParameters,
	values,
}: SingleSelectAddObjectEntryProps) {
	return (
		<>
			on
			<SingleSelect
				aria-label={Liferay.Language.get('choose-an-object')}
				disabled={values.system}
				error={errors.objectDefinitionExternalReferenceCode}
				items={objectsOptions}
				onSelectionChange={(value) => {
					let selectedObjectDefinition:
						| ObjectOptionsListItem
						| undefined = undefined;

					objectsOptions.forEach((objectOption) =>
						objectOption.items.forEach((item) => {
							if (
								item.objectDefinitionExternalReferenceCode ===
								value
							) {
								selectedObjectDefinition = item;
							}
						})
					);

					if (selectedObjectDefinition) {
						updateObjectDefinitionParameters(
							selectedObjectDefinition
						);
					}
				}}
				placeholder={Liferay.Language.get('choose-an-object')}
				selectedKey={
					values.parameters?.objectDefinitionExternalReferenceCode
				}
			>
				{(group) => (
					<DropDown.Group header={group.label} items={group.items}>
						{(item) => (
							<Option
								key={item.objectDefinitionExternalReferenceCode}
							>
								{item.label}
							</Option>
						)}
					</DropDown.Group>
				)}
			</SingleSelect>
		</>
	);
}
