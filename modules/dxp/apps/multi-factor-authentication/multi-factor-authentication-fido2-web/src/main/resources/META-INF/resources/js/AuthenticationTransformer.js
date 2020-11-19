/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
			const button = this.document.activeElement;
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
