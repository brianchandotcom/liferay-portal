import Description from './Description';
import getCN from 'classnames';
import Heading from './Heading';
import MetricType from './MetricType';
import ProgressBar from './ProgressBar';
import React from 'react';
import Variant from './Variant';

export const CLASSNAME = 'analytics-summary-section';

interface SummarySectionIProps extends React.HTMLAttributes<HTMLElement> {
	title: string;
}

const SummarySection: React.FC<SummarySectionIProps> & {
	Variant: typeof Variant;
	Description: typeof Description;
	Heading: typeof Heading;
	ProgressBar: typeof ProgressBar;
	MetricType: typeof MetricType;
} = ({children, className, title, ...otherProps}) => {
	const classes = getCN(CLASSNAME, className);

	return (
		<div className={classes} {...otherProps}>
			<div className={`${CLASSNAME}-title`}>{title}</div>

			{children}
		</div>
	);
};

SummarySection.Description = Description;
SummarySection.Variant = Variant;
SummarySection.Heading = Heading;
SummarySection.MetricType = MetricType;
SummarySection.ProgressBar = ProgressBar;

export default SummarySection;
