/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import FIELDS_MAPPER from './FieldsWrapperConstants';

const FieldsWrapper = ({
	mode = 'view',
	namespace,
	objectData,
	objectExternalReferenceCode,
	onObjectDataChange,
	onObjectDefinitionLoad,
}: TFieldsWrapperProps) => {
	const [fields, setFields] = useState<TObjectField[]>([]);

	const renderField = useCallback(
		(field: TObjectField) => {
			const genericField = FIELDS_MAPPER[field.businessType];

			if (genericField) {
				const GenericFieldComponent = genericField.component;

				return (
					<GenericFieldComponent
						id={field.id}
						key={`${namespace}_${field.id}`}
						label={
							field.label[Liferay.ThemeDisplay.getLanguageId()] ||
							field.label['en_US']
						}
						mode={mode}
						name={field.name}
						namespace={namespace}
						onChange={({hasError, name, value}) => {
							objectData[name] = value;

							if (onObjectDataChange) {
								onObjectDataChange({
									data: objectData,
									hasError,
									name,
								});
							}
						}}
						originalField={field}
						readOnly={!!field.readOnly && field.readOnly === 'true'}
						required={field.required}
						specificProps={genericField.props}
						value={objectData[field.name]}
					/>
				);
			}
		},
		[mode, namespace, objectData, onObjectDataChange]
	);

	useEffect(() => {
		fetch(
			`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${objectExternalReferenceCode}`
		)
			.then((response) => {
				return response.json() as Promise<TObjectDefinitionResponse>;
			})
			.then((data) => {
				const objectFields = (data.objectFields || [])
					.filter((field) => {
						return !field.system;
					})
					.filter((field) => {
						return FIELDS_MAPPER[field.businessType];
					});

				setFields(objectFields);

				if (onObjectDefinitionLoad) {
					onObjectDefinitionLoad({data: objectFields});
				}
			})
			.catch((error: any) => {
				console.error(error);
			});
	}, [objectExternalReferenceCode, onObjectDefinitionLoad]);

	return fields.length ? (
		<>
			<div className="sheet-subtitle">
				{Liferay.Language.get('custom-fields')}
			</div>

			{fields.map((field) => {
				return renderField(field);
			})}
		</>
	) : (
		<></>
	);
};

export type TGenericFieldProps = {
	onChange({
		hasError,
		name,
		value,
	}: {
		hasError: boolean;
		name: string;
		value: boolean | null | number | string;
	}): void;
	disabled?: boolean;
	id: string | number;
	label: string;
	mode?: string;
	name: string;
	namespace: string;
	originalField: TObjectField;
	readOnly?: boolean;
	required?: boolean;
	specificProps?: any;
	value?: any;
};

type TFieldsWrapperProps = {
	onObjectDataChange?({data, hasError}: TOnObjectDataChange): void;
	onObjectDefinitionLoad?({data}: {data: TObjectField[]}): void;
	mode?: string;
	namespace: string;
	objectData: {[key: string]: any};
	objectExternalReferenceCode: string;
};

export type TOnObjectDataChange = {
	data: any;
	hasError: boolean;
	name: string;
};

type TObjectDefinitionResponse = {
	objectFields: TObjectField[];
};

type TObjectField = {
	businessType: string;
	externalReferenceCode: string;
	id: string;
	label: {
		[key: string]: string;
	};
	listTypeDefinitionId: number;
	localized: boolean;
	name: string;
	objectFieldSettings: [];
	readOnly: string;
	readOnlyConditionExpression: string;
	required: boolean;
	system: boolean;
};

export default FieldsWrapper;
