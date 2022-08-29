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

import classNames from 'classnames';

import i18n from '../../i18n';
import {getPercentLabel} from '../../util/graph.util';

type TaskbarProgress = {
	displayTotalCompleted: boolean;
	items: [string, number][];
	legend?: boolean;
	taskbarClassNames?: {
		[key: string]: string;
	};
	totalCompleted?: number;
};

const NaNToZero = (value: number) => (Number.isNaN(value) ? 0 : value);

const TaskbarProgress: React.FC<TaskbarProgress> = ({
	displayTotalCompleted,
	items,
	legend,
	taskbarClassNames = {
		blocked: 'blocked',
		failed: 'failed',
		incomplete: 'test-incomplete',
		other: 'others-completed',
		passed: 'passed',
		self: 'self-completed',
		test_fix: 'test-fix',
	},
	totalCompleted,
}) => {
	const total = items
		.map(([, value]) => value)
		.reduce((prevValue, currentValue) => prevValue + currentValue);

	return (
		<>
			<div className="testray-progress-bar">
				{items.map((item, index) => {
					const [label, value] = item;

					const percent = NaNToZero(
						(value / (total as number)) * 100
					);

					const percentLabel = getPercentLabel(
						(value / (total as number)) * 100
					);

					return (
						<div
							className={classNames(
								'progress-bar-item',
								taskbarClassNames[label]
							)}
							key={index}
							style={{width: `${percent}%`}}
							title={`${percentLabel} ${label}`}
						/>
					);
				})}
			</div>

			{legend && (
				<div className="d-flex testray-progress-bar">
					{displayTotalCompleted && (
						<div className="justify-content-between mr-5">
							<div className="align-items-center d-flex">
								<span className="font-family-sans-serif font-weight-semi-bold mr-1 text-paragraph-lg">
									{totalCompleted}
								</span>

								<span>/</span>

								<span className="font-family-sans-serif ml-1 text-paragraph-sm">
									{total}
								</span>
							</div>

							<span className="font-family-sans-serif legend-item-label text-neutral-6">
								{i18n.translate('total-completed')}
							</span>
						</div>
					)}

					{items.map((item, index) => {
						const [label, value] = item;

						const percentLabel = getPercentLabel(
							(value / (total as number)) * 100
						);

						const percentTitle = `${percentLabel} (${value})`;

						return (
							<div className="d-flex flex-column" key={index}>
								<div className="align-items-center d-flex mr-5">
									<div
										className={classNames(
											'legend-bar-item font-family-sans-serif',
											taskbarClassNames[label]
										)}
										title={percentTitle}
									/>

									<span
										className="font-family-sans-serif mx-2"
										title={percentTitle}
									>
										{percentTitle}
									</span>
								</div>

								<span className="legend-item-label mt-1 text-neutral-6">
									{label.toUpperCase()}
								</span>
							</div>
						);
					})}
				</div>
			)}
		</>
	);
};

export default TaskbarProgress;
