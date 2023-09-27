/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {CheckboxField} from '../../../../../../app/components/fragment_configuration_fields/CheckboxField';
import {
	TargetCollectionsField,
	selectConfiguredCollectionDisplays,
} from '../../../../../../app/components/fragment_configuration_fields/TargetCollectionsField';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import CollectionService from '../../../../../../app/services/CollectionService';
import {deepEqual} from '../../../../../../app/utils/checkDeepEqual';
import {getResponsiveConfig} from '../../../../../../app/utils/getResponsiveConfig';
import isEmptyArray from '../../../../../../app/utils/isEmptyArray';
import isEmptyObject from '../../../../../../app/utils/isEmptyObject';
import updateConfigurationValue from '../../../../../../app/utils/updateConfigurationValue';
import getLayoutDataItemPropTypes from '../../../../../../prop_types/getLayoutDataItemPropTypes';
import {CommonStyles} from './CommonStyles';

export function CollectionAppliedFiltersGeneralPanel({item}) {
	const dispatch = useDispatch();
	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);

	const collections = useSelectorCallback(
		selectConfiguredCollectionDisplays,
		[],
		deepEqual
	);

	const [filterableCollections, setFilterableCollections] = useState(null);
	const [loading, setLoading] = useState(false);

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

	const languageId = useSelector(selectLanguageId);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);

	const configurationValues =
		fragmentEntryLink.editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR] ||
		{};

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

	if (loading) {
		return <ClayLoadingIndicator className="my-0" size="sm" />;
	}

	return (
		<>
			{isEmptyObject(filterableCollections) ? (
				<p aria-live="polite" className="alert alert-info text-center">
					{Liferay.Language.get(
						'display-a-collection-on-the-page-that-support-at-least-one-type-of-filter'
					)}
				</p>
			) : (
				<>
					<p
						aria-live="polite"
						className="alert alert-info text-center"
					>
						{Liferay.Language.get(
							'you-will-see-this-fragment-on-the-page-only-after-applying-a-filter'
						)}
					</p>
					<div className="mb-3 panel-group-sm">
						<ClayPanel
							collapsable
							defaultExpanded
							displayTitle={Liferay.Language.get(
								'applied-filter-options'
							)}
							displayType="unstyled"
							showCollapseIcon
						>
							<ClayPanel.Body>
								<TargetCollectionsField
									filterableCollections={
										filterableCollections
									}
									onValueSelect={onValueSelect}
									value={
										configurationValues.targetCollections
									}
								/>

								<CheckboxField
									field={{
										label: Liferay.Language.get(
											'include-clear-filters-option'
										),
										name: 'showClearFilters',
									}}
									onValueSelect={onValueSelect}
									value={configurationValues.showClearFilters}
								/>
							</ClayPanel.Body>
						</ClayPanel>
					</div>
				</>
			)}

			<CommonStyles
				commonStylesValues={itemConfig.styles}
				item={item}
				role={COMMON_STYLES_ROLES.general}
			/>
		</>
	);
}

CollectionAppliedFiltersGeneralPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired,
		}).isRequired,
	}),
};
