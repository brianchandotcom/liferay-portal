/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

					{Liferay.FeatureFlags['LPS-170122'] &&
						(objectField.readOnly === 'true' ||
							objectField.readOnly === 'conditional') && (
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
