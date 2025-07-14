/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Autocomplete from '@clayui/autocomplete';
import {useResource} from '@clayui/data-provider';
import Label from '@clayui/label';
import Panel from '@clayui/panel';
import {fetch, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import {IAssetObjectEntry} from '../../../structure_builder/types/AssetType';

const AssetTags = ({
	objectEntry,
	updateObjectEntry,
}: {
	objectEntry: IAssetObjectEntry;
	updateObjectEntry: (
		object: Pick<IAssetObjectEntry, 'keywords' | 'taxonomyCategoryIds'>
	) => Promise<any>;
}) => {
	const [keywords, setKeywords] = useState([] as string[]);
	const [keywordInputValue, setKeywordInputValue] = useState('');

	const [networkStatus, setNetworkStatus] = useState(4);

	const {resource} = useResource({
		fetch,
		link: `${Liferay.ThemeDisplay.getPortalURL()}/o/headless-admin-taxonomy/v1.0/keywords`,
		onNetworkStatusChange: setNetworkStatus,
	});

	const [items, setItems] = useState([] as {[key: string]: any}[]);

	const updateKeywords = useCallback(
		(keywords: string[] = []) => {
			setKeywordInputValue('');

			setKeywords(keywords);
		},
		[setKeywordInputValue, setKeywords]
	);

	useEffect(() => {
		setItems(() => {
			if (keywordInputValue.length) {
				return [
					...items.filter(({name}) =>
						name.includes(keywordInputValue)
					),
				];
			}

			return [...(resource?.items ?? [])];
		});
	}, [items, keywordInputValue, resource]);

	useEffect(() => {
		setItems(resource?.items ?? []);
	}, [resource, setItems]);

	useEffect(() => {
		updateKeywords(objectEntry.keywords);
	}, [objectEntry, updateKeywords]);

	return (
		<Panel
			displayTitle={Liferay.Language.get('tags')}
			displayType="unstyled"
			expanded
			showCollapseIcon={true}
		>
			<Panel.Body>
				<>
					<Autocomplete
						filterKey="name"
						items={items}
						loadingState={networkStatus}
						onChange={setKeywordInputValue}
						placeholder={sub(Liferay.Language.get('add-x'), 'tag')}
						value={keywordInputValue}
					>
						{!items.length ? (
							<Autocomplete.Item
								key="createNewKeyword"
								onClick={async (event) => {
									event.preventDefault();

									try {
										const response = await fetch(
											`${Liferay.ThemeDisplay.getPortalURL()}/o/headless-admin-taxonomy/v1.0/keywords`,
											{
												body: JSON.stringify({
													name: keywordInputValue,
												} as any),
												headers: {
													'Accept':
														'application/json',
													'Content-Type':
														'application/json',
													'x-csrf-token':
														Liferay.authToken,
												},
												method: 'POST',
											}
										);

										const json = await response.json();

										if (!response.ok) {
											throw new Error(json);
										}

										await updateObjectEntry({
											keywords: [
												...keywords,
												keywordInputValue,
											],
										});
									}
									catch (error) {
										console.error(error);
									}
								}}
								textValue={sub(
									Liferay.Language.get('create-new-tag-x'),
									keywordInputValue
								)}
							/>
						) : (
							items.map((item) => {
								return (
									<Autocomplete.Item
										key={item.id}
										onClick={async (event) => {
											event.preventDefault();

											if (
												!keywords.includes(
													keywordInputValue
												)
											) {
												await updateObjectEntry({
													keywords: [
														...keywords,
														keywordInputValue,
													],
												});
											}
										}}
									>
										{item.name}
									</Autocomplete.Item>
								);
							})
						)}
					</Autocomplete>

					{keywords.map((keyword: string, index: number) => {
						return (
							<Label
								closeButtonProps={{
									'aria-label': Liferay.Language.get('close'),
									'onClick': async (event) => {
										event.preventDefault();

										const curKeywords = [...keywords];

										const index = curKeywords.findIndex(
											(value: string) => value === keyword
										);

										if (index !== -1) {
											curKeywords.splice(index, 1);

											await updateObjectEntry({
												keywords: curKeywords,
											});
										}
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
				</>
			</Panel.Body>
		</Panel>
	);
};

export default AssetTags;
