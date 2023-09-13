/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React from 'react';
import {useStore} from 'react-flow-renderer';

import {getBusinessTypeLabel} from '../../../utils/businessTypeLabel';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

import './ObjectDefinitionNodeObjectFields.scss';

interface ObjectDefinitionNodeFieldsProps {
	defaultLanguageId: Liferay.Language.Locale;
	objectFields: ObjectFieldNode[];
	selectedObjectDefinitionId: number;
	showAllObjectFields: boolean;
}

export default function ObjectDefinitionNodeFields({
	defaultLanguageId,
	objectFields,
	selectedObjectDefinitionId,
	showAllObjectFields,
}: ObjectDefinitionNodeFieldsProps) {
	const store = useStore();
	const [_, dispatch] = useObjectFolderContext();

	const handleClickDetails = (
		selectedObjectDefinitionField: ObjectFieldNode
	) => {
		const {edges, nodes} = store.getState();

		dispatch({
			payload: {
				edges,
				nodes,
				selectedFieldDefinitionName: selectedObjectDefinitionField.name as string,
				selectedObjectDefinitionField,
				selectedObjectDefinitionId,
			},
			type: TYPES.SET_SELECTED_FIELD,
		});
	};

	return (
		<>
			{objectFields.map((objectField, index) => {
				if (index < 5 || showAllObjectFields) {
					return (
						<div
							className={classNames(
								'lfr-objects__model-builder-node-field',
								{
									'lfr-objects__model-builder-node-field--selected':
										objectField.selected,
								}
							)}
							key={objectField.name}
							onClick={() => handleClickDetails(objectField)}
						>
							<div className="lfr-objects__model-builder-node-field-label">
								<span>
									{getLocalizableLabel(
										defaultLanguageId,
										objectField.label,
										objectField.name
									)}
								</span>
							</div>

							<div className="lfr-objects__model-builder-node-field-business-type">
								<span>
									{getBusinessTypeLabel(
										objectField.businessType as string
									)}
								</span>
							</div>
						</div>
					);
				}
			})}
		</>
	);
}
