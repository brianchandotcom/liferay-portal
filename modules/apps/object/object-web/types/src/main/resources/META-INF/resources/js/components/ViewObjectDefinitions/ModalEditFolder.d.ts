/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ModalAddFolderProps {
	externalReferenceCode: string;
	folderID: number;
	handleOnClose: () => void;
	initialLabel?: LocalizedValue<string>;
	name?: string;
}
export declare function ModalEditFolder({
	externalReferenceCode,
	folderID,
	handleOnClose,
	initialLabel,
	name,
}: ModalAddFolderProps): JSX.Element;
export {};
