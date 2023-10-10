/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, fetch, openModal} from 'frontend-js-web';

export default function propsTransformer({additionalProps, ...props}) {
	const {redirect: signInRedirect, signInURL} = additionalProps;

	const signInLink = document.querySelector('.sign-in > div > button');

	const modalSignInURL = addParams('windowState=exclusive', signInURL);

	const setModalContent = function (html) {
		const modalBody = document.querySelector('.liferay-modal-body');

		if (modalBody) {
			const fragment = document
				.createRange()
				.createContextualFragment(html);

			modalBody.innerHTML = '';

			modalBody.appendChild(fragment);
		}
	};

	let loading = false;
	let redirect = false;
	let html = '';
	let modalOpen = false;

	const fetchModalSignIn = function () {
		if (loading || html) {
			return;
		}

		loading = true;

		fetch(modalSignInURL)
			.then((response) => {
				return response.text();
			})
			.then((response) => {
				if (!loading) {
					return;
				}

				loading = false;

				if (!response) {
					redirect = true;

					return;
				}

				html = response;

				if (modalOpen) {
					setModalContent(response);
				}
			})
			.catch(() => {
				redirect = true;
			});
	};

	return {
		...props,
		onClick() {
			fetchModalSignIn();

			if (signInLink && !signInRedirect) {
				if (redirect) {
					Liferay.Util.navigate(signInURL);

					return;
				}

				openModal({
					bodyHTML: html ? html : '<span class="loading-animation">',
					containerProps: {
						className: '',
					},
					onClose() {
						loading = false;
						redirect = false;
						html = '';
						modalOpen = false;
					},
					onOpen() {
						modalOpen = true;

						if (
							html &&
							document.querySelector('.loading-animation')
						) {
							setModalContent(html);
						}
					},
					size: 'md',
					title: Liferay.Language.get('sign-in'),
				});
			}
		},
	};
}
