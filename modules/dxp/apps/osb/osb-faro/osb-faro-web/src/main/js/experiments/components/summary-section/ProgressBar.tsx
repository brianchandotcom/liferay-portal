import getCN from 'classnames';
import React from 'react';
import {CLASSNAME} from './index';

interface ISummarySectionProgressBarProps
	extends React.HTMLAttributes<HTMLElement> {
	value: number;
}

const SummarySectionProgressBar: React.FC<ISummarySectionProgressBarProps> = ({
	className,
	value
}) => {
	const classes = getCN(className, `${CLASSNAME}-progress`, {
		complete: value === 100
	});

	return (
		<div className={classes}>
			<div
				className={`${CLASSNAME}-progress-bar`}
				style={{width: `${value}%`}}
			/>
		</div>
	);
};

export default SummarySectionProgressBar;
