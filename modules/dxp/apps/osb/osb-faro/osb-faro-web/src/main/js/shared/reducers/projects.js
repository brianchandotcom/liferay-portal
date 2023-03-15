import {actionTypes} from '../actions/projects';
import {createReducer} from 'redux-toolbox';
import {fromJS, OrderedMap} from 'immutable';
import {handleError, handleLoading} from '../util/redux';
import {Project, RemoteData} from 'shared/util/records';

const actionHandlers = {
	[actionTypes.FETCH_PROJECT_REQUEST]: handleLoading,
	[actionTypes.FETCH_PROJECT_FAILURE]: handleError,
	[actionTypes.FETCH_PROJECT_VIA_CORP_PROJECT_UUID_FAILURE]: handleError,
	[actionTypes.FETCH_PROJECT_VIA_CORP_PROJECT_UUID_REQUEST]: handleLoading,
	[actionTypes.UPDATE_PROJECT_FAILURE]: state =>
		state.merge({loading: false}),
	[actionTypes.UPDATE_PROJECT_REQUEST]: handleLoading,
	[actionTypes.UPDATE_PROJECT_SUCCESS]: (
		state,
		{meta: {newId, prevId}, payload}
	) => {
		const project = payload.entities.projects[prevId];

		const {
			data: {groupId}
		} = project;

		const updatedState = state.set(
			newId || String(groupId),
			new RemoteData({
				data: new Project(fromJS(project.data)),
				loading: false
			})
		);

		if (newId && prevId !== newId) {
			return updatedState.delete(prevId);
		}

		return updatedState;
	}
};

export default createReducer(new OrderedMap(), actionHandlers);
