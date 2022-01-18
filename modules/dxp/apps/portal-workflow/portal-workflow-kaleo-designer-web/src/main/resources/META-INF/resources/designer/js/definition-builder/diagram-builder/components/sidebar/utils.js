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

function isIdDuplicated(elements, id) {
	let duplicated = false;

	elements.map((element) => {
		if (element.id === id) {
			duplicated = true;
		}
	});

	return duplicated;
}

function getModalInfo(itemType) {
	if (itemType === 'condition') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-condition-node'
			),
			title: Liferay.Language.get('delete-condition-node'),
		};
	}
	if (itemType === 'end') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-end-node'
			),
			title: Liferay.Language.get('delete-end-node'),
		};
	}
	else if (itemType === 'fork') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-fork-node'
			),
			title: Liferay.Language.get('delete-fork-node'),
		};
	}
	else if (itemType === 'join') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-join-node'
			),
			title: Liferay.Language.get('delete-join-node'),
		};
	}
	else if (itemType === 'join-xor') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-join-xor-node'
			),
			title: Liferay.Language.get('delete-join-xor-node'),
		};
	}
	else if (itemType === 'start') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-start-node'
			),
			title: Liferay.Language.get('delete-start-node'),
		};
	}
	else if (itemType === 'state') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-state-node'
			),
			title: Liferay.Language.get('delete-state-node'),
		};
	}
	else if (itemType === 'task') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-task-node'
			),
			title: Liferay.Language.get('delete-task-node'),
		};
	}
	else if (itemType === 'transition') {
		return {
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-transition'
			),
			title: Liferay.Language.get('delete-transition'),
		};
	}
	else {
		return {};
	}
}

export {getModalInfo, isIdDuplicated};
