/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Props {
	onAdd: () => void;
	onClose: () => void;
	showAddButton: boolean;
	showCreateButton: boolean;
	showRetryButton: boolean;
}
export declare function FormFooter({
	onAdd,
	onClose,
	showAddButton,
	showCreateButton,
	showRetryButton,
}: Props): JSX.Element;
export {};
