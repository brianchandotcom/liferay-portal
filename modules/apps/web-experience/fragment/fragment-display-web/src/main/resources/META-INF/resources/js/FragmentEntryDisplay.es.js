import {Config} from 'metal-state';
import Soy from 'metal-soy';

import FragmentsEditor from 'layout-admin-web/js/fragments_editor/FragmentsEditor.es';
import FragmentEntryLink from 'layout-admin-web/js/fragments_editor/FragmentEntryLink.es';

import templates from './FragmentEntryDisplay.soy';

/**
 * FragmentEntryDisplay
 * @review
 */

class FragmentEntryDisplay extends FragmentsEditor {

	/**
	 * Callback executed everytime an editable field has been changed
	 * @param {{
	 *   editableId: !string,
	 *   fragmentEntryLinkId: !string,
	 *   value: !string
	 * }} data
	 * @private
	 * @review
	 */

	_handleEditableChanged(data) {
		const fragmentEntryLink = this.fragmentEntryLink;

		fragmentEntryLink.editableValues[data.editableId] = data.value;

		this._updateFragmentEntryLink(fragmentEntryLink);
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