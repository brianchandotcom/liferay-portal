/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {InputLocalized} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {IField, IFilter} from '../utils/types';
import RequiredMark from './RequiredMark';
import ValidationFeedback from './ValidationFeedback';

interface IFilterModalConfigurationProps {
	fieldNames?: string[];
	fields: IField[];
	filter?: IFilter;
	namespace: string;
	onChange: ({
		fieldInUseValidationError,
		i18nFilterLabels,
		selectedField,
	}: {
		fieldInUseValidationError: boolean;
		i18nFilterLabels: any;
		selectedField: IField | undefined;
	}) => void;
}

function FilterModalConfiguration({
	fieldNames,
	fields,
	filter,
	namespace,
	onChange,
}: IFilterModalConfigurationProps) {
	const [fieldInUseValidationError, setFieldInUseValidationError] = useState<
		boolean
	>(false);

	const [selectedField, setSelectedField] = useState<IField | undefined>(
		fields.find((item) => item.name === filter?.fieldName)
	);
	const fdsFilterLabelTranslations = filter?.label_i18n ?? {};

	const [i18nFilterLabels, setI18nFilterLabels] = useState(
		fdsFilterLabelTranslations
	);

	const inUseFields: (string | undefined)[] = fields.map((item) =>
		fieldNames?.includes(item.name) ? item.name : undefined
	);

	const nameFormElementId = `${namespace}Name`;
	const selectedFieldFormElementId = `${namespace}SelectedField`;

	const FieldNameDropdown = ({
		fields,
		onItemClick,
	}: {
		fields: IField[];
		onItemClick: Function;
	}) => {
		return (
			<ClayDropDown
				closeOnClick
				menuElementAttrs={{
					className: 'fds-field-name-dropdown-menu',
				}}
				trigger={
					<ClayButton
						className="form-control form-control-select form-control-select-secondary"
						displayType="secondary"
						id={selectedFieldFormElementId}
					>
						{selectedField
							? selectedField.label
							: Liferay.Language.get('select')}
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList items={fields} role="listbox">
					{fields.map((field) => (
						<ClayDropDown.Item
							className="align-items-center d-flex justify-content-between"
							disabled={!!filter}
							key={field.name}
							onClick={() => onItemClick(field)}
							roleItem="option"
						>
							{field.label}

							{inUseFields.includes(field.name) && (
								<ClayLabel displayType="info">
									{Liferay.Language.get('in-use')}
								</ClayLabel>
							)}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		);
	};

	return (
		<>
			{Liferay.FeatureFlags['LPD-10754'] && (
				<ClayLayout.SheetSection className="mb-4">
					<h3 className="sheet-subtitle">
						{Liferay.Language.get('configuration')}
					</h3>

					<ClayForm.Text>
						{Liferay.Language.get(
							'add-a-name-for-your-filter-and-select-a-field-to-start-creating-it'
						)}
					</ClayForm.Text>
				</ClayLayout.SheetSection>
			)}

			<ClayForm.Group>
				<InputLocalized
					id={nameFormElementId}
					label={Liferay.Language.get('name')}
					name="label"
					onChange={(values) => {
						onChange({
							fieldInUseValidationError,
							i18nFilterLabels: values,
							selectedField,
						});
						setI18nFilterLabels(values);
					}}
					placeholder={Liferay.Language.get('add-a-name')}
					required={Liferay.FeatureFlags['LPD-10754']}
					translations={i18nFilterLabels}
				/>
			</ClayForm.Group>

			<ClayForm.Group
				className={classNames({
					'has-error': fieldInUseValidationError,
				})}
			>
				<label htmlFor={selectedFieldFormElementId}>
					{Liferay.Language.get('filter-by')}

					{Liferay.FeatureFlags['LPD-10754'] && <RequiredMark />}
				</label>

				<FieldNameDropdown
					fields={fields}
					onItemClick={(item: IField) => {
						const newVal = fields.find((field) => {
							return field.label === item.label;
						});

						if (newVal) {
							setSelectedField(newVal);

							const isInUseField = inUseFields.includes(
								newVal.name
							);
							setFieldInUseValidationError(isInUseField);

							onChange({
								fieldInUseValidationError: isInUseField,
								i18nFilterLabels,
								selectedField: newVal,
							});
						}
					}}
				/>

				{fieldInUseValidationError && (
					<ValidationFeedback
						message={Liferay.Language.get(
							'this-field-is-being-used-by-another-filter'
						)}
					/>
				)}
			</ClayForm.Group>
		</>
	);
}

export default FilterModalConfiguration;
