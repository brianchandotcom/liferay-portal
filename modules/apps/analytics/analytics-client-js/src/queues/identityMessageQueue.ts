/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Analytics from '../analytics';
import {Analytics as AnalyticsType} from '../types';
import BaseSendMessageQueue from './baseSendMessageQueue';

class IdentityMessageQueue extends BaseSendMessageQueue {
	constructor({
		analyticsInstance,
		name = AnalyticsType.Queues.IdentityMessage,
	}: {
		analyticsInstance: Analytics;
		name?: AnalyticsType.Queues;
	}) {
		super({
			analyticsInstance,
			flushTo: analyticsInstance.config.identityEndpoint,
			name,
		});
	}
}

export {IdentityMessageQueue};
export default IdentityMessageQueue;
