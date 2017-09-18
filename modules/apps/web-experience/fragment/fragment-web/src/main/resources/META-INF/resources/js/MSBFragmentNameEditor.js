import 'metal-modal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import Uri from 'metal-uri';
import { Config } from 'metal-state';

import templates from './MSBFragmentNameEditor.soy';

/**
 * Component that generates a form for setting the Fragment name.
 */
class MSBFragmentNameEditor extends Component {
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

		fetch(this.addMSBFragmentEntryURL, {
			body: formData,
			credentials: 'include',
			method: 'POST',
		})
			.then(response => response.json())
			.then((jsonResponse) => {
				this.error = jsonResponse.error;

				if (jsonResponse.msbFragmentEntryId) {
					const uri = new Uri(this.editMSBFragmentEntryURL);

					uri.addParameterValue(
						`${this.namespace}msbFragmentEntryId`,
						jsonResponse.msbFragmentEntryId,
					);

					const uriString = uri.toString();

					if (Liferay.SPA) {
						Liferay.SPA.app.navigate(uriString);
						this.disposeInternal();
					} else {
						location.href = uriString;
					}
				}
			});
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
MSBFragmentNameEditor.STATE = {
	/**
	 * URL used for creating the fragment. The generated form
	 * will be submited to this url.
	 * @instance
	 * @memberOf MSBFragmentNameEditor
	 * @type {!string}
	 */
	addMSBFragmentEntryURL: Config.string().required(),

	/**
	 * URL used for editing the fragment.
	 * Once the fragment has been successfully created, the browser
	 * will be redirected to this url.
	 * @instance
	 * @memberOf MSBFragmentNameEditor
	 * @type {!string}
	 */
	editMSBFragmentEntryURL: Config.string().required(),

	/**
	 * Error message returned by the server.
	 * It is actually the exception description, but safely captured
	 * and processed.
	 * @default ''
	 * @instance
	 * @memberOf MSBFragmentNameEditor
	 * @type {?string}
	 */
	error: Config.string().value(''),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @instance
	 * @memberOf MSBFragmentNameEditor
	 * @type {!string}
	 */
	namespace: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @instance
	 * @memberOf MSBFragmentEditor
	 * @type {!string}
	 */
	spritemap: Config.string().required(),
};

Soy.register(MSBFragmentNameEditor, templates);

export { MSBFragmentNameEditor };
export default MSBFragmentNameEditor;