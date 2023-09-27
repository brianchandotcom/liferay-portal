/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

import StatusLabel from './StatusLabel';

interface APIApplicationManagementToolbarProps {
	applicationStatusKey: 'published' | 'unpublished';
	hideManagementButtons: boolean;
	managementButtonsProps: ManagementButtonsProps;
	title: string;
}

export function APIApplicationManagementToolbar({
	applicationStatusKey,
	hideManagementButtons,
	managementButtonsProps: buttons,
	title,
}: APIApplicationManagementToolbarProps) {
	return (
		<>
			<ClayManagementToolbar>
				<ClayManagementToolbar.ItemList>
					<div className="ml-sm-2 mr-3">
						<h2 className="mb-0 text-truncate toolbar-title">
							{title}
						</h2>
					</div>

					<StatusLabel statusKey={applicationStatusKey} />
				</ClayManagementToolbar.ItemList>

				{!hideManagementButtons && (
					<ClayManagementToolbar.ItemList>
						<ClayButton.Group key={1} spaced>
							{buttons.cancel.visible && (
								<ClayButton
									borderless={buttons.save.visible}
									displayType="secondary"
									id="apiAppManagementToolbarCancelButton"
									name="cancel"
									onClick={buttons.cancel.onClick}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							)}

							{buttons.save.visible && (
								<ClayButton
									displayType="secondary"
									id="apiAppManagementToolbarSaveButton"
									name="save"
									onClick={buttons.save.onClick}
								>
									{Liferay.Language.get('save')}
								</ClayButton>
							)}

							{buttons.publish.visible && (
								<ClayButton
									displayType="primary"
									id="apiAppManagementToolbarPublishButton"
									name="publish"
									onClick={buttons.publish.onClick}
								>
									{Liferay.Language.get('publish')}
								</ClayButton>
							)}
						</ClayButton.Group>
					</ClayManagementToolbar.ItemList>
				)}
			</ClayManagementToolbar>
		</>
	);
}
