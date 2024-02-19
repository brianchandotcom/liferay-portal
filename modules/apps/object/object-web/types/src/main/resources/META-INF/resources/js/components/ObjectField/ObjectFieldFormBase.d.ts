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
	dbObjectFieldRequired?: boolean;
	disabled?: boolean;
	editingObjectField?: boolean;
	errors: ObjectFieldErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	modelBuilder?: boolean;
	objectDefinition?: ObjectDefinition;
	objectField: Partial<ObjectField>;
	objectFieldBusinessTypesInfo: ObjectFieldBusinessType[];
	objectRelationshipId?: number;
	onAggregationFilterChange?: (aggregationFilterArray: []) => void;
	onObjectRelationshipChange?: (
		objectDefinitionExternalReferenceCode2: string
	) => void;
	onSubmit?: (values?: Partial<ObjectField>) => void;
	setDbObjectFieldRequired?: (value: boolean) => void;
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
	dbObjectFieldRequired,
	disabled,
	editingObjectField,
	errors,
	handleChange,
	modelBuilder,
	objectDefinition,
	objectField: values,
	objectFieldBusinessTypesInfo,
	objectRelationshipId,
	onAggregationFilterChange,
	onObjectRelationshipChange,
	onSubmit,
	setDbObjectFieldRequired,
	setValues,
}: ObjectFieldFormBaseProps): JSX.Element;
export {};
