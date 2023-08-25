/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './NodeFooter.scss';
interface NodeFooterProps {
	isLinkedNode: boolean;
	setShowAllFields: (value: boolean) => void;
	showAllFields: boolean;
}
export default function NodeFooter({
	isLinkedNode,
	setShowAllFields,
	showAllFields,
}: NodeFooterProps): JSX.Element;
export {};
