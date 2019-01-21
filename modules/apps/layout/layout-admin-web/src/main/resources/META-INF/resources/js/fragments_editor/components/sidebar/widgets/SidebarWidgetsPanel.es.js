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
	 * Returns true if the given keywords matches the given text
	 * @param {string} text
	 * @param {string} keywords
	 * @return {boolean}
	 * @review
	 */
	static _keywordsMatch(text, keywords) {
		return text.toLowerCase().indexOf(keywords.toLowerCase()) !== -1;
	}

	/**
	 * Filters widgets tree based on the keywords provided
	 * @param {object[]} widgets
	 * @param {string} [keywords='']
	 * @private
	 * @return {object[]}
	 * @review
	 */
	static _filterWidgets(widgets, keywords = '') {
		let filteredWidgets = widgets;

		if (keywords) {
			filteredWidgets = SidebarWidgetsPanel._filterCategories(
				widgets,
				keywords
			);
		}

		return filteredWidgets;
	}

	/**
	 * Returns a filtered list of categories
	 * @param {object[]} categories
	 * @param {string} keywords
	 * @private
	 * @return {object[]}
	 * @review
	 */
	static _filterCategories(categories, keywords) {
		return categories
			.map(
				widgetCategory => SidebarWidgetsPanel._filterCategory(
					widgetCategory,
					keywords
				)
			)
			.filter(
				widgetCategory => widgetCategory
			);
	}

	/**
	 * Filters a widget category based on the keywords provided
	 * @param {object} category
	 * @param {string} keywords
	 * @private
	 * @return {object|null}
	 * @review
	 */
	static _filterCategory(category, keywords) {
		let filteredCategory = setIn(
			category,
			['categories'],
			SidebarWidgetsPanel._filterCategories(
				category.categories || [],
				keywords
			)
		);

		const filteredPortlets = (category.portlets || []).filter(
			portlet => SidebarWidgetsPanel._keywordsMatch(portlet.title, keywords)
		);

		if (!SidebarWidgetsPanel._keywordsMatch(category.title, keywords)) {
			filteredCategory = setIn(filteredCategory, ['portlets'], filteredPortlets);
		}

		if (!filteredCategory.portlets.length && !filteredCategory.categories.length) {
			filteredCategory = null;
		}

		return filteredCategory;
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
				'_keywords',
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
	_handleSearchInputKeyUp(event) {
		if (event.key === ENTER_KEY) {
			event.preventDefault();
			event.stopImmediatePropagation();
		}

		this._keywords = event.delegateTarget.value;
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