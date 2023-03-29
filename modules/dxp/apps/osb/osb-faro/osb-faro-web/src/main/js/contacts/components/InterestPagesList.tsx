import * as API from 'shared/api';
import React from 'react';
import SearchableEntityTable from 'shared/components/SearchableEntityTable';
import {
	createOrderIOMap,
	TITLE,
	UNIQUE_VISITS_COUNT,
	URL
} from 'shared/util/pagination';
import {pagesListColumns} from 'shared/util/table-columns';
import {Routes} from 'shared/util/router';
import {useQueryPagination} from 'shared/hooks';

const PAGES_ORDER_BY_OPTIONS = [
	{
		label: Liferay.Language.get('page-title'),
		value: TITLE
	},
	{
		label: Liferay.Language.get('url'),
		value: URL
	}
];

interface IInterestPages {
	channelId?: string;
	groupId?: string;
	dataSourceFn: () => void;
	dataSourceParams: any;
	entityLabel: string;
	rowIdentifier: string[];
}

const ActivePagesList: React.FC<IInterestPages> = ({
	channelId,
	groupId,
	...otherProps
}) => {
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(UNIQUE_VISITS_COUNT)
	});

	return (
		<SearchableEntityTable
			{...otherProps}
			columns={[
				pagesListColumns.getTitleUrl({
					channelId,
					groupId,
					route: Routes.SITES_TOUCHPOINTS_OVERVIEW
				}),
				pagesListColumns.url,
				pagesListColumns.viewCount
			]}
			delta={delta}
			orderByOptions={[
				...PAGES_ORDER_BY_OPTIONS,
				{
					label: Liferay.Language.get('views'),
					value: UNIQUE_VISITS_COUNT
				}
			]}
			orderIOMap={orderIOMap}
			page={page}
			query={query}
		/>
	);
};
const InactivePagesList: React.FC<IInterestPages> = ({
	channelId,
	groupId,
	...otherProps
}) => {
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(URL)
	});

	return (
		<SearchableEntityTable
			{...otherProps}
			columns={[
				pagesListColumns.getTitleUrl({
					channelId,
					groupId,
					route: Routes.SITES_TOUCHPOINTS_OVERVIEW
				}),
				pagesListColumns.url,
				pagesListColumns.inactiveViewCount
			]}
			delta={delta}
			orderByOptions={PAGES_ORDER_BY_OPTIONS}
			orderIOMap={orderIOMap}
			page={page}
			query={query}
		/>
	);
};

const InterestPagesList = ({dataSourceParams, ...otherProps}) => {
	const {active} = dataSourceParams;

	const PagesListComponent = active ? ActivePagesList : InactivePagesList;

	return (
		<PagesListComponent
			{...otherProps}
			dataSourceFn={API.pagesVisited.search}
			dataSourceParams={dataSourceParams}
			entityLabel={Liferay.Language.get('pages')}
			rowIdentifier={['url', 'dataSourceId']}
		/>
	);
};

export default InterestPagesList;
