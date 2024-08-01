/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Props = {
	id: string;
	objectDefinitionId?: string;
	pageElements?: PageElement[];
};

export default function getFormContainerDefinition({
	id,
	objectDefinitionId,
	pageElements,
}: Props): PageElement {
	if (!objectDefinitionId) {
		return {
			definition: {},
			id,
			type: 'Form',
		};
	}

	return {
		definition: {
			formConfig: {
				formReference: {
					className: `com.liferay.object.model.ObjectDefinition#${objectDefinitionId}`,
					classType: 0,
				},
			},
		},
		id,
		pageElements,
		type: 'Form',
	};
}
