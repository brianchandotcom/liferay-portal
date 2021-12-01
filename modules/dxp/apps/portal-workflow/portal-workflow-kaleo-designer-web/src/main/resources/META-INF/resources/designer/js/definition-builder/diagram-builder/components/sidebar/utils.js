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

function getModalInfo(nodeType) {
	if (nodeType === 'end') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-end-node'
			),
			title: Liferay.Language.get('delete-end-node'),
		};
	}
	else if (nodeType === 'start') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-start-node'
			),
			title: Liferay.Language.get('delete-start-node'),
		};
	}
	else if (nodeType === 'state') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-state-node'
			),
			title: Liferay.Language.get('delete-state-node'),
		};
	}
	else {
		return {};
	}
}

export {getModalInfo};
