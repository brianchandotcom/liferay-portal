import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class FragmentEntryUsageManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	propagate() {
		submitForm(this.one('#fm'));
	}
}
export default FragmentEntryUsageManagementToolbarDefaultEventHandler;
