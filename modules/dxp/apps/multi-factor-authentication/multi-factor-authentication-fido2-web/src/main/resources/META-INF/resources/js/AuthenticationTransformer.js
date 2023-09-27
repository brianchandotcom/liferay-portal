/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as webauthn from './webauthn';

export default function AuthenticationTransformer({
	additionalProps: {assertionRequest},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onClick() {
			const button = window.document.activeElement;
			const form = button.form;

			if (!assertionRequest) {
				return;
			}

			assertionRequest = JSON.parse(assertionRequest);

			webauthn
				.get(assertionRequest.publicKeyCredentialRequestOptions)
				.then((value) => {
					const publicKey = webauthn.credentialToObject(value);

					if (
						publicKey.response.userHandle !== null &&
						publicKey.response.userHandle === ''
					) {
						delete publicKey.response.userHandle;
					}

					const responseInput = document.getElementById(
						`${portletNamespace}responseJSON`
					);

					responseInput.value = JSON.stringify(publicKey);

					form.submit();
				})
				.catch(() => {
					const messageContainer = document.getElementById(
						`${portletNamespace}messageContainer`
					);

					messageContainer.innerHTML = `<span class="alert alert-danger">
                    ${Liferay.Language.get(
						'your-authenticator-was-unable-to-verify-your-credential'
					)}
                </span>`;
				});
		},
	};
}
