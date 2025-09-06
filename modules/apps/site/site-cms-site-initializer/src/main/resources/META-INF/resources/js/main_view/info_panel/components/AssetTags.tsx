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

import TagService from '../../../common/services/TagService';
import {IAssetObjectEntry} from '../../../common/types/AssetType';
import {EntryCategorizationDTO} from '../services/ObjectEntryService';
import {CategorizationInputSize} from './AssetCategorization';

const AssetTags = ({
	cmsGroupId,
	inputSize,
	objectEntry,
	updateObjectEntry,
}: {
	cmsGroupId: number | string;
	inputSize?: CategorizationInputSize;
	objectEntry:
		| IAssetObjectEntry
		| Pick<IAssetObjectEntry, 'keywords' | 'taxonomyCategoryBriefs'>;
	updateObjectEntry: (object: EntryCategorizationDTO) => void | Promise<void>;
}) => {
	const [keywords, setKeywords] = useState([] as string[]);
	const [networkStatus, setNetworkStatus] = useState(4);
	const [value, setValue] = useState('');

	const {refetch, resource} = useResource({
		fetch,
		link: `${Liferay.ThemeDisplay.getPortalURL()}/o/headless-admin-taxonomy/v1.0/sites/${cmsGroupId}/keywords`,
		onNetworkStatusChange: setNetworkStatus,
	});

	const [items, setItems] = useState([] as {[key: string]: any}[]);

	const addKeyword = useCallback(
		async (keyword: any) => {
			if (keywords.includes(keyword.name)) {
				return;
			}

			await updateObjectEntry({
				keywords: [...keywords, keyword.name],
			});
		},
		[keywords, updateObjectEntry]
	);

	const createAndAddKeyword = useCallback(
		async (event: any) => {
			event.preventDefault();

			const {data, error} = await TagService.createTag({
				groupId: cmsGroupId,
				name: value,
			});

			if (data) {
				refetch();

				await addKeyword(data);
			}
			else if (error) {
				console.error('Failed to create new keyword.', error);
			}
		},
		[addKeyword, cmsGroupId, refetch, value]
	);

	const removeKeyword = useCallback(
		async (keyword: string) => {
			const index = keywords.findIndex((value) => value === keyword);

			if (index === -1) {
				return;
			}

			const curKeywords = [...keywords];

			curKeywords.splice(index, 1);

			await updateObjectEntry({
				keywords: curKeywords,
			});
		},
		[keywords, updateObjectEntry]
	);

	const updateKeywords = useCallback(
		(keywords: string[] = []) => {
			setValue('');

			setKeywords(keywords);
		},
		[setValue, setKeywords]
	);

	useEffect(() => {
		setItems((currentItems) => {
			if (value.length) {
				return [
					...currentItems.filter(({name}) => name.includes(value)),
				];
			}

			return [...(resource?.items || [])];
		});
	}, [value, resource, setItems]);

	useEffect(() => {
		updateKeywords(objectEntry.keywords);
	}, [objectEntry, updateKeywords]);

	return (
		<ClayPanel
			collapsable
			defaultExpanded={true}
			displayTitle={
				<ClayPanel.Title className="panel-title text-secondary">
					{Liferay.Language.get('tags')}
				</ClayPanel.Title>
			}
			displayType="unstyled"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<Autocomplete
					filterKey="name"
					id="asset-tags-autocomplete"
					items={items}
					loadingState={networkStatus}
					menuTrigger="focus"
					onChange={setValue}
					placeholder={sub(Liferay.Language.get('add-x'), 'tag')}
					sizing={inputSize}
					value={value}
				>
					{!items.length ? (
						<Autocomplete.Item
							className="text-info"
							key="createNewKeyword"
							onClick={createAndAddKeyword}
							textValue={sub(
								Liferay.Language.get('create-new-tag-x'),
								value
							)}
						/>
					) : (
						items.map((item) => {
							return (
								<Autocomplete.Item
									key={item.id}
									onClick={async (event) => {
										event.preventDefault();

										await addKeyword(item);
									}}
								>
									{item.name}
								</Autocomplete.Item>
							);
						})
					)}
				</Autocomplete>

				<div className="asset-tags mt-3">
					{keywords.map((keyword: string, index: number) => {
						return (
							<Label
								className="mr-2 mt-2"
								closeButtonProps={{
									'aria-label': Liferay.Language.get('close'),
									'onClick': async (event) => {
										event.preventDefault();

										await removeKeyword(keyword);
									},
									'title': Liferay.Language.get('close'),
								}}
								displayType="secondary"
								key={`${keyword}_${index}`}
							>
								{keyword}
							</Label>
						);
					})}
				</div>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default AssetTags;
