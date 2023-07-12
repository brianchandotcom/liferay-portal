/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

import './Header.scss';

import {sub} from 'frontend-js-web';

interface Header {
	folderExternalReferenceCode: string;
	folderName: string;
	hasDraftObjectDefinitions: boolean;
}

export default function ({
	folderExternalReferenceCode,
	folderName,
	hasDraftObjectDefinitions,
}: Header) {
	return (
		<div className="lfr-objects__model-builder-header">
			<div className="lfr-objects__model-builder-header-container">
				<div className="lfr-objects__model-builder-header-folder-info">
					<div className="lfr-objects__model-builder-header-folder-info-name">
						<span>{folderName}</span>
					</div>

					<span className="lfr-objects__model-builder-header-folder-info-erc">
						{Liferay.Language.get('erc')}:
					</span>

					<strong>{folderExternalReferenceCode}</strong>

					<span
						role="tooltip"
						title={Liferay.Language.get(
							'unique-key-for-referencing-the-object-folder'
						)}
					>
						<ClayIcon symbol="question-circle" />
					</span>

					{folderExternalReferenceCode !== 'uncategorized' && (
						<ClayButtonWithIcon
							aria-label={sub(
								Liferay.Language.get('edit-x'),
								Liferay.Language.get('external-reference-code')
							)}
							displayType="unstyled"
							symbol="pencil"
						/>
					)}
				</div>

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
