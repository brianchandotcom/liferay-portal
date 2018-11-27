import {
	CLEAR_DROP_TARGET,
	UPDATE_DROP_TARGET,
	UPDATE_HIGHLIGHT_MAPPING_STATUS
} from '../actions/actions.es';

/**
 * Available drag positions
 * @review
 * @type {!object}
 */
const DROP_TARGET_BORDERS = {
	bottom: 'drag-bottom',
	top: 'drag-top'
};

/**
 * Possible drop target types
 * @review
 * @type {!object}
 */
const DROP_TARGET_ITEM_TYPES = {
	column: 'layout-column',
	fragment: 'fragment-entry-link',
	fragmentList: 'fragment-entry-link-list',
	section: 'layout-section'
};

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
	const nextState = Object.assign({}, state);

	if (actionType === CLEAR_DROP_TARGET) {
		nextState.dropTargetBorder = null;
		nextState.dropTargetItemId = null;
		nextState.dropTargetItemType = null;
	}
	else if (actionType === UPDATE_DROP_TARGET) {
		nextState.dropTargetBorder = payload.dropTargetBorder;
		nextState.dropTargetItemId = payload.dropTargetItemId;
		nextState.dropTargetItemType = payload.dropTargetItemType;
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
				highlightMapping: Boolean(payload.highlightMapping)
			}
		);
	}

	return nextState;
}

export {
	DROP_TARGET_BORDERS,
	DROP_TARGET_ITEM_TYPES,
	updateDropTargetReducer,
	updateHighlightMappingReducer
};