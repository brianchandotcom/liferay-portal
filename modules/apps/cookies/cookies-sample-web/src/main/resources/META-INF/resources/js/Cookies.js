/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {
	checkCookieConsentForTypes,
	openCookieConsentModal,
} from '@liferay/cookies-banner-web';
import {COOKIE_TYPES, openAlertModal} from 'frontend-js-web';
import React from 'react';

const Cookie = () => {
	return (
		<>
			<ClayButton
				displayType="secondary"
				onClick={() => {
					openCookieConsentModal({});
				}}
			>
				Default Modal
			</ClayButton>

			<ClayButton
				onClick={() => {
					openCookieConsentModal({
						alertDisplayType: 'info',
						alertMessage:
							'This feature requires functional cookies to be accepted',
						customTitle: 'This feature uses non-essential cookies',
					});
				}}
			>
				Modified Modal
			</ClayButton>
			<ClayButton
				onClick={() => {
					checkCookieConsentForTypes(
						[COOKIE_TYPES.FUNCTIONAL, COOKIE_TYPES.PERFORMANCE],
						{
							alertMessage:
								'This feature requires functional and performance cookies to be accepted',
							customTitle:
								'This feature uses non-essential cookies',
						}
					)
						.then(() => {
							openAlertModal({
								message:
									'All needed cookies are accepted, thank you!',
								onConfirm: () => {},
							});
						})
						.catch(() => {
							openAlertModal({
								message:
									'Seems that you still need to accept some cookies...',
								onConfirm: () => {},
							});
						});
				}}
			>
				Check consent for functional and personalization cookies
			</ClayButton>
		</>
	);
};

export default Cookie;
