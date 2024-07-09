/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {SingleSelect} from '@liferay/object-js-components-web';
import React from 'react';

import {ActionError} from '../../ObjectActionContainer';
import {ObjectOptionsListItem, ObjectsOptionsList} from '../../fetchUtil';

import './SingleSelectAddObjectEntry.scss';

interface SingleSelectAddObjectEntryProps {
	errors: ActionError;
	objectsOptions: ObjectsOptionsList;
	setValues: (values: Partial<ObjectAction>) => void;
	updateParameters: (value: ObjectOptionsListItem) => Promise<void>;
	values: Partial<ObjectAction>;
}

export function SingleSelectAddObjectEntry({
	errors,
	objectsOptions,
	setValues,
	updateParameters,
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
						updateParameters(selectedObjectDefinition);
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
			{values.parameters?.relatedObjectEntries !== undefined && (
				<>
					<ClayCheckbox
						checked={
							values.parameters.relatedObjectEntries === true
						}
						disabled={values.system}
						label={Liferay.Language.get('also-relate-entries')}
						onChange={({target: {checked}}) => {
							setValues({
								parameters: {
									...values.parameters,
									relatedObjectEntries: checked,
								},
							});
						}}
					/>
					<ClayTooltipProvider>
						<div
							data-tooltip-align="top"
							title={Liferay.Language.get(
								'automatically-relate-object-entries-involved-in-the-action'
							)}
						>
							<ClayIcon
								className=".lfr-object__action-builder-tooltip-icon"
								symbol="question-circle-full"
							/>
						</div>
					</ClayTooltipProvider>
				</>
			)}
		</>
	);
}
