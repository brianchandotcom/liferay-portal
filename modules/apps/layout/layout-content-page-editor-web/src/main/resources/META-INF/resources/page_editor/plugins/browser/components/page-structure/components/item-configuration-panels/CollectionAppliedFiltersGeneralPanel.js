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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {CheckboxField} from '../../../../../../app/components/fragment-configuration-fields/CheckboxField';
import {
	TargetCollectionsField,
	selectConfiguredCollectionDisplays,
} from '../../../../../../app/components/fragment-configuration-fields/TargetCollectionsField';
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
import Collapse from '../../../../../../common/components/Collapse';
import getLayoutDataItemPropTypes from '../../../../../../prop-types/getLayoutDataItemPropTypes';
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
		return <ClayLoadingIndicator className="my-0" small />;
	}

	return (
		<>
			{isEmptyObject(filterableCollections) ? (
				<p className="alert alert-info text-center" role="alert">
					{Liferay.Language.get(
						'display-a-collection-on-the-page-that-support-at-least-one-type-of-filter'
					)}
				</p>
			) : (
				<>
					<p className="alert alert-info text-center" role="alert">
						{Liferay.Language.get(
							'you-will-see-this-fragment-on-the-page-only-after-applying-a-filter'
						)}
					</p>
					<div className="mb-3">
						<Collapse
							label={Liferay.Language.get(
								'applied-filter-options'
							)}
							open
						>
							<TargetCollectionsField
								filterableCollections={filterableCollections}
								onValueSelect={onValueSelect}
								value={configurationValues.targetCollections}
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
						</Collapse>
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
