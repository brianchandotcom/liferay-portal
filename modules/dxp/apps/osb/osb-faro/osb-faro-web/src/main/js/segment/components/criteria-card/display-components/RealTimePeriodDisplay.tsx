import React from 'react';
import {Criterion} from 'segment/segment-editor/dynamic/utils/types';

const RealTimePeriodDisplay: React.FC<{
	conjunctionCriterion: Criterion;
}> = ({conjunctionCriterion}) => {
	const {value} = conjunctionCriterion;

	const [interval, timeWindow] = value.split('_');

	return (
		<span>
			{Liferay.Language.get('in-the-last').toLowerCase()}
			<strong className='ml-1'>
				{interval} {timeWindow}
			</strong>
		</span>
	);
};

export default RealTimePeriodDisplay;
