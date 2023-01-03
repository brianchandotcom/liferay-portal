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

const getQuarter = (quarter) => {
	return (item) => {
		const month = new Date(item?.dateCreated).getMonth() + 1;

		if (quarter === 'quarter1') {
			return month === 1 || month === 2 || month === 3;
		} else if (quarter === 'quarter2') {
			return month === 4 || month === 5 || month === 6;
		} else if (quarter === 'quarter3') {
			return month === 7 || month === 8 || month === 9;
		} else if (quarter === 'quarter4') {
			return month === 10 || month === 11 || month === 12;
		}
	};
};

export default getQuarter;
