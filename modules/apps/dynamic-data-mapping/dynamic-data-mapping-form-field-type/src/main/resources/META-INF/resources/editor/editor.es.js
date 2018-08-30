import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './editor.soy';

/**
 * Editor Component
 */

class Editor extends Component {}

// Register component

Soy.register(Editor, templates, 'render');

if (!window.DDMEditor) {
	window.DDMEditor = {

	};
}

window.DDMEditor.render = Editor;

export default Editor;