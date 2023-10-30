/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import {FormError, SingleSelect} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {SiteCompanyJSONArray} from './EditObjectDetails';

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
	companyJSONArray: SiteCompanyJSONArray[];
	errors: FormError<ObjectDefinition>;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	isLinkedObjectDefinition?: boolean;
	isRootDescendantNode: boolean;
	onSubmit?: (editedObjectDefinition?: Partial<ObjectDefinition>) => void;
	setValues: (values: Partial<ObjectDefinition>) => void;
	siteJSONArray: SiteCompanyJSONArray[];
	values: Partial<ObjectDefinition>;
}

export function ScopeContainer({
	companyJSONArray,
	errors,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	isLinkedObjectDefinition,
	isRootDescendantNode,
	onSubmit,
	setValues,
	siteJSONArray,
	values,
}: ScopeContainerProps) {
	const [
		selectedPanelCategoryValue,
		setSelectedPanelCategoryValue,
	] = useState('');

	const setPanelCategoryKey = (
		siteCompanyJSONArray: SiteCompanyJSONArray[],
		panelCategoryValue: string
	) => {
		siteCompanyJSONArray.forEach(({items}) => {
			const selectedPanelCategory = items.find(
				({value}) => value === panelCategoryValue
			);

			if (selectedPanelCategory) {
				setSelectedPanelCategoryValue(selectedPanelCategory.value);
			}
		});
	};

	useEffect(() => {
		setPanelCategoryKey(
			values.scope === 'company' ? companyJSONArray : siteJSONArray,
			values.panelCategoryKey as string
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.scope, companyJSONArray, siteJSONArray]);

	return (
		<>
			<SingleSelect<LabelValueObject>
				disabled={
					isApproved ||
					!hasUpdateObjectDefinitionPermission ||
					values.storageType === 'salesforce' ||
					isRootDescendantNode ||
					isLinkedObjectDefinition
				}
				error={errors.titleObjectFieldId}
				items={SCOPE_OPTIONS}
				label={Liferay.Language.get('scope')}
				onSelectionChange={(value) => {
					setValues({
						panelCategoryKey: '',
						scope: value as string,
					});

					if (onSubmit) {
						onSubmit({
							...values,
							panelCategoryKey: '',
							scope: value as string,
						});
					}

					setSelectedPanelCategoryValue('');
				}}
				selectedKey={values.scope}
			/>

			<SingleSelect
				disabled={
					(!values.modifiable && values.system) ||
					!hasUpdateObjectDefinitionPermission ||
					isRootDescendantNode ||
					isLinkedObjectDefinition
				}
				error={errors.titleObjectFieldId}
				id="objectDetailsScopeContainer"
				items={
					values.scope === 'company'
						? companyJSONArray
						: siteJSONArray
				}
				label={Liferay.Language.get('panel-link')}
				onSelectionChange={(value) => {
					setValues({
						panelCategoryKey: value as string,
					});

					if (onSubmit) {
						onSubmit({
							...values,
							panelCategoryKey: value as string,
						});
					}

					setSelectedPanelCategoryValue(value as string);
				}}
				selectedKey={selectedPanelCategoryValue}
			>
				{(group) => (
					<ClayDropDown.Group
						header={group.label}
						items={group.items}
					>
						{(item) => (
							<Option key={item.value} textValue={item.label}>
								{item.label}
							</Option>
						)}
					</ClayDropDown.Group>
				)}
			</SingleSelect>
		</>
	);
}
