import {Config} from 'metal-state';
import ClayComponent from 'clay-component';
import Soy from 'metal-soy';

import 'clay-table';
import 'clay-pagination-bar';

import templates from './Table.soy';

class Table extends ClayComponent {
	_handlepageSizeClicked(event) {
		event.preventDefault();

		this.pageSize = event.data.item.label;

		this.paginationSelectedEntry = this.paginationEntries.map(
			x => x.label
		).indexOf(this.pageSize);

		this._loadData();
	}

	_handlePageClicked(event) {
		event.preventDefault();

		this.page = parseInt(event.data.page, 10);

		this._loadData();
	}

	_loadData() {
		fetch(this._getDataSourceURL())
			.then(response => response.json())
			.then(
				result => {
					this.items = result.items;
				}
			);
	}

	_getDataSourceURL() {
		return `${this.dataSourceURL}?pageSize=${this.pageSize}&page=${this.page}`;
	}
}

Soy.register(Table, templates);

Table.STATE = {
	dataSourceURL: Config.string(),
	items: Config.array(),
	page: Config.number(),
	pageSize: Config.number(),
	paginationEntries: Config.array(),
	paginationSelectedEntry: Config.number()
};

export {Table};
export default Table;