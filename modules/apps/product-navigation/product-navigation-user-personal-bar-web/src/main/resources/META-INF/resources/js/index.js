/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, fetch, navigate, openModal} from 'frontend-js-web';

async function fetchModalContent(url) {
	try {
		const modalSignInURL = addParams('windowState=exclusive', url);

		const response = await fetch(modalSignInURL);
		const responseText = await response.text();

		return responseText;
	}
	catch (error) {
		if (process.env.NODE_ENV === 'development') {
			console.error(error);
		}

		return '';
	}
}

export function signInButtonPropsTransformer({
	additionalProps: {redirect, signInURL},
	...props
}) {
	const onClick = async () => {
		if (redirect) {
			navigate(signInURL);

			return;
		}

		const modalContentPromise = fetchModalContent(signInURL);

		openModal({
			bodyHTML: '<span class="loading-animation"></span>',
			containerProps: {className: ''},
			onOpen: async () => {
				const modalBody = document.querySelector('.liferay-modal-body');

				try {
					const modalContent = await modalContentPromise;

					if (modalBody && modalContent) {
						modalBody.innerHTML = modalContent;
					}
					else {
						navigate(signInURL);
					}
				}
				catch (error) {
					navigate(signInURL);
				}
			},
			size: 'md',
			title: Liferay.Language.get('sign-in'),
		});
	};

	return {
		...props,
		onClick,
	};
}
