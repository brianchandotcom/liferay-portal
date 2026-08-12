/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import {FormError, SingleSelect} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {Scope} from './EditObjectDetails';

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
	className?: string;
	companies: Scope[];
	errors: FormError<ObjectDefinition>;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	isLinkedObjectDefinition?: boolean;
	onSubmit?: (editedObjectDefinition?: Partial<ObjectDefinition>) => void;
	setValues: (values: Partial<ObjectDefinition>) => void;
	sites: Scope[];
	values: Partial<ObjectDefinition>;
}

export function ScopeContainer({
	className,
	companies,
	errors,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	isLinkedObjectDefinition,
	onSubmit,
	setValues,
	sites,
	values,
}: ScopeContainerProps) {
	const [selectedPanelCategoryValue, setSelectedPanelCategoryValue] =
		useState('');

	const initialPanelCategoryKeysRef = useRef<{[ERC: string]: string}>({});

	if (
		values?.externalReferenceCode &&
		!initialPanelCategoryKeysRef.current[values.externalReferenceCode]
	) {
		initialPanelCategoryKeysRef.current[values.externalReferenceCode] =
			values.panelCategoryKey ?? '';
	}

	const panelCategoryScopes = useMemo(() => {
		const scopes: Scope[] = [];

		const initialPanelCategoryKey =
			values.externalReferenceCode &&
			initialPanelCategoryKeysRef.current[values.externalReferenceCode];

		(values.scope === 'company' ? companies : sites).forEach(
			({items, label}) => {
				const visibleItems = items.filter(({deprecated, value}) => {
					return !deprecated || value === initialPanelCategoryKey;
				});

				if (visibleItems.length) {
					scopes.push({items: visibleItems, label});
				}
			}
		);

		return scopes;
	}, [companies, sites, values.externalReferenceCode, values.scope]);

	const setPanelCategoryKey = (
		scopes: Scope[],
		panelCategoryValue: string
	) => {
		let selectedPanelCategory: string = '';

		scopes.find(({items}) =>
			items.find(({value}) => {
				if (value === panelCategoryValue) {
					selectedPanelCategory = value;

					return true;
				}
			})
		);

		setSelectedPanelCategoryValue(selectedPanelCategory);
	};

	useEffect(() => {
		setPanelCategoryKey(
			panelCategoryScopes,
			values.panelCategoryKey as string
		);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.id, values.scope, panelCategoryScopes]);

	return (
		<>
			<SingleSelect<LabelValueObject>
				className={className}
				disabled={
					isApproved ||
					!hasUpdateObjectDefinitionPermission ||
					values.storageType === 'salesforce' ||
					isLinkedObjectDefinition
				}
				error={errors.titleObjectFieldId}
				id="lfr-objects__object-scope-container-scope"
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
				className={className}
				disabled={
					(!values.modifiable && values.system) ||
					!hasUpdateObjectDefinitionPermission ||
					isLinkedObjectDefinition ||
					values.scope === 'depot'
				}
				error={errors.titleObjectFieldId}
				id="lfr-objects__object-scope-container-panel-link"
				items={panelCategoryScopes}
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
