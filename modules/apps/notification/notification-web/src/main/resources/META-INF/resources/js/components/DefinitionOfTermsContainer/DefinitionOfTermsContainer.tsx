/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {Text} from '@clayui/core';
import {
	Card,
	fieldsUtils,
	objectDefinitionUtils,
} from '@liferay/object-js-components-web';
import React, {useMemo, useState} from 'react';

import {DefinitionOfTerms} from './DefinitionOfTerms';
import {GeneralTerms} from './GeneralTerms';

interface DefinitionOfTermsContainerProps {
	baseResourceURL: string;
	objectDefinitions: ObjectDefinition[];
}

function hasLocalizedField(
	objectDefinitions: ObjectDefinition[],
	entityId: number
): boolean {
	if (!objectDefinitions.length || !entityId) {
		return false;
	}

	const selectedObjectDefinition =
		objectDefinitionUtils.findObjectDefinitionById(
			entityId,
			objectDefinitions
		);

	return (
		!!selectedObjectDefinition &&
		fieldsUtils.hasLocalizedField(
			selectedObjectDefinition as ObjectDefinition
		)
	);
}

export default function DefinitionOfTermsContainer({
	baseResourceURL,
	objectDefinitions,
}: DefinitionOfTermsContainerProps) {
	const [selectedEntityId, setSelectedEntityId] = useState<number>(0);

	const localizedFields = useMemo(() => {
		return hasLocalizedField(objectDefinitions, selectedEntityId);
	}, [objectDefinitions, selectedEntityId]);

	return (
		<Card title={Liferay.Language.get('definition-of-terms')}>
			{localizedFields && (
				<ClayAlert
					displayType="info"
					title={`${Liferay.Language.get('info')}:`}
				>
					{Liferay.Util.sub(
						Liferay.Language.get(
							'this-object-includes-translatable-fields-.x'
						),
						Liferay.Language.get('notification-terms')
					)}
				</ClayAlert>
			)}

			<Text as="span" color="secondary">
				{Liferay.Language.get(
					'use-terms-to-populate-fields-dynamically-with-the-exception-of-the-freemarker-template-editor'
				)}
			</Text>

			<GeneralTerms baseResourceURL={baseResourceURL} />

			<DefinitionOfTerms
				baseResourceURL={baseResourceURL}
				objectDefinitions={objectDefinitions}
				selectedEntityId={selectedEntityId}
				setSelectedEntityId={setSelectedEntityId}
			/>
		</Card>
	);
}
