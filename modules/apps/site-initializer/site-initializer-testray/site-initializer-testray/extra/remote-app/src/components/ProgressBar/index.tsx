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

import {useMemo} from 'react';

import {Progress, Tasks} from '../../util/mock';
import TaskbarProgress from './TaskbarProgress';

type ProgressBarProps = {
	displayTotalCompleted?: boolean;
	items: Tasks | Progress;
	legend?: boolean;
};

const ProgressBar: React.FC<ProgressBarProps> = ({
	displayTotalCompleted = true,
	items,
	legend,
}) => {
	const sortedItems = Object.entries(items).sort(
		([, valueA], [, valueB]) => valueB - valueA
	);

	const totalCompleted = useMemo(() => {
		const _totalCompleted = sortedItems
			.filter(([label, value]) => {
				if (label !== 'incomplete') {
					return value;
				}
			})
			.map(([, value]) => value);

		if (_totalCompleted.length) {
			return _totalCompleted.reduce(
				(previous, current) => previous + current
			);
		}

		return 0;
	}, [sortedItems]);

	return (
		<TaskbarProgress
			displayTotalCompleted={displayTotalCompleted}
			items={sortedItems}
			legend={legend}
			totalCompleted={totalCompleted}
		/>
	);
};

export default ProgressBar;
