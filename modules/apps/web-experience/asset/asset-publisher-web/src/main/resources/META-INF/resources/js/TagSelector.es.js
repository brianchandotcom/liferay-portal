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
		AUI().use(
			'liferay-asset-taglib-tags-selector',
			function(A) {
				this.input_ = this.element.querySelector('input[type="text"]');

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

				this.element.addEventListener('click', this.focusTagInput_.bind(this));
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
	divId: Config.string().value(''),
	eventName: Config.string().value(''),
	groupIds: Config.string().value(''),
	hiddenInput: Config.string().value(''),
	index: Config.number().value(0),
	namespace: Config.string().value(''),
	rule: Config.object().value({}),
	tagSelectorURL: Config.string().value('')
}

Soy.register(TagSelector, templates);

export { TagSelector };
export default TagSelector;