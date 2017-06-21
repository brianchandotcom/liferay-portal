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
	rendered() {
		AUI().use(
			'liferay-asset-taglib-tags-selector', 
			function(A) {
				const config = {
					allowAddEntry: true,
					contentBox: this.element,
					eventName: this.eventName,
					groupIds: this.groupIds,
					hiddenInput: `#${this.element.querySelector('input[type="hidden"]').getAttribute('id')}`,
					input: `#${this.element.querySelector('input[type="text"]').getAttribute('id')}`,
					portletURL: this.tagSelectorURL,
					tagNames: this.rule.queryValues || ''
				};

				this.tagsSelector_ = new Liferay.AssetTaglibTagsSelector(config);

				const entries = this.tagsSelector_.entries;

				entries.after('add', this.onEntriesChanged_, this);
				entries.after('remove', this.onEntriesChanged_, this);
				
				this.tagsSelector_.render();
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