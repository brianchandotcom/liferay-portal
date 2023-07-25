/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormError, SingleSelect} from '@liferay/object-js-components-web';
import React from 'react';

interface ExternalDataSourceContainerProps {
	errors: FormError<ObjectDefinition>;
	setValues: (values: Partial<ObjectDefinition>) => void;
	storageTypes: LabelValueObject[];
	values: Partial<ObjectDefinition>;
}

export function ExternalDataSourceContainer({
	errors,
	setValues,
	storageTypes,
	values,
}: ExternalDataSourceContainerProps) {
	return (
		<SingleSelect<LabelValueObject>
			disabled={true}
			error={errors.titleObjectFieldId}
			label={Liferay.Language.get('storage-type')}
			onChange={({value}) => {
				setValues({
					storageType: value,
				});
			}}
			options={storageTypes}
			value={
				storageTypes.find(
					(storageType) => storageType.value === values.storageType
				)?.label
			}
		/>
	);
}
