/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {stringUtils} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React from 'react';
import {useStore} from 'react-flow-renderer';

import {getObjectFieldBusinessTypeLabel} from '../../../utils/getObjectFieldBusinessTypeLabel';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

import './ObjectDefinitionNodeFields.scss';

interface ObjectDefinitionNodeFieldsProps {
	defaultLanguageId: Liferay.Language.Locale;
	objectFields: ObjectFieldNodeRow[];
	selectedObjectDefinitionId: number;
	showAllObjectFields: boolean;
}

export function ObjectDefinitionNodeFields({
	defaultLanguageId,
	objectFields,
	selectedObjectDefinitionId,
	showAllObjectFields,
}: ObjectDefinitionNodeFieldsProps) {
	const [_, dispatch] = useObjectFolderContext();

	const store = useStore();

	const handleSelectObjectField = (
		selectedObjectField: ObjectFieldNodeRow
	) => {
		const {edges, nodes} = store.getState();

		dispatch({
			payload: {
				objectDefinitionNodes: nodes,
				objectRelationshipEdges: edges,
				selectedObjectDefinitionId,
				selectedObjectField,
				selectedObjectFieldName: selectedObjectField.name as string,
			},
			type: TYPES.SET_SELECTED_OBJECT_FIELD,
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
							onClick={() => handleSelectObjectField(objectField)}
						>
							<div className="lfr-objects__model-builder-node-field-label">
								<span>
									{stringUtils.getLocalizableLabel(
										defaultLanguageId,
										objectField.label,
										objectField.name
									)}
								</span>
							</div>

							{objectField.businessType && (
								<div className="lfr-objects__model-builder-node-field-business-type">
									<span>
										{getObjectFieldBusinessTypeLabel(
											objectField.businessType
										)}
									</span>
								</div>
							)}
						</div>
					);
				}
			})}
		</>
	);
}
