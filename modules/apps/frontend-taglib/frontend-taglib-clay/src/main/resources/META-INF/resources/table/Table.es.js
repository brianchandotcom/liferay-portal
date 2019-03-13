import 'clay-table';
import 'clay-pagination-bar';
import ClayComponent from 'clay-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './Table.soy';

class Table extends ClayComponent {

	_handleItemToggled(event) {
	}

	_handleItemsPerPageClicked(event) {
		event.preventDefault();

		if (this.itemsPerPage == event.data.item.label) {
			return;
		}

		this.itemsPerPage = event.data.item.label;

		this.paginationSelectedEntry = this.paginationEntries.map((x) => x.label).indexOf(this.itemsPerPage);

		this._loadData();
	}

	_handlePageClicked(event) {
		const newPageNumber = parseInt(event.data.page, 10);

		if (this.pageNumber == newPageNumber) {
			return;
		}

		this.pageNumber = newPageNumber;

		this._loadData();
	}

	_loadData() {
		fetch(
			this._getDataSourceURL()
		).then(
			response =>
				response.json()
		).then(
			result => {
				this.items = result.items;
			}
		).catch(
			() => {}
		);
	}

	_getDataSourceURL() {
		return `${this.dataSourceURL}?` +
			`itemsPerPage=${this.itemsPerPage}` +
			`&pageNumber=${this.pageNumber}`;
	}

}

Soy.register(Table, templates);

Table.STATE = {
	dataSourceURL: Config.string(),
	items: Config.array(),
	itemsPerPage: Config.number(),
	pageNumber: Config.number(),
	paginationEntries: Config.array(),
	paginationSelectedEntry: Config.number()
};

export {Table};
export default Table;