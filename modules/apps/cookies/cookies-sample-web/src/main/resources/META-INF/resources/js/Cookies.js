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
