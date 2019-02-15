import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

class DefaultEventHandler extends PortletBase {
	callAction(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}

	handleCreationMenuItemClicked(event) {
		this.callAction(event);
	}

	handleFilterItemClicked(event) {
		this.callAction(event);
	}

	handleItemClicked(event) {
		this.callAction(event);
	}
}

export default DefaultEventHandler;