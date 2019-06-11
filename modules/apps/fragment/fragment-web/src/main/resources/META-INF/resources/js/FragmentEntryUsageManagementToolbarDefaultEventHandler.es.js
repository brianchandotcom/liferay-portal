import {DefaultEventHandler} from 'frontend-js-web';

class FragmentEntryUsageManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	propagate() {
		submitForm(this.one('#fm'));
	}
}
export default FragmentEntryUsageManagementToolbarDefaultEventHandler;
