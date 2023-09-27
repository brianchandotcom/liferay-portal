/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const compareArrays = (array1, array2) => {
	if (array1 === array2) {
		return true;
	}

	if (!array1 || !array2 || array1.length !== array2.length) {
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
