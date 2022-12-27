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

import ClayPanel from '@clayui/panel';
import {FormError, SingleSelect} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';

interface EntryDisplayContainerProps {
	errors: FormError<ObjectDefinition>;
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
	nonRelationshipObjectFieldsInfo,
	objectFields,
	setValues,
	values,
}: EntryDisplayContainerProps) {
	const [selectedObjectField, setSelectedObjectField] = useState<
		ObjectField
	>();

	const titleFieldOptions = useMemo(() => {
		return nonRelationshipObjectFieldsInfo.map(({label, name}) => {
			return {label: label[defaultLanguageId] ?? '', name};
		});
	}, [nonRelationshipObjectFieldsInfo]);

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
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('entry-display')}
			displayType="unstyled"
		>
			<ClayPanel.Body>
				<SingleSelect<{label: string; name: string}>
					error={errors.titleObjectFieldId}
					label={Liferay.Language.get('title-object-field-id')}
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
					value={selectedObjectField?.label[defaultLanguageId]}
				/>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
