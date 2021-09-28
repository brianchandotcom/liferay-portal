import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import {ProgressRing} from '../ProgressRing';

export const StepItem = ({
	children,
	onClick,
	percentage = 0,
	selected = false,
}) => {
	const completed = percentage === 100;
	const partially = percentage !== 0;

	return (
		<div
			className={classNames('step-item', {
				completed,
				partially,
				selected,
			})}
			onClick={partially ? onClick : undefined}
		>
			<i>
				{partially && (
					<ProgressRing
						className="progress-ring"
						diameter={32}
						percent={percentage}
						strokeWidth={3}
					/>
				)}
			</i>
			{completed && (
				<div>
					<ClayIcon symbol="check" />
				</div>
			)}
			{children}
		</div>
	);
};
