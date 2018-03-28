import {Config} from 'metal-state';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';

import templates from './AddToCart.soy';

/**
 * It opens a dialog where the user can flag the page.
 * @abstract
 * @extends {PortletBase}
 */

class AddToCart extends PortletBase {

	/**
	 * Makes an ajax request to submit the data.
	 * @param {Event} event
	 * @protected
	 */

	_addToCart() {
		var instance = this;

		var _quantity = this.quantity;
		var ddmFormValues = '[]';

		var productContent = this._getProductContent();

		if (productContent) {
			ddmFormValues = JSON.stringify(productContent.getFormValues());

			if (this.cpInstanceId == '0') {
				this.cpInstanceId = productContent.getCPInstanceId();
			}
		}

		var quantityNode = document.querySelector('#' + this.taglibQuantityInputId);

		if (quantityNode) {
			_quantity = quantityNode.value;
		}

		let formData = new FormData();

		formData.append(this.portletNamespace + 'cpDefinitionId', this.cpDefinitionId);
		formData.append(this.portletNamespace + 'cpInstanceId', this.cpInstanceId);
		formData.append(this.portletNamespace + 'ddmFormValues', ddmFormValues);
		formData.append(this.portletNamespace + 'quantity', _quantity);

		fetch(
			this.uri,
			{
				body: formData,
				credentials: 'include',
				method: 'post'
			}
		).then(
			response => response.json()
		).then(
			(jsonresponse) => {
				if (jsonresponse.success) {
					Liferay.fire('commerce:productAddedToCart', jsonresponse);
				}
				else {
					var validatorErrors = jsonresponse.validatorErrors;

					if (validatorErrors) {
						validatorErrors.forEach(
							function(validatorError) {
								instance._showNotification(validatorError.message, 'danger');
							}
						);
					}
					else {
						instance._showNotification(jsonresponse.error, 'danger');
					}
				}
			}
		);
	}

	_getProductContent() {
		return Liferay.component(this.productContentId);
	}

	_handleClick() {
		var instance = this;

		var productContent = this._getProductContent();

		if (productContent) {
			productContent.validateProduct(
				function(hasError) {
					if (!hasError) {
						instance._addToCart();
					}
				}
			);
		}
		else {
			this._addToCart();
		}
	}

	_showNotification(message, type) {
		AUI().use(
			'liferay-notification',
			() => {
				new Liferay.Notification(
					{
						message: message,
						render: true,
						title: '',
						type: type
					}
				);
			}
		);
	}
}

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */

AddToCart.STATE = {

	/**
	 * CPDefinitionId.
	 * @instance
	 * @memberof AddToCart
	 * @type {?number}
	 * @default undefined
	 */

	cpDefinitionId: Config.string(),

	/**
	 * CPInstanceId.
	 * @instance
	 * @memberof AddToCart
	 * @type {?number}
	 * @default undefined
	 */

	cpInstanceId: Config.string(),

	/**
	 * CSS classes to be applied to the element.
	 * @instance
	 * @memberof AddToCart
	 * @type {?string}
	 * @default undefined
	 */

	elementClasses: Config.string(),

	/**
	 * Id of the html input to get the quantity.
	 * @instance
	 * @memberof AddToCart
	 * @type {?string}
	 * @default undefined
	 */

	taglibQuantityInputId: Config.string(),

	/**
	 * Default quantity to add to cart.
	 * @instance
	 * @memberof AddToCart
	 * @type {?string}
	 * @default undefined
	 */

	quantity: Config.string(),

	/**
	 * Component id.
	 * @instance
	 * @memberof AddToCart
	 * @type {String}
	 */

	id: Config.string().required(),

	/**
	 * Text to display inside the add to cart button.
	 * @instance
	 * @memberof AddToCart
	 * @type {String}
	 */

	label: Config.string().required(),

	/**
	 * Portlet's namespace
	 * @instance
	 * @memberof AddToCart
	 * @type {String}
	 */

	portletNamespace: Config.string().required(),

	/**
	 * Product content id
	 * @instance
	 * @memberof AddToCart
	 * @type {String}
	 */

	productContentId: Config.string(),

	/**
	 * Uri to add a cart item.
	 * @instance
	 * @memberof AddToCart
	 * @type {String}
	 */

	uri: Config.string().required()
};

// Register component

Soy.register(AddToCart, templates);

export default AddToCart;