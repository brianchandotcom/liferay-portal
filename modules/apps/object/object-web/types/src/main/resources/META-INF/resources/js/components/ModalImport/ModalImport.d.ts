/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export declare type ModalImportKeys =
	| 'listTypeDefinition'
	| 'objectDefinition'
	| 'objectDefinitions'
	| 'objectFolder';
interface ModalImportProps {
	JSONInputId: string;
	apiURL: string;
	handleOnClose?: () => void;
	importExtendedInfo: KeyValueObject;
	importURL: string;
	modalImportKey: ModalImportKeys;
	nameMaxLength: string;
	onAfterImport?: () => void;
	portletNamespace: string;
	showModal?: boolean;
}
export declare type TFile = {
	fileName?: string;
	inputFile?: File | null;
};
export default function ModalImport({
	JSONInputId,
	apiURL,
	handleOnClose,
	importExtendedInfo,
	importURL,
	modalImportKey,
	nameMaxLength,
	onAfterImport,
	portletNamespace,
	showModal,
}: ModalImportProps): JSX.Element | null;
export {};
