/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	BuilderScreen,
	Card,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import React from 'react';

export interface UniqueCompositeKeyProps {
	creationLanguageId: Liferay.Language.Locale;
	objectFields: ObjectField[];
	setShowUniqueCompositeKeyAlert: (value: boolean) => void;
	showUniqueCompositeKeyAlert: boolean;
}

export function UniqueCompositeKey({
	creationLanguageId,
	objectFields,
	setShowUniqueCompositeKeyAlert,
	showUniqueCompositeKeyAlert,
}: UniqueCompositeKeyProps) {

	const filteredObjectFields = objectFields.filter(
		(objectField) =>
			objectField.businessType === 'Integer' ||
			'Picklist' ||
			'Relationship' ||
			'Text'
	);

	const handleAddObjectFields = () => {
		const parentWindow = Liferay.Util.getOpener();

		parentWindow.Liferay.fire('openModalSelectObjectFields', {
			getName: ({label, name}: ObjectField) =>
				getLocalizableLabel(creationLanguageId, label, name),
			header: Liferay.Language.get('add-fields-to-unique-composite-key'),
			items: filteredObjectFields.map((filteredObjectField) => ({
				...filteredObjectField,
				checked: false,
			})),
			onSave: () => {},
			selected: filteredObjectFields,
			title: Liferay.Language.get('select-the-fields'),
		});
	};

	return (
		<>
			<Card
				alert={{
					content: Liferay.Language.get(
						'a-unique-composite-key-validation-checks-if-the-combination-of-two-or-more-fields-can-be-used-to-uniquely-identify-each-entry'
					),
					otherProps: {
						displayType: 'info',
						title: Liferay.Language.get('info'),
						variant: 'stripe',
					},
					setShowAlert: setShowUniqueCompositeKeyAlert,
					showAlert: showUniqueCompositeKeyAlert,
				}}
				title={Liferay.Language.get('fields')}
			>
				<BuilderScreen
					builderScreenItems={[]}
					emptyState={{
						buttonText: Liferay.Language.get('add-fields'),
						description: Liferay.Language.get(
							'add-a-minimum-of-two-fields-to-create-composite-unique-keys'
						),
						title: Liferay.Language.get('no-fields-added-yet'),
					}}
					firstColumnHeader={Liferay.Language.get('label')}
					onDeleteColumn={() => {}}
					openModal={handleAddObjectFields}
					secondColumnHeader={Liferay.Language.get('type')}
				/>
			</Card>
		</>
	);
}
