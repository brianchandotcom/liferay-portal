(function() {
	'use strict';

	var headingTextSelectionTest = function(payload) {
		var nativeEditor = payload.editor.get('nativeEditor');

		var selectionEmpty = nativeEditor.isSelectionEmpty();

		var selectionData = payload.data.selectionData;

		var headings = ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'];

		return !!(
			!selectionData.element &&
			selectionData.region &&
			!selectionEmpty &&
			!nativeEditor.getSelection().getCommonAncestor().isReadOnly() &&
			nativeEditor.elementPath().contains(headings)
		);
	};

	AlloyEditor.SelectionTest = AlloyEditor.SelectionTest || {};

	AlloyEditor.SelectionTest.headingtext = headingTextSelectionTest;
}());