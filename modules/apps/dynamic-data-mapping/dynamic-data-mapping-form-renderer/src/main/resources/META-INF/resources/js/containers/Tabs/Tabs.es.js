import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Tabs.soy.js';

class Tabs extends Component {
	_handleItemClicked({delegateTarget: {dataset}}) {
		const {dispatch} = this.context;
		const {pageIndex} = dataset;

		dispatch('paginationItemClicked', {
			pageIndex: Number(pageIndex)
		});
	}
}

Soy.register(Tabs, templates);

export default Tabs;
