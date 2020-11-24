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

const ACTIONS = {
	deleteLayoutPrototype({deleteLayoutPrototypeURL}) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, deleteLayoutPrototypeURL);
		}
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
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](item.data);
					}
				},
			};
		}),
	};
}
