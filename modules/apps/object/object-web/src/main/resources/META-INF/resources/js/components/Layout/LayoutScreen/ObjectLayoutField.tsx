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

import {Panel, PanelSimpleBody} from '@liferay/object-js-components-web';
import React from 'react';

import {TYPES, useLayoutContext} from '../objectLayoutContext';
import {HeaderDropdown} from './HeaderDropdown';
import {RequiredLabel} from './RequiredLabel';

interface ObjectLayoutFieldProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	columnIndex: number;
	objectFieldName: string;
	rowIndex: number;
	tabIndex: number;
}

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

export function ObjectLayoutField({
	boxIndex,
	columnIndex,
	objectFieldName,
	rowIndex,
	tabIndex,
}: ObjectLayoutFieldProps) {
	const [{objectFieldTypes, objectFields}, dispatch] = useLayoutContext();

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
					title={objectField?.label[defaultLanguageId]!}
				>
					<small className="text-secondary">
						{objectFieldType?.label} |{' '}
					</small>

					<RequiredLabel required={objectField?.required} />
				</PanelSimpleBody>
			</Panel>
		</>
	);
}
