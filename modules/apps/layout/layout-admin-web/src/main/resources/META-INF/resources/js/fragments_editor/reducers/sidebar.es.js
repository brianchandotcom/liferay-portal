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
	let nextState = state;

	if (actionType === HIDE_SIDEBAR) {
		nextState = Object.assign({}, nextState);
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
	let nextState = state;

	if (actionType === TOGGLE_SIDEBAR) {
		nextState = Object.assign({}, nextState);
		nextState.contextualSidebarVisible = !nextState.contextualSidebarVisible;
	}

	return nextState;
}

export {
	hideFragmentsEditorSidebarReducer,
	toggleFragmentsEditorSidebarReducer
};