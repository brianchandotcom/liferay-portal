const headingTextSelectionTest = function(payload) {
	const headings = ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'];
	const nativeEditor = payload.editor.get('nativeEditor');
	const selectionData = payload.data.selectionData;
	const selectionEmpty = nativeEditor.isSelectionEmpty();

	return !!(
		!selectionData.element &&
		selectionData.region &&
		!selectionEmpty &&
		!nativeEditor.getSelection().getCommonAncestor().isReadOnly() &&
		nativeEditor.elementPath().contains(headings)
	);
};

export default headingTextSelectionTest;
export {headingTextSelectionTest};