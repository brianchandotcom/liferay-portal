import 'metal-modal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import Uri from 'metal-uri';
import { Config } from 'metal-state';

import templates from './FragmentNameEditor.soy';

/**
 * Component that generates a form for setting the Fragment name.
 */
class FragmentNameEditor extends Component {
	/**
	 * Callback executed when the modal is hidden.
	 * @protected
	 */
	_handleModalHidden() {
		this.emit('hide');
	}

	/**
	 * Callback executed when the generated form is submited.
	 * If the user has written a Fragment name, it closes the dialog,
	 * otherwise it does nothing.
	 * @param {Event} event Submit event that is prevented.
	 * @protected
	 */
	_handleSubmitForm(event) {
		event.preventDefault();

		const form = document.getElementById(`${this.namespace}addForm`);
		const formData = new FormData(form);

		fetch(this.actionURL, {
			body: formData,
			credentials: 'include',
			method: 'POST',
		})
			.then(response => response.json())
			.then((jsonResponse) => {
				if (jsonResponse.redirectURL) {
					this._redirect(jsonResponse.redirectURL);
				}
				else if(jsonResponse.error) {
					this.error = jsonResponse.error;
				}
			});
	}

	_redirect(uri) {
		if (Liferay.SPA) {
			this.disposeInternal();

			Liferay.SPA.app.navigate(uri);
		}
		else {
			location.href = uri;
		}
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
FragmentNameEditor.STATE = {
	/**
	 * URL used for creating the fragment. The generated form
	 * will be submited to this url.
	 * @default ''
	 * @instance
	 * @memberOf FragmentNameEditor
	 * @type {!string}
	 */
	actionURL: Config.string().value(''),

	editorTitle: Config.string().required(),

	/**
	 * Error message returned by the server.
	 * It is actually the exception description, but safely captured
	 * and processed.
	 * @default ''
	 * @instance
	 * @memberOf FragmentNameEditor
	 * @type {?string}
	 */
	error: Config.string().value(''),

	/**
	 * ID of an existing fragment. When specified, this ID will be submitted
	 * inside the form to modify an existing fragment.
	 * @default ''
	 * @instance
	 * @memberOf FragmentNameEditor
	 * @type {!string}
	 */
	fragmentEntryId: Config.string().value(''),

	/**
	 * Initial name of the fragment. When specified, this value will initially
	 * fill the rendered form.
	 * @default ''
	 * @instance
	 * @memberOf FragmentNameEditor
	 * @type {!string}
	 */
	fragmentEntryName: Config.string().value(''),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @instance
	 * @memberOf FragmentNameEditor
	 * @type {!string}
	 */
	namespace: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */
	spritemap: Config.string().required(),
};

Soy.register(FragmentNameEditor, templates);

export { FragmentNameEditor };
export default FragmentNameEditor;