import 'clay-multi-select';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import templates from './InputCategoriesSelector.soy';

class InputCategoriesSelector extends Component {

	/**
	 * @inheritDoc
	 */
	attached() {
		this.refs.categoriesSelector.on('buttonClicked', this._handleButtonClicked);
	}

	_handleButtonClicked(event) {
	}

	/**
	 * Transforms the categories list in the object needed
	 * for the ClayMultiSelect component.
	 *
	 * @param {List<Long, String>} categories
	 * @return {List<{label, value}>} new commonItems object list
	 */
	_setCommonCategories(categories) {
		let categoriesObjList = [];

		if (categories.length > 0) {
			categories.forEach(
				item => {
					let itemObj = {
						'label': item.name,
						'value': item.categoryId
					};

					categoriesObjList.push(itemObj);
				}
			);
		}

		return categoriesObjList;
	}
}

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
InputCategoriesSelector.STATE = {

	/**
	 * Categories of the selected fileEntries.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {List<String>}
	 */
	commonCategories: Config.array().setter('_setCommonCategories').value([]),

	/**
	 * Url to the categories selector page
	 * @type {String}
	 */
	selectCategoriesUrl: Config.string().required(),

	/**
	 * Path to images.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {String}
	 */
	spritemap: Config.string().required()
};

// Register component

Soy.register(InputCategoriesSelector, templates);

export default InputCategoriesSelector;