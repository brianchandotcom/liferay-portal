import ClayIcon from '@clayui/icon';
import React from 'react';
import {CLASSNAME} from './index';

interface SummarySectionMetricTypeIProps
	extends React.HTMLAttributes<HTMLElement> {
	value: string;
}

const SummarySectionMetricType: React.FC<SummarySectionMetricTypeIProps> = ({
	value
}) => (
	<div className={`${CLASSNAME}-metric-type`}>
		<span className={`${CLASSNAME}-metric-type-icon`}>
			<ClayIcon className='icon-root' symbol='web-content' />
		</span>

		{value}
	</div>
);

export default SummarySectionMetricType;
