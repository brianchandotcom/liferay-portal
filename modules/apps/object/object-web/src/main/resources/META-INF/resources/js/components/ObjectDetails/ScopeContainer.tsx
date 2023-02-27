/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayPanel from '@clayui/panel';
import {
	AutoComplete,
	FormError,
	SingleSelect,
	filterArrayByQuery,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import {KeyValuePair} from './EditObjectDetails';

import './ObjectDetails.scss';

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
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('scope')}
			displayType="unstyled"
		>
			<ClayPanel.Body>
				<SingleSelect<LabelValueObject>
					disabled={
						isApproved || !hasUpdateObjectDefinitionPermission
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

				<AutoComplete
					creationLanguageId={
						values.defaultLanguageId as Liferay.Language.Locale
					}
					disabled={
						(!values.modifiable && values.system) ||
						!hasUpdateObjectDefinitionPermission
					}
					emptyStateMessage={Liferay.Language.get(
						'no-options-were-found'
					)}
					error={errors.titleObjectFieldId}
					items={filteredPanelCategoryKey}
					label={Liferay.Language.get('panel-category-key')}
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
			</ClayPanel.Body>
		</ClayPanel>
	);
}
