/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
