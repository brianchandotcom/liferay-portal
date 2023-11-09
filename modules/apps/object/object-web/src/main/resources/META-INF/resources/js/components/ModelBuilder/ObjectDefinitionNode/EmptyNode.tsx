/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import './EmptyNode.scss';

import './NodeContainer.scss';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

export function EmptyNode() {
	const [{modelBuilderModals}, dispatch] = useObjectFolderContext();

	return (
		<div className="lfr-objects__model-builder-node-container">
			<div className="lfr-objects__model-builder-node-container-empty">
				<div className="lfr-objects__model-builder-node-container-empty-title">
					<span>{Liferay.Language.get('start-with-an-object')}</span>
				</div>

				<div className="lfr-objects__model-builder-node-container-empty-description">
					<span>
						{Liferay.Language.get(
							'create-your-first-object-or-add-an-existing-one-for-this-folder'
						)}
					</span>
				</div>

				<ClayButton
					aria-labelledby={Liferay.Language.get('create-new-object')}
					displayType="primary"
					onClick={() =>
						dispatch({
							payload: {
								modelBuilderModals: {
									...modelBuilderModals,
									addObjectDefinition: true,
								},
							},
							type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
						})
					}
					size="sm"
				>
					<span>{Liferay.Language.get('create-new-object')}</span>
				</ClayButton>
			</div>
		</div>
	);
}
