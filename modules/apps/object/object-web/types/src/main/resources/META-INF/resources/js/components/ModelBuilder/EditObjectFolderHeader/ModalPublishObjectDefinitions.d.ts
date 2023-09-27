/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Elements} from 'react-flow-renderer';
import {ObjectRelationshipEdgeData, TAction} from '../types';
import './ModalPublishObjectDefinitions.scss';
interface ModalPublishObjectDefinitionsProps {
	disableAutoClose: boolean;
	dispatch: React.Dispatch<TAction>;
	elements: Elements<ObjectDefinitionNodeData | ObjectRelationshipEdgeData>;
	handleOnClose: () => void;
}
export declare function ModalPublishObjectDefinitions({
	disableAutoClose,
	dispatch,
	elements,
	handleOnClose,
}: ModalPublishObjectDefinitionsProps): JSX.Element;
export {};
