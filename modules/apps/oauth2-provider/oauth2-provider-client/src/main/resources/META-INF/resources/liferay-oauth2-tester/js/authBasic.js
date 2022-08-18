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

/*
 * Basic Authorization Code Grand Flow
 * Step 1: Obtain Authorization Code from Liferay Configured Application with ClientID (Callback URL must be this application)
 */
// eslint-disable-next-line no-unused-vars
function getAuthorizationCode() {
	if ($('#authForm').valid()) {
		const clientId = $('#clientId').val();
		const liferayUrl = $('#liferayUrl').val();

		const redirectUri = encodeURIComponent(
			window.location.href +
				'?url=' +
				liferayUrl +
				'&client_id=' +
				clientId
		);
		window.location.href =
			$('#liferayUrl').val() +
			$('#authorizeUrl').val() +
			'?client_id={0}&response_type=code&redirect_uri={1}'
				.replace('{0}', clientId)
				.replace('{1}', redirectUri);
	}
}
