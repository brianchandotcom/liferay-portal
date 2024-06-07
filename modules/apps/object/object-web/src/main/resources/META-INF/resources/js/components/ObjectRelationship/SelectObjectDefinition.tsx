/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option} from '@clayui/core';
import ClayLabel from '@clayui/label';
import {
	API,
	SingleSelect,
	stringUtils,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import './SelectObjectDefinition.scss';

interface SelectObjectDefinitionProps {
	creationLanguageId: Liferay.Language.Locale;
	disabled?: boolean;
	error?: string;
	label?: string;
	objectDefinition?: Partial<ObjectDefinition>;
	objectDefinitionExternalReferenceCode?: string;
	objectDefinitions: Partial<ObjectDefinition>[];
	readOnly?: boolean;
	reverseOrder: boolean;
	setObjectDefinition: (
		value: React.SetStateAction<Partial<ObjectDefinition> | undefined>
	) => void;
	setValues: (values: Partial<ObjectRelationship>) => void;
}

export default function SelectObjectDefinition({
	creationLanguageId,
	disabled,
	error,
	label,
	objectDefinition,
	objectDefinitionExternalReferenceCode,
	objectDefinitions,
	readOnly,
	reverseOrder,
	setObjectDefinition,
	setValues,
}: SelectObjectDefinitionProps) {
	const [
		selectedObjectDefinitionExternalReferenceCode,
		setSelectedObjectDefinitionExternalReferenceCode,
	] = useState<string | undefined>(objectDefinition?.externalReferenceCode);

	const objectDefinitionsItems = useMemo(() => {
		return objectDefinitions.map(
			({externalReferenceCode, label, name, system}) => ({
				label: stringUtils.getLocalizableLabel(
					creationLanguageId as Liferay.Language.Locale,
					label,
					name
				),
				system,
				value: externalReferenceCode,
			})
		);
	}, [creationLanguageId, objectDefinitions]);

	useEffect(() => {
		if (readOnly && !objectDefinition) {
			const fetchObjectDefinition = async () => {
				const {externalReferenceCode} =
					await API.getObjectDefinitionByExternalReferenceCode(
						objectDefinitionExternalReferenceCode as string
					);

				setSelectedObjectDefinitionExternalReferenceCode(
					externalReferenceCode
				);
			};

			fetchObjectDefinition();
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<SingleSelect
			disabled={disabled}
			error={error}
			id="objectRelationshipSelectObjectDefinition"
			items={objectDefinitionsItems}
			label={label ?? ''}
			onSelectionChange={(value) => {
				const selectedObjectDefinition = objectDefinitions.find(
					({externalReferenceCode}) => externalReferenceCode === value
				);

				if (!reverseOrder) {
					setValues({
						objectDefinitionExternalReferenceCode2:
							selectedObjectDefinition?.externalReferenceCode,
						objectDefinitionId2: selectedObjectDefinition?.id,
						objectDefinitionName2: selectedObjectDefinition?.name,
					});
				}
				else {
					setValues({
						objectDefinitionExternalReferenceCode1:
							selectedObjectDefinition?.externalReferenceCode,
						objectDefinitionId1: selectedObjectDefinition?.id,
					});
				}

				setObjectDefinition(selectedObjectDefinition);
				setSelectedObjectDefinitionExternalReferenceCode(
					selectedObjectDefinition?.externalReferenceCode
				);
			}}
			required
			selectedKey={selectedObjectDefinitionExternalReferenceCode}
		>
			{({label, system, value}) => (
				<Option key={value} textValue={label}>
					<div className="lfr-objects__select-object-definition-option">
						<div>{label}</div>

						<ClayLabel displayType={system ? 'info' : 'warning'}>
							{system
								? Liferay.Language.get('system')
								: Liferay.Language.get('custom')}
						</ClayLabel>
					</div>
				</Option>
			)}
		</SingleSelect>
	);
}
