/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider, commerceEvents} from 'commerce-frontend-js';

const AdminCatalogResource = CommerceServiceProvider.AdminCatalogAPI('v1');

const CMD = {
	ASSIGN: 'assign',
	CREATE: 'add',
};
export default function CreateOrAssignCPSpecificationOptionListTypeDefinition({
	cmd,
	cpSpecificationOptionId,
	namespace,
}) {
	let listTypeDefinitionId = 0;

	if (cmd === CMD.ASSIGN) {
		Liferay.on(`list-type-definition-id-selected`, ({id}) => {
			listTypeDefinitionId = id;
		});
	}

	Liferay.provide(window, `${namespace}storeToParentForm`, async (form) => {
		try {
			if (cmd === CMD.CREATE) {
				const name = form.querySelector('input[name="name"]').value;

				const {id} =
					await AdminCatalogResource.createSpecificationListTypeDefinitionById(
						cpSpecificationOptionId,
						{name}
					);

				listTypeDefinitionId = id;
			}
			else if (cmd === CMD.ASSIGN) {
				await AdminCatalogResource.createSpecificationListTypeDefinition(
					cpSpecificationOptionId,
					listTypeDefinitionId
				);
			}

			if (!listTypeDefinitionId) {
				throw new Error(
					Liferay.Language.get(
						'failed-to-associate-the-picklist-to-the-current-specification'
					)
				);
			}

			window.parent.Liferay.fire(commerceEvents.CLOSE_MODAL, {
				successNotification: {
					message: Liferay.Language.get(
						'your-request-completed-successfully'
					),
					showSuccessNotification: true,
				},
			});
		}
		catch (error) {
			window.parent.Liferay.fire(commerceEvents.CLOSE_MODAL);

			const message =
				error.message ??
				Liferay.Language.get('an-unexpected-error-occurred');

			window.parent.Liferay.Util.openToast({
				message,
				type: 'danger',
			});
		}
	});
}
