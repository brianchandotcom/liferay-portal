/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 r*
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {openModal} from 'frontend-js-web';
import React, {useMemo} from 'react';

import StatusLabel from './StatusLabel';
import {CancelEditAPIApplicationModalContent} from './modals/CancelEditAPIApplicationModalContent';

interface APIApplicationManagementToolbarProps {
	itemData: Partial<ItemData>;
	onSave: voidReturn;
	title: string;
}

export function APIApplicationManagementToolbar({
	itemData,
	onSave,
	title,
}: APIApplicationManagementToolbarProps) {
	const initialFieldData = useMemo(
		() => ({
			baseURL: itemData.baseURL,
			description: itemData.description,
			title: itemData.title,
		}),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[title]
	);

	const hasDataChanged = () => {
		for (const [key, value] of Object.entries(initialFieldData)) {
			if (itemData[key as keyof Partial<ItemData>] !== value) {
				return true;
			}
		}

		return false;
	};

	const handleCancel = () => {
		if (hasDataChanged()) {
			openModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
					CancelEditAPIApplicationModalContent({
						closeModal,
					}),
				id: 'confirmCancelEditModal',
				size: 'md',
				status: 'warning',
			});
		}
		else {
			history.back();
		}
	};

	return (
		<>
			<ClayManagementToolbar>
				<ClayManagementToolbar.ItemList>
					<div className="ml-sm-2 mr-3">
						<h2 className="mb-0 text-truncate toolbar-title">
							{title}
						</h2>
					</div>

					<StatusLabel statusKey={itemData.applicationStatus!.key} />
				</ClayManagementToolbar.ItemList>

				<ClayManagementToolbar.ItemList>
					<ClayButton.Group key={1} spaced>
						<ClayButton
							borderless={true}
							displayType="secondary"
							id="apiAppManagementToolbarCancelButton"
							name="cancel"
							onClick={handleCancel}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						{itemData.applicationStatus?.key !== 'published' && (
							<ClayButton
								displayType="secondary"
								id="apiAppManagementToolbarSaveButton"
								name="save"
								onClick={() =>
									(onSave() as unknown) as voidReturn
								}
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						)}
					</ClayButton.Group>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>
		</>
	);
}
