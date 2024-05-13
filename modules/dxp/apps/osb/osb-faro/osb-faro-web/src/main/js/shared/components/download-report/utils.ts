import moment from 'moment';
import {buildOrderByFields} from 'shared/util/pagination';
import {DEFAULT_DATE_FORMAT} from 'shared/util/date';
import {DEFAULT_RANGE_SELECTORS} from 'shared/hooks/useQueryRangeSelectors';
import {INDIVIDUALS} from 'shared/util/router';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {useParams} from 'react-router-dom';

export function formatDate(date) {
	return moment(date).format(DEFAULT_DATE_FORMAT);
}

export enum CSVType {
	Individual = 'individual',
	Blog = 'blog',
	Document = 'document',
	Form = 'form',
	Journal = 'journal',
	Event = 'event',
	Page = 'page'
}

export function useDownloadCSV({
	assetId,
	assetType,
	type
}: {
	assetId?: string;
	assetType?: string;
	type: CSVType;
}) {
	const {channelId, groupId, title} = useParams();

	return initialRangeSelectors => {
		const rangeSelectors = initialRangeSelectors || DEFAULT_RANGE_SELECTORS;
		const searchParams = new URLSearchParams(location.search);

		const field = searchParams.get('field');
		const query = searchParams.get('query');
		const sortOrder = searchParams.get('sortOrder');

		let url = `/o/faro/main/${groupId}/reports/export/csv/${type}?channelId=${channelId}`;

		if (rangeSelectors.rangeKey === RangeKeyTimeRanges.CustomRange) {
			url += '&rangeKey=CUSTOM';
			url += `&fromDate=${formatDate(rangeSelectors?.rangeStart)}`;
			url += `&toDate=${formatDate(rangeSelectors?.rangeEnd)}`;
		} else {
			url += `&rangeKey=${rangeSelectors.rangeKey}`;
		}

		if (assetId) {
			url += `&assetId=${encodeURIComponent(assetId)}`;
		}

		if (title) {
			url += `&assetTitle=${title}`;
		}

		if (assetType) {
			url += `&assetType=${assetType}`;
		}

		if (field && sortOrder) {
			const orderByFields = JSON.stringify(
				buildOrderByFields({field, sortOrder}, INDIVIDUALS)
			);

			url += `&orderByFields=${encodeURIComponent(orderByFields)}`;
		}

		if (query) {
			url += `&query=${query}`;
		}

		return url;
	};
}

export const MAX_CSV_ENTRIES = 10000;
