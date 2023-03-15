import React, {useRef} from 'react';
import Table from 'shared/components/table';
import TextTruncate from 'shared/components/TextTruncate';
import {Dictionary, pickBy} from 'lodash';
import {getUrl as getUrlUtil} from 'shared/util/urls';
import {Link, useParams} from 'react-router-dom';
import {Routes} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {toFixedPoint} from 'shared/util/numbers';
import {useQueryRangeSelectors} from 'shared/hooks';

interface ITouchpointRouter {
	params: {
		channelId: string;
		groupId: string;
		title: string;
		touchpoint: string;
	};
	query: Dictionary<string>;
}

const MAX_PAGES_LIMIT = 1000;
type Item = {
	title: string;
	touchpoint: string;
};
interface ITouchpointsListCardProps
	extends React.HTMLAttributes<HTMLDivElement> {
	items?: Array<Item>;
}

const TouchpointsListCard: React.FC<ITouchpointsListCardProps> = ({items}) => {
	const {channelId, groupId} = useParams();
	const rangeSelectors = useQueryRangeSelectors();

	const elementRef = useRef(null);

	const getUrl = ({title, touchpoint}: Item): string => {
		const router: ITouchpointRouter = {
			params: {
				channelId,
				groupId,
				title,
				touchpoint: encodeURIComponent(touchpoint)
			},
			query: {
				...pickBy(rangeSelectors)
			}
		};

		return getUrlUtil(Routes.SITES_TOUCHPOINTS_OVERVIEW, router);
	};

	const renderTitleColumn = ({
		title,
		touchpoint
	}: Item): React.ReactElement => {
		const url = getUrl({title, touchpoint});
		return (
			<td className='table-cell-expand'>
				<Link
					className='font-weight-semibold text-truncate-inline text-dark'
					to={url}
				>
					<TextTruncate title={title} />
				</Link>
			</td>
		);
	};

	const renderTouchpointColumn = ({
		title,
		touchpoint
	}: Item): React.ReactElement => {
		const url = getUrl({title, touchpoint});
		return (
			<td className='table-cell-expand'>
				<Link className='text-secondary text-truncate-inline' to={url}>
					<TextTruncate title={touchpoint} />
				</Link>
			</td>
		);
	};

	const tableColumns = [
		{
			accessor: 'title',
			cellRenderer: ({data}) => renderTitleColumn(data),
			label: Liferay.Language.get('page-name'),
			sortable: false,
			title: true
		},
		{
			accessor: 'url',
			cellRenderer: ({data}) => renderTouchpointColumn(data),
			label: Liferay.Language.get('canonical-url'),
			sortable: false
		}
	];

	return (
		<div className='analytics-touchpoints-list' ref={elementRef}>
			<Table
				className='table-hover'
				columns={tableColumns}
				items={items}
				rowIdentifier={['touchpoint', 'title']}
			/>
			{items?.length >= MAX_PAGES_LIMIT && (
				<p>
					{sub(
						Liferay.Language.get('x-page-limit-has-been-reached'),
						[toFixedPoint(MAX_PAGES_LIMIT)]
					)}
				</p>
			)}
		</div>
	);
};

export default TouchpointsListCard;
