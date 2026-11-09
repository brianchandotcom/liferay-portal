/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput, ClaySelect} from '@clayui/form';
import {RequiredMark} from '@liferay/site-cms-site-initializer';
import React from 'react';

import getObjectFields from './getObjectFields';
import {
	IFieldMapping,
	IObjectDefinition,
	TYPE_CATEGORIES,
	TYPE_DYNAMIC_VALUE,
	TYPE_FIXED_VALUE,
	TYPE_TAGS,
} from './types';

interface IProps {
	allowedFieldMappingTypes: string[];
	deletable: boolean;
	fieldMapping: IFieldMapping;
	index: number;
	objectDefinitions: IObjectDefinition[];
	onAdd: () => void;
	onChange: (index: number, fieldMapping: IFieldMapping) => void;
	onDelete: (index: number) => void;
	spritemap: string;
}

export default function FieldMappingRow({
	allowedFieldMappingTypes,
	deletable,
	fieldMapping,
	index,
	objectDefinitions,
	onAdd,
	onChange,
	onDelete,
	spritemap,
}: IProps) {
	const fixedValue = fieldMapping.type === TYPE_FIXED_VALUE;

	const objectFields = getObjectFields(
		objectDefinitions,
		fieldMapping.sourceClassName
	);

	const handleSourceFieldNameChange = (value: string) => {
		const type = [TYPE_CATEGORIES, TYPE_FIXED_VALUE, TYPE_TAGS].includes(
			value
		)
			? value
			: TYPE_DYNAMIC_VALUE;

		onChange(index, {
			...fieldMapping,
			sourceFieldName: type === TYPE_DYNAMIC_VALUE ? value : '',
			type,
			value: type === TYPE_FIXED_VALUE ? fieldMapping.value : '',
		});
	};

	return (
		<>
			<ClayInput.Group className="c-gap-3">
				<ClayInput.GroupItem className="c-gap-1">
					<label>
						{Liferay.Language.get('source')}

						<RequiredMark />
					</label>

					<ClaySelect
						aria-label={Liferay.Language.get('source')}
						onChange={(event) =>
							onChange(index, {
								...fieldMapping,
								sourceClassName: event.target.value,
								sourceFieldName: '',
							})
						}
						value={fieldMapping.sourceClassName}
					>
						<ClaySelect.Option
							label={Liferay.Language.get('all-structures')}
							value=""
						/>

						{objectDefinitions.map((objectDefinition) => (
							<ClaySelect.Option
								key={objectDefinition.className}
								label={objectDefinition.label}
								value={objectDefinition.className}
							/>
						))}
					</ClaySelect>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem className="c-gap-1">
					<div className="align-items-center d-flex flex-fill">
						<label>
							{Liferay.Language.get('source-attribute')}

							<RequiredMark />
						</label>

						<div className="ml-auto">
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('delete-row')}
								className="rounded-circle"
								disabled={!deletable}
								onClick={() => onDelete(index)}
								size="xs"
								spritemap={spritemap}
								symbol="hr"
							/>

							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('add-row')}
								className="ml-1 rounded-circle"
								onClick={onAdd}
								size="xs"
								spritemap={spritemap}
								symbol="plus"
							/>
						</div>
					</div>

					<ClaySelect
						aria-label={Liferay.Language.get('source-attribute')}
						onChange={(event) =>
							handleSourceFieldNameChange(event.target.value)
						}
						value={
							fieldMapping.type === TYPE_DYNAMIC_VALUE
								? fieldMapping.sourceFieldName
								: fieldMapping.type
						}
					>
						<ClaySelect.Option
							label={Liferay.Language.get('not-mapped')}
							value=""
						/>

						{allowedFieldMappingTypes.includes(
							TYPE_DYNAMIC_VALUE
						) &&
							objectFields.map((objectField) => (
								<ClaySelect.Option
									key={objectField.name}
									label={`${objectField.label} (${objectField.name})`}
									value={objectField.name}
								/>
							))}

						{allowedFieldMappingTypes.includes(TYPE_CATEGORIES) && (
							<ClaySelect.Option
								label={Liferay.Language.get('categories')}
								value={TYPE_CATEGORIES}
							/>
						)}

						{allowedFieldMappingTypes.includes(TYPE_TAGS) && (
							<ClaySelect.Option
								label={Liferay.Language.get('tags')}
								value={TYPE_TAGS}
							/>
						)}

						{allowedFieldMappingTypes.includes(
							TYPE_FIXED_VALUE
						) && (
							<ClaySelect.Option
								label={Liferay.Language.get('fixed-value')}
								value={TYPE_FIXED_VALUE}
							/>
						)}
					</ClaySelect>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			{fixedValue && (
				<ClayInput.Group>
					<ClayInput.GroupItem className="c-gap-1">
						<label>
							{Liferay.Language.get('value')}

							<RequiredMark />
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('value')}
							onChange={(event) =>
								onChange(index, {
									...fieldMapping,
									value: event.target.value,
								})
							}
							type="text"
							value={fieldMapping.value}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			)}
		</>
	);
}
