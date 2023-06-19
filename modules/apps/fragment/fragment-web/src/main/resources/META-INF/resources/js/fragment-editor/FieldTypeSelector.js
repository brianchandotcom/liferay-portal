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

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React from 'react';

export function FieldTypeSelector({
	availableFieldTypes,
	description,
	fieldTypes,
	fragmentConfigurationURL,
	onChangeFieldTypes,
	portletNamespace,
	readOnly,
	small,
	title,
}) {
	const handleChange = (key, checked) => {
		if (key === 'captcha' && checked) {
			onChangeFieldTypes([key]);

			return;
		}

		const filteredFieldTypes = fieldTypes.filter(
			(fieldTypeKey) => fieldTypeKey !== key
		);

		if (checked) {
			onChangeFieldTypes([...filteredFieldTypes, key]);
		}
		else {
			onChangeFieldTypes(filteredFieldTypes);
		}
	};

	return (
		<ClayForm.Group className={classNames({'form-group-sm': small})}>
			<div className="sheet-section">
				<h2 className="sheet-subtitle">{title}</h2>

				{readOnly ? (
					fieldTypes.length ? (
						fieldTypes.map((fieldType) => {
							const label = availableFieldTypes.find(
								({key}) => key === fieldType
							).label;

							return (
								<p className="mb-1" key={fieldType}>
									{label}
								</p>
							);
						})
					) : (
						<ClayAlert displayType="info">
							{Liferay.Language.get(
								'no-field-type-is-defined-for-this-fragment'
							)}
						</ClayAlert>
					)
				) : (
					<>
						<p>{description}</p>

						{availableFieldTypes.map(({key, label}) => (
							<ClayCheckbox
								aria-label={label}
								checked={fieldTypes.includes(key)}
								disabled={
									fieldTypes.includes('captcha') &&
									key !== 'captcha'
								}
								key={key}
								label={label}
								name={`${portletNamespace}fieldTypes`}
								onChange={(event) =>
									handleChange(key, event.target.checked)
								}
								value={key}
							/>
						))}
					</>
				)}

				<div className="mt-4">
					<ClayLink href={fragmentConfigurationURL}>
						{Liferay.Language.get(
							'define-the-default-form-fragments-for-this-site'
						)}
					</ClayLink>
				</div>
			</div>
		</ClayForm.Group>
	);
}
