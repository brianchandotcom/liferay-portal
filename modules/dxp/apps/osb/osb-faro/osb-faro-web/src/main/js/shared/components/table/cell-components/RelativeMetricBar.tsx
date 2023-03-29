import MetricBar, {Sizes} from 'shared/components/MetricBar';
import React, {FC} from 'react';
import TextTruncate from 'shared/components/TextTruncate';
import {round} from 'lodash';

interface IRelativeMetricBarProps extends React.HTMLAttributes<HTMLElement> {
	data: {
		count: number;
		name: string;
	};
	empty?: boolean;
	maxCount?: number;
	showName?: boolean;
	total?: number;
	totalCount?: number;
}

const RelativeMetricBar: FC<IRelativeMetricBarProps> = ({
	data: {count, name},
	empty = false,
	showName = false,
	totalCount
}) => {
	const percent = round(count / totalCount, 2);

	const displayName = showName ? name : '';

	return (
		<td className='table-cell-expand relative-metric-bar-root'>
			<MetricBar percent={percent} size={Sizes.Lg}>
				<TextTruncate className='title' title={displayName} />

				{!empty && <span className='count'>{count}</span>}
			</MetricBar>
		</td>
	);
};

export default RelativeMetricBar;
