import {
	HIDE_MAPPING_DIALOG,
	HIDE_MAPPING_TYPE_DIALOG,
	OPEN_ASSET_TYPE_DIALOG,
	OPEN_MAPPING_FIELDS_DIALOG,
	SELECT_MAPPEABLE_TYPE
} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.type
 * @return {object}
 * @review
 */
function openAssetTypeDialogReducer(state, action) {
	let nextState = state;

	if (action.type === OPEN_ASSET_TYPE_DIALOG) {
		nextState = setIn(nextState, ['selectMappingTypeDialogVisible'], true);
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.editableId
 * @param {string} action.editableType
 * @param {string} action.fragmentEntryLinkId
 * @param {string} action.mappedFieldId
 * @param {string} action.type
 * @return {object}
 * @review
 */
function openMappingFieldsDialogReducer(state, action) {
	let nextState = state;

	if (action.type === OPEN_MAPPING_FIELDS_DIALOG) {
		nextState = setIn(
			nextState,
			['selectMappingDialogEditableId'],
			action.editableId
		);

		nextState = setIn(
			nextState,
			['selectMappingDialogEditableType'],
			action.editableType
		);

		nextState = setIn(
			nextState,
			['selectMappingDialogMappedFieldId'],
			action.mappedFieldId
		);

		nextState = setIn(
			nextState,
			['selectMappingDialogFragmentEntryLinkId'],
			action.fragmentEntryLinkId
		);

		if (
			nextState.selectedMappingTypes &&
			nextState.selectedMappingTypes.type
		) {
			nextState = setIn(nextState, ['selectMappingDialogVisible'], true);
		} else {
			nextState = setIn(
				nextState,
				['selectMappingTypeDialogVisible'],
				true
			);
		}
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {{}} action.mappingTypes
 * @param {string} action.selectedMappingSubtypeId
 * @param {string} action.selectedMappingTypeId
 * @param {string} action.type
 * @return {object}
 * @review
 */
function selectMappeableTypeReducer(state, action) {
	return new Promise(resolve => {
		let nextState = state;

		if (action.type === SELECT_MAPPEABLE_TYPE) {
			_selectMappingType(
				nextState.classPK,
				nextState.portletNamespace,
				action.selectedMappingSubtypeId,
				action.selectedMappingTypeId,
				nextState.updateLayoutPageTemplateEntryAssetTypeURL
			)
				.then(() => {
					nextState = setIn(
						nextState,
						['selectedMappingTypes'],
						action.mappingTypes
					);

					if (
						nextState.selectMappingDialogFragmentEntryLinkId &&
						nextState.selectMappingDialogEditableId
					) {
						nextState = setIn(
							nextState,
							['selectMappingDialogVisible'],
							true
						);
					}

					resolve(nextState);
				})
				.catch(() => {
					resolve(nextState);
				});
		} else {
			resolve(nextState);
		}
	});
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.type
 * @return {object}
 * @review
 */
function hideMappingDialogReducer(state, action) {
	let nextState = state;

	if (action.type === HIDE_MAPPING_DIALOG) {
		nextState = setIn(nextState, ['selectMappingDialogVisible'], false);
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.type
 * @return {object}
 * @review
 */
function hideMappingTypeDialogReducer(state, action) {
	let nextState = state;

	if (action.type === HIDE_MAPPING_TYPE_DIALOG) {
		nextState = setIn(nextState, ['selectMappingTypeDialogVisible'], false);
	}

	return nextState;
}

/**
 * @param {string} classPK
 * @param {string} portletNamespace
 * @param {string} selectedMappingSubtypeId
 * @param {string} selectedMappingTypeId
 * @param {string} updateLayoutPageTemplateEntryAssetTypeURL
 * @return {object}
 * @review
 */
function _selectMappingType(
	classPK,
	portletNamespace,
	selectedMappingSubtypeId,
	selectedMappingTypeId,
	updateLayoutPageTemplateEntryAssetTypeURL
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}classTypeId`, selectedMappingSubtypeId);
	formData.append(`${portletNamespace}classNameId`, selectedMappingTypeId);
	formData.append(`${portletNamespace}classPK`, classPK);

	return fetch(updateLayoutPageTemplateEntryAssetTypeURL, {
		body: formData,
		credentials: 'include',
		method: 'POST'
	}).then(response => response.json());
}

export {
	hideMappingDialogReducer,
	hideMappingTypeDialogReducer,
	openAssetTypeDialogReducer,
	openMappingFieldsDialogReducer,
	selectMappeableTypeReducer
};
