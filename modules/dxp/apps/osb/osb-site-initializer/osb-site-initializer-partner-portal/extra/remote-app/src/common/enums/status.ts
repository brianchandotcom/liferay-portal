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

export enum Status {
	DRAFT = 'Draft',
	PENDING = 'Pending Marketing Review',
	APPROVED = 'Approved',
	REQUEST_MORE_INFO = 'More Info Requested',
	REJECT = 'Rejected',
	EXPIRED = 'Expired',
	MARKETING_DIRECTOR_REVIEW = 'Marketing Director Review',
	CANCELED = 'Canceled',
	CLAIM_PAID = 'Claim Paid',
	IN_FINANCE_REVIEW = 'In Finance Review',
}
