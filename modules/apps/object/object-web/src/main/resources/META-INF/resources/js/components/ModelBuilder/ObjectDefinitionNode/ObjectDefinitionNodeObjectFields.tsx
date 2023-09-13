/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import {getBusinessTypeLabel} from '../../../utils/businessTypeLabel';

import './ObjectDefinitionNodeObjectFields.scss';

interface ObjectDefinitionNodeFieldsProps {
	defaultLanguageId: Liferay.Language.Locale;
	objectFields: ObjectFieldNode[];
	showAllObjectFields: boolean;
}

export default function ObjectDefinitionNodeFields({
	objectFields,
	showAllObjectFields,
}: ObjectDefinitionNodeFieldsProps) {
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
						>
							<div className="lfr-objects__model-builder-node-field-label">
								<span>{objectField.label}</span>
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
