/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ModalImportProps {
	JSONInputId: string;
	apiURL: string;
	handleOnClose?: () => void;
	importExtendedInfo?: {
		key: string;
		value: string;
	};
	importURL: string;
	modalImportKey: string;
	nameMaxLength: string;
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
	portletNamespace,
	showModal,
}: ModalImportProps): JSX.Element | null;
export {};
