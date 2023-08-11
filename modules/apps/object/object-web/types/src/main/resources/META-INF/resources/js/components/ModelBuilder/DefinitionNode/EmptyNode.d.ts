/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {NodeProps} from 'react-flow-renderer';
import './DefinitionNode.scss';
import './EmptyNode.scss';
interface EmptyNodeProps {
	setShowModal: (value: boolean) => void;
}
export declare function EmptyNode({
	data: {setShowModal},
}: NodeProps<EmptyNodeProps>): JSX.Element;
export {};
