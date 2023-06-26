/* eslint-disable prefer-const */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ApolloClient} from '@apollo/client';
import {
	getNotificationTemplateByExternalRefenceCode,
	notificationQueueEntry,
} from '../liferay/graphql/queries';

type NotificationTemplateLanguage = {
	en_US: string;
};

type NotificationTemplateType = {
	body: NotificationTemplateLanguage;
	recipients: {
		from: string;
		fromName: string;
		to: NotificationTemplateLanguage;
	}[];
	subject: NotificationTemplateLanguage;
	type: string;
};

type DataToReplaceType = {
	[key: string]: string;
};

type ExternalReferenceCodeOptions =
	| 'SETUP-ANALYTICS-CLOUD-ENVIRONMENT-NOTIFICATION-TEMPLATE'
	| 'SETUP-DXP-CLOUD-ENVIRONMENT-NOTIFICATION-TEMPLATE';

export default class NotificationQueueService {
	private client: ApolloClient<any>;

	constructor(client: ApolloClient<any>) {
		this.client = client;
	}

	private async getNotificationTemplateByExternalReferenceCode(
		externalReferenceCode: string
	) {
		const {
			data: {notificationTemplateByExternalReferenceCode},
		} = await this.client.query({
			query: getNotificationTemplateByExternalRefenceCode,
			variables: {
				externalReferenceCode,
			},
		});

		return notificationTemplateByExternalReferenceCode as NotificationTemplateType;
	}

	public async send(
		externalReferenceCode: ExternalReferenceCodeOptions,
		dataToReplace: DataToReplaceType
	) {
		const notificationTemplate = await this.getNotificationTemplateByExternalReferenceCode(
			externalReferenceCode
		);

		let {
			body: {en_US: body},
			recipients,
			subject: {en_US: subject},
			type,
		} = notificationTemplate;

		for (const key in dataToReplace) {
			const value = dataToReplace[key];

			body = body.replace(key, value);
			subject = subject.replace(key, value);
		}

		await this.client.mutate({
			context: {
				displaySuccess: false,
			},
			mutation: notificationQueueEntry,
			variables: {
				notificationQueueEntry: {
					body,
					recipients: recipients.map(({from, fromName, to}) => ({
						from,
						fromName,
						to: to.en_US,
					})),
					subject,
					type,
				},
			},
		});
	}
}
