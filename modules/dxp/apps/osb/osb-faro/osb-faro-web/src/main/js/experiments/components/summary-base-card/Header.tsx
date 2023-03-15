import React from 'react';
import Title from './Title';

interface SummaryBaseCardHeaderIProps
	extends React.HTMLAttributes<HTMLDivElement> {
	Description?: React.FC;
	title: string;
}

const SummaryBaseCardHeader: React.FC<SummaryBaseCardHeaderIProps> = ({
	Description,
	title
}) => (
	<div>
		<Title className='mb-2' label={title} />

		{Description && (
			<span className='font-size-sm font-weight-normal'>
				<Description />
			</span>
		)}
	</div>
);

export default SummaryBaseCardHeader;
