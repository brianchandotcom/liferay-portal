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

import ClayForm, {ClaySelect} from '@clayui/form';
import classNames from 'classnames';
import React from 'react';

import {EDITABLE_TYPES} from '../../app/config/constants/editableTypes';
import getSelectedField from '../../app/utils/getSelectedField';
import {useId} from '../../app/utils/useId';

const UNMAPPED_OPTION = {
	label: `-- ${Liferay.Language.get('unmapped')} --`,
	value: 'unmapped',
};

export default function MappingFieldSelector({
	fieldType,
	fields,
	onValueSelect,
	value,
}) {
	const mappingSelectorFieldSelectId = useId();

	const hasWarnings = fields && !fields.length;

	const selectedField = getSelectedField({fields, value});

	return (
		<ClayForm.Group
			className={classNames('mb-2 mt-3', {'has-warning': hasWarnings})}
			small
		>
			<label htmlFor={mappingSelectorFieldSelectId}>
				{Liferay.Language.get('field')}
			</label>

			<ClaySelect
				disabled={!(fields && !!fields.length)}
				id={mappingSelectorFieldSelectId}
				onChange={onValueSelect}
				value={selectedField?.key}
			>
				{fields && !!fields.length && (
					<>
						<ClaySelect.Option
							label={UNMAPPED_OPTION.label}
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

			{hasWarnings && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						{Liferay.Util.sub(
							Liferay.Language.get(
								'no-fields-are-available-for-x-editable'
							),
							[
								EDITABLE_TYPES.backgroundImage,
								EDITABLE_TYPES.image,
							].includes(fieldType)
								? Liferay.Language.get('image')
								: Liferay.Language.get('text')
						)}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
}
