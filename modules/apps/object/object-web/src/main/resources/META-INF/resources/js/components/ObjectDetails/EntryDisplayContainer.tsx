/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FormError,
	SingleSelect,
	stringUtils,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo} from 'react';

interface EntryDisplayContainerProps {
	className?: string;
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
	className,
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
				label: stringUtils.getLocalizableLabel(
					values.defaultLanguageId as Liferay.Language.Locale,
					label,
					name
				),
				value: name,
			};
		});
	}, [nonRelationshipObjectFieldsInfo, values.defaultLanguageId]);

	useEffect(() => {
		const titleObjectField = objectFields.find(
			(objectField) => objectField.name === values.titleObjectFieldName
		);

		if (!titleObjectField) {
			const idField = objectFields.find((field) => field.name === 'id');

			setValues({titleObjectFieldName: idField?.name});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<SingleSelect<LabelValueObject>
			className={className}
			disabled={isLinkedObjectDefinition}
			error={errors.titleObjectFieldId}
			id="lfr-objects__object-display-container-entry-title-field"
			items={titleFieldOptions}
			label={Liferay.Language.get('entry-title-field')}
			onSelectionChange={(itemKey) => {
				const field = objectFields.find(({name}) => name === itemKey);

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
			selectedKey={values.titleObjectFieldName}
		/>
	);
}
