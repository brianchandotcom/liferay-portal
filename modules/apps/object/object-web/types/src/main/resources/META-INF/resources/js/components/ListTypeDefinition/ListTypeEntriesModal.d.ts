/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export interface IModalState extends Partial<ListTypeEntry> {
	header?: string;
	itemExternalReferenceCode?: string;
	itemId?: number;
	itemKey?: string;
	modalType?: 'add' | 'edit';
	pickListId?: number;
	readOnly?: boolean;
	reloadIframeWindow?: () => void;
	system?: boolean;
}
declare function ListTypeEntriesModal(): JSX.Element | null;
export default ListTypeEntriesModal;
