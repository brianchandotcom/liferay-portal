/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
import {AggregationFilters} from './BasicInfoTab';
import '../../EditObjectFieldContent.scss';
interface BasicInfoContainerProps {
	baseResourceURL: string;
	creationLanguageId2?: Liferay.Language.Locale;
	dbObjectFieldRequired?: boolean;
	errors: ObjectFieldErrors;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	modelBuilder?: boolean;
	objectDefinition?: ObjectDefinition;
	objectFieldBusinessTypes: ObjectFieldBusinessType[];
	objectRelationshipId: number;
	onSubmit?: () => void;
	readOnly: boolean;
	setAggregationFilters: (values: AggregationFilters[]) => void;
	setDbObjectFieldRequired?: (value: boolean) => void;
	setObjectDefinitionExternalReferenceCode2: (value: string) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}
export declare function BasicInfoContainer({
	baseResourceURL,
	creationLanguageId2,
	dbObjectFieldRequired,
	errors,
	handleChange,
	modelBuilder,
	objectDefinition,
	objectFieldBusinessTypes,
	objectRelationshipId,
	onSubmit,
	readOnly,
	setAggregationFilters,
	setDbObjectFieldRequired,
	setObjectDefinitionExternalReferenceCode2,
	setValues,
	values,
}: BasicInfoContainerProps): JSX.Element;
export {};
