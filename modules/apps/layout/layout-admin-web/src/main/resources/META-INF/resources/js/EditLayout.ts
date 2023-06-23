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

import {openConfirmModal} from 'frontend-js-web';

interface Options {
	checkFriendlyURL: string;
	getFriendlyURLWarningURL: string;
	namespace: string;
}

export default function EditLayout({namespace}: Options) {
	const form = document.getElementById(
		`${namespace}editLayoutFm`
	) as HTMLFormElement;

	const onSubmit = () => {
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
