import React from 'react';

interface ISummaryBaseCardSubtitleProps
	extends React.HTMLAttributes<HTMLElement> {
	label: string;
}

const SummaryBaseCardSubtitle: React.FC<ISummaryBaseCardSubtitleProps> = ({
	label
}) => <div className='font-size-sm-1x mb-2 text-uppercase'>{label}</div>;

export default SummaryBaseCardSubtitle;
