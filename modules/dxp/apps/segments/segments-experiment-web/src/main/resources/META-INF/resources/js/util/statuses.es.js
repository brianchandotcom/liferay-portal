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

export const STATUS_COMPLETED = 2;
export const STATUS_DRAFT = 0;
export const STATUS_FINISHED_NO_WINNER = 4;
export const STATUS_FINISHED_WINNER = 3;
export const STATUS_PAUSED = 5;
export const STATUS_RUNNING = 1;
export const STATUS_SCHEDULED = 7;
export const STATUS_TERMINATED = 6;

const STATUS_TO_TYPE = {
	[STATUS_COMPLETED]: 'success',
	[STATUS_DRAFT]: 'secondary',
	[STATUS_FINISHED_NO_WINNER]: 'secondary',
	[STATUS_FINISHED_WINNER]: 'success',
	[STATUS_PAUSED]: 'warning',
	[STATUS_RUNNING]: 'primary',
	[STATUS_SCHEDULED]: 'warning',
	[STATUS_TERMINATED]: 'danger',
};

export const statusToLabelDisplayType = (status) => STATUS_TO_TYPE[status];
