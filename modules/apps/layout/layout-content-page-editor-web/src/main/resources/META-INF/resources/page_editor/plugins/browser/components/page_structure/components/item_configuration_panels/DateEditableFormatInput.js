/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import classnames from 'classnames';
import React, {useEffect, useState} from 'react';

import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {TextField} from '../../../../../../app/components/fragment_configuration_fields/TextField';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/editableFragmentEntryProcessor';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import {getEditableLocalizedValue} from '../../../../../../app/utils/getEditableLocalizedValue';
import {updateIn} from '../../../../../../app/utils/updateIn';
import CurrentLanguageFlag from '../../../../../../common/components/CurrentLanguageFlag';

const DATE_EDITABLE_FORMAT_OPTIONS = {
	custom: 'custom',
};

const DATE_FORMAT_OPTIONS = [
	{
		label: Liferay.Language.get('default'),
		value: '',
	},
	{
		label: 'MM/DD/YY',
		value: 'MM/dd/yy',
	},
	{
		label: 'DD/MM/YY',
		value: 'dd/MM/yy',
	},
	{
		label: 'YY/MM/DD',
		value: 'yy/MM/dd',
	},
	{
		label: 'DD/MM/YYYY',
		value: 'dd/MM/yyyy',
	},
	{
		label: Liferay.Language.get('custom'),
		value: DATE_EDITABLE_FORMAT_OPTIONS.custom,
	},
];

export default function DateEditableFormatInput({
	editableId,
	editableValueNamespace,
	editableValues,
	fragmentEntryLinkId,
}) {
	const dispatch = useDispatch();

	const languageId = useSelector(selectLanguageId);

	const editableValue = editableValues[editableValueNamespace][editableId];

	const dateFormat = getEditableLocalizedValue(
		editableValue.config?.dateFormat,
		languageId
	);

	const [selectedOption, setSelectedOption] = useState(() =>
		getSelectedOption(dateFormat)
	);
	const [enableCustomInput, setEnableCustomInput] = useState(
		() => selectedOption === DATE_EDITABLE_FORMAT_OPTIONS.custom
	);

	useEffect(() => {
		if (dateFormat) {
			if (
				getSelectedOption(dateFormat) !==
				DATE_EDITABLE_FORMAT_OPTIONS.custom
			) {
				setEnableCustomInput(false);
			}
			else {
				setEnableCustomInput(true);
			}
		}
	}, [languageId, dateFormat]);

	const onValueSelectHandler = (name, value) => {
		setSelectedOption(getSelectedOption(value));
		dispatch(
			updateEditableValues({
				editableValues: updateIn(
					editableValues,
					[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
						editableId,
						'config',
						name,
					],
					(nextValue) => ({...nextValue, [languageId]: value})
				),
				fragmentEntryLinkId,
			})
		);
	};

	return (
		<>
			<div className={classnames('autofit-row align-items-end mb-3')}>
				<div className={classnames('autofit-col autofit-col-expand')}>
					<SelectField
						field={{
							label: Liferay.Language.get('date-format'),
							name: 'dateFormat',
							typeOptions: {
								validValues: DATE_FORMAT_OPTIONS,
							},
						}}
						onValueSelect={(name, value) => {
							if (value === DATE_EDITABLE_FORMAT_OPTIONS.custom) {
								setEnableCustomInput(true);
							}
							else {
								setEnableCustomInput(false);
								onValueSelectHandler(name, value);
							}
						}}
						value={getSelectedOption(dateFormat)}
					/>
				</div>

				<CurrentLanguageFlag />
			</div>
			{enableCustomInput && (
				<>
					<TextField
						field={{
							label: Liferay.Language.get('custom-format'),
							name: 'dateFormat',
						}}
						onValueSelect={onValueSelectHandler}
						value={dateFormat ?? DATE_FORMAT_OPTIONS[0].value}
					/>

					<ClayLink
						className="text-3"
						href="https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html"
						target="_blank"
					>
						{Liferay.Language.get('learn-more-about-date-formats')}
					</ClayLink>
				</>
			)}
		</>
	);

	function getSelectedOption(value) {
		if (!value) {
			return DATE_FORMAT_OPTIONS[0].value;
		}

		const selectedOption = DATE_FORMAT_OPTIONS.find(
			(option) => value === option.value
		);

		return selectedOption?.value ?? DATE_EDITABLE_FORMAT_OPTIONS.custom;
	}
}
