/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {openModal} from 'frontend-js-web';

import {selectPageContents} from './selectPageContents';

export const selectPageContentDropdownItems = (classPK) => (state) => {
	const pageContent = selectPageContents(state)?.find(
		(pageContent) => pageContent.classPK === classPK
	);

	if (!pageContent) {
		return null;
	}

	const {editURL, permissionsURL, viewUsagesURL} = pageContent.actions;

	const dropdownItems = [];

	if (editURL) {
		dropdownItems.push({
			href: editURL,
			label: Liferay.Language.get('edit'),
		});
	}

	if (permissionsURL) {
		dropdownItems.push({
			label: Liferay.Language.get('permissions'),
			onClick: () =>
				openModal({
					title: Liferay.Language.get('permissions'),
					url: permissionsURL,
				}),
		});
	}

	if (viewUsagesURL) {
		dropdownItems.push({
			label: Liferay.Language.get('view-usages'),
			onClick: () =>
				openModal({
					title: Liferay.Language.get('view-usages'),
					url: viewUsagesURL,
				}),
		});
	}

	if (dropdownItems.length === 0) {
		return null;
	}

	return dropdownItems;
};
