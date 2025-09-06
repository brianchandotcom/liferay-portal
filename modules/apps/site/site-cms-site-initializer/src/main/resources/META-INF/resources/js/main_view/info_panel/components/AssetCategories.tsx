/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Autocomplete from '@clayui/autocomplete';
import {useResource} from '@clayui/data-provider';
import Label from '@clayui/label';
import ClayPanel from '@clayui/panel';
import {fetch, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import {
	IAssetObjectEntry,
	IGroupedTaxonomies,
	ITaxonomyCategoryFacade,
} from '../../../common/types/AssetType';
import {EntryCategorizationDTO} from '../services/ObjectEntryService';
import {CategorizationInputSize} from './AssetCategorization';

const AssetCategories = ({
	cmsGroupId,
	inputSize,
	objectEntry,
	updateObjectEntry,
}: {
	cmsGroupId: number;
	inputSize?: CategorizationInputSize;
	objectEntry:
		| IAssetObjectEntry
		| Pick<IAssetObjectEntry, 'keywords' | 'taxonomyCategoryBriefs'>;
	updateObjectEntry: (object: EntryCategorizationDTO) => void | Promise<void>;
}) => {
	const [groupedTaxonomies, setGroupedTaxonomies] = useState(
		{} as IGroupedTaxonomies
	);
	const [networkStatus, setNetworkStatus] = useState(4);
	const [value, setValue] = useState('');

	const {resource} = useResource({
		fetch,
		link: `${Liferay.ThemeDisplay.getPortalURL()}/o/headless-admin-taxonomy/v1.0/sites/${cmsGroupId}/taxonomy-categories`,
		onNetworkStatusChange: setNetworkStatus,
	});

	const addCategory = useCallback(
		async (item: any) => {
			const taxonomyCategoryId = parseInt(item.id, 10);

			if (
				groupedTaxonomies.taxonomyCategoryIds.includes(
					taxonomyCategoryId
				)
			) {
				return;
			}

			await updateObjectEntry({
				lastAddedBrief: item,
				taxonomyCategoryIds: [
					...groupedTaxonomies.taxonomyCategoryIds,
					taxonomyCategoryId,
				],
			});
		},
		[groupedTaxonomies.taxonomyCategoryIds, updateObjectEntry]
	);

	const removeCategory = useCallback(
		async (category: ITaxonomyCategoryFacade) => {
			const {taxonomyCategoryIds} = groupedTaxonomies;

			const index = taxonomyCategoryIds.findIndex(
				(id) => id === parseInt(category.id, 10)
			);

			if (index === -1) {
				return;
			}

			taxonomyCategoryIds.splice(index, 1);

			await updateObjectEntry({taxonomyCategoryIds});
		},
		[groupedTaxonomies, updateObjectEntry]
	);

	const groupTaxonomies = useCallback(
		(taxonomyCategoryBriefs: any[] = []) => {
			setValue('');

			if (!taxonomyCategoryBriefs.length) {
				setGroupedTaxonomies({
					taxonomyCategoryIds: [],
					taxonomyVocabularies: {},
				} as IGroupedTaxonomies);

				return;
			}

			setGroupedTaxonomies(
				taxonomyCategoryBriefs.reduce(
					(
						groupedTaxonomies,
						{embeddedTaxonomyCategory: categoryBrief}
					) => {
						const {id, taxonomyVocabularyId} = categoryBrief;

						const taxonomyCategories =
							groupedTaxonomies.taxonomyVocabularies[
								taxonomyVocabularyId
							] || [];

						taxonomyCategories.push(categoryBrief);

						return {
							taxonomyCategoryIds: [
								...groupedTaxonomies.taxonomyCategoryIds,
								parseInt(id, 10),
							],
							taxonomyVocabularies: {
								...groupedTaxonomies.taxonomyVocabularies,
								[taxonomyVocabularyId]: taxonomyCategories,
							},
						};
					},
					{
						taxonomyCategoryIds: [],
						taxonomyVocabularies: {},
					} as IGroupedTaxonomies
				)
			);
		},
		[setGroupedTaxonomies]
	);

	useEffect(() => {
		groupTaxonomies(objectEntry.taxonomyCategoryBriefs);
	}, [objectEntry, groupTaxonomies]);

	return (
		<ClayPanel
			collapsable
			defaultExpanded={true}
			displayTitle={
				<ClayPanel.Title className="panel-title text-secondary">
					{Liferay.Language.get('categories')}
				</ClayPanel.Title>
			}
			displayType="unstyled"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				{resource?.items ? (
					<Autocomplete
						defaultItems={resource?.items}
						filterKey="name"
						id="asset-categories-autocomplete"
						loadingState={networkStatus}
						menuTrigger="focus"
						onChange={setValue}
						placeholder={sub(
							Liferay.Language.get('add-x'),
							'category'
						)}
						sizing={inputSize}
						value={value}
					>
						{(item: any) => (
							<Autocomplete.Item
								key={item.id}
								onClick={async (event: any) => {
									event.preventDefault();

									await addCategory(item);
								}}
							>
								{item.name}
							</Autocomplete.Item>
						)}
					</Autocomplete>
				) : null}

				{groupedTaxonomies.taxonomyVocabularies &&
					Object.entries(groupedTaxonomies?.taxonomyVocabularies).map(
						([, vocabularyCategories], index) => {
							const vocabularyName =
								vocabularyCategories[0].parentTaxonomyVocabulary
									.name;

							return vocabularyCategories.length ? (
								<div className="pt-3" key={index}>
									<p className="font-weight-semi-bold vocabulary-name">
										{vocabularyName}
									</p>

									{vocabularyCategories.map(
										(category: ITaxonomyCategoryFacade) => (
											<Label
												closeButtonProps={{
													'aria-label':
														Liferay.Language.get(
															'close'
														),
													'onClick': async (
														event
													) => {
														event.preventDefault();

														await removeCategory(
															category
														);
													},
													'title':
														Liferay.Language.get(
															'close'
														),
												}}
												displayType="secondary"
												key={`${category.taxonomyVocabularyId}_${category.id}`}
											>
												{category.name}
											</Label>
										)
									)}
								</div>
							) : null;
						}
					)}
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default AssetCategories;
