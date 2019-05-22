import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from './constants';

/**
 * @param {object} object
 * @param {string} defaultSegmentsExperienceKey
 */
function _editableFragmentMigrator(object, defaultSegmentsExperienceKey) {
	let alternativeObject = null;
	let defaultSegment = {};
	Object.keys(object).forEach(oKey => {
		if (oKey !== 'defaultValue' && typeof object[oKey] === 'string') {
			defaultSegment[oKey] = object[oKey];
		}
	});
	if (Object.keys(defaultSegment).length > 0) {
		alternativeObject = {
			[defaultSegmentsExperienceKey]: defaultSegment,
			defaultValue: object.defaultValue
		};
	}
	return alternativeObject || object;
}

/**
 * @param {object} editableValue
 * @param {string} defaultSegmentsExperienceKey
 */
function editableValuesMigrator(editableValue, defaultSegmentsExperienceKey) {
	let jsonEditableValues = JSON.parse(editableValue);
	let result;

	if (!defaultSegmentsExperienceKey) {
		result = jsonEditableValues;
	} else {
		result = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {}
		};
		Object.keys(
			jsonEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]
		).forEach(editableFragmentKey => {
			result[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][
				editableFragmentKey
			] = _editableFragmentMigrator(
				jsonEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][
					editableFragmentKey
				],
				defaultSegmentsExperienceKey
			);
		});
	}
	return result;
}

export default editableValuesMigrator;
