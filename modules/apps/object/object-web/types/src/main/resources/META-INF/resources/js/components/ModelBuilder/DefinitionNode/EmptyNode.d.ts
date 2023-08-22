/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {NodeProps} from 'react-flow-renderer';
import './DefinitionNode.scss';
import './EmptyNode.scss';
import {ViewObjectDefinitionsModals} from '../../ViewObjectDefinitions/ViewObjectDefinitions';
interface EmptyNodeProps {
	setShowModal: (
		value: React.SetStateAction<ViewObjectDefinitionsModals>
	) => void;
}
export declare function EmptyNode({
	data: {setShowModal},
}: NodeProps<EmptyNodeProps>): JSX.Element;
export {};
