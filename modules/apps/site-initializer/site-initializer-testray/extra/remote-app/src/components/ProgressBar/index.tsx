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

type ProgressBarProps = {
	blocked: number;
	failed: number;
	incomplete: number;
	passed: number;
	test_fix: number;
};

const ProgressBar: React.FC<ProgressBarProps> = (props) => {
	const {failed, incomplete, passed, test_fix} = props;

	const total = failed + incomplete + passed + test_fix;

	return (
		<div className="testray-progress-bar">
			{['failed', 'incomplete', 'passed', 'test_fix'].map((type) => {
				const keyValue = (props as any)[type];
				const percent = Math.ceil((keyValue * 100) / total);

				return (
					<div
						className={classNames('progress-bar-item', {
							'failed': type === 'failed',
							'passed': type === 'passed',
							'test-fix': type === 'test_fix',
							'test-incomplete': type === 'incomplete',
						})}
						key={type}
						style={{width: `${percent}%`}}
						title={`${percent}% ${type}`}
					></div>
				);
			})}
		</div>
	);
};

export default ProgressBar;
