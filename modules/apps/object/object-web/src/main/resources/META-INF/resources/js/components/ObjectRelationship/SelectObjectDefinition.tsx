/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {
	API,
	AutoComplete,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import React, {Dispatch, useEffect, useState} from 'react';

interface SelectObjectDefinitionProps {
	creationLanguageId: Liferay.Language.Locale;
	disabled?: boolean;
	error?: string;
	filteredRelationships: Partial<ObjectDefinition>[];
	label?: string;
	objectDefinition?: Partial<ObjectDefinition>;
	objectDefinitionExternalReferenceCode?: string;
	query: string;
	readOnly?: boolean;
	reverseOrder: boolean;
	setObjectDefinition: (
		value: React.SetStateAction<Partial<ObjectDefinition> | undefined>
	) => void;
	setQuery: Dispatch<React.SetStateAction<string>>;
	setValues: (values: Partial<ObjectRelationship>) => void;
}

export default function SelectObjectDefinition({
	creationLanguageId,
	disabled,
	error,
	filteredRelationships,
	label,
	objectDefinition,
	objectDefinitionExternalReferenceCode,
	query,
	readOnly,
	reverseOrder,
	setObjectDefinition,
	setQuery,
	setValues,
}: SelectObjectDefinitionProps) {
	const [selectedObjectDefinition, setSelectedObjectDefinition] = useState<
		Partial<ObjectDefinition> | undefined
	>(objectDefinition);

	useEffect(() => {
		if (readOnly && !objectDefinition) {
			const fetchObjectDefinition = async () => {
				const object = await API.getObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode as string
				);

				setSelectedObjectDefinition(object);
			};

			fetchObjectDefinition();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<AutoComplete<Partial<ObjectDefinition>>
			disabled={disabled}
			emptyStateMessage={Liferay.Language.get('no-objects-were-found')}
			error={error}
			items={filteredRelationships}
			label={label ?? ''}
			onActive={(item) => item.name === selectedObjectDefinition?.name}
			onChangeQuery={setQuery}
			onSelectItem={(item) => {
				if (!reverseOrder) {
					setValues({
						objectDefinitionExternalReferenceCode2:
							item.externalReferenceCode,
						objectDefinitionId2: item.id,
						objectDefinitionName2: item.name,
					});
				}
				else {
					setValues({
						objectDefinitionExternalReferenceCode1:
							item.externalReferenceCode,
						objectDefinitionId1: item.id,
					});
				}

				setObjectDefinition(item);
				setSelectedObjectDefinition(item);
			}}
			query={query}
			required
			value={getLocalizableLabel(
				selectedObjectDefinition?.defaultLanguageId as Liferay.Language.Locale,
				selectedObjectDefinition?.label,
				selectedObjectDefinition?.name
			)}
		>
			{({label, name, system}) => (
				<div className="d-flex justify-content-between">
					<div>
						{getLocalizableLabel(
							creationLanguageId as Liferay.Language.Locale,
							label,
							name
						)}
					</div>

					<ClayLabel displayType={system ? 'info' : 'warning'}>
						{system
							? Liferay.Language.get('system')
							: Liferay.Language.get('custom')}
					</ClayLabel>
				</div>
			)}
		</AutoComplete>
	);
}
