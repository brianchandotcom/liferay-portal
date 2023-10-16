/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Observer} from '@clayui/modal/lib/types';
interface ModalAddDefaultSortColumnProps {
	editingObjectFieldName?: string;
	header: string;
	isEditingSort: boolean;
	observer: Observer;
	onClose: () => void;
}
export declare function ModalAddDefaultSortColumn({
	editingObjectFieldName,
	header,
	isEditingSort,
	observer,
	onClose,
}: ModalAddDefaultSortColumnProps): JSX.Element;
export {};
