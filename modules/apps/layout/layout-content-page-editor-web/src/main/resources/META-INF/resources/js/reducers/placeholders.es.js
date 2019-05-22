import {
	CLEAR_ACTIVE_ITEM,
	CLEAR_DROP_TARGET,
	CLEAR_HOVERED_ITEM,
	UPDATE_ACTIVE_ITEM,
	UPDATE_DROP_TARGET,
	UPDATE_HOVERED_ITEM
} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * Updates active element data with the information sent.
 * @param {!object} state
 * @param {object} action
 * @param {string} action.activeItemId
 * @param {string} action.activeItemType
 * @param {string} action.type
 * @return {object}
 * @review
 */
function updateActiveItemReducer(state, action) {
	let nextState = state;

	if (action.type === CLEAR_ACTIVE_ITEM) {
		nextState = setIn(nextState, ['activeItemId'], null);
		nextState = setIn(nextState, ['activeItemType'], null);
	} else if (action.type === UPDATE_ACTIVE_ITEM) {
		nextState = setIn(nextState, ['activeItemId'], action.activeItemId);
		nextState = setIn(nextState, ['activeItemType'], action.activeItemType);
	}

	return nextState;
}

/**
 * Updates drop target element with the information sent.
 * @param {!object} state
 * @param {object} action
 * @param {string} action.dropTargetBorder
 * @param {string} action.dropTargetItemId
 * @param {string} action.dropTargetItemType
 * @param {string} action.type
 * @return {object}
 * @review
 */
function updateDropTargetReducer(state, action) {
	let nextState = state;

	if (action.type === CLEAR_DROP_TARGET) {
		nextState = setIn(nextState, ['dropTargetBorder'], null);
		nextState = setIn(nextState, ['dropTargetItemId'], null);
		nextState = setIn(nextState, ['dropTargetItemType'], null);
	} else if (action.type === UPDATE_DROP_TARGET) {
		nextState = setIn(
			nextState,
			['dropTargetBorder'],
			action.dropTargetBorder
		);

		nextState = setIn(
			nextState,
			['dropTargetItemId'],
			action.dropTargetItemId
		);

		nextState = setIn(
			nextState,
			['dropTargetItemType'],
			action.dropTargetItemType
		);
	}

	return nextState;
}

/**
 * Updates hovered element data with the information sent.
 * @param {object} state
 * @param {object} action
 * @param {string} action.hoveredItemId
 * @param {string} action.hoveredItemType
 * @param {string} action.type
 * @return {object}
 * @review
 */
function updateHoveredItemReducer(state, action) {
	let nextState = state;

	if (action.type === CLEAR_HOVERED_ITEM) {
		nextState = setIn(nextState, ['hoveredItemId'], null);
		nextState = setIn(nextState, ['hoveredItemType'], null);
	} else if (action.type === UPDATE_HOVERED_ITEM) {
		nextState = setIn(nextState, ['hoveredItemId'], action.hoveredItemId);

		nextState = setIn(
			nextState,
			['hoveredItemType'],
			action.hoveredItemType
		);
	}

	return nextState;
}

export {
	updateActiveItemReducer,
	updateDropTargetReducer,
	updateHoveredItemReducer
};
