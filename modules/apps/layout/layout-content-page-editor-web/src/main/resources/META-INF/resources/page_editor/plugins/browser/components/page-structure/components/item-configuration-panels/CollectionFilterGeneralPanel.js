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

import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {SelectField} from '../../../../../../app/components/fragment-configuration-fields/SelectField';
import {
	TargetCollectionsField,
	selectConfiguredCollectionDisplays,
} from '../../../../../../app/components/fragment-configuration-fields/TargetCollectionsField';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import CollectionService from '../../../../../../app/services/CollectionService';
import updateFragmentConfiguration from '../../../../../../app/thunks/updateFragmentConfiguration';
import isEmptyArray from '../../../../../../app/utils/isEmptyArray';
import updateConfigurationValue from '../../../../../../app/utils/updateConfigurationValue';
import getLayoutDataItemPropTypes from '../../../../../../prop-types/getLayoutDataItemPropTypes';
import {FieldSet} from './FieldSet';

export const CollectionFilterGeneralPanel = ({item}) => {
	const dispatch = useDispatch();

	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);
	const hasConfiguredCollections = useSelector(
		(state) => !isEmptyArray(selectConfiguredCollectionDisplays(state))
	);

	const languageId = useSelector(selectLanguageId);

	const configurationValues =
		fragmentEntryLink.editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR] ||
		{};

	const [collectionFilters, setCollectionFilters] = useState(null);

	const selectedFilter = collectionFilters?.[configurationValues.filterKey];

	const targetCollections = configurationValues.targetCollections || [];

	useEffect(() => {
		if (hasConfiguredCollections && !collectionFilters) {
			CollectionService.getCollectionFilters().then(
				(collectionFilters) => {
					setCollectionFilters(collectionFilters);
				}
			);
		}
	}, [hasConfiguredCollections, collectionFilters]);

	const onValueSelect = useCallback(
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

	if (!hasConfiguredCollections) {
		return (
			<p className="alert alert-info text-center" role="alert">
				{Liferay.Language.get(
					'display-a-collection-on-the-page-so-that-you-can-use-the-collection-filter-fragment'
				)}
			</p>
		);
	}

	return (
		<>
			<TargetCollectionsField
				onValueSelect={(name, value) => {
					if (!isEmptyArray(value)) {
						onValueSelect(name, value);
					}
					else {
						const nextConfigurationValues = {
							filterKey: '',
							[name]: value,
						};

						dispatch(
							updateFragmentConfiguration({
								configurationValues: nextConfigurationValues,
								fragmentEntryLink,
								languageId,
							})
						);
					}
				}}
				value={targetCollections}
			/>

			{!isEmptyArray(targetCollections) &&
				collectionFilters &&
				Object.keys(collectionFilters).length > 0 && (
					<SelectField
						field={{
							label: Liferay.Language.get('filter'),
							name: 'filterKey',
							typeOptions: {
								validValues: [
									{
										label: Liferay.Language.get('none'),
										value: '',
									},
									...Object.values(collectionFilters).map(
										({key, label}) => ({
											label,
											value: key,
										})
									),
								],
							},
						}}
						onValueSelect={onValueSelect}
						value={configurationValues.filterKey}
					/>
				)}

			{selectedFilter?.configuration &&
				selectedFilter.configuration.fieldSets
					?.filter((fieldSet) => fieldSet.fields.length)
					.map((fieldSet, index) => (
						<FieldSet
							fields={fieldSet.fields}
							key={`${fieldSet.label || ''}-${index}`}
							label={fieldSet.label}
							languageId={languageId}
							onValueSelect={(name, value) =>
								onValueSelect(name, value)
							}
							values={configurationValues}
						/>
					))}
		</>
	);
};

CollectionFilterGeneralPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired,
		}).isRequired,
	}),
};
