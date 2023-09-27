/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as webauthn from './webauthn';

export default function RegistrationTransformer({
	additionalProps: {pkccOptions},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onClick() {
			const button = window.document.activeElement;
			const form = button.form;

			if (!pkccOptions) {
				return;
			}

			webauthn
				.create(JSON.parse(pkccOptions))
				.then((value) => {
					const responseInput = document.getElementById(
						`${portletNamespace}responseJSON`
					);

					const publicKeyCredentials = webauthn.credentialToObject(
						value
					);

					responseInput.value = JSON.stringify(publicKeyCredentials);

					form.submit();
				})
				.catch(() => {
					const messageContainer = document.getElementById(
						`${portletNamespace}messageContainer`
					);

					messageContainer.innerHTML = `<span class="alert alert-danger">
                        ${Liferay.Language.get(
							'your-authenticator-was-unable-to-create-a-credential'
						)}
                    </span>`;
				});
		},
	};
}
