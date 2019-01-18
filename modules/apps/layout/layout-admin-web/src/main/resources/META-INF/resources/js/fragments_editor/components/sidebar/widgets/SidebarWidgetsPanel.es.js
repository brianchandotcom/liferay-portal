import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './SidebarWidgetsPanel.soy';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';

/**
 * KeyBoardEvent enter key
 * @review
 * @type {!string}
 */
const ENTER_KEY = 'Enter';

/**
 * SidebarWidgetsPanel
 */
class SidebarWidgetsPanel extends Component {

	/**
	 * Filters widgets tree based on the keywords provided
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_filterWidgets(event) {
		const keywords = event.delegateTarget.value.toLowerCase();

		if (!this.initialWidgets) {
			this.initialWidgets = this.widgets.slice(0);
		}

		if (keywords === '') {
			const filteredWidgets = this.initialWidgets.slice(0);

			this.widgets = filteredWidgets;
		}
		else {
			const widgets = this.initialWidgets.slice(0);

			const filteredWidgets = widgets.reduce(
				(result, category) => {
					const filteredCategories = this._filterCategory(category, keywords);

					filteredCategories.forEach(
						filteredCategory => {
							filteredCategory.expanded = true;

							result.push(filteredCategory);
						}
					);

					return result;
				},
				[]
			);

			this.widgets = filteredWidgets;
		}
	}

	/**
	 * Filters a widget category based on the keywords provided
	 * @param {object} category
	 * @param {string} keywords
	 * @private
	 * @return {object[]}
	 * @review
	 */
	_filterCategory(category, keywords) {
		const filteredCategories = [];

		if (category.categories) {
			const categoriesCopy = category.categories.slice(0);

			categoriesCopy.forEach(
				subCategory => {
					const filteredSubCategories = this._filterCategory(subCategory, keywords);

					filteredSubCategories.forEach(
						filteredSubCategory => filteredCategories.push(filteredSubCategory)
					);
				}
			);
		}

		const categoryCopy = Object.assign({}, category);

		if (category.title.toLowerCase().indexOf(keywords) !== -1) {
			filteredCategories.push(categoryCopy);
		}

		if (category.portlets &&
			(filteredCategories.indexOf(categoryCopy) === -1)) {

			const portletsCopy = category.portlets.slice(0);

			const filteredPortlets = portletsCopy.filter(
				portlet => portlet.title.toLowerCase().indexOf(keywords) !== -1
			);

			if (filteredPortlets.length > 0) {
				categoryCopy.portlets = filteredPortlets;

				delete categoryCopy.categories;

				filteredCategories.push(categoryCopy);
			}
		}

		return filteredCategories;
	}

	/**
	 * When the search form is submitted, nothing should happen,
	 * as filtering is performed on keypress.
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */
	_handleSearchFormKeyDown(event) {
		if (event.key === ENTER_KEY) {
			event.preventDefault();
			event.stopImmediatePropagation();
		}
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SidebarWidgetsPanel.STATE = {};

const ConnectedSidebarWidgetsPanel = getConnectedComponent(
	SidebarWidgetsPanel,
	[
		'widgets',
		'spritemap'
	]
);

Soy.register(ConnectedSidebarWidgetsPanel, templates);

export {ConnectedSidebarWidgetsPanel, SidebarWidgetsPanel};
export default ConnectedSidebarWidgetsPanel;