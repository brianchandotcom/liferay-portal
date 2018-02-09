import {Config} from 'metal-state';
import dom from 'metal-dom';
import Modal from 'metal-modal';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';

import templates from './AddToWishList.soy';

class AddToWishList extends PortletBase {

	_addToWishList() {
		var that = this;

		var ddmFormValues = "[]";

		var productContent = this._getProductContent();

		if (productContent) {
			ddmFormValues = JSON.stringify(productContent.getFormValues());
			this.cpInstanceId = productContent.getCPInstanceId();
		}

		let formData = new FormData();

		formData.append(this.portletNamespace + 'cpDefinitionId', this.cpDefinitionId);
		formData.append(this.portletNamespace + 'cpInstanceId', this.cpInstanceId);
		formData.append(this.portletNamespace + 'ddmFormValues', ddmFormValues);

		fetch(this.uri, {
			body: formData,
			credentials: 'include',
			method: 'post'
		})
		.then(response => response.json())
		.then((jsonresponse) => {
			if (jsonresponse.success) {
				Liferay.fire('commerce:productAddedToWishList', jsonresponse);
			}
			else {
				var validatorErrors = jsonresponse.validatorErrors;

				if (validatorErrors) {

					validatorErrors.forEach(
						function(validatorError) {
							that._showNotification(validatorError.message, 'danger');
						}
					);
				}
				else {
					that._showNotification(response.error, 'danger');
				}
			}
		});
	}

	_getProductContent() {
		return Liferay.component(this.productContentId);
	}

	_handleClick() {
		var that = this;

		var productContent = this._getProductContent();

		if(productContent) {
			productContent.validateProduct(
				function(hasError) {
					if (!hasError) {
						that._addToWishList();
					}
				}
			);
		}
		else {
			this._addToWishList();
		}
	}

	_showNotification(messageKey, type) {
		new Liferay.Notification(
			{
				message: Liferay.Language.get(messageKey),
				render: true,
				title: '',
				type: type
			}
		);
	}
};

AddToWishList.STATE = {
	cpDefinitionId: Config.string(),
	cpInstanceId: Config.string(),
	elementClasses: Config.string(),
	id: Config.string().required(),
	label: Config.string().required(),
	portletNamespace: Config.string().required(),
	productContentId: Config.string(),
	uri: Config.string().required()
};

// Register component
Soy.register(AddToWishList, templates);

export default AddToWishList;