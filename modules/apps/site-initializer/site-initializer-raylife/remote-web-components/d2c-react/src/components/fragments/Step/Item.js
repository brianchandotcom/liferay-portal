import React from 'react';

import {ProgressRing} from '../ProgressRing';

export const StepItem = ({children, percentage = 0, selected = false}) => {
	return (
		<div className={`step-item ${selected && 'selected'}`}>
			<i>
				{selected && (
					<ProgressRing
						className="progress-ring"
						diameter={32}
						percent={percentage}
						strokeWidth={3}
					/>
				)}
			</i>
			{children}
		</div>
	);
};
