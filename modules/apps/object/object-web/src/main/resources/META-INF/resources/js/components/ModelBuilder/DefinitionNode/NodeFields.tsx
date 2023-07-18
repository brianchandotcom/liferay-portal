/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import {getBusinessTypeLabel} from '../../../utils/businessTypeLabel';
import {FieldNode} from '../types';

import './NodeFields.scss';

import {getLocalizableLabel} from '@liferay/object-js-components-web';

interface NodeFieldsProps {
	defaultLanguageId: Liferay.Language.Locale;
	objectFields: FieldNode[];
	showAll: boolean;
}

export default function NodeFields({
	defaultLanguageId,
	objectFields,
	showAll,
}: NodeFieldsProps) {
	return (
		<>
			{objectFields.map((objectField, index) => {
				if (index < 5 || showAll) {
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
										objectField.businessType
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
