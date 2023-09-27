/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-web';

import {checkFriendlyURL} from './checkFriendlyURL';

interface Options {
	getFriendlyURLWarningURL: string;
	namespace: string;
	shouldCheckFriendlyURL: string;
}

export default function EditLayout({
	getFriendlyURLWarningURL,
	namespace,
	shouldCheckFriendlyURL,
}: Options) {
	const form = document.getElementById(
		`${namespace}editLayoutFm`
	) as HTMLFormElement;

	const onSubmit = async (event: Event) => {
		event.preventDefault();
		event.stopPropagation();

		if (shouldCheckFriendlyURL) {
			const {shouldSubmit} = await checkFriendlyURL(
				getFriendlyURLWarningURL,
				new FormData(form)
			);

			if (!shouldSubmit) {
				return;
			}
		}

		const applyLayoutPrototype = document.getElementById(
			`${namespace}applyLayoutPrototype`
		) as HTMLInputElement;

		if (!applyLayoutPrototype || applyLayoutPrototype.value === 'false') {

			// @ts-ignore

			submitForm(form);
		}
		else if (
			applyLayoutPrototype &&
			applyLayoutPrototype.value === 'true'
		) {
			openConfirmModal({
				message: Liferay.Language.get(
					'reactivating-inherited-changes-may-update-the-page-with-the-possible-changes-that-could-have-been-made-in-the-original-template'
				),
				onConfirm: (isConfirm: boolean) => {
					if (isConfirm) {

						// @ts-ignore

						submitForm(form);
					}
				},
			});
		}
	};

	form.addEventListener('submit', onSubmit);

	return {
		dispose() {
			form.removeEventListener('submit', onSubmit);
		},
	};
}
