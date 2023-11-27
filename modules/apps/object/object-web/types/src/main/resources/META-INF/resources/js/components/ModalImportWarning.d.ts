/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ModalImportWarningProps {
	handleImport: () => void;
	handleOnClose: (value: boolean) => void;
	header: string;
	paragraphs: string[];
}
export declare function ModalImportWarning({
	handleImport,
	handleOnClose,
	header,
	paragraphs,
}: ModalImportWarningProps): JSX.Element;
export {};
