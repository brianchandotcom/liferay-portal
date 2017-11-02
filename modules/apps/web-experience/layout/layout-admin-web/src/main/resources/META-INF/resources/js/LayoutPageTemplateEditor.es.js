import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './LayoutPageTemplateFragment.es';
import './LayoutPageTemplateFragmentCollection.es';
import templates from './LayoutPageTemplateEditor.soy';

/**
 * Component that allows creating/editing Layout Page Templates
 */
class LayoutPageTemplateEditor extends Component {
	/**
	 * Callback executed when a fragment entry of a collection is clicked.
	 * It receives fragmentEntryId and fragmentName as event data.
	 * @param {Event} event
	 * @private
	 */
	_handleFragmentCollectionEntryClick(event) {
		this.fragments = [
			...this.fragments,
			{
				fragmentEntryId: event.fragmentEntryId,
				name: event.fragmentName,
				config: {},
			},
		];
	}

	/**
	 * Removes a fragment from the fragment list. The fragment to
	 * be removed should be specified inside the event as fragmentIndex
	 * @param {Event} event
	 * @private
	 */
	_handleFragmentRemoveButtonClick(event) {
		const index = event.fragmentIndex;

		this.fragments = [
			...this.fragments.slice(0, index),
			...this.fragments.slice(index + 1),
		];
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
LayoutPageTemplateEditor.STATE = {
	/**
	 * Available entries that can be dragged inside the existing
	 * Layout Page Template, organized by fragment categories.
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateEditor
	 * @type {!Array<object>}
	 */
	fragmentCollections: Config.arrayOf(
		Config.shapeOf({
			fragmentCollectionId: Config.string().required(),
			name: Config.string().required(),
			entries: Config.arrayOf(
				Config.shapeOf({
					fragmentEntryId: Config.string().required(),
					name: Config.string().required(),
				})
			).required(),
		})
	).required(),

	/**
	 * List of fragment instances part of the Layout Page Template, the order
	 * of the elements in this array defines their position.
	 * @default []
	 * @instance
	 * @memberOf LayoutPageTemplateEditor
	 * @type {Array<string>}
	 */
	fragments: Config.arrayOf(
		Config.shapeOf({
			fragmentEntryId: Config.string().required(),
			name: Config.string().required(),
			config: Config.object().value({}),
		})
	).value([]),

	/**
	 * Optional ID provided by the template system.
	 * @default ''
	 * @instance
	 * @memberOf LayoutPageTemplateEditor
	 * @type {string}
	 */
	id: Config.string().value(''),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateEditor
	 * @type {!string}
	 */
	portletNamespace: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateEditor
	 * @type {!string}
	 */
	spritemap: Config.string().required(),
};

Soy.register(LayoutPageTemplateEditor, templates);

export {LayoutPageTemplateEditor};
export default LayoutPageTemplateEditor;
