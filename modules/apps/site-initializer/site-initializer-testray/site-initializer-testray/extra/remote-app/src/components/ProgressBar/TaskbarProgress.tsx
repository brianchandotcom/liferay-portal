/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			<div className="tr-progress-bar">
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
								'tr-progress-bar__item',
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
				<div className="tr-progress-bar">
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

							<span className="tr-progress-bar__legend-item-label">
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
							<div
								className="d-flex flex-column mr-3"
								key={index}
							>
								<div className="align-items-center d-flex mr-5">
									<div
										className={classNames(
											'tr-progress-bar__legend-bar-item',
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

								<span className="tr-progress-bar__legend-item-label">
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
