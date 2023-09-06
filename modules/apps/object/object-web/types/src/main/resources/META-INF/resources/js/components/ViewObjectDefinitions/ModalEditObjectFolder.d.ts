/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ModalEditFolderProps {
	externalReferenceCode: string;
	handleOnClose: () => void;
	initialLabel?: LocalizedValue<string>;
	name?: string;
	objectFolderID: number;
}
export declare function ModalEditObjectFolder({
	externalReferenceCode,
	handleOnClose,
	initialLabel,
	name,
	objectFolderID,
}: ModalEditFolderProps): JSX.Element;
export {};
