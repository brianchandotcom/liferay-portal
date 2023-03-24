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

import {ClayToggle} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import {
	FormError,
	Input,
	InputLocalized,
} from '@liferay/object-js-components-web';
import React, {ChangeEventHandler, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';

interface ObjectDataContainerProps {
	dbTableName: string;
	errors: FormError<ObjectDefinition>;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function ObjectDataContainer({
	dbTableName,
	errors,
	handleChange,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	setValues,
	values,
}: ObjectDataContainerProps) {
	const [selectedLocale, setSelectedLocale] = useState<
		Liferay.Language.Locale
	>(defaultLanguageId);

	const isReadOnly = Liferay.FeatureFlags['LPS-167253']
		? !values.modifiable && values.system
		: values.system;

	return (
		<ClayPanel
			displayTitle={Liferay.Language.get('object-definition-data')}
			displayType="unstyled"
		>
			<ClayPanel.Body>
				<Input
					disabled={
						isApproved || !hasUpdateObjectDefinitionPermission
					}
					error={errors.name}
					label={Liferay.Language.get('name')}
					name="name"
					onChange={handleChange}
					required
					value={values.name}
				/>

				<InputLocalized
					disabled={
						isReadOnly || !hasUpdateObjectDefinitionPermission
					}
					error={errors.label}
					label={Liferay.Language.get('label')}
					onChange={(label) => setValues({label})}
					onSelectedLocaleChange={setSelectedLocale}
					required
					selectedLocale={selectedLocale}
					translations={values.label as LocalizedValue<string>}
				/>

				<InputLocalized
					disabled={
						isReadOnly || !hasUpdateObjectDefinitionPermission
					}
					error={errors.pluralLabel}
					label={Liferay.Language.get('plural-label')}
					onChange={(pluralLabel) => setValues({pluralLabel})}
					onSelectedLocaleChange={setSelectedLocale}
					required
					selectedLocale={selectedLocale}
					translations={values.pluralLabel as LocalizedValue<string>}
				/>

				<Input
					disabled
					label={Liferay.Language.get('object-definition-table-name')}
					name="name"
					value={dbTableName}
				/>

				<ClayToggle
					disabled={
						!isApproved ||
						isReadOnly ||
						!hasUpdateObjectDefinitionPermission
					}
					label={Liferay.Language.get('active')}
					name="active"
					onToggle={() => setValues({active: !values.active})}
					toggled={values.active}
				/>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
