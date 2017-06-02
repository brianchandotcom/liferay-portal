import Component from 'metal-component';
import Soy from 'metal-soy';

import dom from 'metal-dom';
import templates from './CategorySelector.soy';
import { Config } from 'metal-state';
/**
 * CategorySelector
 *
 */
class CategorySelector extends Component {

	rendered() {
		let instance = this;

		AUI().use('liferay-asset-taglib-categories-selector', function(A) {

			let config = {
				categoryIds: instance.rule.queryValues || '',
				categoryTitles: instance.rule.categoryIdsTitles ? instance.rule.categoryIdsTitles[1] : '',
				contentBox: '#' + instance.divId,
				eventName: instance.eventName,
				groupIds: instance.groupIds,
				hiddenInput: '#' + instance.hiddenInput,
				portletURL: instance.portletURLCategorySelector,
				vocabularyIds: instance.vocabularyIds,
			};

			instance.AssetTaglibCategoriesSelector = new Liferay.AssetTaglibCategoriesSelector(
				config
			).render();

		});

	}

}

CategorySelector.STATE = {
	divId: Config.string().value(''),
	eventName: Config.string().value(''),
	groupIds: Config.string().value(''),
	hiddenInput: Config.string().value(''),
	index: Config.number().value(0),
	namespace: Config.string().value(''),
	portletURLCategorySelector: Config.string().value(''),
	rule: Config.object().value({}),
	vocabularyIds: Config.string().value('')
}

Soy.register(CategorySelector, templates);

export default CategorySelector;