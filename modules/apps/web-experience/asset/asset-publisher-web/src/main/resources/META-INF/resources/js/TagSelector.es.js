import Component from 'metal-component';
import Soy from 'metal-soy';
import { Config } from 'metal-state';

import templates from './TagSelector.soy';

/**
 * TagSelector
 *
 */
class TagSelector extends Component {

	rendered() {
		let instance = this;

		AUI().use('liferay-asset-taglib-tags-selector', function(A) {
			let config = {
				allowAddEntry: true,
				contentBox: '#' + instance.divId,
				eventName: instance.eventName,
				groupIds: instance.groupIds,
				hiddenInput: '#' + instance.hiddenInput,
				input: '#' + instance.inputId,
				portletURL: instance.portletURLTagSelector,
				tagNames: instance.rule.queryValues || ''
			};

			instance._tagSelector = new Liferay.AssetTaglibTagsSelector(
				config
			).render();

			let entries = instance._tagSelector.entries;

			entries.after('add', instance._updateQueryValues, instance);
			entries.after('remove', instance._updateQueryValues, instance);
		});
	}

	_updateQueryValues() {
		let instance = this;

		let tagSelector = instance._tagSelector;

		instance.rule.queryValues = tagSelector.entries.keys.join();
	}
}

TagSelector.STATE = {
	divId: Config.string().value(''),
	eventName: Config.string().value(''),
	groupIds: Config.string().value(''),
	hiddenInput: Config.string().value(''),
	index: Config.number().value(0),
	namespace: Config.string().value(''),
	portletURLTagSelector: Config.string().value(''),
	rule: Config.object().value({})
}

Soy.register(TagSelector, templates);

export { TagSelector };
export default TagSelector;