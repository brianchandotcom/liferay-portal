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

import AJAX from '../AJAX/index';
import {CP_INSTANCE_CHANGED} from '../eventsDefinitions';
import {getDefaultFieldsShape, updateFields} from './formsHelper';

class DDMFormHandler {
	constructor({DDMFormInstance, actionURL, namespace, portletId}) {
		this.actionURL = actionURL;
		this.DDMFormInstance = DDMFormInstance;
		this.namespace = namespace;
		this.portletId = portletId;
		this.fields = getDefaultFieldsShape(
			DDMFormInstance.reactComponentRef.current.toJSON()
		);

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
		const ddmFormValues = JSON.stringify(this.fields);
		const fieldsParam = new FormData();

		fieldsParam.append(`_${this.portletId}_ddmFormValues`, ddmFormValues);

		AJAX.POST(this.actionURL, null, {
			body: fieldsParam,
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
		}).then((cpInstance) => {
			if (cpInstance.cpInstanceExist) {
				cpInstance.disabled = this.checkCPInstanceOptions();
				cpInstance.skuOptions = ddmFormValues;
				cpInstance.skuId = parseInt(cpInstance.cpInstanceId, 10);

				const dispatchedPayload = {
					cpInstance,
					formFields: this.fields,
					namespace: this.namespace,
				};

				Liferay.fire(
					`${this.namespace}${CP_INSTANCE_CHANGED}`,
					dispatchedPayload
				);
			}
		});
	}

	checkCPInstanceOptions() {
		let disabled = false;

		for (const ddmFormValue of this.fields) {
			if (ddmFormValue.required) {
				if (ddmFormValue.value.length === 0) {
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
