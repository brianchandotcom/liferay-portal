/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import React from 'react';

import './ObjectDefinitionNodeHeader.scss';
import {DropDownItems} from '../types';

interface ObjectDefinitionNodeHeaderProps {
	dropDownItems: DropDownItems[];
	isLinkedObjectDefinition: boolean;
	objectDefinitionLabel: string;
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	system: boolean;
}

export default function ObjectDefinitionNodeHeader({
	dropDownItems,
	isLinkedObjectDefinition,
	objectDefinitionLabel,
	status,
	system,
}: ObjectDefinitionNodeHeaderProps) {
	return (
		<>
			<div className="lfr-objects__model-builder-node-header-container">
				<div className="lfr-objects__model-builder-node-header-label-container">
					<div className="lfr-objects__model-builder-node-header-label-title">
						{isLinkedObjectDefinition && (
							<ClayIcon className="c-pt-1 text-4" symbol="link" />
						)}

						<span>{objectDefinitionLabel}</span>
					</div>

					<ClayDropDownWithItems
						className="lfr__object-web-view-object-definitions-actions"
						items={dropDownItems}
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'show-actions'
								)}
								displayType="secondary"
								onClick={(event) => {
									event?.stopPropagation();
								}}
								size="sm"
								symbol="ellipsis-v"
							/>
						}
					/>
				</div>

				<div>
					<ClayLabel displayType={system ? 'info' : 'warning'}>
						{Liferay.Language.get(system ? 'system' : 'custom')}
					</ClayLabel>

					<ClayLabel
						displayType={
							status?.label === 'approved' ? 'success' : 'info'
						}
					>
						{Liferay.Language.get(
							status?.label === 'approved' ? 'approved' : 'draft'
						)}
					</ClayLabel>
				</div>
			</div>
		</>
	);
}
