import Component from 'metal-component';
import {Config} from 'metal-state';

class ItemSelectorDialog extends Component {
	created() {
		console.log(this.eventName);
	}
}

ItemSelectorDialog.STATE = {
	dialogClasses: Config.string(),
	eventName: Config.string().required(),
	selectedItem: Config.object(),
	strings: {},
	title: Config.string().value(Liferay.Language.get('select-file')),
	zIndex: Config.number(),
	url: Config.string().required(),
	visible: Config.bool().value(false)
}

export default ItemSelectorDialog;