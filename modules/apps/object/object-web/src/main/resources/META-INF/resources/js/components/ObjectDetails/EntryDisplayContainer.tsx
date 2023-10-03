/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FormError,
	SingleSelect,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import React, {useMemo} from 'react';

interface EntryDisplayContainerProps {
	errors: FormError<ObjectDefinition>;
	isLinkedObjectDefinition?: boolean;
	nonRelationshipObjectFieldsInfo: {
		label: LocalizedValue<string>;
		name: string;
	}[];
	objectFields: ObjectField[];
	onSubmit?: (editedObjectDefinition?: Partial<ObjectDefinition>) => void;
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function EntryDisplayContainer({
	errors,
	isLinkedObjectDefinition,
	nonRelationshipObjectFieldsInfo,
	objectFields,
	onSubmit,
	setValues,
	values,
}: EntryDisplayContainerProps) {
	const titleFieldOptions = useMemo(() => {
		return nonRelationshipObjectFieldsInfo?.map(({label, name}) => {
			return {
				label: getLocalizableLabel(
					values.defaultLanguageId as Liferay.Language.Locale,
					label,
					name
				),
				name,
			};
		});
	}, [nonRelationshipObjectFieldsInfo, values.defaultLanguageId]);

	const getEntryTitleObjectFieldValue = () => {
		const titleObjectField = objectFields.find(
			(objectField) => objectField.name === values.titleObjectFieldName
		);

		if (titleFieldOptions) {
			return getLocalizableLabel(
				values.defaultLanguageId as Liferay.Language.Locale,
				titleObjectField?.label,
				titleObjectField?.name
			);
		}

		const idField = objectFields.find((field) => field.name === 'id');

		setValues({titleObjectFieldName: idField?.name});

		return getLocalizableLabel(
			values.defaultLanguageId as Liferay.Language.Locale,
			idField?.label,
			idField?.name
		);
	};

	return (
		<SingleSelect<{label: string; name: string}>
			disabled={isLinkedObjectDefinition}
			error={errors.titleObjectFieldId}
			label={Liferay.Language.get('entry-title-field')}
			onChange={(target: {label: string; name: string}) => {
				const field = objectFields.find(
					({name}) => name === target.name
				);

				setValues({
					titleObjectFieldName: field?.name,
				});

				if (onSubmit) {
					onSubmit({
						...values,
						titleObjectFieldName: field?.name,
					});
				}
			}}
			options={titleFieldOptions}
			value={getEntryTitleObjectFieldValue()}
		/>
	);
}
