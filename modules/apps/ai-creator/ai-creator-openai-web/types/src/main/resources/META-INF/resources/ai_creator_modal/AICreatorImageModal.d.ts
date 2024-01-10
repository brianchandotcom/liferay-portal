/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Props {
	eventName?: string;
	getGenerationsURL: string;
	learnResources: AICreatorModalLearnResources;
	portletNamespace: string;
	uploadGenerationsURL: string;
}
declare type AICreatorModalLearnResources = {
	'ai-creator-openai-web': {
		general: {
			[key: string]: {
				message: string;
				url: string;
			};
		};
	};
};
export default function AICreatorImageModal({
	eventName,
	getGenerationsURL,
	learnResources,
	portletNamespace,
	uploadGenerationsURL,
}: Props): JSX.Element;
export {};
