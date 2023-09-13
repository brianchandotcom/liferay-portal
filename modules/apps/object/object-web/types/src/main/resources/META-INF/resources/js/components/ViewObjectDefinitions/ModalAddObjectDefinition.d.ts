/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ModalAddObjectDefinition.scss';
interface ModalAddObjectDefinitionProps {
	handleOnClose: () => void;
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectFolderExternalReferenceCode?: string;
	onAfterSubmit?: (value: ObjectDefinition) => void;
	reload?: boolean;
}
export declare function ModalAddObjectDefinition({
	handleOnClose,
	objectDefinitionsStorageTypes,
	objectFolderExternalReferenceCode,
	onAfterSubmit,
	reload,
}: ModalAddObjectDefinitionProps): JSX.Element;
export {};
