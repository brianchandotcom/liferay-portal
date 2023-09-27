/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const organizationStatusHandler = document.querySelector(
	'.organizationStatusHandler div div div div div input'
);
organizationStatusHandler.setAttribute('value', 'Awaiting Approval On EVP');

const requestStatus = document.querySelector('[name="organizationStatus"]');
requestStatus.setAttribute('value', 'awaitingApprovalOnEvp');

const organizationStatusLabel = document.querySelector(
	'[name="organizationStatus-label"]'
);
organizationStatusLabel.setAttribute('value', 'Awaiting Approval On EVP');
