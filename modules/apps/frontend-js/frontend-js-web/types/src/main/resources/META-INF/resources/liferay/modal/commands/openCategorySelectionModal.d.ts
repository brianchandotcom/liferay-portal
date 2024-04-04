/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface Category {
	categoryId: string;
	className: string;
	classNameId: string;
	classPK: string;
	nodePath: string;
	title: string;
	value: string;
	vocabularyId: number;
}
export default function openCategorySelectionModal({
	onSelect,
	portletNamespace,
	redirectURL,
	selectCategoryURL,
	title,
}: {
	onSelect?: (selectedItems: Record<string, Category>) => void;
	portletNamespace: string;
	redirectURL: string;
	selectCategoryURL: string;
	title: string;
}): void;
export {};
