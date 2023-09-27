/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ModalImportWarningProps {
	handleImport: () => void;
	header: string;
	onClose: (value: boolean) => void;
	paragraphs: string[];
}
export declare function ModalImportWarning({
	handleImport,
	header,
	onClose,
	paragraphs,
}: ModalImportWarningProps): JSX.Element;
export {};
