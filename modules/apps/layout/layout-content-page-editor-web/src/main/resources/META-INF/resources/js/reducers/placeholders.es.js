import {
	CLEAR_ACTIVE_ITEM,
	CLEAR_DROP_TARGET,
	CLEAR_HOVERED_ITEM,
	UPDATE_ACTIVE_ITEM,
	UPDATE_DROP_TARGET,
	UPDATE_HIGHLIGHT_MAPPING_STATUS,
	UPDATE_HOVERED_ITEM
} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * Updates active element data with the information sent.
 * @param {!object} state
 * @param {UPDATE_ACTIVE_ITEM} actionType
 * @param {!object} payload
 * @param {string} payload.activeItemId
 * @param {string} payload.activeItemType
 * @return {object}
 * @review
 */
function updateActiveItemReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === CLEAR_ACTIVE_ITEM) {
		nextState = setIn(nextState, ['activeItemId'], null);
		nextState = setIn(nextState, ['activeItemType'], null);
	}
	else if (actionType === UPDATE_ACTIVE_ITEM) {
		nextState = setIn(nextState, ['activeItemId'], payload.activeItemId);
		nextState = setIn(nextState, ['activeItemType'], payload.activeItemType);
	}

	return nextState;
}

/**
 * Updates drop target element with the information sent.
 * @param {!object} state
 * @param {CLEAR_DROP_TARGET|UPDATE_DROP_TARGET} actionType
 * @param {!object} payload
 * @param {string} payload.dropTargetBorder
 * @param {string} payload.dropTargetItemId
 * @param {string} payload.dropTargetItemType
 * @return {object}
 * @review
 */
function updateDropTargetReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === CLEAR_DROP_TARGET) {
		nextState = setIn(nextState, ['dropTargetBorder'], null);
		nextState = setIn(nextState, ['dropTargetItemId'], null);
		nextState = setIn(nextState, ['dropTargetItemType'], null);
	}
	else if (actionType === UPDATE_DROP_TARGET) {
		nextState = setIn(nextState, ['dropTargetBorder'], payload.dropTargetBorder);
		nextState = setIn(nextState, ['dropTargetItemId'], payload.dropTargetItemId);
		nextState = setIn(nextState, ['dropTargetItemType'], payload.dropTargetItemType);
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
		nextState = setIn(nextState, ['highlightMapping'], Boolean(payload.highlightMapping));
	}

	return nextState;
}

/**
 * Updates hovered element data with the information sent.
 * @param {!object} state
 * @param {UPDATE_HOVERED_ITEM} actionType
 * @param {!object} payload
 * @param {string} payload.hoveredItemId
 * @param {string} payload.hoveredItemType
 * @return {object}
 * @review
 */
function updateHoveredItemReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === CLEAR_HOVERED_ITEM) {
		nextState = setIn(nextState, ['hoveredItemId'], null);
		nextState = setIn(nextState, ['hoveredItemType'], null);
	}
	else if (actionType === UPDATE_HOVERED_ITEM) {
		nextState = setIn(nextState, ['hoveredItemId'], payload.hoveredItemId);
		nextState = setIn(nextState, ['hoveredItemType'], payload.hoveredItemType);
	}

	return nextState;
}

export {
	updateActiveItemReducer,
	updateDropTargetReducer,
	updateHighlightMappingReducer,
	updateHoveredItemReducer
};