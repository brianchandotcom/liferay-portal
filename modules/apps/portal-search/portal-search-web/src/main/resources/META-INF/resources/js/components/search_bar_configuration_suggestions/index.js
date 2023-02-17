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

import React, {useState} from 'react';

import LearnMessage from '../../shared/LearnMessage';
import cleanSuggestionsContributorConfiguration from '../../utils/clean_suggestions_contributor_configuration';
import {CONTRIBUTOR_TYPES} from '../../utils/types/contributorTypes';
import FieldList from '../FieldList';
import FieldListInputs from './FieldListInputs';

const DEFAULT_ATTRIBUTES = {
	characterThreshold: '',
	fields: [],
	includeAssetSearchSummary: true,
	includeAssetURL: true,
	sxpBlueprintId: '',
};

/**
 * Cleans up the fields array by removing those that do not have the required
 * fields (contributorName, displayGroupName, size). If blueprint, check
 * for sxpBlueprintId as well.
 * @param {Array} fields The list of fields.
 * @return {Array} The cleaned up list of fields.
 */
const removeEmptyFields = (fields) =>
	fields.filter(({attributes, contributorName, displayGroupName, size}) => {
		if (contributorName === CONTRIBUTOR_TYPES.BASIC) {
			return displayGroupName && size;
		}

		return (
			contributorName &&
			displayGroupName &&
			size &&
			attributes?.sxpBlueprintId
		);
	});

function SearchBarConfigurationSuggestions({
	initialSuggestionsContributorConfiguration = '[]',
	isDXP = false,
	isSearchExperiencesSupported = true,
	learnMessages,
	namespace = '',
	suggestionsContributorConfigurationName = '',
}) {
	const blueprintsEnabled = isDXP && isSearchExperiencesSupported;

	const [
		suggestionsContributorConfiguration,
		setSuggestionsContributorConfiguration,
	] = useState(
		cleanSuggestionsContributorConfiguration(
			initialSuggestionsContributorConfiguration,
			isSearchExperiencesSupported
		).map((item, index) => ({
			...item,
			id: index, // For FieldList item `key` when reordering.
		}))
	);

	/*
	 * If blueprints are not enabled, exactly one contributor can be added.
	 */
	const _hasAvailableContributors = () =>
		blueprintsEnabled || !suggestionsContributorConfiguration.length;

	const _getContributorOptions = (index) => {
		const BASIC_OPTION = {
			name: CONTRIBUTOR_TYPES.BASIC,
			subtitle: Liferay.Language.get(
				'basic-suggestions-contributor-help'
			),
			title: Liferay.Language.get('basic'),
		};

		const BLUEPRINT_OPTION = {
			name: CONTRIBUTOR_TYPES.SXP_BLUEPRINT,
			subtitle: (
				<>
					{Liferay.Language.get(
						'blueprint-suggestions-contributor-help'
					)}

					<LearnMessage
						className="ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-blueprints"
					/>
				</>
			),
			title: Liferay.Language.get('blueprint'),
		};

		if (!blueprintsEnabled) {
			return [BASIC_OPTION];
		}

		const indexOfBasic = suggestionsContributorConfiguration.findIndex(
			(value) => value.contributorName === CONTRIBUTOR_TYPES.BASIC
		);

		if (indexOfBasic > -1 && index !== indexOfBasic) {
			return [BLUEPRINT_OPTION];
		}

		return [BASIC_OPTION, BLUEPRINT_OPTION];
	};

	const _getDefaultValue = () => {
		if (
			suggestionsContributorConfiguration.some(
				(config) => config.contributorName === CONTRIBUTOR_TYPES.BASIC
			)
		) {
			return {
				attributes: DEFAULT_ATTRIBUTES,
				contributorName: CONTRIBUTOR_TYPES.SXP_BLUEPRINT,
				displayGroupName: '',
				size: '',
			};
		}

		return {
			contributorName: CONTRIBUTOR_TYPES.BASIC,
			displayGroupName: '',
			size: '',
		};
	};

	return (
		<div className="search-bar-configuration-suggestions">
			{removeEmptyFields(suggestionsContributorConfiguration).length ? (
				removeEmptyFields(
					suggestionsContributorConfiguration
				).map(({id, ...item}) => (
					<input
						hidden
						key={id}
						name={`${namespace}${suggestionsContributorConfigurationName}`}
						readOnly
						value={JSON.stringify(item)}
					/>
				))
			) : (
				<input
					hidden
					name={`${namespace}${suggestionsContributorConfigurationName}`}
					readOnly
					value=""
				/>
			)}

			<FieldList
				addButtonLabel={Liferay.Language.get('add-contributor')}
				defaultValue={_getDefaultValue()}
				onChange={setSuggestionsContributorConfiguration}
				renderInputs={({index, onChange, onReplace, value}) => (
					<FieldListInputs
						contributorOptions={_getContributorOptions(index)}
						key={index}
						onChange={onChange}
						onReplace={onReplace}
						value={value}
					/>
				)}
				showAddButton={_hasAvailableContributors()}
				showDeleteButton={true}
				showDragButton={blueprintsEnabled}
				value={suggestionsContributorConfiguration}
			/>
		</div>
	);
}

export default SearchBarConfigurationSuggestions;
