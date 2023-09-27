/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Observer} from '@clayui/modal/lib/types';
import React from 'react';
interface IProps extends React.HTMLAttributes<HTMLElement> {
	editingObjectFieldName: string;
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
}: IProps): JSX.Element;
export {};
