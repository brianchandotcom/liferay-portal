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

import {DefaultEventHandler} from 'frontend-js-web';

class EntriesDefaultEventHandler extends DefaultEventHandler {
	moveEntry(itemData) {
		const instance = this;

		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true,
					width: 1024
				},
				eventName: this.ns('selectContainer'),
				id: this.ns('selectContainer'),
				title: Liferay.Language.get('warning'),
				uri: itemData.moveEntryURL
			},
			event => {
				const selectContainerForm = document.getElementById(
					`${instance.namespace}selectContainerForm`
				);

				if (selectContainerForm) {
					const className = selectContainerForm.querySelector(
						`#${instance.namespace}className`
					);

					if (className) {
						className.setAttribute('value', event.classname);
					}

					const classPK = selectContainerForm.querySelector(
						`#${instance.namespace}classPK`
					);

					if (classPK) {
						classPK.setAttribute('value', event.classpk);
					}

					const containerModelId = selectContainerForm.querySelector(
						`#${instance.namespace}containerModelId`
					);

					if (containerModelId) {
						containerModelId.setAttribute(
							'value',
							event.containermodelid
						);
					}

					const redirect = selectContainerForm.querySelector(
						`#${instance.namespace}redirect`
					);

					if (redirect) {
						redirect.setAttribute('value', event.redirect);
					}

					submitForm(selectContainerForm);
				}
			}
		);
	}

	restoreEntry(itemData) {
		submitForm(document.hrefFm, itemData.restoreEntryURL);
	}

	deleteEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteEntryURL);
		}
	}
}

export default EntriesDefaultEventHandler;
