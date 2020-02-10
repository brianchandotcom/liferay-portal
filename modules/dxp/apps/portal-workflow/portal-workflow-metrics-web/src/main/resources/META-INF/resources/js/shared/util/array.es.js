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

const compareArrays = (array1, array2) => {
	if (array1 === array2) {
		return true;
	}

	if (!array1 || !array2 || array1.length != array2.length) {
		return false;
	}

	return array1.reduce(
		(acc, cur, index) => !!acc && cur === array2[index],
		true
	);
};

const paginateArray = (array, page, pageSize) => {
	return array.slice((page - 1) * pageSize, page * pageSize);
};

export {compareArrays, paginateArray};
