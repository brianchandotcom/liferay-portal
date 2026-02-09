/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';

export default function ({namespace}) {
	const delegateHandler = delegate(
		document.body,
		'change',
		'input[type="checkbox"]',
		(event) => {
			const consentRenewalPeriod = document.querySelector(
				`input[type='number'][name='${namespace}consentRenewalPeriod']`
			);

			const explicitConsentMode = document.querySelector(
				`input[type='checkbox'][name='${namespace}explicitConsentMode']`
			);

			const storeConsent = document.querySelector(
				`input[type='checkbox'][name='${namespace}storeConsent']`
			);

			if (event.delegateTarget.id === `${namespace}enabled`) {
				if (event.delegateTarget.checked) {
					consentRenewalPeriod.removeAttribute('disabled');
					consentRenewalPeriod.required = true;
					explicitConsentMode.removeAttribute('disabled');

					if (Liferay.FeatureFlags['LPD-75032']) {
						storeConsent.removeAttribute('disabled');
					}
				}
				else {
					consentRenewalPeriod.required = false;
					consentRenewalPeriod.setAttribute('disabled', '');
					consentRenewalPeriod.value = 12;
					explicitConsentMode.checked = true;
					explicitConsentMode.setAttribute('disabled', '');

					if (Liferay.FeatureFlags['LPD-75032']) {
						storeConsent.checked = false;
						storeConsent.setAttribute('disabled', '');
					}
				}
			}
		}
	);

	return {
		dispose() {
			delegateHandler.dispose();
		},
	};
}
