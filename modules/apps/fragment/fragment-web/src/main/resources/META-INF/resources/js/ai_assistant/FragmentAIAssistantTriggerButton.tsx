/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AIAssistantTriggerButton,
	CONTENT_CHANGED_EVENT,
} from '@liferay/ai-hub-cell-js-components-web';
import React, {useEffect} from 'react';

import {
	AI_ASSISTANT_TRIGGER_ID,
	GENERATE_FRAGMENT_CHATBOT_ERC,
} from './aiAssistant';

function getFragment(
	data: string | undefined
): {fragmentSetExternalReferenceCode?: string} | null {
	if (!data) {
		return null;
	}

	try {
		return JSON.parse(JSON.parse(data).data);
	}
	catch {
		return null;
	}
}

type Props = {
	configurationSchema?: string;
	fragmentPortletNamespace: string;
	fragmentSetExternalReferenceCode?: string;
	siteExternalReferenceCode: string;
};

export default function FragmentAIAssistantTriggerButton({
	configurationSchema,
	fragmentPortletNamespace,
	fragmentSetExternalReferenceCode,
	siteExternalReferenceCode,
}: Props) {
	useEffect(() => {
		const contentChangedHandler = Liferay.on(
			CONTENT_CHANGED_EVENT,
			({data}: {data?: string}) => {
				const fragment = getFragment(data);

				if (
					fragment &&
					fragment.fragmentSetExternalReferenceCode !==
						fragmentSetExternalReferenceCode &&
					fragmentSetExternalReferenceCode
				) {
					return;
				}

				Liferay.Portlet.refresh(`#p_p_id${fragmentPortletNamespace}`);
			}
		);

		return () => {
			contentChangedHandler.detach();
		};
	}, [fragmentPortletNamespace, fragmentSetExternalReferenceCode]);

	return (
		<AIAssistantTriggerButton
			chatbotExternalReferenceCode={GENERATE_FRAGMENT_CHATBOT_ERC}
			context={{
				configurationSchema,
				fragmentSetExternalReferenceCode,
				siteExternalReferenceCode,
			}}
			greeting={Liferay.Language.get(
				'hi-i-can-help-you-generate-fragments'
			)}
			hideLabel
			instructionDefinitionScope=""
			presentation="sidebar"
			pushContainer="#main-content"
			triggerId={AI_ASSISTANT_TRIGGER_ID}
		/>
	);
}
