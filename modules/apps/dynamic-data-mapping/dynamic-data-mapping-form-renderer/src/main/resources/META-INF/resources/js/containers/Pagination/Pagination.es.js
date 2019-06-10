import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Pagination.soy.js';

class Pagination extends Component {
	_handleItemClicked({delegateTarget: {dataset}}) {
		const {dispatch} = this.context;
		const {pageIndex} = dataset;

		dispatch('paginationItemClicked', {
			pageIndex: Number(pageIndex)
		});
	}

	_handleNextClicked() {
		const {dispatch} = this.context;

		dispatch('paginationNextClicked');
	}

	_handlePreviousClicked() {
		const {dispatch} = this.context;

		dispatch('paginationPreviousClicked');
	}
}

Soy.register(Pagination, templates);

export default Pagination;
