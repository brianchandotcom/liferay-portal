import Component from 'metal-component';
import Soy from 'metal-soy';
import { Config } from 'metal-state';

import templates from './TagSelector.soy';

/**
 * TagSelector is a temporary Component wrapping the existing
 * AUI module liferay-asset-taglib-tags-selector
 */
class TagSelector extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.input_ = this.element.querySelector('input[type="text"]');
		this.element.addEventListener('click', this.focusTagInput_.bind(this));

		AUI().use(
			'liferay-asset-taglib-tags-selector',
			function(A) {
				const config = {
					allowAddEntry: true,
					contentBox: this.element,
					eventName: this.eventName,
					groupIds: this.groupIds,
					hiddenInput: `#${this.element.querySelector('input[type="hidden"]').getAttribute('id')}`,
					input: `#${this.input_.getAttribute('id')}`,
					portletURL: this.tagSelectorURL,
					tagNames: this.rule.queryValues || ''
				};

				this.tagsSelector_ = new Liferay.AssetTaglibTagsSelector(config);

				const entries = this.tagsSelector_.entries;

				entries.after('add', this.onEntriesChanged_, this);
				entries.after('remove', this.onEntriesChanged_, this);

				this.tagsSelector_.render();
				this.element.parentNode.removeAttribute('tabindex');
			}.bind(this)
		);
	}

	/**
	 * Updates the calculated rule fields for `queryValues` every time a new
	 * tag entry is added or removed to the selection
	 * @protected
	 */
	onEntriesChanged_() {
		this.rule.queryValues = this.tagsSelector_.entries.keys.join();
	}

	/**
	 * Focuses the input field using for adding new tags.
	 * This method uses the private input_ attribute, which is
	 * stablished inside attached() method.
	 * @private
	 */
	focusTagInput_() {
		this.input_.focus();
	}
}

TagSelector.STATE = {
	/**
	 * Number used for avoiding conflicts between different
	 * instances of the component/portlet.
	 */
	index: Config.number().value(0),

	/**
	 * String used for avoiding conflicts between different
	 * instances of the component/portlet.
	 */
	namespace: Config.string().value(''),

	/**
	 * Name of the event that will be fired when the tag selector dialog
	 * request being closed
	 */
	eventName: Config.string().value(''),

	/**
	 * Array of group ids (sites) where tags will be searched.
	 * It defaults to an empty array, which is the current site.
	 */
	groupIds: Config.string().value(''),

	/**
	 * Id of the hidden input used to pass the selected tags
	 */
	hiddenInput: Config.string().value(''),

	/**
	 * Existing information of the form.
	 * Currently only rule.queryValues is present.
	 * @prop {string[]} queryValues Tags that are already selected.
	 *  This property is updated as the user selects new tags.
	 */
	rule: Config.object().value({}),

	/**
	 * When specified, this porlet (found for the given ID) will
	 * be used for tag selection as a popup. If no ID is given,
	 * the select button will not appear.
	 */
	tagSelectorURL: Config.string().value('')
};

Soy.register(TagSelector, templates);

export { TagSelector };
export default TagSelector;