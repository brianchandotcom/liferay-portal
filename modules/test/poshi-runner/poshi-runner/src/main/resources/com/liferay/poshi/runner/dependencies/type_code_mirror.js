Type = {
	codeMirror: function(element, value) {
		console.log('trying to type in ', element);

		const cm = element.CodeMirror;

		if (cm) {
			const codeMirrorDoc = cm.getDoc();

			codeMirrorDoc.setValue(value);
		}
	}
}