/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ModalImportWarningProps {
	errorMessage: string;
	handleImport: () => void;
	handleOnClose: () => void;
	modalImportKey: string;
}
export declare function ModalImportWarning({
	errorMessage,
	handleImport,
	handleOnClose,
	modalImportKey,
}: ModalImportWarningProps): JSX.Element;
export {};
