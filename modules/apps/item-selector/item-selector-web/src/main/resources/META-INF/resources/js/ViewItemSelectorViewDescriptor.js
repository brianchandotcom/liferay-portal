/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, getOpener} from 'frontend-js-web';

export default function ({
	itemSelectorReturnType,
	itemSelectorSelectedEvent,
	namespace,
}) {
	const selectItem = (event) => {
		getOpener().Liferay.fire(itemSelectorSelectedEvent, {
			data: {
				returnType: itemSelectorReturnType,
				value: event.delegateTarget.dataset.value,
			},
		});
	};

	const onClickHandler = delegate(
		document.getElementById(`${namespace}entriesContainer`),
		'click',
		'.entry',
		(event) => {
			selectItem(event);
		}
	);

	const onKeydownHandler = delegate(
		document.getElementById(`${namespace}entriesContainer`),
		'keydown',
		'.entry',
		(event) => {
			if (event.code === 'Enter') {
				selectItem(event);
			}
		}
	);

	return {
		dispose() {
			onClickHandler.dispose();
			onKeydownHandler.dispose();
		},
	};
}
