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

import ClayDropDown from '@clayui/drop-down';
import getCN from 'classnames';
import React, {useMemo} from 'react';

import LearnMessage from '../../shared/LearnMessage';
import SearchContext from '../../shared/SearchContext';
import InputSets, {useInputSets} from '../../shared/input_sets/index';
import {ITEM_ID_PROPERTY} from '../../shared/input_sets/useInputSets';
import cleanSuggestionsContributorConfiguration from '../../utils/clean_suggestions_contributor_configuration';
import {
	CONTRIBUTOR_TYPES,
	CONTRIBUTOR_TYPES_ASAH_DEFAULT_DISPLAY_GROUP_NAMES,
} from '../../utils/types/contributorTypes';
import SuggestionContributorAddButton from './SuggestionContributorAddButton';
import ContributorInputSetItem from './contributor_input_set_item/index';

/**
 * Cleans up the fields array by removing those that do not have the required
 * fields (contributorName, displayGroupName, size). If blueprint, check
 * for sxpBlueprintId as well.
 * @param {Array} fields The list of fields.
 * @return {Array} The cleaned up list of fields.
 */
const removeEmptyFields = (fields) =>
	fields.filter(({attributes, contributorName, displayGroupName, size}) => {
		if (contributorName === CONTRIBUTOR_TYPES.SXP_BLUEPRINT) {
			return (
				contributorName &&
				displayGroupName &&
				size &&
				attributes?.sxpBlueprintId
			);
		}

		return contributorName && displayGroupName && size;
	});

function SearchBarConfigurationSuggestions({
	initialSuggestionsContributorConfiguration = '[]',
	isDXP = false,
	isSearchExperiencesSupported = true,
	learnMessages,
	namespace = '',
	suggestionsContributorConfigurationName = '',
}) {
	const preparedSuggestionsContributorConfiguration = useMemo(
		() =>
			cleanSuggestionsContributorConfiguration(
				initialSuggestionsContributorConfiguration,
				isDXP,
				isSearchExperiencesSupported
			),
		[
			initialSuggestionsContributorConfiguration,
			isDXP,
			isSearchExperiencesSupported,
		]
	);

	const {
		getInputSetItemProps,
		onInputSetItemChange,
		onInputSetsAdd,
		value: suggestionsContributorConfiguration,
	} = useInputSets(preparedSuggestionsContributorConfiguration);

	const contributorOptions = useMemo(() => {
		const BASIC_OPTION = {
			contributorName: CONTRIBUTOR_TYPES.BASIC,
			description: Liferay.Language.get(
				'basic-suggestions-contributor-help'
			),
			title: Liferay.Language.get('basic'),
		};

		const BLUEPRINT_OPTION = {
			contributorName: CONTRIBUTOR_TYPES.SXP_BLUEPRINT,
			description: (
				<>
					{Liferay.Language.get(
						'blueprint-suggestions-contributor-help'
					)}

					<LearnMessage
						className="c-ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-blueprints"
					/>
				</>
			),
			title: Liferay.Language.get('blueprint'),
		};

		const SITE_ACTIVITIES_OPTION = {
			contributorName: CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS,
			description: (
				<>
					{Liferay.Language.get(
						'site-activities-suggestions-contributor-help'
					)}

					<LearnMessage
						className="c-ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-site-activities"
					/>
				</>
			),
			title: Liferay.Language.get('site-activities'),
		};

		const options = [];

		const basicContributorExists =
			suggestionsContributorConfiguration.findIndex(
				(value) => value.contributorName === CONTRIBUTOR_TYPES.BASIC
			) > -1;

		if (!basicContributorExists) {
			options.push(BASIC_OPTION);
		}

		if (isDXP && isSearchExperiencesSupported) {
			options.push(BLUEPRINT_OPTION);
		}

		if (isDXP && Liferay.FeatureFlags['LPS-159643']) {
			options.push(SITE_ACTIVITIES_OPTION);
		}

		return options;
	}, [suggestionsContributorConfiguration.length]); // eslint-disable-line react-hooks/exhaustive-deps

	const _handleInputSetAdd = (contributorName) => () => {
		if (contributorName === CONTRIBUTOR_TYPES.BASIC) {
			onInputSetsAdd({
				attributes: {
					characterThreshold: '',
				},
				contributorName,
				displayGroupName: 'suggestions',
				size: '5',
			});
		}
		else if (
			contributorName === CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_KEYWORDS ||
			contributorName === CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS
		) {
			onInputSetsAdd({
				attributes: {
					characterThreshold: '0',
					matchDisplayLanguageId: true,
					minCounts: '5',
				},
				contributorName,
				displayGroupName:
					CONTRIBUTOR_TYPES_ASAH_DEFAULT_DISPLAY_GROUP_NAMES[
						contributorName
					] || '',

				size: '3',
			});
		}
		else if (contributorName === CONTRIBUTOR_TYPES.SXP_BLUEPRINT) {
			onInputSetsAdd({
				attributes: {
					characterThreshold: '',
					fields: [],
					includeAssetSearchSummary: true,
					includeAssetURL: true,
					sxpBlueprintId: '',
				},
				contributorName,
				displayGroupName: '',
				size: '',
			});
		}
	};

	return (
		<SearchContext.Provider value={{learnMessages}}>
			<div className="search-bar-configuration-suggestions-root">
				{removeEmptyFields(suggestionsContributorConfiguration)
					.length ? (
					removeEmptyFields(
						suggestionsContributorConfiguration
					).map(({[ITEM_ID_PROPERTY]: key, ...item}) => (
						<input
							hidden
							key={key}
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

				<InputSets>
					{suggestionsContributorConfiguration.map(
						(valueItem, valueIndex) => (
							// eslint-disable-next-line react/jsx-key
							<InputSets.Item
								{...getInputSetItemProps(valueItem, valueIndex)}
							>
								<ContributorInputSetItem
									index={valueIndex}
									onInputSetItemChange={onInputSetItemChange}
									value={valueItem}
								/>
							</InputSets.Item>
						)
					)}

					{!!contributorOptions.length && (
						<div
							className={getCN({
								'c-mt-4': !suggestionsContributorConfiguration.length,
							})}
						>
							<SuggestionContributorAddButton>
								{contributorOptions.map((option, index) => (
									<ClayDropDown.Item
										key={index}
										onClick={_handleInputSetAdd(
											option.contributorName
										)}
									>
										<div>{option.title}</div>

										<div className="text-2">
											{option.description}
										</div>
									</ClayDropDown.Item>
								))}
							</SuggestionContributorAddButton>
						</div>
					)}
				</InputSets>
			</div>
		</SearchContext.Provider>
	);
}

export default SearchBarConfigurationSuggestions;
