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

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import ClayToolbar from '@clayui/toolbar';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useEffect, useRef, useState} from 'react';

import {useSelector} from '../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../app/selectors/selectLanguageId';
import {selectPageContents} from '../../../../../app/selectors/selectPageContents';
import CollectionService from '../../../../../app/services/CollectionService';
import {setIn} from '../../../../../app/utils/setIn';
import {FieldSet} from './item-configuration-panels/FieldSet';

const COLLECTION_TYPE_DIVIDER = ' - ';
const DEFAULT_CONFIG_VALUES = {};

export default function CollectionFilterConfigurationModal({
	collectionConfiguration,
	handleConfigurationChanged,
	itemConfig: initialItemConfig,
	observer,
	onClose,
}) {
	const languageId = useSelector(selectLanguageId);
	const pageContents = useSelector(selectPageContents);
	const [itemConfig, setItemConfig] = useState(initialItemConfig);

	const {
		classNameId,
		classPK,
		key: collectionKey,
	} = initialItemConfig?.collection;

	const collection = pageContents.find((content) =>
		collectionKey
			? content.classPK === collectionKey
			: content.classNameId === classNameId && content.classPK === classPK
	);

	const [typeLabel, subtypeLabel] =
		collection?.subtype?.split(COLLECTION_TYPE_DIVIDER) || [];

	const handleFieldValueSelect = (fieldSet, name, value) => {
		const field = fieldSet.fields.find((field) => field.name === name);
		let nextConfig;

		if (field.localizable) {
			nextConfig = setIn(
				itemConfig,
				['collection', 'config', name, languageId],
				value
			);
		}
		else {
			nextConfig = setIn(
				itemConfig,
				['collection', 'config', name],
				value
			);
		}

		setItemConfig((previousItemConfig) => ({
			...previousItemConfig,
			...nextConfig,
		}));
	};

	const handleSaveButtonClick = () => {
		handleConfigurationChanged(itemConfig);
		onClose();
	};

	useEffect(() => {
		if (initialItemConfig) {
			setItemConfig(initialItemConfig);
		}
	}, [initialItemConfig]);

	return (
		<ClayModal
			className="page-editor__collection-filter-configuration-modal"
			containerProps={{
				className: 'cadmin',
			}}
			observer={observer}
		>
			<ClayModal.Header>
				{Liferay.Language.get('filter-collection')}
			</ClayModal.Header>
			<ClayModal.Body className="pt-0">
				<FilterInformationToolbar
					collectionConfiguration={collectionConfiguration}
					itemConfig={itemConfig}
					setItemConfig={setItemConfig}
				/>
				<div className="p-4">
					{typeLabel && (
						<p
							className={classNames(
								'page-editor__collection-filter-configuration-modal__type-label',
								{
									'mb-0': subtypeLabel,
									'mb-3': !subtypeLabel,
								}
							)}
						>
							<span className="mr-1">
								{Liferay.Language.get('type')}:
							</span>
							{typeLabel}
						</p>
					)}

					{subtypeLabel && (
						<p className="mb-3 page-editor__collection-filter-configuration-modal__type-label">
							<span className="mr-1">
								{Liferay.Language.get('subtype')}:
							</span>
							{subtypeLabel}
						</p>
					)}

					<div className="page-editor__collection-filter-configuration-modal__configuration-field-sets">
						{collectionConfiguration ? (
							collectionConfiguration.fieldSets
								.filter((fieldSet) => fieldSet.fields.length)
								.map((fieldSet, index) => (
									<FieldSet
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
										values={
											itemConfig?.collection?.config ?? {}
										}
									/>
								))
						) : (
							<ClayLoadingIndicator />
						)}
					</div>
				</div>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<>
						<ClayButton
							className="mr-2"
							displayType="secondary"
							onClick={onClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton onClick={handleSaveButtonClick}>
							{Liferay.Language.get('apply')}
						</ClayButton>
					</>
				}
			/>
		</ClayModal>
	);
}

const FilterInformationToolbar = ({
	itemConfig = {},
	setItemConfig,
	collectionConfiguration,
}) => {
	const collectionConfigurationValues =
		itemConfig?.collection?.config ?? DEFAULT_CONFIG_VALUES;

	const isMounted = useIsMounted();
	const [totalNumberOfItems, setTotalNumberOfItems] = useState(null);
	const [showAll, setShowAll] = useState(false);
	const [enableShowAll, setEnableShowAll] = useState(false);
	const filterInformationMessageElementRef = useRef();

	const hasConfigurationValues =
		Object.values(collectionConfigurationValues).filter((value) => !!value)
			.length > 0;

	const filterInformationMessage = getFilterInformationMessage({
		collectionConfiguration,
		collectionConfigurationValues,
	});

	useEffect(() => {
		if (hasConfigurationValues) {
			CollectionService.getCollectionItemCount({
				collection: itemConfig?.collection,
				onNetworkStatus: () => {},
			}).then(({totalNumberOfItems}) => {
				if (isMounted()) {
					setTotalNumberOfItems(totalNumberOfItems || 0);
				}
			});
		}
	}, [isMounted, itemConfig?.collection, hasConfigurationValues]);

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
		<ClayToolbar subnav={{displayType: 'primary'}}>
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
									? Liferay.Util.sub(
											Liferay.Language.get(
												'there-is-1-result-for-x'
											),
											filterInformationMessage
									  )
									: Liferay.Util.sub(
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
								const nextConfig = setIn(
									itemConfig,
									['collection', 'config'],
									{}
								);
								setItemConfig((previousItemConfig) => ({
									...previousItemConfig,
									...nextConfig,
								}));
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

function getFilterInformationMessage({
	collectionConfiguration,
	collectionConfigurationValues,
}) {
	if (!collectionConfiguration || !collectionConfigurationValues) {
		return null;
	}

	const fields = collectionConfiguration.fieldSets[0].fields;

	return Object.entries(collectionConfigurationValues)
		.filter(([_name, value]) => !!value)
		.map(([name, value]) => {
			const field = fields.find((field) => field.name === name);

			if (field?.type === 'select') {
				return Array.isArray(value)
					? value.map((v) => getFieldLabel(field, v)).join(', ')
					: value;
			}

			return value;
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
