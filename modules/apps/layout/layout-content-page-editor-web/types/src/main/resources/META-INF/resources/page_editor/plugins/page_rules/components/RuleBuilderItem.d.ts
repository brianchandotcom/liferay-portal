/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
interface RuleBuilderItemProps {
	children: ReactNode;
	onDeleteButtonClick: () => void;
	showDeleteButton: boolean;
	type: 'action' | 'condition';
}
export default function RuleBuilderItem({
	children,
	onDeleteButtonClick,
	showDeleteButton,
	type,
}: RuleBuilderItemProps): JSX.Element;
export {};
