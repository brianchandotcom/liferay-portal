/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ServiceProvider from '../../ServiceProvider/index';
import {CP_INSTANCE_CHANGED} from '../eventsDefinitions';
import {getDefaultFieldsShape, updateFields} from './formsHelper';

class DDMFormHandler {
	constructor({
		DDMFormInstance,
		accountId,
		channelId,
		namespace,
		productId,
		quantity,
	}) {
		this.DDMFormInstance = DDMFormInstance;
		this.accountId = accountId;
		this.channelId = channelId;
		this.fields = getDefaultFieldsShape(
			DDMFormInstance.reactComponentRef.current.toJSON()
		);
		this.namespace = namespace;
		this.productId = productId;
		this.quantity = quantity;

		this._attachFormListener();
		this.checkCPInstance();
	}

	_attachFormListener() {
		this.DDMFormInstance.unstable_onEvent(
			({payload: field, type: eventName}) => {
				if (eventName === 'field_change') {
					this.fields = updateFields(this.fields, field);
					this.checkCPInstance();
				}
			}
		);
	}

	checkCPInstance() {
		const SkuResource = ServiceProvider.DeliveryCatalogAPI('v1');

		SkuResource.postChannelProductSku(
			this.channelId,
			this.productId,
			this.accountId,
			this.quantity,
			this.fields
		).then((cpInstance) => {
			cpInstance.disabled = this.checkCPInstanceOptions();
			cpInstance.skuOptions = JSON.stringify(this.fields);
			cpInstance.skuId = parseInt(cpInstance.id, 10);

			const dispatchedPayload = {
				cpInstance,
				formFields: this.fields,
				namespace: this.namespace,
			};

			Liferay.fire(
				`${this.namespace}${CP_INSTANCE_CHANGED}`,
				dispatchedPayload
			);
		});
	}

	checkCPInstanceOptions() {
		let disabled = false;

		for (const ddmFormValue of this.fields) {
			if (ddmFormValue.required) {
				if (!ddmFormValue.value.length) {
					disabled = true;
				}
				else {
					for (const value of ddmFormValue.value) {
						if (value === null || value === '') {
							disabled = true;
							break;
						}
						else {
							disabled = false;
						}
					}
				}
			}

			if (disabled) {
				break;
			}
		}

		return disabled;
	}
}

export default DDMFormHandler;
