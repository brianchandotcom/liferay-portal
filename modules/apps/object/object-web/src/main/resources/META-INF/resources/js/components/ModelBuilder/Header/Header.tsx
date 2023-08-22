/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import React from 'react';

import './Header.scss';

import {sub} from 'frontend-js-web';

import {ViewObjectDefinitionsModals} from '../../ViewObjectDefinitions/ViewObjectDefinitions';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';

interface Header {
	folder: ObjectFolder;
	hasDraftObjectDefinitions: boolean;
	setShowModal: (
		value: React.SetStateAction<ViewObjectDefinitionsModals>
	) => void;
}

export default function ({
	folder,
	hasDraftObjectDefinitions,
	setShowModal,
}: Header) {
	const [{showChangesSaved}] = useFolderContext();

	return (
		<div className="lfr-objects__model-builder-header">
			<div className="lfr-objects__model-builder-header-container">
				<div className="lfr-objects__model-builder-header-folder-info">
					<div
						className={classNames(
							'lfr-objects__model-builder-header-folder-info-name',
							{
								'lfr-objects__model-builder-header-folder-info-name-changes-saved': showChangesSaved,
							}
						)}
					>
						<ClayTooltipProvider>
							<span
								title={
									Liferay.Language.get('folder-name') +
									`: ${folder.name}`
								}
							>
								{folder.name}
							</span>
						</ClayTooltipProvider>
					</div>

					<span className="lfr-objects__model-builder-header-folder-info-erc-title">
						{Liferay.Language.get('erc')}:
					</span>

					<ClayTooltipProvider>
						<span
							className={classNames(
								'lfr-objects__model-builder-header-folder-info-erc-content',
								{
									'lfr-objects__model-builder-header-folder-info-erc-content-changes-saved': showChangesSaved,
								}
							)}
							title={
								Liferay.Language.get('erc') +
								`: ${folder.externalReferenceCode}`
							}
						>
							<strong>{folder.externalReferenceCode}</strong>
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

					{folder.externalReferenceCode !== 'uncategorized' &&
						folder.actions?.update && (
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'edit-label-and-erc'
								)}
								displayType="unstyled"
								onClick={() =>
									setShowModal(
										(
											previousState: ViewObjectDefinitionsModals
										) => ({
											...previousState,
											editFolder: true,
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
					>
						{Liferay.Language.get('publish')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
}
