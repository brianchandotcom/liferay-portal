/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayToolbar from '@clayui/toolbar';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {fetch, objectToFormData, sub} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {initializeConfig} from '../../app/config/index';
import {setIn} from '../../app/utils/setIn';
import {FieldSet} from '../browser/components/page_structure/components/item_configuration_panels/FieldSet';

import './CollectionConfiguration.scss';

export default function (data) {
	initializeConfig({portletNamespace: data.portletNamespace});

	return <CollectionConfiguration {...data} />;
}

function CollectionConfiguration({
	collection,
	collectionItemTypeLabel,
	collectionLabel,
	configurationDefinition,
	getCollectionItemCountURL,
	languageId,
	portletNamespace: namespace,
}) {
	const initialConfig = collection.config || {};

	const [collectionConfig, setCollectionConfig] = useState(initialConfig);

	const handleFieldValueSelect = (fieldSet, name, value) => {
		const field = fieldSet.fields.find((field) => field.name === name);
		let nextConfig;

		if (field.localizable) {
			nextConfig = setIn(collectionConfig, [name, languageId], value);
		}
		else {
			nextConfig = setIn(collectionConfig, [name], value);
		}

		setCollectionConfig((previousItemConfig) => ({
			...previousItemConfig,
			...nextConfig,
		}));
	};

	return (
		<div className="cadmin">
			<div className="collection-configuration">
				<h1 className="m-0 p-4 sheet-title">
					{Liferay.Language.get('filter-collection')}
				</h1>

				<FilterInformationToolbar
					collection={collection}
					collectionConfig={collectionConfig}
					configurationDefinition={configurationDefinition}
					getCollectionItemCountURL={getCollectionItemCountURL}
					namespace={namespace}
					setCollectionConfig={setCollectionConfig}
				/>

				<div className="px-4">
					<p className="mb-1 text-secondary">
						<span className="font-weight-bold mr-1">
							{Liferay.Language.get('collection')}:
						</span>

						{collectionLabel}
					</p>

					<p className="text-secondary">
						<span className="font-weight-bold mr-1">
							{Liferay.Language.get('content-type')}:
						</span>

						{collectionItemTypeLabel}
					</p>

					<div className="collection-configuration__configuration-fieldsets">
						{configurationDefinition.fieldSets
							.filter((fieldSet) => fieldSet.fields.length)
							.map((fieldSet, index) => (
								<FieldSet
									description={fieldSet.description}
									fields={fieldSet.fields}
									key={`${fieldSet.label || ''}-${index}`}
									label={fieldSet.label}
									languageId={languageId}
									onValueSelect={(name, value) =>
										handleFieldValueSelect(
											fieldSet,
											name,
											value
										)
									}
									values={collectionConfig}
								/>
							))}
					</div>
				</div>

				<input
					name={`${namespace}collectionConfig`}
					type="hidden"
					value={JSON.stringify(collectionConfig)}
				/>
			</div>
		</div>
	);
}

const FilterInformationToolbar = ({
	collection,
	collectionConfig,
	configurationDefinition,
	getCollectionItemCountURL,
	namespace,
	setCollectionConfig,
}) => {
	const isMounted = useIsMounted();
	const [totalNumberOfItems, setTotalNumberOfItems] = useState(null);
	const [showAll, setShowAll] = useState(false);
	const [enableShowAll, setEnableShowAll] = useState(false);
	const filterInformationMessageElementRef = useRef();

	const hasConfigurationValues = !!Object.values(collectionConfig).filter(
		(value) => !!value
	).length;

	const filterInformationMessage = getFilterInformationMessage(
		configurationDefinition,
		collectionConfig
	);

	useEffect(() => {
		if (hasConfigurationValues) {
			fetch(getCollectionItemCountURL, {
				body: objectToFormData({
					[`${namespace}layoutObjectReference`]: JSON.stringify({
						...collection,
						config: collectionConfig,
					}),
				}),
				method: 'POST',
			})
				.then((response) => response.json())
				.then(({totalNumberOfItems}) => {
					if (isMounted()) {
						setTotalNumberOfItems(totalNumberOfItems || 0);
					}
				});
		}
	}, [
		collection,
		collectionConfig,
		getCollectionItemCountURL,
		hasConfigurationValues,
		isMounted,
		namespace,
	]);

	useEffect(() => {
		const element = filterInformationMessageElementRef.current;
		if (element && element.offsetWidth < element.scrollWidth) {
			setEnableShowAll(true);
		}
		else {
			setEnableShowAll(false);
		}
	}, [filterInformationMessage]);

	return hasConfigurationValues && totalNumberOfItems !== null ? (
		<ClayToolbar className="mb-3" subnav={{displayType: 'primary'}}>
			<ClayLayout.ContainerFluid>
				<ClayToolbar.Nav>
					<ClayToolbar.Item className="pl-2 text-left" expand>
						<ClayToolbar.Section>
							<span
								className={classNames('component-text', {
									'mb-0': showAll,
									'text-truncate': !showAll,
								})}
								ref={filterInformationMessageElementRef}
							>
								{totalNumberOfItems === 1
									? sub(
											Liferay.Language.get(
												'there-is-1-result-for-x'
											),
											filterInformationMessage
									  )
									: sub(
											Liferay.Language.get(
												'there-are-x-results-for-x'
											),
											totalNumberOfItems,
											filterInformationMessage
									  )}
							</span>
						</ClayToolbar.Section>

						{enableShowAll && (
							<ClayToolbar.Section>
								<ClayButton
									className="btn-link font-weight-semi-bold pl-0 tbar-link"
									displayType="unstyled"
									onClick={() =>
										setShowAll(
											(previousShowAll) =>
												!previousShowAll
										)
									}
								>
									<span
										className="c-inner ml-0"
										tabIndex="-1"
									>
										{showAll
											? Liferay.Language.get('show-less')
											: Liferay.Language.get('show-all')}
									</span>
								</ClayButton>
							</ClayToolbar.Section>
						)}
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<ClayButton
							className="component-link tbar-link"
							displayType="unstyled"
							onClick={() => {
								setCollectionConfig({});
							}}
						>
							{Liferay.Language.get('clear')}
						</ClayButton>
					</ClayToolbar.Item>
				</ClayToolbar.Nav>
			</ClayLayout.ContainerFluid>
		</ClayToolbar>
	) : null;
};

function getFilterInformationMessage(
	configurationDefinition,
	configurationValues
) {
	if (!configurationDefinition || !configurationValues) {
		return null;
	}

	const fields = configurationDefinition.fieldSets.flatMap(
		({fields}) => fields
	);

	return Object.entries(configurationValues)
		.filter(([_name, value]) => !!value)
		.map(([name, value]) => {
			const field = fields.find((field) => field.name === name);

			if (field?.type === 'select') {
				return Array.isArray(value)
					? value.map((v) => getFieldLabel(field, v)).join(', ')
					: getFieldLabel(field, value);
			}

			return typeof value === 'object' ? value.title : value;
		})
		.join(', ');
}

function getFieldLabel(field, value) {
	return (
		field.typeOptions?.validValues.find(
			(validValue) => validValue.value === value
		)?.label ?? value
	);
}
