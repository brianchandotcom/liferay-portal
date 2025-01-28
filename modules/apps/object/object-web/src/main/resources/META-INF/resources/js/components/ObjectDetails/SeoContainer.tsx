/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import React from 'react';

interface SeoContainerProps {
	onSubmit?: (editedObjectDefinition?: Partial<ObjectDefinition>) => void;
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function SeoContainer({onSubmit, setValues, values}: SeoContainerProps) {
	const disabled =
		(Liferay.FeatureFlags['LPS-135430'] &&
			values.storageType !== 'default') ||
		(!values.modifiable && values.system);

	return (
		<ClayForm.Group>
			<div className="c-mb-sm-4">
				<Text color="secondary" size={3}>
					{Liferay.Language.get(
						'when-an-object-entry-is-created-a-friendly-url-is-automatically-generated-based-on-the-entry-title-field'
					)}
				</Text>
			</div>

			<ClayCheckbox
				checked={!!values.enableFriendlyURLCustomization}
				disabled={disabled}
				label={Liferay.Language.get(
					'allow-authorized-users-to-customize-entry-friendly-urls'
				)}
				onBlur={(event) => {
					event.stopPropagation();

					if (onSubmit) {
						onSubmit();
					}
				}}
				onChange={({target: {checked}}) => {
					setValues({
						enableFriendlyURLCustomization: checked,
					});
				}}
			/>
		</ClayForm.Group>
	);
}
