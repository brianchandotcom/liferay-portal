/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface DeleteAPISchemaModal {
	closeModal: voidReturn;
	itemData: BaseItem;
	loadData: voidReturn;
}
export declare function DeleteAPISchemaModalContent({
	closeModal,
	itemData,
	loadData,
}: DeleteAPISchemaModal): JSX.Element;
export {};
