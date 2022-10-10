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

import React, {useState} from 'react';

import {SelectField} from '../../../../../../app/components/fragment-configuration-fields/SelectField';
import {TextField} from '../../../../../../app/components/fragment-configuration-fields/TextField';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/editableFragmentEntryProcessor';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import {setIn} from '../../../../../../app/utils/setIn';

const DATE_EDITABLE_FORMAT_OPTIONS = {
	custom: 'custom',
};

const DATE_FORMAT_OPTIONS = [
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
	const editableValue = editableValues[editableValueNamespace][editableId];
	const languageId = useSelector(selectLanguageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const [selectedOption, setSelectedOption] = useState(() =>
		!editableValue.config.dateFormat
			? DATE_FORMAT_OPTIONS[0].value
			: getSelectedOption(editableValue.config.dateFormat)
	);
	const [enableCustomInput, setEnableCustomInput] = useState(
		() => selectedOption === DATE_EDITABLE_FORMAT_OPTIONS.custom
	);

	const onValueSelectHandler = (name, value) => {
		setSelectedOption(getSelectedOption(value));
		dispatch(
			updateEditableValues({
				editableValues: setIn(
					editableValues,
					[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
						editableId,
						'config',
						name,
					],
					value
				),
				fragmentEntryLinkId,
				languageId,
				segmentsExperienceId,
			})
		);
	};

	return (
		<>
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
				value={selectedOption}
			/>
			{enableCustomInput && (
				<TextField
					field={{
						label: Liferay.Language.get('custom-format'),
						name: 'dateFormat',
					}}
					onValueSelect={onValueSelectHandler}
					value={editableValue.config.dateFormat}
				/>
			)}
		</>
	);

	function getSelectedOption(value) {
		const selectedOption = DATE_FORMAT_OPTIONS.find(
			(option) => value === option.value
		);

		return selectedOption?.value ?? DATE_EDITABLE_FORMAT_OPTIONS.custom;
	}
}
