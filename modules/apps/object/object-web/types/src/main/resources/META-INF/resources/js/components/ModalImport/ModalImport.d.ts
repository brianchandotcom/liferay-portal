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
	label: string;
	nameMaxLength: string;
	portletNamespace: string;
	showModal?: boolean;
	title: string;
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
	label,
	nameMaxLength,
	portletNamespace,
	showModal,
	title,
}: ModalImportProps): JSX.Element | null;
export {};
