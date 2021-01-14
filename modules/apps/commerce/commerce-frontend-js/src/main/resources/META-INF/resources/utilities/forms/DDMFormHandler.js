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
	constructor({DDMFormInstance, actionURL, portletId}) {
		this.actionURL = actionURL;
		this.DDMFormInstance = DDMFormInstance;
		this.portletId = portletId;
		this.fields = getDefaultFieldsShape(DDMFormInstance);

		this._attachFormListener();
		this.checkCPInstance();
	}

	_attachFormListener() {
		this.DDMFormInstance.on('fieldEdited', (field) => {
			this.fields = updateFields(this.fields, field);
			this.checkCPInstance();
		});
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
				cpInstance.options = ddmFormValues;
				cpInstance.skuId = parseInt(cpInstance.cpInstanceId, 10);

				const dispatchedPayload = {
					cpInstance,
					formFields: this.fields,
				};

				Liferay.fire(CP_INSTANCE_CHANGED, dispatchedPayload);
			}
		});
	}
}

Liferay.component(
	'DDMFormHandler',
	(() => ({
		attach: (configuration) => new DDMFormHandler(configuration),
	}))()
);

export default DDMFormHandler;
