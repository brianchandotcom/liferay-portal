/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	SingleSelect,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

interface IProps {
	error?: string;
	objectDefinitionExternalReferenceCode1: string;
	onChange: (objectFieldName: string) => void;
	value?: string;
}

export default function SelectRelationship({
	error,
	objectDefinitionExternalReferenceCode1,
	onChange,
	value,
	...otherProps
}: IProps) {
	const [creationLanguageId, setCreationLanguageId] = useState<
		Liferay.Language.Locale
	>();
	const [objectFields, setObjectFields] = useState<ObjectField[]>([]);
	const objectFieldItems = useMemo(
		() =>
			objectFields.map(({label, name}) => {
				return {
					label: getLocalizableLabel(
						creationLanguageId as Liferay.Language.Locale,
						label,
						name
					),
					value: name,
				};
			}),
		[creationLanguageId, objectFields]
	);

	const selectedValue = useMemo(() => {
		return objectFields.find(({name}) => name === value);
	}, [objectFields, value]);

	useEffect(() => {
		if (objectDefinitionExternalReferenceCode1) {
			const makeFetch = async () => {
				const objectFields = await API.getObjectDefinitionByExternalReferenceCodeObjectFields(
					objectDefinitionExternalReferenceCode1
				);

				const objectDefinition = await API.getObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode1
				);

				setCreationLanguageId(objectDefinition.defaultLanguageId);

				const objectFieldOptions = objectFields.filter(
					({objectFieldSettings}) => {
						const objectDefinition1ShortName = objectFieldSettings?.find(
							({name}) => name === 'objectDefinition1ShortName'
						);

						return (
							objectDefinition1ShortName &&
							objectDefinition1ShortName.value === 'AccountEntry'
						);
					}
				);

				setCreationLanguageId(objectDefinition.defaultLanguageId);

				setObjectFields(objectFieldOptions);
			};

			makeFetch();
		}
		else {
			setObjectFields([]);
		}
	}, [objectDefinitionExternalReferenceCode1]);

	return (
		<SingleSelect
			error={error}
			id="objectRelationshipSelectObjectRelationship"
			items={objectFieldItems ?? []}
			label={Liferay.Language.get('parameter')}
			onSelectionChange={(value) => {
				onChange(
					objectFields.find(
						({name: fieldName}) => fieldName === value
					)?.name!
				);
			}}
			required
			selectedKey={selectedValue?.name}
			tooltip={Liferay.Language.get(
				'choose-a-relationship-field-from-the-selected-object'
			)}
			{...otherProps}
		/>
	);
}
