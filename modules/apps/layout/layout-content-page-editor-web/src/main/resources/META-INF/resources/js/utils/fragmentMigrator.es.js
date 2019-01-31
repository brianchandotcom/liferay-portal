const EDITABLE_FRAGMENT_ENTRY_PROCESSOR = 'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor';

function _editableFragmentMigrator(object, defaultSegmentKey) {
	let alternativeObject = null;
	let defaultSegment = {};
	Object.keys(object).forEach(
		oKey => {
			if (oKey !== 'defaultValue' && typeof object[oKey] === 'string') {
				defaultSegment[oKey] = object[oKey];
			}
		}
	);
	if (Object.keys(defaultSegment).length > 0) {
		alternativeObject = {
			[defaultSegmentKey]: defaultSegment,
			defaultValue: object.defaultValue
		};
	}
	return alternativeObject || object;
}

function editableValuesMigrator(editableValue, defaultSegmentKey) {
	let jsonEditableValues = JSON.parse(editableValue);
	let object = {
		[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {}
	};
	Object.keys(jsonEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR])
		.forEach(
			editableFragmentKey => {
				object[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editableFragmentKey] = _editableFragmentMigrator(
					jsonEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editableFragmentKey],
					defaultSegmentKey
				);
			}
		);
	return object;
}

export default editableValuesMigrator;