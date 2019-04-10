import {UPDATE_SELECTED_SIDEBAR_PANEL_ID} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {object} state
 * @param {string} actionType
 * @param {object} payload
 * @param {string} [payload.sidebarPanelId='']
 * @return {object}
 * @review
 */
function updateSelectedSidebarPanelId(state, actionType, payload) {
	let nextState = state;

	if (actionType === UPDATE_SELECTED_SIDEBAR_PANEL_ID) {
		nextState = setIn(
			nextState,
			['selectedSidebarPanelId'],
			payload.sidebarPanelId
		);
	}

	return nextState;
}

export {updateSelectedSidebarPanelId};