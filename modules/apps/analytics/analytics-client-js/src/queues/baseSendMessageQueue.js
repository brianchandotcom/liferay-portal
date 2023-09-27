/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClientAdapter from '../clientAdapter';
import BaseQueue from './baseQueue';

class BaseSendMessageQueue extends BaseQueue {
	constructor({analyticsInstance, flushTo, name}) {
		super({analyticsInstance, name});

		this.clientAdapter = new ClientAdapter({
			endpointUrl: flushTo,
			projectId: analyticsInstance.config.projectId,
		});
	}

	onFlush() {
		return this.getItems().map((message) =>
			this.clientAdapter
				.sendWithTimeout(message)
				.then(() => {
					this._dequeue(message.id);
				})
				.catch((error) => {
					if (error.status === 400) {
						this._dequeue(message.id);
					}

					return Promise.reject(error);
				})
		);
	}
}

export {BaseSendMessageQueue};
export default BaseSendMessageQueue;
