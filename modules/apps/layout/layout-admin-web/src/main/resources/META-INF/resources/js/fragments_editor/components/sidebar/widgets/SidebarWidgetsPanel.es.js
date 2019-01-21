import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './SidebarWidgetsPanel.soy';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {shouldUpdateOnChangeProperties} from '../../../utils/FragmentsEditorComponentUtils.es';

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
	 * @param {object[]} widgets
	 * @param {string} [keywords='']
	 * @private
	 * @return {object[]}
	 * @review
	 */
	static _filterWidgets(widgets, keywords = '') {
		let filteredWidgets = [...widgets];

		if (keywords) {
			filteredWidgets = filteredWidgets.reduce(
				(result, category) => {
					const filteredCategories = SidebarWidgetsPanel._filterCategory(
						category,
						keywords
					);

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
		}

		return filteredWidgets;
	}

	/**
	 * Filters a widget category based on the keywords provided
	 * @param {object} category
	 * @param {string} keywords
	 * @private
	 * @return {object[]}
	 * @review
	 */
	static _filterCategory(category, keywords) {
		const filteredCategories = [];

		if (category.categories) {
			const categoriesCopy = category.categories.slice(0);

			categoriesCopy.forEach(
				subCategory => {
					const filteredSubCategories = SidebarWidgetsPanel._filterCategory(
						subCategory,
						keywords
					);

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
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		return setIn(
			state,
			['widgets'],
			SidebarWidgetsPanel._filterWidgets(
				state.widgets,
				state._keywords
			)
		);
	}

	/**
	 * @inheritdoc
	 * @param {object} changes
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdateOnChangeProperties(
			changes,
			[
				'spritemap',
				'widgets'
			]
		);
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

		this._keywords = event.delegateTarget.value.toLowerCase();
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SidebarWidgetsPanel.STATE = {

	/**
	 * @default ''
	 * @instance
	 * @memberOf SidebarWidgetsPanel
	 * @private
	 * @review
	 * @type {string}
	 */
	_keywords: Config
		.string()
		.internal()
		.value('')
};

const ConnectedSidebarWidgetsPanel = getConnectedComponent(
	SidebarWidgetsPanel,
	[
		'spritemap',
		'widgets'
	]
);

Soy.register(ConnectedSidebarWidgetsPanel, templates);

export {ConnectedSidebarWidgetsPanel, SidebarWidgetsPanel};
export default ConnectedSidebarWidgetsPanel;