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
	hasObjectDefinitionDeleteResourcePermission: boolean;
	hasObjectDefinitionManagePermissionsResourcePermission: boolean;
	isLinkedNode: boolean;
	objectDefinitionLabel: string;
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	system: boolean;
}

export default function NodeHeader({
	hasObjectDefinitionDeleteResourcePermission,
	hasObjectDefinitionManagePermissionsResourcePermission,
	isLinkedNode,
	objectDefinitionLabel,
	status,
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

						<hr />

						<DropDown.Item>
							<ClayIcon
								className="c-mr-3 text-4"
								symbol="info-circle-open"
							/>

							{Liferay.Language.get('view-details')}
						</DropDown.Item>

						<hr />

						{hasObjectDefinitionManagePermissionsResourcePermission && (
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

						{hasObjectDefinitionDeleteResourcePermission && (
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
						status?.label === 'approved' ? 'success' : 'info'
					}
				>
					{Liferay.Language.get(
						status?.label === 'approved' ? 'approved' : 'draft'
					)}
				</ClayLabel>
			</div>
		</div>
	);
}
