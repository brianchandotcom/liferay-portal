const headingTextSelectionTest = function(payload) {
	const nativeEditor = payload.editor.get('nativeEditor');
	const selectionEmpty = nativeEditor.isSelectionEmpty();
	const selectionData = payload.data.selectionData;
	const headings = ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'];

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