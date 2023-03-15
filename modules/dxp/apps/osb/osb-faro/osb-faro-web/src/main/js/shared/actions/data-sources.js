import * as API from 'shared/api';
import {CALL_API} from '../middleware/api';
import {createActionTypes} from 'redux-toolbox';
import {dataSource} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('create', 'liferay_data_source'),
	...createActionTypes('create', 'salesforce_data_source'),
	...createActionTypes('delete', 'data_source'),
	...createActionTypes('fetch', 'data_source'),
	...createActionTypes('update', 'csv_data_source'),
	...createActionTypes('update', 'liferay_data_source'),
	...createActionTypes('update', 'salesforce_data_source')
};

export function createLiferayDataSource(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.dataSource.createLiferay,
				schema: dataSource,
				types: [
					actionTypes.CREATE_LIFERAY_DATA_SOURCE_REQUEST,
					actionTypes.CREATE_LIFERAY_DATA_SOURCE_SUCCESS,
					actionTypes.CREATE_LIFERAY_DATA_SOURCE_FAILURE
				]
			}
		},
		payload: {
			id: data.id
		},
		type: 'NO_OP'
	};
}

export function createSalesforceDataSource(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.dataSource.createSalesforce,
				schema: dataSource,
				types: [
					actionTypes.CREATE_SALESFORCE_DATA_SOURCE_REQUEST,
					actionTypes.CREATE_SALESFORCE_DATA_SOURCE_SUCCESS,
					actionTypes.CREATE_SALESFORCE_DATA_SOURCE_FAILURE
				]
			}
		},
		payload: {
			id: data.id
		},
		type: 'NO_OP'
	};
}

export function deleteDataSource({groupId, id}) {
	return {
		meta: {
			[CALL_API]: {
				data: {groupId, id},
				requestFn: API.dataSource.delete,
				types: [
					actionTypes.DELETE_DATA_SOURCE_REQUEST,
					actionTypes.DELETE_DATA_SOURCE_SUCCESS,
					actionTypes.DELETE_DATA_SOURCE_FAILURE
				]
			},
			id
		},
		payload: {
			id
		},
		type: 'NO_OP'
	};
}

export function fetchDataSource({groupId, id}) {
	return {
		meta: {
			[CALL_API]: {
				data: {groupId, id},
				requestFn: API.dataSource.fetch,
				schema: dataSource,
				types: [
					actionTypes.FETCH_DATA_SOURCE_REQUEST,
					actionTypes.FETCH_DATA_SOURCE_SUCCESS,
					actionTypes.FETCH_DATA_SOURCE_FAILURE
				]
			}
		},
		payload: {
			id
		},
		type: 'NO_OP'
	};
}

export function updateCSVDataSource(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.dataSource.updateCSV,
				schema: dataSource,
				types: [
					actionTypes.UPDATE_CSV_DATA_SOURCE_REQUEST,
					actionTypes.UPDATE_CSV_DATA_SOURCE_SUCCESS,
					actionTypes.UPDATE_CSV_DATA_SOURCE_FAILURE
				]
			}
		},
		payload: {
			id: data.id
		},
		type: 'NO_OP'
	};
}

export function updateLiferayDataSource(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.dataSource.updateLiferay,
				schema: dataSource,
				types: [
					actionTypes.UPDATE_LIFERAY_DATA_SOURCE_REQUEST,
					actionTypes.UPDATE_LIFERAY_DATA_SOURCE_SUCCESS,
					actionTypes.UPDATE_LIFERAY_DATA_SOURCE_FAILURE
				]
			}
		},
		payload: {
			id: data.id
		},
		type: 'NO_OP'
	};
}

export function updateSalesforceDataSource(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.dataSource.updateSalesforce,
				schema: dataSource,
				types: [
					actionTypes.UPDATE_SALESFORCE_DATA_SOURCE_REQUEST,
					actionTypes.UPDATE_SALESFORCE_DATA_SOURCE_SUCCESS,
					actionTypes.UPDATE_SALESFORCE_DATA_SOURCE_FAILURE
				]
			}
		},
		payload: {
			id: data.id
		},
		type: 'NO_OP'
	};
}
