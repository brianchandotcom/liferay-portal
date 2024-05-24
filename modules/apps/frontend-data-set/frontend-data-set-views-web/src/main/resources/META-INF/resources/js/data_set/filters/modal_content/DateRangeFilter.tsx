/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {format, getYear, isBefore, isEqual} from 'date-fns';
import React, {useState} from 'react';

import FilterModalConfiguration from '../../../components/FilterModalConfiguration';
import FilterModalFooter from '../../../components/FilterModalFooter';
import {IDateFilter, IField, IFilter} from '../../../utils/types';

function Header() {
	return <>{Liferay.Language.get('new-date-range-filter')}</>;
}

interface IBodyProps {
	closeModal: Function;
	fieldNames?: string[];
	fields: IField[];
	filter?: IFilter;
	handleSave: Function;
	namespace: string;
}

function Body({
	closeModal,
	fieldNames,
	fields,
	filter,
	handleSave,
	namespace,
}: IBodyProps) {
	const [fieldInUseValidationError, setFieldInUseValidationError] = useState<
		boolean
	>(false);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState<boolean>(
		filter ? false : true
	);
	const fdsFilterLabelTranslations = filter?.label_i18n ?? {};
	const [i18nFilterLabels, setI18nFilterLabels] = useState(
		fdsFilterLabelTranslations
	);

	const inUseFields: (string | undefined)[] = fields.map((item) =>
		fieldNames?.includes(item.name) ? item.name : undefined
	);

	const [selectedField, setSelectedField] = useState<IField | undefined>(
		fields.find((item) => item.name === filter?.fieldName)
	);
	const [from, setFrom] = useState<string>(
		(filter as IDateFilter)?.from ?? ''
	);
	const [to, setTo] = useState<string>((filter as IDateFilter)?.to ?? '');
	const [isValidDateRange, setIsValidDateRange] = useState<boolean>(true);

	const fromFormElementId = `${namespace}From`;
	const toFormElementId = `${namespace}To`;

	const handleFilterSave = () => {
		const body = {
			fieldName: selectedField?.name,
			from,
			label_i18n: i18nFilterLabels,
			to,
			type: selectedField?.format,
		};

		handleSave(body);
	};

	const isFormInvalid = ({
		from,
		i18nFilterLabels,
		selectedField,
		to,
	}: {
		from: string;
		i18nFilterLabels: Partial<Liferay.Language.FullyLocalizedValue<string>>;
		selectedField: IField | undefined;
		to: string;
	}) => {
		if (!selectedField) {
			return true;
		}

		if (selectedField && !filter) {
			if (inUseFields.includes(selectedField.name)) {
				return true;
			}
		}

		if (!i18nFilterLabels || !Object.values(i18nFilterLabels).length) {
			return true;
		}
		else {
			let isI18nFilterLabelInvalid = false;

			Object.values(i18nFilterLabels).forEach((value) => {
				if (!value) {
					isI18nFilterLabelInvalid = true;
				}
			});

			if (isI18nFilterLabelInvalid) {
				return true;
			}
		}

		const dateTo = new Date(to);

		const dateFrom = new Date(from);

		if (to && from) {
			return !(isBefore(dateFrom, dateTo) || isEqual(dateFrom, dateTo));
		}

		return true;
	};

	return (
		<>
			<ClayModal.Body>
				<FilterModalConfiguration
					fieldNames={fieldNames}
					fields={fields}
					filter={filter}
					namespace={namespace}
					onChange={({
						fieldInUseValidationError,
						i18nFilterLabels,
						selectedField,
					}) => {
						setI18nFilterLabels(i18nFilterLabels);
						setSelectedField(selectedField);
						setFieldInUseValidationError(fieldInUseValidationError);

						setSaveButtonDisabled(
							isFormInvalid({
								from,
								i18nFilterLabels,
								selectedField,
								to,
							})
						);
					}}
				/>

				{!fieldInUseValidationError && (
					<ClayForm.Group className="form-group-autofit">
						<div
							className={classNames('form-group-item', {
								'has-error': !isValidDateRange,
							})}
						>
							<label htmlFor={fromFormElementId}>
								{Liferay.Language.get('from')}
							</label>

							<ClayDatePicker
								inputName={fromFormElementId}
								onChange={(value: any) => {
									setFrom(value);

									const isInvalid = isFormInvalid({
										from: value,
										i18nFilterLabels,
										selectedField,
										to,
									});

									setIsValidDateRange(!isInvalid);
									setSaveButtonDisabled(isInvalid);
								}}
								placeholder="YYYY-MM-DD"
								value={
									from
										? format(new Date(from), 'yyyy-MM-dd')
										: ''
								}
								years={{
									end: getYear(new Date()) + 25,
									start: getYear(new Date()) - 50,
								}}
							/>

							{!isValidDateRange && (
								<ClayForm.FeedbackGroup>
									<ClayForm.FeedbackItem>
										<ClayForm.FeedbackIndicator symbol="exclamation-full" />

										{Liferay.Language.get(
											'date-range-is-invalid.-from-must-be-before-to'
										)}
									</ClayForm.FeedbackItem>
								</ClayForm.FeedbackGroup>
							)}
						</div>

						<div className="form-group-item">
							<label htmlFor={toFormElementId}>
								{Liferay.Language.get('to')}
							</label>

							<ClayDatePicker
								inputName={toFormElementId}
								onChange={(value: any) => {
									setTo(value);

									const isInvalid = isFormInvalid({
										from,
										i18nFilterLabels,
										selectedField,
										to: value,
									});

									setIsValidDateRange(!isInvalid);
									setSaveButtonDisabled(isInvalid);
								}}
								placeholder="YYYY-MM-DD"
								value={
									to ? format(new Date(to), 'yyyy-MM-dd') : ''
								}
								years={{
									end: getYear(new Date()) + 25,
									start: getYear(new Date()) - 50,
								}}
							/>
						</div>
					</ClayForm.Group>
				)}
			</ClayModal.Body>

			<FilterModalFooter
				closeModal={closeModal}
				handleSave={handleFilterSave}
				saveButtonDisabled={saveButtonDisabled}
			/>
		</>
	);
}

export default {
	Body,
	Header,
};
