/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AutoComplete,
	FormError,
	SingleSelect,
	filterArrayByQuery,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import {KeyValuePair} from './EditObjectDetails';

const SCOPE_OPTIONS = [
	{
		label: Liferay.Language.get('company'),
		value: 'company',
	},
	{
		label: Liferay.Language.get('site'),
		value: 'site',
	},
];

interface ScopeContainerProps {
	companyKeyValuePair: KeyValuePair[];
	errors: FormError<ObjectDefinition>;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	setValues: (values: Partial<ObjectDefinition>) => void;
	siteKeyValuePair: KeyValuePair[];
	values: Partial<ObjectDefinition>;
}

export function ScopeContainer({
	companyKeyValuePair,
	errors,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	setValues,
	siteKeyValuePair,
	values,
}: ScopeContainerProps) {
	const [panelCategoryKeyQuery, setPanelCategoryKeyQuery] = useState('');

	const [selectedPanelCategoryKey, setSelectedPanelCategoryKey] = useState(
		''
	);

	const filteredPanelCategoryKey = useMemo(() => {
		return filterArrayByQuery({
			array:
				values.scope === 'company'
					? companyKeyValuePair
					: siteKeyValuePair,
			creationLanguageId: values.defaultLanguageId,
			query: panelCategoryKeyQuery,
			str: 'value',
		}) as KeyValuePair[];
	}, [
		values.defaultLanguageId,
		values.scope,
		companyKeyValuePair,
		siteKeyValuePair,
		panelCategoryKeyQuery,
	]);

	const setPanelCategoryKey = (
		KeyValuePairArray: KeyValuePair[],
		panelCategoryKey: string
	) => {
		const currentPanelCategory = KeyValuePairArray.find(
			(company) => company.key === panelCategoryKey
		);

		if (currentPanelCategory) {
			setSelectedPanelCategoryKey(currentPanelCategory.value);
		}
	};

	useEffect(() => {
		setPanelCategoryKey(
			values.scope === 'company' ? companyKeyValuePair : siteKeyValuePair,
			values.panelCategoryKey as string
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.scope, companyKeyValuePair, siteKeyValuePair]);

	return (
		<>
			<SingleSelect<LabelValueObject>
				disabled={
					isApproved ||
					!hasUpdateObjectDefinitionPermission ||
					values.storageType === 'salesforce'
				}
				error={errors.titleObjectFieldId}
				label={Liferay.Language.get('scope')}
				onChange={({value}) => {
					setValues({
						panelCategoryKey: '',
						scope: value,
					});
					setSelectedPanelCategoryKey('');
				}}
				options={SCOPE_OPTIONS}
				value={
					SCOPE_OPTIONS.find(
						(scopeOption) => scopeOption.value === values.scope
					)?.label
				}
			/>

			<AutoComplete<KeyValuePair>
				disabled={
					(Liferay.FeatureFlags['LPS-167253']
						? !values.modifiable && values.system
						: values.system) || !hasUpdateObjectDefinitionPermission
				}
				emptyStateMessage={Liferay.Language.get(
					'no-options-were-found'
				)}
				error={errors.titleObjectFieldId}
				items={filteredPanelCategoryKey}
				label={Liferay.Language.get('panel-link')}
				onActive={(item) => selectedPanelCategoryKey === item.value}
				onChangeQuery={setPanelCategoryKeyQuery}
				onSelectItem={({key, value}: KeyValuePair) => {
					setValues({
						panelCategoryKey: key,
					});

					setSelectedPanelCategoryKey(value);
				}}
				query={panelCategoryKeyQuery}
				value={selectedPanelCategoryKey}
			>
				{({value}) => (
					<div className="d-flex justify-content-between">
						<div>{value}</div>
					</div>
				)}
			</AutoComplete>
		</>
	);
}
