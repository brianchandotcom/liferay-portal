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

const ROLE = {
	ADMINISTRATOR: 'Administrator',
	EVP_MANAGER: 'EVP Manager',
	FINANCE_USER: 'Finance User',
};

const userRoles = document.querySelector('.userRoles').value;
const fieldEVPRequestsListing = document.querySelector('.EVPRequestsListing');

if (
	![ROLE.EVP_MANAGER, ROLE.FINANCE_USER, ROLE.ADMINISTRATOR].includes(
		userRoles
	)
) {
	fieldEVPRequestsListing.style.display = 'none';
}
