/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormError} from '@liferay/object-js-components-web';
import {ChangeEventHandler, ReactNode} from 'react';
import './ObjectFieldFormBase.scss';
interface ObjectFieldFormBaseProps {
	baseResourceURL: string;
	children?: ReactNode;
	className?: string;
	creationLanguageId2?: Liferay.Language.Locale;
	disabled?: boolean;
	editingObjectField?: boolean;
	errors: ObjectFieldErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	modelBuilder?: boolean;
	objectDefinition?: Partial<ObjectDefinition>;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionName: string;
	objectField: Partial<ObjectField>;
	objectFieldTypes: ObjectFieldType[];
	objectRelationshipId?: number;
	onAggregationFilterChange?: (aggregationFilterArray: []) => void;
	onObjectRelationshipChange?: (
		objectDefinitionExternalReferenceCode2: string
	) => void;
	onSubmit?: (values?: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
}
export declare type ObjectFieldErrors = FormError<
	ObjectField &
		{
			[key in ObjectFieldSettingName]: unknown;
		}
>;
export default function ObjectFieldFormBase({
	baseResourceURL,
	children,
	className,
	creationLanguageId2,
	disabled,
	editingObjectField,
	errors,
	handleChange,
	modelBuilder,
	objectDefinition,
	objectDefinitionExternalReferenceCode,
	objectDefinitionName,
	objectField: values,
	objectFieldTypes,
	objectRelationshipId,
	onAggregationFilterChange,
	onObjectRelationshipChange,
	onSubmit,
	setValues,
}: ObjectFieldFormBaseProps): JSX.Element;
export {};
