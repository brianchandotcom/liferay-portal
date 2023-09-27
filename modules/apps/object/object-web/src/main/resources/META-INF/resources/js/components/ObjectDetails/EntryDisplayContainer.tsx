/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FormError,
	SingleSelect,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

interface EntryDisplayContainerProps {
	errors: FormError<ObjectDefinition>;
	isLinkedObjectDefinition?: boolean;
	nonRelationshipObjectFieldsInfo: {
		label: LocalizedValue<string>;
		name: string;
	}[];
	objectFields: ObjectField[];
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function EntryDisplayContainer({
	errors,
	isLinkedObjectDefinition,
	nonRelationshipObjectFieldsInfo,
	objectFields,
	setValues,
	values,
}: EntryDisplayContainerProps) {
	const [selectedObjectField, setSelectedObjectField] = useState<
		ObjectField
	>();

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

	useEffect(() => {
		if (values.titleObjectFieldName) {
			const titleObjectField = objectFields.find(
				(objectField) =>
					objectField.name === values.titleObjectFieldName
			);

			setSelectedObjectField(titleObjectField);

			return;
		}

		const idField = objectFields.find((field) => field.name === 'id');

		setValues({titleObjectFieldName: idField?.name});
		setSelectedObjectField(idField);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.titleObjectFieldName, objectFields]);

	return (
		<SingleSelect<{label: string; name: string}>
			disabled={isLinkedObjectDefinition}
			error={errors.titleObjectFieldId}
			label={Liferay.Language.get('entry-title-field')}
			onChange={(target: {label: string; name: string}) => {
				const field = objectFields.find(
					({name}) => name === target.name
				);

				setSelectedObjectField(field);

				setValues({
					titleObjectFieldName: field?.name,
				});
			}}
			options={titleFieldOptions}
			value={getLocalizableLabel(
				values.defaultLanguageId as Liferay.Language.Locale,
				selectedObjectField?.label,
				selectedObjectField?.name
			)}
		/>
	);
}
