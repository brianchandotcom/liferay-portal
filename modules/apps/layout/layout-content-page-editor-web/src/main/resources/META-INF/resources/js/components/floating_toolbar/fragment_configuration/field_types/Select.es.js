/**
 * Get data from select field
 * @param {Event} event
 */
export function getSelectData(event) {
	const targetElement = event.delegateTarget;

	const fieldName = targetElement.id;
	const fieldSetName = targetElement.dataset.fieldSetName;
	const fieldValue = targetElement.options[targetElement.selectedIndex].value;

	return {
		fieldName,
		fieldSetName,
		fieldValue
	};
}