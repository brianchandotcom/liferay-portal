/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FormError,
	Input,
	InputLocalized,
	Toggle,
} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
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
		<>
			<Input
				disabled={isApproved || !hasUpdateObjectDefinitionPermission}
				error={errors.name}
				label={Liferay.Language.get('name')}
				name="name"
				onChange={handleChange}
				required
				value={values.name}
			/>

			<InputLocalized
				disabled={isReadOnly || !hasUpdateObjectDefinitionPermission}
				error={errors.label}
				label={Liferay.Language.get('label')}
				onChange={(label) => setValues({label})}
				onSelectedLocaleChange={setSelectedLocale}
				required
				selectedLocale={selectedLocale}
				translations={values.label as LocalizedValue<string>}
			/>

			<InputLocalized
				disabled={isReadOnly || !hasUpdateObjectDefinitionPermission}
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

			<Toggle
				disabled={
					!isApproved ||
					isReadOnly ||
					!hasUpdateObjectDefinitionPermission
				}
				label={sub(
					Liferay.Language.get('activate-x'),
					Liferay.Language.get('object')
				)}
				name="active"
				onToggle={() => setValues({active: !values.active})}
				toggled={values.active}
			/>
		</>
	);
}
