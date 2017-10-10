import 'metal-modal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import Uri from 'metal-uri';
import {Config} from 'metal-state';

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

		const form = document.getElementById(`${this.namespace}fragmentNameEditorForm`);
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

	/**
	 * Method to redirect via JS or SPA depending if SPA exists or not.
	 * @param {!string} uri URI to be redirected to.
	 * @protected
	 */
	_redirect(uri) {
		if (Liferay.SPA) {
			this.dispose();

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
	 * Error message returned by the server.
	 * It is actually the exception description, but safely captured
	 * and processed.
	 * @default undefined
	 * @instance
	 * @memberof FragmentNameEditor
	 * @type {?string}
	 */
	_error: Config.string().internal(),

	/**
	 * URL used for creating the fragment. The generated form
	 * will be submited to this url.
	 * @default undefined
	 * @instance
	 * @memberof FragmentNameEditor
	 * @type {!string}
	 */
	actionURL: Config.string().required(),

	/**
	 * String to be shown as the editor modal title.
	 * @default undefined
	 * @instance
	 * @memberof FragmentNameEditor
	 * @type {!string}
	 */
	editorTitle: Config.string().required(),

	/**
	 * ID of an existing fragment. When specified, this ID will be submitted
	 * inside the form to modify an existing fragment.
	 * @default undefined
	 * @instance
	 * @memberof FragmentNameEditor
	 * @type {?string}
	 */
	fragmentEntryId: Config.string(),

	/**
	 * Initial name of the fragment. When specified, this value will initially
	 * fill the rendered form.
	 * @default undefined
	 * @instance
	 * @memberof FragmentNameEditor
	 * @type {?string}
	 */
	fragmentEntryName: Config.string(),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @default undefined
	 * @instance
	 * @memberof FragmentNameEditor
	 * @type {!string}
	 */
	namespace: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberof FragmentEditor
	 * @type {!string}
	 */
	spritemap: Config.string().required(),
};

Soy.register(FragmentNameEditor, templates);

export { FragmentNameEditor };
export default FragmentNameEditor;