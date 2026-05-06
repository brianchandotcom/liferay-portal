import FaroConstants from 'shared/util/constants';
import sendRequest from 'shared/util/request';
import {ACCOUNTS} from 'shared/util/router';
import {buildOrderByFields} from 'shared/util/pagination';
import {escapeSingleQuotes} from 'segment/segment-editor/dynamic/utils/odata';

const {
	pagination: {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA}
} = FaroConstants;

const USE_MOCK_ACCOUNT_FETCH = true;
const USE_MOCK_ACCOUNT_DETAILS = true;

const MOCK_ACCOUNT_DETAILS = {
	fields: [
		{
			dataSourceId: 'ds-1',
			dataSourceName: 'Salesforce Production',
			lastModified: '2024-11-20T08:30:00.000Z',
			name: 'website',
			sourceName: 'Web',
			value: 'https://acme.com'
		},
		{
			dataSourceId: 'ds-1',
			dataSourceName: 'Salesforce Production',
			lastModified: '2024-10-15T10:00:00.000Z',
			name: 'industry',
			sourceName: 'CRM',
			value: 'Technology'
		},
		{
			dataSourceId: 'ds-2',
			dataSourceName: 'HubSpot',
			lastModified: '2024-09-10T14:00:00.000Z',
			name: 'annualRevenue',
			sourceName: 'Financial',
			value: '5000000'
		},
		{
			dataSourceId: 'ds-3',
			dataSourceName: 'Workday',
			lastModified: '2024-08-05T09:00:00.000Z',
			name: 'numberOfEmployees',
			sourceName: 'HR',
			value: '250'
		},
		{
			dataSourceId: 'ds-1',
			dataSourceName: 'Salesforce Production',
			lastModified: '2024-07-22T11:00:00.000Z',
			name: 'country',
			sourceName: 'Geo',
			value: 'United States'
		},
		{
			dataSourceId: 'ds-2',
			dataSourceName: 'HubSpot',
			lastModified: '2024-06-30T16:45:00.000Z',
			name: 'accountType',
			sourceName: 'CRM',
			value: 'Customer'
		}
	]
};

export function fetch({accountId, channelId, groupId}) {
	if (USE_MOCK_ACCOUNT_FETCH) {
		return Promise.resolve({accountName: 'Dickenson plc'});
	}

	return sendRequest({
		data: {channelId},
		method: 'GET',
		path: `contacts/${groupId}/account/${accountId}`
	});
}

export function fetchDetails({accountId, channelId, groupId}) {
	if (USE_MOCK_ACCOUNT_DETAILS) {
		return Promise.resolve(MOCK_ACCOUNT_DETAILS);
	}

	return sendRequest({
		data: {channelId},
		method: 'GET',
		path: `contacts/${groupId}/account/${accountId}/details`
	});
}

export function fetchFieldValues({
	channelId,
	fieldMappingFieldName,
	groupId,
	query
}) {
	return sendRequest({
		data: {
			channelId,
			delta: DEFAULT_DELTA,
			fieldMappingFieldName,
			query: escapeSingleQuotes(query)
		},
		method: 'GET',
		path: `contacts/${groupId}/account/field_values`
	});
}

export function fetchMetrics({groupId}) {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account/metrics`
	});
}

export function search({
	channelId = '',
	delta = DEFAULT_DELTA,
	groupId,
	orderIOMap,
	page = DEFAULT_PAGE,
	query = '',
	...otherParams
}) {
	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams, ACCOUNTS);

	return sendRequest({
		data: {
			channelId,
			cur: page,
			delta,
			orderByFields,
			query,
			...otherParams
		},
		method: 'POST',
		path: `contacts/${groupId}/account/search`
	});
}
