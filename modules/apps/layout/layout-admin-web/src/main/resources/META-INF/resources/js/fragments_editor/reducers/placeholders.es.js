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
 * Updates hover status with the information sent.
 * @param {!object} state
 * @param {CLEAR_DRAG_TARGET|UPDATE_DRAG_TARGET} actionType
 * @param {!object} payload
 * @param {string} payload.hoveredElementBorder
 * @param {string} payload.hoveredFragmentEntryLinkId
 * @param {string} payload.hoveredSectionId
 * @return {object}
 * @review
 */
function updateDragTargetReducer(state, actionType, payload) {
	const nextState = Object.assign({}, state);

	if (actionType === CLEAR_DRAG_TARGET) {
		nextState.hoveredElementBorder = null;
		nextState.hoveredFragmentEntryLinkId = null;
		nextState.hoveredSectionId = null;
	}
	else if (actionType === UPDATE_DRAG_TARGET) {
		if (payload.hoveredSectionId) {
			nextState.hoveredSectionId = payload.hoveredSectionId;
		}
		else if (payload.hoveredFragmentEntryLinkId) {
			nextState.hoveredFragmentEntryLinkId = payload.hoveredFragmentEntryLinkId;
		}
		nextState.hoveredElementBorder = payload.hoveredElementBorder;
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