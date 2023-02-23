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

import ClayLabel from '@clayui/label';
import {
	Panel,
	PanelSimpleBody,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import React from 'react';

import {TYPES, useLayoutContext} from '../objectLayoutContext';
import {HeaderDropdown} from './HeaderDropdown';

interface ObjectLayoutFieldProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	columnIndex: number;
	objectFieldName: string;
	rowIndex: number;
	tabIndex: number;
}

export function ObjectLayoutField({
	boxIndex,
	columnIndex,
	objectFieldName,
	rowIndex,
	tabIndex,
}: ObjectLayoutFieldProps) {
	const [
		{creationLanguageId, objectFieldTypes, objectFields},
		dispatch,
	] = useLayoutContext();

	const objectField = objectFields.find(
		({name}) => name === objectFieldName
	)!;

	const objectFieldType = objectFieldTypes.find(
		({businessType}) => businessType === objectField.businessType
	);

	return (
		<>
			<Panel key={`field_${objectFieldName}`}>
				<PanelSimpleBody
					contentRight={
						<HeaderDropdown
							deleteElement={() => {
								dispatch({
									payload: {
										boxIndex,
										columnIndex,
										objectFieldName,
										rowIndex,
										tabIndex,
									},
									type: TYPES.DELETE_OBJECT_LAYOUT_FIELD,
								});
							}}
						/>
					}
					title={getLocalizableLabel(
						creationLanguageId,
						objectField.label,
						objectField.name
					)}
				>
					<small className="text-secondary">
						{objectFieldType?.label} |{' '}
					</small>

					<ClayLabel
						className="label-inside-custom-select"
						displayType={
							objectField?.required ? 'warning' : 'success'
						}
					>
						{objectField?.required
							? Liferay.Language.get('mandatory')
							: Liferay.Language.get('optional')}
					</ClayLabel>

					{Liferay.FeatureFlags['LPS-159913'] &&
						objectField.objectFieldSettings?.find(
							(fieldSetting: ObjectFieldSetting) =>
								fieldSetting.value === 'true' ||
								fieldSetting.value === 'conditional'
						) && (
							<ClayLabel
								className="label-inside-custom-select"
								displayType="secondary"
							>
								{Liferay.Language.get('read-only')}
							</ClayLabel>
						)}
				</PanelSimpleBody>
			</Panel>
		</>
	);
}
