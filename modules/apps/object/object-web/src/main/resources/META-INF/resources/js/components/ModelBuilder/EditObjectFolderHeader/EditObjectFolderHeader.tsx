/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {stringUtils} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React from 'react';

import {defaultLanguageId} from '../../../utils/constants';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

import './EditObjectFolderHeader.scss';

interface EditObjectFolderHeaderProps {
	hasDraftObjectDefinitions: boolean;
	selectedObjectFolder: ObjectFolder;
}

export default function EditObjectFolderHeader({
	hasDraftObjectDefinitions,
	selectedObjectFolder,
}: EditObjectFolderHeaderProps) {
	const [{showChangesSaved, showSidebars}, dispatch] =
		useObjectFolderContext();

	return (
		<div className="lfr-objects__model-builder-header">
			<div className="lfr-objects__model-builder-header-container">
				<div className="lfr-objects__model-builder-header-object-folder-info">
					<div
						className={classNames(
							'lfr-objects__model-builder-header-object-folder-info-label',
							{
								'lfr-objects__model-builder-header-object-folder-info-label-changes-saved':
									showChangesSaved,
							}
						)}
					>
						<ClayTooltipProvider>
							<span
								title={
									Liferay.Language.get(
										'object-folder-label'
									) +
									`: ${stringUtils.getLocalizableLabel(
										defaultLanguageId,
										selectedObjectFolder.label,
										selectedObjectFolder.name
									)}`
								}
							>
								{stringUtils.getLocalizableLabel(
									defaultLanguageId,
									selectedObjectFolder.label,
									selectedObjectFolder.name
								)}
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
									'lfr-objects__model-builder-header-object-folder-info-erc-content-changes-saved':
										showChangesSaved,
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

					{selectedObjectFolder.externalReferenceCode !== 'default' &&
						selectedObjectFolder.actions?.update && (
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'edit-label-and-erc'
								)}
								displayType="unstyled"
								name="editObjectFolderButton"
								onClick={() =>
									dispatch({
										payload: {
											updatedModelBuilderModals: {
												editObjectFolder: true,
											},
										},
										type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
									})
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
						onClick={() =>
							dispatch({
								payload: {updatedShowSidebars: !showSidebars},
								type: TYPES.SET_SHOW_SIDEBARS,
							})
						}
						size="sm"
						symbol={showSidebars ? 'view' : 'hidden'}
						title={Liferay.Language.get('toggle-sidebars')}
					/>

					<ClayButton
						aria-labelledby={Liferay.Language.get('publish')}
						disabled={!hasDraftObjectDefinitions}
						displayType="primary"
						onClick={() => {
							dispatch({
								payload: {
									updatedModelBuilderModals: {
										publishObjectDefinitions: true,
									},
								},
								type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
							});
						}}
						size="sm"
					>
						{Liferay.Language.get('publish')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
}
