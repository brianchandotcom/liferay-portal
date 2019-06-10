import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Wizard.soy.js';
import {Config} from 'metal-state';

class Wizard extends Component {
	_handleItemClicked({delegateTarget: {dataset}}) {
		const {dispatch} = this.context;
		const {pageIndex} = dataset;

		dispatch('paginationItemClicked', {
			pageIndex: Number(pageIndex)
		});
	}
}

Wizard.STATE = {
	activePage: Config.number(),
	pages: Config.array()
};

Soy.register(Wizard, templates);

export default Wizard;
