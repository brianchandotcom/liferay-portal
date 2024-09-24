/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {InputLocalized} from 'frontend-js-components-web';
import {openModal} from 'frontend-js-web';
import React, {useState} from 'react';

import AddDataSourceFieldsModalContent from '../../../components/AddDataSourceFieldsModalContent';
import RequiredMark from '../../../components/RequiredMark';
import ValidationFeedback from '../../../components/ValidationFeedback';
import {IField, IFilter} from '../../../utils/types';

interface IConfigurationProps {
	fieldInUseValidationError: boolean;
	fieldValidationError: boolean;
	fields: IField[];
	filter?: IFilter;
	labelValidationError?: boolean;
	namespace: string;
	onBlur: (event: React.FocusEvent<HTMLInputElement>) => void;
	onChangeField: (selectedField: IField | undefined) => void;
	onChangeLabel: (
		i18nFilterLabels: Partial<Liferay.Language.FullyLocalizedValue<string>>
	) => void;
	selectedField: IField | undefined;
}

function Configuration({
	fieldInUseValidationError,
	fieldValidationError,
	fields,
	filter,
	labelValidationError,
	namespace,
	onBlur,
	onChangeField,
	onChangeLabel,
	selectedField,
}: IConfigurationProps) {
	const fdsFilterLabelTranslations = filter?.label_i18n ?? {};

	const [i18nFilterLabels, setI18nFilterLabels] = useState(
		fdsFilterLabelTranslations
	);

	const nameFormElementId = `${namespace}Name`;
	const selectedFieldFormElementId = `${namespace}SelectedField`;

	const openDataSourceFieldsModal = () => {
		openModal({
			className: 'modal-height-full',
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddDataSourceFieldsModalContent
					closeModal={closeModal}
					fieldTreeItems={fields}
					onSaveButtonClick={({
						selectedFields,
					}: {
						selectedFields: Array<IField>;
					}) => {
						onChangeField(selectedFields[0]);

						closeModal();
					}}
					saveButtonDisabled={false}
					selectedFields={selectedField ? [selectedField] : []}
				/>
			),
			size: 'lg',
		});
	};

	return (
		<>
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

			<ClayForm.Group
				className={classNames({
					'has-error': labelValidationError,
				})}
			>
				<InputLocalized
					id={nameFormElementId}
					label={Liferay.Language.get('name')}
					name="label"
					onBlur={onBlur}
					onChange={(values) => {
						onChangeLabel(values);
						setI18nFilterLabels(values);
					}}
					placeholder={Liferay.Language.get('add-a-name')}
					required={true}
					translations={i18nFilterLabels}
				/>

				{labelValidationError && <ValidationFeedback />}
			</ClayForm.Group>

			<ClayForm.Group
				className={classNames({
					'has-error':
						fieldInUseValidationError || fieldValidationError,
				})}
			>
				<label htmlFor={selectedFieldFormElementId}>
					{Liferay.Language.get('filter-by')}

					<RequiredMark />
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							disabled
							id={selectedFieldFormElementId}
							placeholder={Liferay.Language.get('select')}
							type="text"
							value={
								filter ? filter?.fieldName : selectedField?.name
							}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							disabled={!!filter}
							displayType="secondary"
							onClick={openDataSourceFieldsModal}
							type="submit"
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				{fieldInUseValidationError && (
					<ValidationFeedback
						message={Liferay.Language.get(
							'this-field-is-being-used-by-another-filter'
						)}
					/>
				)}

				{fieldValidationError && <ValidationFeedback />}
			</ClayForm.Group>
		</>
	);
}

export default Configuration;
