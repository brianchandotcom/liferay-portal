/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	showFragmentConfigurationLink,
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

				{showFragmentConfigurationLink && (
					<div className="mt-4">
						<ClayLink
							href={fragmentConfigurationURL}
							target="_blank"
						>
							{Liferay.Language.get(
								'define-the-default-form-fragments-for-this-site'
							)}
						</ClayLink>
					</div>
				)}
			</div>
		</ClayForm.Group>
	);
}
