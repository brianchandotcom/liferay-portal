/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {TargetCollectionDisplayField} from '../../../../../../app/components/fragment_configuration_fields/TargetCollectionDisplayField';
import {selectConfiguredCollectionDisplays} from '../../../../../../app/components/fragment_configuration_fields/TargetCollectionDisplayField';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {VIEWPORT_SIZES} from '../../../../../../app/config/constants/viewportSizes';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import CollectionService from '../../../../../../app/services/CollectionService';
import {deepEqual} from '../../../../../../app/utils/checkDeepEqual';
import getFragmentConfigurationValues from '../../../../../../app/utils/getFragmentConfigurationValues';
import {getResponsiveConfig} from '../../../../../../app/utils/getResponsiveConfig';
import isEmptyArray from '../../../../../../app/utils/isEmptyArray';
import isEmptyObject from '../../../../../../app/utils/isEmptyObject';
import updateConfigurationValue from '../../../../../../app/utils/updateConfigurationValue';
import getLayoutDataItemPropTypes from '../../../../../../prop_types/getLayoutDataItemPropTypes';
import {CommonStyles} from './CommonStyles';
import {FieldSet} from './FieldSet';

export function CollectionFilterGeneralPanel({item}) {
	const dispatch = useDispatch();

	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);

	const languageId = useSelector(selectLanguageId);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const collections = useSelectorCallback(
		selectConfiguredCollectionDisplays,
		[],
		deepEqual
	);

	const [collectionFilters, setCollectionFilters] = useState(null);
	const [filterableCollections, setFilterableCollections] = useState(null);
	const [loading, setLoading] = useState(false);

	const configurationValues =
		getFragmentConfigurationValues(fragmentEntryLink);

	const targetCollections = (
		configurationValues.targetCollections || []
	).filter((targetCollection) => filterableCollections?.[targetCollection]);

	const selectedFilter = collectionFilters?.[configurationValues.filterKey];

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);

	useEffect(() => {
		if (isEmptyArray(collections)) {
			setFilterableCollections({});

			return;
		}

		setLoading(true);

		CollectionService.getCollectionSupportedFilters(
			collections.map((item) => ({
				collectionId: item.itemId,
				layoutObjectReference: item.config?.collection,
			}))
		)
			.then((response) => {
				const nextFilterableCollections = {};

				collections
					.filter(
						(collection) =>
							!isEmptyArray(response[collection.itemId])
					)
					.forEach((collection) => {
						nextFilterableCollections[collection.itemId] = {
							...collection,
							supportedFilters: response[collection.itemId],
						};
					});

				setFilterableCollections(nextFilterableCollections);
			})
			.finally(() => setLoading(false));
	}, [collections]);

	useEffect(() => {
		if (!collectionFilters) {
			CollectionService.getCollectionFilters().then(
				(collectionFilters) => {
					setCollectionFilters(collectionFilters);
				}
			);
		}
	}, [collectionFilters]);

	const onValueSelect = useCallback(
		(name, value) => {
			updateConfigurationValue({
				dispatch,
				fragmentEntryLink,
				languageId,
				name,
				value,
			});
		},
		[dispatch, fragmentEntryLink, languageId]
	);

	const onFilterValueSelect = useCallback(
		(name, value) => {
			updateConfigurationValue({
				configuration: selectedFilter?.configuration,
				dispatch,
				fragmentEntryLink,
				languageId,
				name,
				value,
			});
		},
		[dispatch, selectedFilter, fragmentEntryLink, languageId]
	);

	if (loading || filterableCollections === null) {
		return <ClayLoadingIndicator className="my-0" size="sm" />;
	}

	return (
		<>
			<div className="mb-3 panel-group-sm">
				<ClayPanel
					collapsable
					defaultExpanded
					displayTitle={Liferay.Language.get(
						'collection-filter-options'
					)}
					displayType="unstyled"
					showCollapseIcon
				>
					<ClayPanel.Body>
						<TargetCollectionDisplayField
							field={{
								label: Liferay.Language.get(
									'target-collection'
								),
								name: 'targetCollections',
								typeOptions: {
									enableCompatibleCollections: true,
								},
							}}
							onValueSelect={onValueSelect}
							value={configurationValues.targetCollections}
						/>

						{!isEmptyArray(targetCollections) &&
							!isEmptyObject(collectionFilters) && (
								<SelectField
									field={{
										label: Liferay.Language.get('filter'),
										name: 'filterKey',
										typeOptions: {
											validValues: [
												{
													label: Liferay.Language.get(
														'none'
													),
													value: '',
												},
												...filterSupportedFilters({
													collectionFilters:
														Object.values(
															collectionFilters
														),
													filterableCollections,
													targetCollections,
												}).map(({key, label}) => ({
													label,
													value: key,
												})),
											],
										},
									}}
									onValueSelect={onFilterValueSelect}
									value={configurationValues.filterKey}
								/>
							)}
					</ClayPanel.Body>
				</ClayPanel>

				{!isEmptyArray(targetCollections) &&
					selectedFilter?.configuration &&
					selectedFilter.configuration.fieldSets
						?.filter((fieldSet) => fieldSet.fields.length)
						.map((fieldSet, index) => (
							<div
								className="mt-3"
								key={`${fieldSet.label || ''}-${index}`}
							>
								<FieldSet
									description={fieldSet.description}
									fields={fieldSet.fields}
									label={fieldSet.label}
									languageId={languageId}
									onValueSelect={onFilterValueSelect}
									values={configurationValues}
								/>
							</div>
						))}
			</div>

			<CommonStyles
				commonStylesValues={itemConfig.styles}
				item={item}
				role={COMMON_STYLES_ROLES.general}
			/>
		</>
	);
}

CollectionFilterGeneralPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired,
		}).isRequired,
	}),
};

function filterSupportedFilters({
	collectionFilters,
	targetCollections = [],
	filterableCollections,
}) {
	if (isEmptyArray(targetCollections)) {
		return {};
	}

	const targetCollectionsSupportedFilters = targetCollections
		.map(
			(targetCollection) =>
				filterableCollections[targetCollection]?.supportedFilters
		)
		.filter(Boolean);

	return collectionFilters.filter(({key}) =>
		targetCollectionsSupportedFilters.every(
			(targetCollectionSupportedFilters) =>
				targetCollectionSupportedFilters.includes(key)
		)
	);
}
