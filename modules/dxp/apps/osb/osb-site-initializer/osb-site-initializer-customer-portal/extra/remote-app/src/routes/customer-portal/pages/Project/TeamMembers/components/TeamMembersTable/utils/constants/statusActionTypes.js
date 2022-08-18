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

const STATUS_ACTION_TYPES = {
	onEditSuccess: {
		message: 'User was modified successfully.',
		type: 'success',
	},
	onRemoveFailure: {
		message: 'Error while removing user, try again later.',
		type: 'danger',
	},
	onRemoveSuccess: {
		message: 'User was removed successfully.',
		type: 'success',
	},
};

export {STATUS_ACTION_TYPES};
