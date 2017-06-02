import Component from 'metal-component';
import Soy from 'metal-soy';

import dom from 'metal-dom';
import templates from './TagSelector.soy';
import { Config } from 'metal-state';
/**
 * TagSelector
 *
 */
class TagSelector extends Component {

	rendered() {
		let instance = this;

		let parentNode = document.getElementById('#' + instance.divId);

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

		});

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

export default TagSelector;