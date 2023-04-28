import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import InfoPopover from 'shared/components/InfoPopover';
import React from 'react';
import {formatChange} from 'shared/util/change';
import {isFinite} from 'lodash';
import {sub} from 'shared/util/lang';

interface ITrendItemProps {
	change: number;
	data: number[];
	id: string;
	info?: {content: string; title: string};
	title: string;
	total: number;
}

export const TrendItem: React.FC<ITrendItemProps> = ({
	change,
	info,
	title,
	total
}) => {
	const finiteChange = isFinite(change);

	return (
		<div className='trend-item-root' key={title}>
			<div className='trend-item-title d-flex justify-content-between'>
				<h5 className='card-title'>{title}</h5>

				{info && <InfoPopover {...info} />}
			</div>

			<div className='d-flex align-items-center flex-grow-1 justify-content-center'>
				<div className='total'>{total.toLocaleString()}</div>
			</div>

			{!!total && (
				<div className='change description'>
					{sub(
						Liferay.Language.get('x-vs-previous-30-days'),
						[
							<span
								className={getCN({
									decrease: change < 0 && finiteChange,
									increase: change > 0 && finiteChange
								})}
								key='CHANGE'
							>
								{finiteChange && !!change && (
									<ClayIcon
										className='icon-root'
										symbol={
											change > 0
												? 'caret-top'
												: 'caret-bottom'
										}
									/>
								)}

								<b>
									{finiteChange
										? `${formatChange(change)}%`
										: '--'}
								</b>
							</span>
						],
						false
					)}
				</div>
			)}
		</div>
	);
};

const TypeTrend: React.FC<{items: ITrendItemProps[]}> = ({items}) => (
	<div className='type-trend-root'>
		{items.map((item, i) => (
			<TrendItem {...item} key={i} />
		))}
	</div>
);

TypeTrend.defaultProps = {
	items: []
};

export default TypeTrend;
