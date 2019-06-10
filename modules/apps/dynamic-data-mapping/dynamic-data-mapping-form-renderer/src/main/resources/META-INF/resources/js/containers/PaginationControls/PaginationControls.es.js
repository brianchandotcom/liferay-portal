import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './PaginationControls.soy.js';

class PaginationControls extends Component {
	_handleNextClicked() {
		const {dispatch} = this.context;

		dispatch('paginationNextClicked');
	}

	_handlePreviousClicked() {
		const {dispatch} = this.context;

		dispatch('paginationPreviousClicked');
	}
}

Soy.register(PaginationControls, templates);

export default PaginationControls;
