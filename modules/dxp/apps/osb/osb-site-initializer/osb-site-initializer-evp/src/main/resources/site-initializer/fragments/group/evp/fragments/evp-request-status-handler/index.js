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

const EVPRequestStatus = document.querySelector(
	'.EVPRequestStatus div div div div div input'
);

EVPRequestStatus.setAttribute('value', 'Awaiting Approval On Evp');

const requestStatus = document.querySelector('[name="requestStatus"]');
requestStatus.setAttribute('value', 'awaitingApprovalOnEvp');

const requestStatusLabel = document.querySelector(
	'[name="requestStatus-label"]'
);
requestStatusLabel.setAttribute('value', 'Awaiting Approval On Evp');
