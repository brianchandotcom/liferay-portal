const embedUrlSelectionTest = function(payload) {
	const selectionData = payload.data.selectionData;

	return !!(
		selectionData.element &&
		selectionData.element.getAttribute('data-widget') === 'embedurl'
	);
};

export default embedUrlSelectionTest;
export {embedUrlSelectionTest};