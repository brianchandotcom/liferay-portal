/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ModalMoveObjectDefinition.scss';
interface ModalMoveObjectDefinitionProps {
	handleOnClose: () => void;
	objectDefinition: ObjectDefinition;
	objectFolders: ObjectFolder[];
	selectedObjectFolder: Partial<ObjectFolder>;
	setMoveObjectDefinition: (value: ObjectDefinition | null) => void;
}
export declare function ModalMoveObjectDefinition({
	handleOnClose,
	objectDefinition,
	objectFolders,
	selectedObjectFolder,
	setMoveObjectDefinition,
}: ModalMoveObjectDefinitionProps): JSX.Element;
export {};
