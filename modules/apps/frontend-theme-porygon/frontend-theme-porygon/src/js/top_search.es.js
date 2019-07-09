/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import dom from 'metal-dom/src/dom';
import State from 'metal-state/src/State';

/**
 * TopSearch
 *
 * This class creates a basic component that enhances the default behaviour of the
 * search portlet form providing proper accessibility and a subtle integration with the
 * Porygon theme.
 */
class TopSearch extends State {
	/**
	 * @inheritDoc
	 */
	constructor() {
		super();

		this.porygonSearch_ = dom.toElement('.porygon-search');
		this.porygonSearchButton_ = dom.toElement('.porygon-search-button');
		this.porygonSearchInput_ = dom.toElement(
			'.porygon-search .search-portlet-keywords-input'
		);

		if (
			this.porygonSearch_ &&
			this.porygonSearchButton_ &&
			this.porygonSearchInput_
		) {
			dom.on(this.porygonSearchButton_, 'click', e =>
				this.onPorygonSearchButtonClick_(e)
			);
			dom.on(this.porygonSearch_, 'keydown', e =>
				this.onPorygonSearchEsc_(e)
			);
		}
	}

	/**
	 * Toggles the visibility of the search component when the user
	 * clicks on the search icon
	 *
	 * @protected
	 */
	onPorygonSearchButtonClick_() {
		dom.toggleClasses(this.porygonSearch_, 'active');
		this.porygonSearchInput_.focus();
	}

	/**
	 * Hides the search component when the user presses the ESC key
	 *
	 * @param  {KeyboardEvent} event
	 * @protected
	 */
	onPorygonSearchEsc_(event) {
		if (event.keyCode === 27) {
			dom.removeClasses(this.porygonSearch_, 'active');
		}
	}
}

export default TopSearch;
