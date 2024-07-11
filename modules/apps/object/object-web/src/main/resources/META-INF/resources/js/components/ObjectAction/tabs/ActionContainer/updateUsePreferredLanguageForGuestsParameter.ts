/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function updateUsePreferredLanguageForGuestsParameter(
	values: Partial<ObjectAction>,
	notificationTemplateType?: string
) {
	const usePreferredLanguageForGuestsAcceptedTriggers =
		values.objectActionTriggerKey === 'onAfterAdd' ||
		values.objectActionTriggerKey === 'onAfterUpdate';

	const usePreferredLanguageForGuestsEnableCondition =
		usePreferredLanguageForGuestsAcceptedTriggers &&
		values.objectActionExecutorKey === 'notification' &&
		notificationTemplateType === 'email';

	if (!usePreferredLanguageForGuestsEnableCondition) {
		delete values.parameters?.usePreferredLanguageForGuests;
	}

	return {
		...values.parameters,
		...(usePreferredLanguageForGuestsEnableCondition && {
			usePreferredLanguageForGuests:
				values.parameters?.usePreferredLanguageForGuests !== undefined
					? values.parameters.usePreferredLanguageForGuests
					: true,
		}),
	};
}
