import {
	HIDE_SIDEBAR,
	TOGGLE_SIDEBAR
} from '../actions/actions.es';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @return {object}
 * @review
 */

function hideFragmentsEditorSidebarReducer(state, actionType) {
	let nextState = Object.assign({}, state);

	if (actionType === HIDE_SIDEBAR) {
		nextState.contextualSidebarVisible = false;
	}

	return nextState;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @return {object}
 * @review
 */

function toggleFragmentsEditorSidebarReducer(state, actionType) {
	let nextState = Object.assign({}, state);

	if (actionType === TOGGLE_SIDEBAR) {
		nextState.contextualSidebarVisible = !nextState.contextualSidebarVisible;
	}

	return nextState;
}

export {
	hideFragmentsEditorSidebarReducer,
	toggleFragmentsEditorSidebarReducer
};