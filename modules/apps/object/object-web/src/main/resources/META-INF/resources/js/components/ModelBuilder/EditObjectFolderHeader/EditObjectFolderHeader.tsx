/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React from 'react';

import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';

import './EditObjectFolderHeader.scss';

interface EditObjectFolderHeaderProps {
	hasDraftObjectDefinitions: boolean;
	selectedObjectFolder: ObjectFolder;
	setShowModal: (value: React.SetStateAction<ModelBuilderModals>) => void;
}

export default function EditObjectFolderHeader({
	hasDraftObjectDefinitions,
	selectedObjectFolder,
	setShowModal,
}: EditObjectFolderHeaderProps) {
	const [{showChangesSaved}] = useObjectFolderContext();

	return (
		<div className="lfr-objects__model-builder-header">
			<div className="lfr-objects__model-builder-header-container">
				<div className="lfr-objects__model-builder-header-object-folder-info">
					<div
						className={classNames(
							'lfr-objects__model-builder-header-object-folder-info-name',
							{
								'lfr-objects__model-builder-header-object-folder-info-name-changes-saved': showChangesSaved,
							}
						)}
					>
						<ClayTooltipProvider>
							<span
								title={
									Liferay.Language.get('folder-name') +
									`: ${selectedObjectFolder.name}`
								}
							>
								{selectedObjectFolder.name}
							</span>
						</ClayTooltipProvider>
					</div>

					<span className="lfr-objects__model-builder-header-object-folder-info-erc-title">
						{Liferay.Language.get('erc')}:
					</span>

					<ClayTooltipProvider>
						<span
							className={classNames(
								'lfr-objects__model-builder-header-object-folder-info-erc-content',
								{
									'lfr-objects__model-builder-header-object-folder-info-erc-content-changes-saved': showChangesSaved,
								}
							)}
							title={
								Liferay.Language.get('erc') +
								`: ${selectedObjectFolder.externalReferenceCode}`
							}
						>
							<strong>
								{selectedObjectFolder.externalReferenceCode}
							</strong>
						</span>
					</ClayTooltipProvider>

					<ClayTooltipProvider>
						<span
							title={Liferay.Language.get(
								'unique-key-for-referencing-the-object-folder'
							)}
						>
							<ClayIcon symbol="question-circle" />
						</span>
					</ClayTooltipProvider>

					{selectedObjectFolder.externalReferenceCode !==
						'uncategorized' &&
						selectedObjectFolder.actions?.update && (
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'edit-label-and-erc'
								)}
								displayType="unstyled"
								onClick={() =>
									setShowModal(
										(
											previousState: ModelBuilderModals
										) => ({
											...previousState,
											editObjectFolder: true,
										})
									)
								}
								symbol="pencil"
							/>
						)}
				</div>

				{showChangesSaved && (
					<span className="lfr-objects__model-builder-header-changes-saved">
						{Liferay.Language.get('changes-saved')}
						&nbsp;
						<ClayIcon symbol="check-circle" />
					</span>
				)}

				<div className="lfr-objects__model-builder-header-buttons-container">
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('toggle-sidebars')}
						displayType="secondary"
						symbol="view"
						title={Liferay.Language.get('toggle-sidebars')}
					/>

					<ClayButton displayType="secondary">
						{sub(
							Liferay.Language.get('x-folder'),
							Liferay.Language.get('create-new')
						)}
					</ClayButton>

					<ClayButton displayType="secondary">
						{Liferay.Language.get('export')}
					</ClayButton>

					<ClayButton
						disabled={!hasDraftObjectDefinitions}
						displayType="primary"
						onClick={() => {
							setShowModal(
								(previousState: ModelBuilderModals) => ({
									...previousState,
									publishObjectDefinitions: true,
								})
							);
						}}
					>
						{Liferay.Language.get('publish')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
}
