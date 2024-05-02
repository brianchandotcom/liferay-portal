/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PORTLET_URLS} from '../../../utils/portletUrls';

export class KnowledgeBaseUrls {
	readonly home: string;
	readonly parameterPrefix =
		'_com_liferay_knowledge_base_web_portlet_AdminPortlet_';

	constructor(siteUrl?: Site['friendlyUrlPath']) {
		this.home = `/group${siteUrl || '/guest'}${PORTLET_URLS.knowledgeBase}`;
	}

	getEditKBArticleUrl(
		id: string,
		forceLock: boolean = false,
		redirect?: string
	) {
		let array = [
			this.getRenderCommandUrl('/knowledge_base/edit_kb_article'),
			this.getParameter('resourcePrimKey', id),
		];

		if (forceLock) {
			array = array.concat(this.getParameter('forceLock', '1'));
		}

		if (redirect !== undefined) {
			array = array.concat(this.getParameter('redirect', redirect));
		}

		return array.join('&');
	}

	private getParameter(key: string, value: string) {
		return `${this.parameterPrefix}${key}=${value}`;
	}

	private getRenderCommandUrl(commandName: string) {
		return [
			this.home,
			this.getParameter('mvcRenderCommandName', commandName),
		].join('&');
	}
}
