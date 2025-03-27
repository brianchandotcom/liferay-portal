/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function checkAll(
	form: HTMLElement | null | Element | string,
	name: string,
	allBox: HTMLElement | null | Element | string,
	selectClassName: string
) {
	if (form) {
		form = Liferay.Util.getDOM(form);

		if (typeof form === 'string') {
			form = document.querySelector(form);
		}

		allBox = Liferay.Util.getDOM(allBox);

		if (typeof allBox === 'string') {
			allBox = document.querySelector(allBox);
		}

		let selector;

		if (Array.isArray(name)) {
			selector = 'input[name=' + name.join('], input[name=') + ']';
		}
		else {
			selector = 'input[name=' + name + ']';
		}

		const allBoxChecked = (allBox as HTMLInputElement)?.checked;

		const uploadedItems = Array.from(
			form ? form.querySelectorAll(selector) : []
		) as Array<HTMLInputElement>;

		uploadedItems.forEach((item) => {
			if (!item.disabled) {
				item.checked = allBoxChecked;
			}
		});

		if (selectClassName) {
			const selectItem = form?.querySelector(selectClassName);

			if (allBoxChecked) {
				selectItem?.classList.add('info');
			}
			else {
				selectItem?.classList.remove('info');
			}
		}
	}
}
