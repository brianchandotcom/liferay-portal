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

import {debounce, fetch} from 'frontend-js-web';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './SearchResults.soy';

class SearchResults extends Component {
	created() {
		this.search = debounce(this.search.bind(this), 500);

		this.handleKeyDown = this.handleKeyDown.bind(this);

		window.Liferay.on('search-term-update', this.updateQuery, this);

		window.Liferay.on('search-term-submit', this.goToSelected, this);
	}

	detached() {
		window.Liferay.detach('search-term-update', this.updateQuery, this);

		window.Liferay.detach('search-term-submit', this.goToSelected, this);

		document.removeEventListener('keydown', this.handleKeyDown);
	}

	getFirstSuggestion() {
		const selectables = this.results.filter((i) => i.type !== 'label');

		return selectables.length ? selectables[0].pos : -1;
	}

	getLastSuggestion() {
		const selectables = this.results
			.filter((i) => i.type !== 'label')
			.reverse();

		return selectables.length ? selectables[0].pos : -1;
	}

	goToSelected() {
		const selected = this.results.filter((i) => i.selected);

		if (selected.length && selected[0].url) {
			if (Liferay.SPA) {
				Liferay.SPA.app.navigate(selected[0].url);
			}
			else {
				window.location.href = selected[0].url;
			}
		}
	}

	handleKeyDown(event) {
		if (event.key === 'ArrowDown') {
			this.selectNext();
		}
		else if (event.key === 'ArrowUp') {
			this.selectPrevious();
		}
	}

	handleMouseEnter(event) {
		this.selectedIndex = parseInt(event.delegateTarget.dataset.pos, 10);
	}

	handleMouseLeave(_e) {
		this.selectedIndex = this.getFirstSuggestion();
	}

	rendered() {
		if (this.refs && this.refs.selected) {
			this.refs.selected.scrollIntoView({
				behavior: 'smooth',
				block: 'nearest',
			});
		}
	}

	search() {
		if (this.lock) {
			return;
		}

		this.lock = true;

		fetch(
			this.searchAPI +
				themeDisplay.getPlid() +
				'?commerceAccountId=' +
				this.commerceAccountId +
				'&groupId=' +
				themeDisplay.getScopeGroupId() +
				'&q=' +
				this.queryString
		)
			.then((response) => response.json())
			.then((results) => {
				this.loading = false;
				this.lock = false;
				this.queryValue = this.queryString;
				this.results = results;
				this.selectedIndex = -1;
				this.selectNext();
			});
	}

	selectNext() {
		const nexts = this.results.filter(
			(i) => i.pos > this.selectedIndex && i.type !== 'label'
		);

		this.selectedIndex = nexts.length
			? nexts[0].pos
			: this.getFirstSuggestion();
	}

	selectPrevious() {
		const prevs = this.results
			.filter((i) => i.pos < this.selectedIndex && i.type !== 'label')
			.reverse();

		this.selectedIndex = prevs.length
			? prevs[0].pos
			: this.getLastSuggestion();
	}

	setSelected(sel) {
		sel =
			((sel + 1 + this.results.length + 1) % (this.results.length + 1)) -
			1;

		this.results = this.results.map((item, i) => ({
			...item,
			pos: i,
			selected: i === sel,
		}));

		return sel;
	}

	updateQuery(event) {
		this.loading = true;
		this.queryString = event.term;
		this.visible = event.term !== '';

		this.search();
	}

	willUpdate({visible}) {
		if (visible) {
			if (visible.newVal) {
				document.addEventListener('keydown', this.handleKeyDown);
			}
			else {
				document.removeEventListener('keydown', this.handleKeyDown);
			}
		}
	}

	_handleClick() {
		this.goToSelected();
	}
}

Soy.register(SearchResults, template);

SearchResults.STATE = {
	commerceAccountId: Config.oneOfType([Config.number(), Config.string()]),
	loading: Config.bool().value(false),
	queryString: Config.string().value(''),
	queryValue: Config.string().value(''),
	results: {
		value: [],
	},
	searchAPI: Config.string().required(),
	selectedIndex: {
		setter: 'setSelected',
		value: -1,
	},
	spritemap: Config.string().required(),
	visible: Config.bool().value(false),
};

export {SearchResults};
export default SearchResults;
