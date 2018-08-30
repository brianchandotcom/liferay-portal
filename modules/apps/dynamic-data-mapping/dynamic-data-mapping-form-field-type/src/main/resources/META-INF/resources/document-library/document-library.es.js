import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './document-library.soy';

/**
 * DocumentLibrary Component
 */

class DocumentLibrary extends Component {}

// Register component

Soy.register(DocumentLibrary, templates, 'render');

if (!window.DDMDocumentLibrary) {
	window.DDMDocumentLibrary = {

	};
}

window.DDMDocumentLibrary.render = DocumentLibrary;

export default DocumentLibrary;