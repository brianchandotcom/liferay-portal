/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';
import {createResourceURL, sub} from 'frontend-js-web';

export async function deleteObjectField(
	objectFieldId: number,
	objectFieldLabel: string
) {
	try {
		await API.deleteObjectField(objectFieldId);

		Liferay.Util.openToast({
			message: sub(
				Liferay.Language.get('x-was-deleted-successfully'),
				`<strong>${Liferay.Util.escapeHTML(objectFieldLabel)}</strong>`
			),
		});
	}
	catch (error) {
		Liferay.Util.openToast({
			message: (error as Error).message,
			type: 'danger',
		});
	}
}

interface handleTriggerDeleteObjectFieldProps {
	baseResourceURL: string;
	objectFieldId: number;
	objectFieldLabel: string;
	onAfterDelete: () => void;
	setObjectFieldDeleteInfo: (value: ObjectFieldDeleteInfoProps) => void;
}

export async function handleTriggerDeleteObjectField({
	baseResourceURL,
	objectFieldId,
	objectFieldLabel,
	onAfterDelete,
	setObjectFieldDeleteInfo,
}: handleTriggerDeleteObjectFieldProps) {
	const objectFieldDeleteInfoURL = createResourceURL(baseResourceURL, {
		objectFieldId,
		p_p_resource_id: '/object_definitions/get_object_field_delete_info',
	}).href;

	const showModalResponse = await API.fetchJSON<{
		deleteLastPublishedObjectDefinitionObjectField: boolean;
		deleteObjectFieldObjectValidationRuleSetting: boolean;
		showObjectFieldDeletionConfirmationModal: boolean;
	}>(objectFieldDeleteInfoURL);

	setObjectFieldDeleteInfo({
		deleteLastPublishedObjectDefinitionObjectField:
			showModalResponse.deleteLastPublishedObjectDefinitionObjectField,
		deleteObjectFieldObjectValidationRuleSetting:
			showModalResponse.deleteObjectFieldObjectValidationRuleSetting,
		showObjectFieldDeletionConfirmationModal:
			showModalResponse.showObjectFieldDeletionConfirmationModal,
		showObjectFieldDeletionNotAllowedModal:
			!showModalResponse.deleteObjectFieldObjectValidationRuleSetting ||
			!showModalResponse.deleteLastPublishedObjectDefinitionObjectField,
	});

	if (
		!showModalResponse.showObjectFieldDeletionConfirmationModal &&
		showModalResponse.deleteObjectFieldObjectValidationRuleSetting &&
		showModalResponse.deleteLastPublishedObjectDefinitionObjectField
	) {
		await deleteObjectField(objectFieldId, objectFieldLabel);

		onAfterDelete();
	}
}
