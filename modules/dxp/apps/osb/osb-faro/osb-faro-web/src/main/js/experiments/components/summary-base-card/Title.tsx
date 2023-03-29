import getCN from 'classnames';
import React from 'react';

interface ISummaryBaseCardTitleProps extends React.HTMLAttributes<HTMLElement> {
	className?: string;
	label: string;
}

const SummaryBaseCardTitle: React.FC<ISummaryBaseCardTitleProps> = ({
	className,
	label
}) => <h3 className={getCN('font-weight-bold', className)}>{label}</h3>;

export default SummaryBaseCardTitle;
