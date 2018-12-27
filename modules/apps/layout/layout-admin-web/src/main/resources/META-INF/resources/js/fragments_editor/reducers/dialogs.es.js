import {
	HIDE_MAPPING_DIALOG,
	HIDE_MAPPING_TYPE_DIALOG,
	OPEN_ASSET_TYPE_DIALOG,
	OPEN_MAPPING_FIELDS_DIALOG,
	SELECT_MAPPEABLE_TYPE
} from '../actions/actions.es';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @return {object}
 * @review
 */
function openAssetTypeDialogReducer(state, actionType) {
	let nextState = state;

	if (actionType === OPEN_ASSET_TYPE_DIALOG) {
		nextState = Object.assign({}, nextState);
		nextState.selectMappingTypeDialogVisible = true;
	}

	return nextState;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.editableId
 * @param {!string} payload.editableType
 * @param {!string} payload.mappedFieldId
 * @return {object}
 * @review
 */
function openMappingFieldsDialogReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === OPEN_MAPPING_FIELDS_DIALOG) {
		nextState = Object.assign({}, nextState);
		nextState.selectMappingDialogEditableId = payload.editableId;
		nextState.selectMappingDialogEditableType = payload.editableType;
		nextState.selectMappingDialogFragmentEntryLinkId = payload.fragmentEntryLinkId;
		nextState.selectMappingDialogMappedFieldId = payload.mappedFieldId;

		if (nextState.selectedMappingTypes && nextState.selectedMappingTypes.type) {
			nextState.selectMappingDialogVisible = true;
		}
		else {
			nextState.selectMappingTypeDialogVisible = true;
		}
	}

	return nextState;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.selectedMappingSubtypeId
 * @param {!string} payload.selectedMappingTypeId
 * @return {object}
 * @review
 */
function selectMappeableTypeReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;

			if (actionType === SELECT_MAPPEABLE_TYPE) {
				nextState = Object.assign({}, nextState);

				_selectMappingType(
					state.classPK,
					state.portletNamespace,
					payload.selectedMappingSubtypeId,
					payload.selectedMappingTypeId,
					state.updateLayoutPageTemplateEntryAssetTypeURL
				)
					.then(
						() => {
							nextState.selectedMappingTypes = payload.mappingTypes;
							if (nextState.selectMappingDialogFragmentEntryLinkId &&
								nextState.selectMappingDialogEditableId) {
								nextState.selectMappingDialogVisible = true;
							}
							resolve(nextState);
						}
					)
					.catch(
						() => {
							resolve(nextState);
						}
					);
			}
			else {
				resolve(nextState);
			}
		}
	);
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @return {object}
 * @review
 */
function hideMappingDialogReducer(state, actionType) {
	let nextState = state;

	if (actionType === HIDE_MAPPING_DIALOG) {
		nextState = Object.assign({}, nextState);
		nextState.selectMappingDialogVisible = false;
	}

	return nextState;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @return {object}
 * @review
 */
function hideMappingTypeDialogReducer(state, actionType) {
	let nextState = state;

	if (actionType === HIDE_MAPPING_TYPE_DIALOG) {
		nextState = Object.assign({}, nextState);
		nextState.selectMappingTypeDialogVisible = false;
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

	return fetch(
		updateLayoutPageTemplateEntryAssetTypeURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	).then(
		response => response.json()
	);
}

export {
	hideMappingDialogReducer,
	hideMappingTypeDialogReducer,
	openAssetTypeDialogReducer,
	openMappingFieldsDialogReducer,
	selectMappeableTypeReducer
};