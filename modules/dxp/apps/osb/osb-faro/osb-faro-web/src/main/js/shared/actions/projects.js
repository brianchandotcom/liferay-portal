import * as API from 'shared/api';
import {CALL_API} from '../middleware/api';
import {createActionTypes} from 'redux-toolbox';
import {project, projects} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('configure', 'project'),
	...createActionTypes('create', 'project'),
	...createActionTypes('fetch', 'project'),
	...createActionTypes('fetch', 'projects'),
	...createActionTypes('fetch', 'project_via_corp_project_uuid'),
	...createActionTypes('update', 'project')
};

export function createProject(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.projects.create,
				schema: project(),
				types: [
					actionTypes.CREATE_PROJECT_REQUEST,
					actionTypes.CREATE_PROJECT_SUCCESS,
					actionTypes.CREATE_PROJECT_FAILURE
				]
			}
		},
		type: 'NO_OP'
	};
}

export function configureProject(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.projects.configure,
				schema: project(),
				types: [
					actionTypes.CONFIGURE_PROJECT_REQUEST,
					actionTypes.CONFIGURE_PROJECT_SUCCESS,
					actionTypes.CONFIGURE_PROJECT_FAILURE
				]
			}
		},
		type: 'NO_OP'
	};
}

export function createTrialProject(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.projects.createTrial,
				schema: project(),
				types: [
					actionTypes.CREATE_PROJECT_REQUEST,
					actionTypes.CREATE_PROJECT_SUCCESS,
					actionTypes.CREATE_PROJECT_FAILURE
				]
			}
		},
		type: 'NO_OP'
	};
}

export function fetchProject({groupId}) {
	return {
		meta: {
			[CALL_API]: {
				data: {groupId},
				requestFn: API.projects.fetch,
				schema: project(groupId),
				types: [
					actionTypes.FETCH_PROJECT_REQUEST,
					actionTypes.FETCH_PROJECT_SUCCESS,
					actionTypes.FETCH_PROJECT_FAILURE
				]
			}
		},
		payload: {
			id: groupId
		},
		type: 'NO_OP'
	};
}

export function fetchProjects() {
	return {
		meta: {
			[CALL_API]: {
				requestFn: API.projects.fetchMany,
				schema: projects,
				types: [
					actionTypes.FETCH_PROJECTS_REQUEST,
					actionTypes.FETCH_PROJECTS_SUCCESS,
					actionTypes.FETCH_PROJECTS_FAILURE
				]
			}
		},
		type: 'NO_OP'
	};
}

export function fetchProjectViaCorpProjectUuid({corpProjectUuid}) {
	return {
		meta: {
			[CALL_API]: {
				data: {corpProjectUuid},
				requestFn: API.projects.fetchProjectViaCorpProjectUuid,
				schema: project(corpProjectUuid),
				types: [
					actionTypes.FETCH_PROJECT_VIA_CORP_PROJECT_UUID_REQUEST,
					actionTypes.FETCH_PROJECT_VIA_CORP_PROJECT_UUID_SUCCESS,
					actionTypes.FETCH_PROJECT_VIA_CORP_PROJECT_UUID_FAILURE
				]
			}
		},
		payload: {
			id: corpProjectUuid
		},
		type: 'NO_OP'
	};
}

export const updateProject = ({
	emailAddressDomains,
	friendlyURL,
	groupId,
	incidentReportEmailAddresses,
	name,
	timeZoneId
}) => ({
	meta: {
		[CALL_API]: {
			data: {
				emailAddressDomains,
				friendlyURL,
				groupId,
				incidentReportEmailAddresses,
				name,
				timeZoneId
			},
			requestFn: API.projects.update,
			schema: project(groupId),
			types: [
				actionTypes.UPDATE_PROJECT_REQUEST,
				actionTypes.UPDATE_PROJECT_SUCCESS,
				actionTypes.UPDATE_PROJECT_FAILURE
			]
		},
		newId: friendlyURL,
		prevId: groupId
	},
	payload: {
		id: groupId
	},
	type: 'NO_OP'
});
