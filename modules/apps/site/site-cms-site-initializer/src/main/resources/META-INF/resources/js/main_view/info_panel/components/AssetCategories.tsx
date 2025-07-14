/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Autocomplete from '@clayui/autocomplete';
import {Heading} from '@clayui/core';
import {useResource} from '@clayui/data-provider';
import Label from '@clayui/label';
import Panel from '@clayui/panel';
import {fetch, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import {IAssetObjectEntry} from '../../../structure_builder/types/AssetType';

interface IGroupedTaxonomyCategory {
	taxonomyCategoryIds: number[];
	taxonomyVocabularies: {
		[taxonomyVocabularyId: number]: ITaxonomyCategoryFacade[];
	};
}

interface ITaxonomyCategoryFacade {
	id: string;
	name?: string;
	parentTaxonomyVocabulary: ITaxonomyVocabulary;
	taxonomyVocabularyId: number;
}

interface ITaxonomyVocabulary {
	id: number;
	name: string;
}

const AssetCategories = ({
	objectEntry,
	updateObjectEntry,
}: {
	objectEntry: IAssetObjectEntry;
	updateObjectEntry: (
		object: Pick<IAssetObjectEntry, 'keywords' | 'taxonomyCategoryIds'>
	) => Promise<any>;
}) => {
	const [groupedTaxonomyCategories, setGroupedTaxonomyCategories] = useState(
		{} as IGroupedTaxonomyCategory
	);

	const [taxonomyCategoryInputValue, setTaxonomyCategoryInputValue] =
		useState('');
	const [networkStatus, setNetworkStatus] = useState(4);

	const {resource} = useResource({
		fetch,
		link: `${Liferay.ThemeDisplay.getPortalURL()}/o/headless-admin-taxonomy/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/taxonomy-categories`,
		onNetworkStatusChange: setNetworkStatus,
	});

	const updateTaxonomyCategories = useCallback(
		(taxonomyCategoryBriefs: any[] = []) => {
			setTaxonomyCategoryInputValue('');

			const taxonomyCategories = taxonomyCategoryBriefs.map(
				({embeddedTaxonomyCategory}: any) => embeddedTaxonomyCategory
			);

			if (!taxonomyCategories.length) {
				setGroupedTaxonomyCategories({
					taxonomyCategoryIds: [],
					taxonomyVocabularies: {},
				} as IGroupedTaxonomyCategory);

				return;
			}

			setGroupedTaxonomyCategories(
				taxonomyCategories.reduce(
					(
						groupedTaxonomyCategories: any,
						taxonomyCategory: ITaxonomyCategoryFacade
					) => {
						const taxonomyCategories =
							groupedTaxonomyCategories.taxonomyVocabularies[
								taxonomyCategory.taxonomyVocabularyId
							] || [];

						taxonomyCategories.push(taxonomyCategory);

						return {
							taxonomyCategoryIds: [
								...groupedTaxonomyCategories.taxonomyCategoryIds,
								parseInt(taxonomyCategory.id, 10),
							],
							taxonomyVocabularies: {
								...groupedTaxonomyCategories.taxonomyVocabularies,
								[taxonomyCategory.taxonomyVocabularyId]:
									taxonomyCategories,
							},
						};
					},
					{
						taxonomyCategoryIds: [],
						taxonomyVocabularies: {},
					} as IGroupedTaxonomyCategory
				)
			);
		},
		[]
	);

	useEffect(() => {
		updateTaxonomyCategories(objectEntry.taxonomyCategoryBriefs);
	}, [objectEntry, updateTaxonomyCategories]);

	return (
		<Panel
			displayTitle={Liferay.Language.get('categories')}
			displayType="unstyled"
			expanded
			showCollapseIcon={true}
		>
			<Panel.Body>
				<>
					{resource?.items ? (
						<Autocomplete
							defaultItems={resource?.items}
							filterKey="name"
							loadingState={networkStatus}
							onChange={setTaxonomyCategoryInputValue}
							placeholder={sub(
								Liferay.Language.get('add-x'),
								'category'
							)}
							value={taxonomyCategoryInputValue}
						>
							{(item: any) => (
								<Autocomplete.Item
									key={item.id}
									onClick={async (event) => {
										event.preventDefault();

										const taxonomyCategoryId = parseInt(
											item.id,
											10
										);

										if (
											!groupedTaxonomyCategories.taxonomyCategoryIds.includes(
												taxonomyCategoryId
											)
										) {
											await updateObjectEntry({
												taxonomyCategoryIds: [
													...groupedTaxonomyCategories.taxonomyCategoryIds,
													taxonomyCategoryId,
												],
											});
										}
									}}
								>
									{item.name}
								</Autocomplete.Item>
							)}
						</Autocomplete>
					) : null}

					{Object.entries(groupedTaxonomyCategories).map(
						([id, curGroupedTaxonomyCategories]) => {
							return curGroupedTaxonomyCategories.length ? (
								<div
									className="pt-3"
									key="taxonomy-categories-container"
								>
									<Heading key={id} level={6} weight="bold">
										{
											curGroupedTaxonomyCategories[0]
												.parentTaxonomyVocabulary.name
										}
									</Heading>

									{curGroupedTaxonomyCategories.map(
										(
											groupedTaxonomyCategory: ITaxonomyCategoryFacade
										) => {
											return (
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

															const {
																taxonomyCategoryIds,
															} =
																groupedTaxonomyCategories;

															const index =
																taxonomyCategoryIds.findIndex(
																	(
																		taxonomyCategoryId: number
																	) =>
																		taxonomyCategoryId ===
																		parseInt(
																			groupedTaxonomyCategory.id,
																			10
																		)
																);

															if (index !== -1) {
																taxonomyCategoryIds.splice(
																	index,
																	1
																);

																await updateObjectEntry(
																	{
																		taxonomyCategoryIds,
																	}
																);
															}
														},
														'title':
															Liferay.Language.get(
																'close'
															),
													}}
													displayType="secondary"
													key={`${groupedTaxonomyCategory.taxonomyVocabularyId}_${groupedTaxonomyCategory.id}`}
												>
													{
														groupedTaxonomyCategory.name
													}
												</Label>
											);
										}
									)}
								</div>
							) : null;
						}
					)}
				</>
			</Panel.Body>
		</Panel>
	);
};

export default AssetCategories;
