import Component from 'metal-component';
import Soy from 'metal-soy';
import { Config } from 'metal-state';

import templates from './CategorySelector.soy';

/**
 * CategorySelector is a temporary Component wrapping the existing
 * AUI module liferay-asset-taglib-categories-selector
 */
class CategorySelector extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		AUI().use(
			'liferay-asset-taglib-categories-selector',
			function(A) {
				const config = {
					categoryIds: this.rule.queryValues || '',
					categoryTitles: this.rule.categoryIdsTitles ? this.rule.categoryIdsTitles : [],
					contentBox: this.element,
					eventName: this.eventName,
					groupIds: this.groupIds,
					hiddenInput:  `#${this.element.querySelector('input[type="hidden"]').getAttribute('id')}`,
					portletURL: this.categorySelectorURL,
					vocabularyIds: this.vocabularyIds,
				};

				this.categoriesSelector_ = new Liferay.AssetTaglibCategoriesSelector(config);

				const entries = this.categoriesSelector_.entries;

				entries.after('add', this.onEntriesChanged_, this);
				entries.after('remove', this.onEntriesChanged_, this);

				this.categoriesSelector_.render();
				this.element.parentNode.removeAttribute('tabindex');
			}.bind(this)
		);

	}

	/**
	 * Updates the calculated rule fields for `queryValues` and `categoryIdsTitles`
	 * every time a new category entry is added or removed to the selection
	 * @protected
	 */
	onEntriesChanged_() {
		this.rule.categoryIdsTitles = this.categoriesSelector_.entries.values.map((element) => element.value);
		this.rule.queryValues = this.categoriesSelector_.entries.keys.join(',');
	}

}

CategorySelector.STATE = {
	categorySelectorURL: Config.string().value(''),
	divId: Config.string().value(''),
	eventName: Config.string().value(''),
	groupIds: Config.string().value(''),
	hiddenInput: Config.string().value(''),
	index: Config.number().value(0),
	namespace: Config.string().value(''),
	rule: Config.object().value({}),
	vocabularyIds: Config.string().value('')
}

Soy.register(CategorySelector, templates);

export { CategorySelector };
export default CategorySelector;