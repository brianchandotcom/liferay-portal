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
