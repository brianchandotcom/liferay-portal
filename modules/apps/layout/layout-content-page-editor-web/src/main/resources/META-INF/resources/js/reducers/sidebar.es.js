import {UPDATE_SELECTED_SIDEBAR_PANEL_ID} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.type
 * @param {string} [action.sidebarPanelId='']
 * @return {object}
 * @review
 */
function updateSelectedSidebarPanelId(state, action) {
	let nextState = state;

	if (action.type === UPDATE_SELECTED_SIDEBAR_PANEL_ID) {
		nextState = setIn(
			nextState,
			['selectedSidebarPanelId'],
			action.sidebarPanelId || ''
		);
	}

	return nextState;
}

export {updateSelectedSidebarPanelId};
