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

type InfoPanelType = {[keys: string]: string};

const sortedByDate = (itemA: InfoPanelType, itemB: InfoPanelType) => {
	if (itemA.date > itemB.date) {
		return -1;
	}

	if (itemA.date < itemB.date) {
		return 1;
	}

	return 0;
};

export default sortedByDate;
