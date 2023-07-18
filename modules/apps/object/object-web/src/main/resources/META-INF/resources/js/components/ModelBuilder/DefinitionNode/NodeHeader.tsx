/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {sub} from 'frontend-js-web';
import React from 'react';

import './NodeHeader.scss';

interface NodeHeaderProps {
	hasDeleteResourcePermission: boolean;
	hasManagePermissionsResourcePermission: boolean;
	hasObjectDefinitionPublished: boolean;
	isLinkedNode: boolean;
	objectDefinitionLabel: string;
	system: boolean;
}

export default function NodeHeader({
	hasDeleteResourcePermission,
	hasManagePermissionsResourcePermission,
	hasObjectDefinitionPublished,
	isLinkedNode,
	objectDefinitionLabel,
	system,
}: NodeHeaderProps) {
	return (
		<div className="lfr-objects__model-builder-node-header-container">
			<div className="lfr-objects__model-builder-node-header-label-container">
				<div className="lfr-objects__model-builder-node-header-label-title">
					{isLinkedNode && (
						<ClayIcon className="c-pt-1 text-4" symbol="link" />
					)}

					<span>{objectDefinitionLabel}</span>
				</div>

				<DropDown
					alignmentPosition={3}
					trigger={
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('show-actions')}
							displayType="secondary"
							size="sm"
							symbol="ellipsis-v"
						/>
					}
				>
					<DropDown.ItemList>
						<DropDown.Item symbolRight="shortcut">
							{sub(
								Liferay.Language.get('edit-in-x'),
								Liferay.Language.get('page view')
							)}
						</DropDown.Item>

						{hasManagePermissionsResourcePermission && (
							<>
								<hr />
								<DropDown.Item>
									<ClayIcon
										className="c-mr-3 text-4"
										symbol="users"
									/>

									{sub(
										Liferay.Language.get('manage-x'),
										Liferay.Language.get('permissions')
									)}
								</DropDown.Item>
							</>
						)}

						{hasDeleteResourcePermission && (
							<>
								<hr />
								<DropDown.Item>
									<ClayIcon
										className="c-mr-3 text-4"
										symbol="trash"
									/>

									{sub(
										Liferay.Language.get('delete-x'),
										Liferay.Language.get('object')
									)}
								</DropDown.Item>
							</>
						)}
					</DropDown.ItemList>
				</DropDown>
			</div>

			<div>
				<ClayLabel displayType={system ? 'info' : 'warning'}>
					{Liferay.Language.get(system ? 'system' : 'custom')}
				</ClayLabel>

				<ClayLabel
					displayType={
						hasObjectDefinitionPublished ? 'success' : 'info'
					}
				>
					{Liferay.Language.get(
						hasObjectDefinitionPublished ? 'approved' : 'draft'
					)}
				</ClayLabel>
			</div>
		</div>
	);
}
