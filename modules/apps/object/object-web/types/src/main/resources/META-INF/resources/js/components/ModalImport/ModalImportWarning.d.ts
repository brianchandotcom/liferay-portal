/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ModalImportWarning.scss';
interface ModalImportWarningProps {
	errorMessage: string;
	existingObjectDefinitions?: ObjectDefinition[];
	handleImport: () => void;
	handleOnClose: () => void;
	importLoading: boolean;
	modalImportKey: string;
}
export declare const tableHeaderItems: {
	id: string;
	name: string;
}[];
export declare function ModalImportWarning({
	errorMessage,
	existingObjectDefinitions,
	handleImport,
	handleOnClose,
	importLoading,
	modalImportKey,
}: ModalImportWarningProps): JSX.Element;
export {};
