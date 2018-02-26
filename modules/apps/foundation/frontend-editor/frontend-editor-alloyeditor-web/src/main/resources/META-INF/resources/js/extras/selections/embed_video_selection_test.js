const videoEmbedSelectionTest = function(payload) {
	const selectionData = payload.data.selectionData;

	return !!(
		selectionData.element &&
		selectionData.element.getAttribute('data-widget') === 'videoembed'
	);
};

export default videoEmbedSelectionTest;
export {videoEmbedSelectionTest};