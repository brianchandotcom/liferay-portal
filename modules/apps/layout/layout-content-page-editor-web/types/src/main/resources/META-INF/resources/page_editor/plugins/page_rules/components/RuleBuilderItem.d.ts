/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, Ref} from 'react';
interface RuleBuilderItemProps {
	'aria-label': string;
	'children': ReactNode;
	'description': string;
	'onDeleteButtonClick': () => void;
	'onItemSelected': () => void;
	'showDeleteButton': boolean;
	'type': 'action' | 'condition';
	'wrapperRef'?: Ref<HTMLDivElement>;
}
export default function RuleBuilderItem({
	children,
	description,
	onDeleteButtonClick,
	onItemSelected,
	showDeleteButton,
	type,
	wrapperRef,
	...otherProps
}: RuleBuilderItemProps): JSX.Element;
export {};
