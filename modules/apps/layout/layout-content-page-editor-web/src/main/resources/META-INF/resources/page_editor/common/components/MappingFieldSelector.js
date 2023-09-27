/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import {EDITABLE_TYPE_LABELS} from '../../app/config/constants/editableTypeLabels';
import {EDITABLE_TYPES} from '../../app/config/constants/editableTypes';
import getSelectedField from '../../app/utils/getSelectedField';

const UNMAPPED_OPTION = {
	label: `-- ${Liferay.Language.get('unmapped')} --`,
	value: 'unmapped',
};

export default function MappingFieldSelector({
	className,
	fieldType,
	fields,
	label = Liferay.Language.get('field'),
	defaultLabel,
	onValueSelect,
	value,
}) {
	const fieldTypeId = useId();
	const mappingSelectorFieldSelectId = useId();

	const hasWarnings = fields && !fields.length;

	const selectedField = getSelectedField({fields, value});

	return (
		<ClayForm.Group
			className={classNames('mb-2 mt-3', className, {
				'has-warning': hasWarnings,
			})}
			small
		>
			<label htmlFor={mappingSelectorFieldSelectId}>{label}</label>

			<ClaySelect
				aria-describedby={fieldTypeId}
				disabled={!(fields && !!fields.length)}
				id={mappingSelectorFieldSelectId}
				onChange={onValueSelect}
				value={selectedField?.key}
			>
				{fields && !!fields.length && (
					<>
						<ClaySelect.Option
							label={defaultLabel || UNMAPPED_OPTION.label}
							value={UNMAPPED_OPTION.value}
						/>

						{fields.map((fieldSet, index) => {
							const key = `${fieldSet.label || ''}${index}`;

							const Wrapper = ({children, ...props}) =>
								fieldSet.label ? (
									<ClaySelect.OptGroup {...props}>
										{children}
									</ClaySelect.OptGroup>
								) : (
									<React.Fragment key={key}>
										{children}
									</React.Fragment>
								);

							return (
								<Wrapper key={key} label={fieldSet.label}>
									{fieldSet.fields.map((field) => (
										<ClaySelect.Option
											key={field.key}
											label={field.label}
											value={field.key}
										/>
									))}
								</Wrapper>
							);
						})}
					</>
				)}
			</ClaySelect>

			{selectedField && (
				<p className="mt-2 text-3" id={fieldTypeId}>
					<b>{Liferay.Language.get('field-type')}: </b>

					{` ${selectedField.typeLabel}`}
				</p>
			)}

			{hasWarnings && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						{getWarningText(fieldType)}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
}

function getWarningText(fieldType) {
	const fieldLabel = [
		EDITABLE_TYPES.backgroundImage,
		EDITABLE_TYPES.image,
	].includes(fieldType)
		? EDITABLE_TYPE_LABELS[EDITABLE_TYPES.image]
		: EDITABLE_TYPES[fieldType];

	if (fieldLabel) {
		return sub(
			Liferay.Language.get('no-fields-are-available-for-x-editable'),
			fieldLabel
		);
	}

	return Liferay.Language.get('no-fields-are-available-for-this-type');
}
