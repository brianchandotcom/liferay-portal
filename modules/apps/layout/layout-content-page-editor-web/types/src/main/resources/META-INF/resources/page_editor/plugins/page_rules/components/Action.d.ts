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
	onActionChange: (action: Action) => void;
	onDeleteAction: () => void;
}
export default function Condition({
	action,
	onActionChange,
	onDeleteAction,
}: ActionProps): JSX.Element;
export {};
