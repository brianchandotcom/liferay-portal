/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export interface Action {
	action?: 'fragment';
	id: string;
	itemId?: string;
	type: 'show' | 'hide' | undefined;
}
interface ActionProps {
	action: Action;
	layoutDataItems: {
		label: string;
		value: string;
	}[];
	onActionChange: (action: Action) => void;
	onDeleteAction: () => void;
	showDeleteButton: boolean;
}
export default function Action({
	action,
	layoutDataItems,
	onActionChange,
	onDeleteAction,
	showDeleteButton,
}: ActionProps): JSX.Element;
export {};
