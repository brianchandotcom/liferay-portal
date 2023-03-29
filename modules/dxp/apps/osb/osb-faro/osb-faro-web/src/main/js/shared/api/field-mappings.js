import FaroConstants from 'shared/util/constants';
import sendRequest from 'shared/util/request';

const {
	cur: DEFAULT_PAGE,
	delta: DEFAULT_DELTA,
	orderDefault
} = FaroConstants.pagination;

export function fetch({fieldMappingFieldName, groupId}) {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/field_mapping/${fieldMappingFieldName}`
	});
}

export function fetchDefault(groupId) {
	return search({groupId, query: 'jobTitle'}).then(({items}) => {
		if (items && items.length) {
			return items[0];
		}
		throw new Error('No default field mapping exists');
	});
}

export function fetchSuggestions({cur, delta, groupId, query}) {
	return sendRequest({
		data: {
			cur,
			delta,
			query
		},
		method: 'GET',
		path: `contacts/${groupId}/field_mapping/suggestions`
	});
}

export function create({groupId, name, type}) {
	return sendRequest({
		data: {
			name,
			type
		},
		method: 'POST',
		path: `contacts/${groupId}/field_mapping`
	});
}

export function search(params) {
	const {
		context,
		delta = DEFAULT_DELTA,
		fieldMappingFieldName = '',
		groupId,
		orderByType = orderDefault,
		ownerType = '',
		page = DEFAULT_PAGE,
		query = ''
	} = params;

	return sendRequest({
		data: {
			context,
			cur: page,
			delta,
			fieldMappingFieldName,
			orderByType,
			ownerType,
			query
		},
		method: 'GET',
		path: `contacts/${groupId}/field_mapping`
	});
}
