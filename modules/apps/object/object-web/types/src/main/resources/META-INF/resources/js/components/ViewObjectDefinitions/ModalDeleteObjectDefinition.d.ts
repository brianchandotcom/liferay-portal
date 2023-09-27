/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {DeletedObjectDefinition} from './ViewObjectDefinitions';
interface ModalDeleteObjectDefinitionProps {
	handleOnClose: () => void;
	objectDefinition: DeletedObjectDefinition;
	setDeletedObjectDefinition: (value: DeletedObjectDefinition | null) => void;
}
export declare function ModalDeleteObjectDefinition({
	handleOnClose,
	objectDefinition,
	setDeletedObjectDefinition,
}: ModalDeleteObjectDefinitionProps): JSX.Element;
export {};
