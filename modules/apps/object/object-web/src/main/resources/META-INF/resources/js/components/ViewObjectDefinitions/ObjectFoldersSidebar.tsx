/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import {getLocalizableLabel} from '@liferay/object-js-components-web';
import React, {SetStateAction} from 'react';

import {defaultLanguageId} from '../../utils/constants';

interface ObjectFoldersSidebarProps {
	objectFolders: ObjectFolder[];
	selectedObjectFolder: ObjectFolder;
	setSelectedObjectFolder: (
		value: SetStateAction<Partial<ObjectFolder>>
	) => void;
	setShowModal: (value: SetStateAction<ViewObjectDefinitionsModals>) => void;
}

export default function ObjectFoldersSideBar({
	objectFolders,
	selectedObjectFolder,
	setSelectedObjectFolder,
	setShowModal,
}: ObjectFoldersSidebarProps) {
	return (
		<div className="lfr__object-web-view-object-definitions-object-folder-list-container">
			<div className="lfr__object-web-view-object-definitions-object-folder-list-header">
				<span className="lfr__object-web-view-object-definitions-object-folder-list-title mb-0">
					{Liferay.Language.get('object-folders').toUpperCase()}
				</span>

				<div className="d-flex">
					<ClayButton
						aria-label={Liferay.Language.get('add-object-folder')}
						className="component-action"
						displayType="unstyled"
						monospaced
						onClick={() =>
							setShowModal(
								(
									previousState: ViewObjectDefinitionsModals
								) => ({
									...previousState,
									addObjectFolder: true,
								})
							)
						}
					>
						<ClayIcon symbol="plus" />
					</ClayButton>
				</div>
			</div>

			<ClayList className="lfr__object-web-view-object-definitions-object-folder-list">
				{objectFolders.map((currentObjectFolder) => (
					<ClayList.Item
						action
						active={
							selectedObjectFolder.externalReferenceCode ===
							currentObjectFolder.externalReferenceCode
						}
						className="cursor-pointer lfr__object-web-view-object-definitions-object-folder-list-item"
						flex
						key={currentObjectFolder.name}
						onClick={() => {
							setSelectedObjectFolder(currentObjectFolder);
						}}
					>
						<span className="lfr__object-web-view-object-definitions-object-folder-list-item-label">
							{getLocalizableLabel(
								defaultLanguageId,
								currentObjectFolder.label,
								currentObjectFolder.name
							)}
						</span>
					</ClayList.Item>
				))}
			</ClayList>
		</div>
	);
}
