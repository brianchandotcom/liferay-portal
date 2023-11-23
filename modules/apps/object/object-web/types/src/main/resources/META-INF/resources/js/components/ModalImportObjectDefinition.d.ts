/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ModalImportObjectDefinitionInfo} from './ViewObjectDefinitions/ViewObjectDefinitions';
interface ModalImportObjectDefinitionProps {
	importObjectDefinitionURL: string;
	modalImportObjectDefinitionInfo?: ModalImportObjectDefinitionInfo;
	nameMaxLength: string;
	objectFolderExternalReferenceCode?: string;
	portletNamespace: string;
	setModalImportObjectDefinitionInfo?: (
		value: React.SetStateAction<ModalImportObjectDefinitionInfo>
	) => void;
}
export default function ModalImportObjectDefinition({
	importObjectDefinitionURL,
	modalImportObjectDefinitionInfo,
	nameMaxLength,
	objectFolderExternalReferenceCode,
	portletNamespace,
	setModalImportObjectDefinitionInfo,
}: ModalImportObjectDefinitionProps): JSX.Element | null;
export {};
