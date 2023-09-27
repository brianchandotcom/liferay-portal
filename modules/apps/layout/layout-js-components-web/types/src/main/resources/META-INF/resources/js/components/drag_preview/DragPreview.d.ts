/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './DragPreview.scss';
interface DragItem {
	icon?: string;
	name?: string;
}
interface Props<T> {
	getIcon: (item: T) => string;
	getLabel: (item: T) => string;
}
export default function DragPreview<T extends DragItem>({
	getIcon,
	getLabel,
}: Props<T>): JSX.Element | null;
export {};
