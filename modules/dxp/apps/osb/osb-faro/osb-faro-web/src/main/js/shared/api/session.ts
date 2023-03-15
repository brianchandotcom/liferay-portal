import FaroConstants from 'shared/util/constants';
import Promise from 'metal-promise';
import sendRequest from 'shared/util/request';
import {escapeSingleQuotes} from 'segment/segment-editor/dynamic/utils/odata';
import {RESTParams} from 'shared/types';

const {cur: defaultCur, delta: defaultDelta} = FaroConstants.pagination;

interface IFetchFieldValues extends RESTParams {
	channelId?: string;
	fieldName?: string;
	filter?: string;
}

export const fetchFieldValues = ({
	channelId = '',
	delta = defaultDelta,
	fieldName,
	filter,
	groupId,
	page = defaultCur,
	query
}: IFetchFieldValues): Promise<{
	disableSearch: boolean;
	items: string[];
	total: number;
}> =>
	sendRequest({
		data: {
			channelId,
			cur: page,
			delta,
			fieldName,
			filter,
			query: escapeSingleQuotes(query)
		},
		method: 'GET',
		path: `contacts/${groupId}/session/values`
	});
