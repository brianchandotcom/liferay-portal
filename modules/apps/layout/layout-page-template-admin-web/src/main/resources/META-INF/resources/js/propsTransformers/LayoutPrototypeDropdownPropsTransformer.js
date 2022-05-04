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

import openDeletePageTemplateModal from '../modal/openDeletePageTemplateModal';

const ACTIONS = {
	deleteLayoutPrototype({deleteLayoutPrototypeURL}) {
		openDeletePageTemplateModal({
			onDelete: () => {
				submitForm(document.hrefFm, deleteLayoutPrototypeURL);
			},
			title: Liferay.Language.get('page-template'),
		});
	},

	exportLayoutPrototype({exportLayoutPrototypeURL}) {
		openModal({
			title: Liferay.Language.get('export'),
			url: exportLayoutPrototypeURL,
		});
	},

	importLayoutPrototype({importLayoutPrototypeURL}) {
		openModal({
			title: Liferay.Language.get('import'),
			url: importLayoutPrototypeURL,
		});
	},

	permissionsLayoutPrototype({permissionsLayoutPrototypeURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsLayoutPrototypeURL,
		});
	},
};

export default function LayoutPrototypeDropdownPropsTransformer({
	actions,
	...otherProps
}) {
	return {
		...otherProps,
		actions: actions?.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => {
					return {
						...child,
						onClick(event) {
							const action = child.data?.action;

							if (action) {
								event.preventDefault();

								ACTIONS[action](child.data);
							}
						},
					};
				}),
			};
		}),
	};
}
