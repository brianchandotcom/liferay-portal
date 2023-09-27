/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';
import {NodeProps} from 'react-flow-renderer';

import './EmptyNode.scss';

import './NodeContainer.scss';

interface EmptyNodeProps {
	setShowModal: (value: React.SetStateAction<ModelBuilderModals>) => void;
}

export function EmptyNode({data: {setShowModal}}: NodeProps<EmptyNodeProps>) {
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
					displayType="primary"
					onClick={() =>
						setShowModal((previousState: ModelBuilderModals) => ({
							...previousState,
							addObjectDefinition: true,
						}))
					}
				>
					<span>{Liferay.Language.get('create-new-object')}</span>
				</ClayButton>
			</div>
		</div>
	);
}
