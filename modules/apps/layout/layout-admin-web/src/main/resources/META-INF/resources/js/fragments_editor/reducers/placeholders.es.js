import {
	CLEAR_DRAG_TARGET,
	UPDATE_DRAG_TARGET,
	UPDATE_HIGHLIGHT_MAPPING_STATUS
} from '../actions/actions.es';

/**
 * Available drag positions
 * @review
 * @type {!object}
 */

const DRAG_POSITIONS = {
	bottom: 'drag-bottom',
	top: 'drag-top'
};

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.hoveredFragmentEntryLinkBorder
 * @param {!string} payload.hoveredFragmentEntryLinkId
 * @return {object}
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

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {bool} payload.highlightMapping
 * @return {object}
 * @review
 */
function updateHighlightMappingReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === UPDATE_HIGHLIGHT_MAPPING_STATUS) {
		nextState = Object.assign(
			{},
			nextState,
			{
				highlightMapping: !!payload.highlightMapping
			}
		);
	}

	return nextState;
}

export {
	DRAG_POSITIONS,
	updateDragTargetReducer,
	updateHighlightMappingReducer
};