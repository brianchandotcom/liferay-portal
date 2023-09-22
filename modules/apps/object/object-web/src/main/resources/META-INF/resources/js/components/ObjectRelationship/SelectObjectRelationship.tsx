/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	AutoComplete,
	getLocalizableLabel,
	stringIncludesQuery,
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
	const [query, setQuery] = useState<string>('');
	const options = useMemo(
		() =>
			objectFields.map(({label, name}) => {
				return {
					label: getLocalizableLabel(
						creationLanguageId as Liferay.Language.Locale,
						label,
						name
					),
					name,
				};
			}),
		[creationLanguageId, objectFields]
	);

	const filteredOptions = useMemo(() => {
		if (options) {
			return options.filter((option) =>
				stringIncludesQuery(option.label, query)
			);
		}
	}, [options, query]);

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
					({businessType}) => businessType === 'Relationship'
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
		<AutoComplete<LabelNameObject>
			emptyStateMessage={Liferay.Language.get('no-parameters-were-found')}
			error={error}
			items={filteredOptions ?? []}
			label={Liferay.Language.get('parameter')}
			onActive={(item) => item.name === selectedValue?.name}
			onChangeQuery={setQuery}
			onSelectItem={({name}) => {
				onChange(
					objectFields.find(({name: fieldName}) => fieldName === name)
						?.name!
				);
			}}
			query={query}
			required
			tooltip={Liferay.Language.get(
				'choose-a-relationship-field-from-the-selected-object'
			)}
			value={getLocalizableLabel(
				creationLanguageId as Liferay.Language.Locale,
				selectedValue?.label,
				selectedValue?.name
			)}
			{...otherProps}
		>
			{({label, name}) => (
				<div className="d-flex justify-content-between">
					{label ?? name}
				</div>
			)}
		</AutoComplete>
	);
}
