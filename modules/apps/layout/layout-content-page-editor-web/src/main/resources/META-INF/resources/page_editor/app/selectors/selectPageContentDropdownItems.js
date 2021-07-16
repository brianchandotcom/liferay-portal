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

export const selectPageContentDropdownItems = (classPK, label = '') => (
	state
) => {
	const pageContent = selectPageContents(state)?.find(
		(pageContent) => pageContent.classPK === classPK
	);

	if (!pageContent) {
		return null;
	}

	const {
		addItems,
		editImage,
		editURL,
		permissionsURL,
		viewItemsURL,
		viewUsagesURL,
	} = pageContent.actions || {};

	const dropdownItems = [];

	if (editURL) {
		dropdownItems.push({
			href: editURL,
			label: label
				? Liferay.Util.sub(Liferay.Language.get('edit-x'), label)
				: Liferay.Language.get('edit'),
		});
	}

	if (editImage) {
		dropdownItems.push({
			...editImage,
			label: Liferay.Language.get('edit-image'),
		});
	}

	if (viewItemsURL) {
		dropdownItems.push({
			label: Liferay.Language.get('view-items'),
			onClick: () =>
				openModal({
					title: Liferay.Language.get('view-items'),
					url: viewItemsURL,
				}),
		});
	}

	if (addItems) {
		dropdownItems.push({
			items: addItems,
			label: Liferay.Language.get('add-items'),
			type: 'contextual',
		});
	}

	if (permissionsURL) {
		dropdownItems.push({
			label: label
				? Liferay.Util.sub(
						Liferay.Language.get('edit-x-permissions'),
						label
				  )
				: Liferay.Language.get('permissions'),
			onClick: () =>
				openModal({
					title: label
						? Liferay.Util.sub(
								Liferay.Language.get('edit-x-permissions'),
								label
						  )
						: Liferay.Language.get('permissions'),
					url: permissionsURL,
				}),
		});
	}

	if (viewUsagesURL) {
		dropdownItems.push({
			label: label
				? Liferay.Util.sub(Liferay.Language.get('view-x-usages'), label)
				: Liferay.Language.get('view-usages'),
			onClick: () =>
				openModal({
					title: label
						? Liferay.Util.sub(
								Liferay.Language.get('view-x-usages'),
								label
						  )
						: Liferay.Language.get('view-usages'),
					url: viewUsagesURL,
				}),
		});
	}

	if (dropdownItems.length === 0) {
		return null;
	}

	return dropdownItems;
};
