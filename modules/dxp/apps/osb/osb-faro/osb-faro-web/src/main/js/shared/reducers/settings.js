import {createReducer} from 'redux-toolbox';
import {Map} from 'immutable';
import {actionTypes as projectsActionTypes} from '../actions/projects';
import {actionTypes as settingsActionTypes} from '../actions/settings';

const actionHandlers = {
	[projectsActionTypes.UPDATE_PROJECT_SUCCESS]: (
		state,
		{meta: {newId, prevId}, payload}
	) => {
		const backURL = state.get('backURL');

		const {groupId} = payload.entities.projects[prevId].data;

		if (backURL && prevId !== newId) {
			const regex = new RegExp(`/${prevId}/`);

			return state.set(
				'backURL',
				backURL.replace(regex, `/${newId || groupId}/`)
			);
		}

		return state;
	},
	[settingsActionTypes.SET_BACK_URL]: (state, action) =>
		state.set('backURL', action.payload.url)
};

export default createReducer(new Map(), actionHandlers);
