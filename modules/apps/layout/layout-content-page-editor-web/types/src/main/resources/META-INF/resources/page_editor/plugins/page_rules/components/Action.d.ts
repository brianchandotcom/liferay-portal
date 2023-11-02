/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentProps} from 'react';
import RuleBuilderItem from './RuleBuilderItem';
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
	wrapperRef?: ComponentProps<typeof RuleBuilderItem>['wrapperRef'];
}
export default function Action({
	action,
	layoutDataItems,
	onActionChange,
	onDeleteAction,
	showDeleteButton,
	wrapperRef,
}: ActionProps): JSX.Element;
export {};
