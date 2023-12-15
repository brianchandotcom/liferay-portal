/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import React from 'react';

import './EmptyObjectFolderCard.scss';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

export default function EmptyObjectFolderCard() {
	const [_, dispatch] = useObjectFolderContext();

	return (
		<ClayCard className="lfr-objects__model-builder-empty-object-folder-card-container">
			<ClayCard.Body className="lfr-objects__model-builder-empty-object-folder-card-body">
				<ClayCard.Description
					className="lfr-objects__model-builder-empty-object-folder-card-title"
					displayType="title"
				>
					{Liferay.Language.get('start-with-an-object')}
				</ClayCard.Description>

				<ClayCard.Description
					className="lfr-objects__model-builder-empty-object-folder-card-description"
					displayType="text"
					truncate={false}
				>
					{Liferay.Language.get(
						'create-your-first-object-or-add-an-existing-one-for-this-folder'
					)}
				</ClayCard.Description>

				<ClayButton
					onClick={() =>
						dispatch({
							payload: {
								updatedModelBuilderModals: {
									addObjectDefinition: true,
								},
							},
							type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
						})
					}
					size="sm"
				>
					{Liferay.Language.get('create-new-object')}
				</ClayButton>
			</ClayCard.Body>
		</ClayCard>
	);
}
