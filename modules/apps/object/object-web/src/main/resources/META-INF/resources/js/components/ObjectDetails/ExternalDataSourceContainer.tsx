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
import React from 'react';

import './ObjectDetails.scss';

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
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('external-data-source')}
			displayType="unstyled"
		>
			<ClayPanel.Body>
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
							(storageType) =>
								storageType.value === values.storageType
						)?.label
					}
				/>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
