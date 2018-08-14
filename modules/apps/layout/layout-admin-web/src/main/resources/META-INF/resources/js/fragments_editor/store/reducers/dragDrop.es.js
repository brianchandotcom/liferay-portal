import {CLEAR_DRAG_TARGET, UPDATE_DRAG_TARGET} from '../actionTypes.es';

/**
 * @param {!MetalStore} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.hoveredFragmentEntryLinkBorder
 * @param {!string} payload.hoveredFragmentEntryLinkId
 * @return {MetalStore}
 * @review
 */

function updateDragTargetReducer(state, actionType, payload) {
	let nextState = Object.assign({}, state);

	if (actionType === CLEAR_DRAG_TARGET) {
		nextState.hoveredFragmentEntryLinkBorder = null;
		nextState.hoveredFragmentEntryLinkId = null;
	}
	else if (actionType === UPDATE_DRAG_TARGET) {
		nextState.hoveredFragmentEntryLinkBorder = payload.hoveredFragmentEntryLinkBorder;
		nextState.hoveredFragmentEntryLinkId = payload.hoveredFragmentEntryLinkId;
	}

	return nextState;
}

export {updateDragTargetReducer};