import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import 'layout-admin-web/js/fragments_editor/FragmentEntryLink.es';
import templates from './FragmentEntryDisplay.soy';

/**
 * FragmentEntryDisplay
 * @review
 */

class FragmentEntryDisplay extends Component {

	/**
	 * Callback executed everytime an editable field has been changed
	 * @param {{
	 *   editableId: !string,
	 *   value: !string
	 * }} data
	 * @private
	 * @review
	 */

	_handleEditableChanged(data) {
		this.fragmentEntryLink.editableValues[data.editableId] = data.value;

		this._updateFragmentEntryLink();
	}

	/**
	 * Sends the change of a single fragment entry link to the server.
	 * @private
	 * @review
	 */

	_updateFragmentEntryLink() {
		const formData = new FormData();

		formData.append(
			`${this.portletNamespace}fragmentEntryLinkId`,
			this.fragmentEntryLink.fragmentEntryLinkId
		);

		formData.append(
			`${this.portletNamespace}editableValues`,
			JSON.stringify(this.fragmentEntryLink.editableValues)
		);

		fetch(
			this.editFragmentEntryLinkURL,
			{
				body: formData,
				credentials: 'include',
				method: 'POST'
			}
		);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentEntryDisplay.STATE = {

	/**
	 * Default configuration for AlloyEditor instances.
	 * @default {}
	 * @instance
	 * @memberOf FragmentEntryDisplay
	 * @review
	 * @type {object}
	 */

	defaultEditorConfiguration: Config.object().value({}),

	/**
	 * URL for updating a distinct fragment entries of the editor.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryDisplay
	 * @review
	 * @type {!string}
	 */

	editFragmentEntryLinkURL: Config.string().required(),

	/**
	 * FragmentEntryLink entity
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryDisplay
	 * @review
	 * @type {{
	 * 		config: object,
	 * 		content: any,
	 * 		editableValues: object,
	 * 		fragmentEntryId: string,
	 * 		fragmentEntryLinkId: string,
	 * 		name: string,
	 * 		position: number
	 * 	}}
	 */

	fragmentEntryLink: Config.shapeOf(
		{
			config: Config.object().value({}),
			content: Config.any().value(''),
			editableValues: Config.object().value({}),
			fragmentEntryId: Config.string().required(),
			fragmentEntryLinkId: Config.string().required(),
			name: Config.string().required(),
			position: Config.number().required()
		}
	),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryDisplay
	 * @review
	 * @type {!string}
	 */

	portletNamespace: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryDisplay
	 * @review
	 * @type {!string}
	 */

	spritemap: Config.string().required()

};

Soy.register(FragmentEntryDisplay, templates);

export {FragmentEntryDisplay};
export default FragmentEntryDisplay;