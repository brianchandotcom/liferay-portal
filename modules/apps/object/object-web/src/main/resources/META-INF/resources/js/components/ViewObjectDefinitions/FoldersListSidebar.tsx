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
import {ViewObjectDefinitionsModals} from './ViewObjectDefinitions';

interface FoldersListSidebarProps {
	foldersList: Folder[];
	selectedFolder: Folder;
	setSelectedFolder: (value: SetStateAction<Partial<Folder>>) => void;
	setShowModal: (value: SetStateAction<ViewObjectDefinitionsModals>) => void;
}

export default function FoldersListSideBar({
	foldersList,
	selectedFolder,
	setSelectedFolder,
	setShowModal,
}: FoldersListSidebarProps) {
	return (
		<div className="lfr__object-web-view-object-definitions-folder-list-container">
			<div className="lfr__object-web-view-object-definitions-folder-list-header">
				<span className="lfr__object-web-view-object-definitions-folder-list-title mb-0">
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
									addFolder: true,
								})
							)
						}
					>
						<ClayIcon symbol="plus" />
					</ClayButton>
				</div>
			</div>

			<ClayList className="lfr__object-web-view-object-definitions-folder-list">
				{foldersList.map((currentFolder) => (
					<ClayList.Item
						action
						active={
							selectedFolder.externalReferenceCode ===
							currentFolder.externalReferenceCode
						}
						className="cursor-pointer lfr__object-web-view-object-definitions-folder-list-item"
						flex
						key={currentFolder.name}
						onClick={() => {
							setSelectedFolder(currentFolder);
						}}
					>
						<span className="lfr__object-web-view-object-definitions-folder-list-item-label">
							{getLocalizableLabel(
								defaultLanguageId,
								currentFolder.label,
								currentFolder.name
							)}
						</span>
					</ClayList.Item>
				))}
			</ClayList>
		</div>
	);
}
