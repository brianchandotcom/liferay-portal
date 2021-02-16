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

import 'clay-icon';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './SearchBar.soy';

class SearchBar extends Component {
	created() {
		this._handleDocumentKeypress = this._handleDocumentKeypress.bind(this);
		this._handleClickOutside = this._handleClickOutside.bind(this);
		this.toggle = this.toggle.bind(this);
		document.addEventListener('keydown', this._handleDocumentKeypress);
		this._addOpenButtonListener();
	}

	detached() {
		document.removeEventListener('keydown', this._handleDocumentKeypress);
	}

	_addOpenButtonListener() {
		return Array.from(document.querySelectorAll('.js-toggle-search')).map(
			(el) => {
				return el.addEventListener('click', this.toggle);
			}
		);
	}

	_removeOpenButtonListener() {
		return Array.from(document.querySelectorAll('.js-toggle-search')).map(
			(el) => {
				return el.removeEventListener('click', this.toggle);
			}
		);
	}

	_handleClickOutside(e) {
		const suggestions = document.querySelector('.commerce-suggestions');
		if (
			!(
				this.element.contains(e.target) ||
				(suggestions && suggestions.contains(e.target))
			)
		) {
			this.toggle();
		}
	}

	_handleDocumentKeypress(evt) {
		if (this.active && evt.key === 'Escape') {
			this.close();
		}

		const noModalsActive = !Array.from(
			document.querySelectorAll('.modal.show')
		).length;

		if (
			!this.active &&
			evt.key === '/' &&
			evt.target.tagName !== 'INPUT' &&
			noModalsActive
		) {
			evt.preventDefault();
			this.open();
		}
	}

	_handleResetQuery(_evt) {
		this._updateQuery('');
	}

	_handleKeyDown(evt) {
		if (evt.key === 'ArrowDown' || evt.key === 'ArrowUp') {
			evt.preventDefault();
		}
	}

	_handleKeyUp(evt) {
		this._updateQuery(evt.target.value);
	}

	_handleSubmit(evt) {
		evt.preventDefault();

		window.Liferay.fire('search-term-submit', {
			term: this.query,
		});
	}

	_updateQuery(query) {
		if (query !== this.query) {
			this.open();

			this.query = query;

			window.Liferay.fire('search-term-update', {
				term: query,
			});
		}
	}

	syncActive() {
		if (this.active) {
			window.addEventListener('click', this._handleClickOutside);
			setTimeout(() => {
				this._removeOpenButtonListener();
				this.refs.searchInput.focus();
			}, 0);
		}
		else {
			window.removeEventListener('click', this._handleClickOutside);
			setTimeout(() => {
				this._addOpenButtonListener();
				this.refs.searchInput.blur();
			}, 0);
		}
		this.emit('toggled', this.active);
	}

	open() {
		this.active = true;
	}

	close() {
		this.active = false;
	}

	toggle() {
		return this.active ? this.close() : this.open();
	}
}

Soy.register(SearchBar, template);

SearchBar.STATE = {
	active: Config.bool(),
	placeholder: Config.value(''),
	query: Config.string(),
};

export {SearchBar};
export default SearchBar;
