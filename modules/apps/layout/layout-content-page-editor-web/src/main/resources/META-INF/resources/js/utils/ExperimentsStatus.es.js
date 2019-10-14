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

/**
 * This code lives also in `segments/segments-experiment-web` module
 */
export const STATUS_COMPLETED = 2;
export const STATUS_DRAFT = 0;
export const STATUS_FINISHED_NO_WINNER = 4;
export const STATUS_FINISHED_WINNER = 3;
export const STATUS_PAUSED = 5;
export const STATUS_RUNNING = 1;
export const STATUS_SCHEDULED = 7;
export const STATUS_TERMINATED = 6;

/**
 * Maps the Experiment status code to its label style
 */
const STATUS_TO_TYPE = {
	[STATUS_COMPLETED]: 'success',
	[STATUS_DRAFT]: undefined,
	[STATUS_FINISHED_NO_WINNER]: undefined,
	[STATUS_FINISHED_WINNER]: 'success',
	[STATUS_PAUSED]: 'warning',
	[STATUS_RUNNING]: 'info',
	[STATUS_SCHEDULED]: 'warning',
	[STATUS_TERMINATED]: 'danger'
};

const statusToLabelDisplayType = status => STATUS_TO_TYPE[status];

export default statusToLabelDisplayType;
