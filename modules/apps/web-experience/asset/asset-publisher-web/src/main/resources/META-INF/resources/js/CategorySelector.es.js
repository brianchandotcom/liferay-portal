import Component from 'metal-component';
import Soy from 'metal-soy';
import dom from 'metal-dom';
import { Config } from 'metal-state';

import templates from './CategorySelector.soy';

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
				categoryTitles: instance.rule.categoryIdsTitles ? instance.rule.categoryIdsTitles : [],
				contentBox: '#' + instance.divId,
				eventName: instance.eventName,
				groupIds: instance.groupIds,
				hiddenInput: '#' + instance.hiddenInput,
				portletURL: instance.portletURLCategorySelector,
				vocabularyIds: instance.vocabularyIds,
			};

			instance._categoriesSelector = new Liferay.AssetTaglibCategoriesSelector(
				config
			).render();

			let entries = instance._categoriesSelector.entries;

			entries.after('add', instance._updateQueryValues, instance);
			entries.after('remove', instance._updateQueryValues, instance);
		});

	}

	_updateQueryValues() {
		let instance = this;

		let categoriesSelector = instance._categoriesSelector;

		instance.rule.categoryIdsTitles = categoriesSelector.entries.values.map((element) => element.value);

		instance.rule.queryValues = categoriesSelector.entries.keys.join(',');
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

export { CategorySelector };
export default CategorySelector;