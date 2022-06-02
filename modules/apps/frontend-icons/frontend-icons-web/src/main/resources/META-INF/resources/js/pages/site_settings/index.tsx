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

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import React, {useMemo, useState} from 'react';

import {getSpritemap} from '../../index';
import AddIconPackModal from './AddIconPackModal';

import type {IIconPacks} from '../../types';

interface IProps {
	allIconResourcePacks: IIconPacks;
	portletNamespace: string;
	saveSiteIconPacksURL: string;
	siteIconResourcePacks: string[];
}

export default function SiteIconPackConfiguration({
	allIconResourcePacks: icons,
	portletNamespace,
	saveSiteIconPacksURL,
	siteIconResourcePacks,
}: IProps) {
	const [selectedPacks, setSelectedPacks] = useState(siteIconResourcePacks);
	const [searchQuery, setSearchQuery] = useState('');
	const [showAddModal, setShowAddModal] = useState(false);

	const iconPackNames = Object.keys(icons);

	const filteredIcons: IIconPacks = useMemo(() => {
		return iconPackNames.reduce((acc, packName) => {
			return {
				...acc,
				[packName]: {
					...icons[packName],
					icons: icons[packName].icons
						.filter((icon) => Object.keys(icon).length)
						.filter((icon) =>
							icon.name
								.toLowerCase()
								.includes(searchQuery.toLowerCase())
						),
				},
			};
		}, {});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [iconPackNames, icons, searchQuery]);

	const referenceTime = useMemo(
		() => new Date().getTime(),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[icons]
	);

	return (
		<>
			<ClayLayout.ContentRow containerElement="p">
				{Liferay.Language.get('icons-spritemap-path-at')}
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow className="mb-2" containerElement="code">
				{`/o/icons/site/${Liferay.ThemeDisplay.getSiteGroupId()}.svg`}
			</ClayLayout.ContentRow>

			<label className="form-control-label">
				<span className="form-control-label-text">
					{Liferay.Language.get('search-available-icons')}
				</span>

				<ClayInput
					onChange={(event) => setSearchQuery(event.target.value)}
					onKeyPress={(event) =>
						event.key === 'Enter' && event.preventDefault()
					}
					placeholder={Liferay.Language.get('search-available-icons')}
					type="text"
					value={searchQuery}
				/>
			</label>

			<ClayPanel.Group className="mt-4">
				<span className="form-control-label-text">
					{Liferay.Language.get('available-icon-packs')}
				</span>

				{iconPackNames
					.filter((name) => selectedPacks.includes(name))
					.map((iconPackName) => (
						<div className="d-flex" key={iconPackName}>
							<ClayPanel
								collapsable
								displayTitle={`${iconPackName} (${filteredIcons[iconPackName]?.icons.length})`}
								displayType="secondary"
								showCollapseIcon={true}
								style={{flex: 1}}
							>
								<ClayPanel.Body className="list-group-card">
									<ul className="list-group">
										{filteredIcons[iconPackName].icons
											.sort()
											.map((icon) => (
												<li
													className="list-group-card-item w-25"
													key={icon.name}
												>
													<ClayIcon
														spritemap={`${getSpritemap(
															iconPackName
														)}?${referenceTime}`}
														symbol={icon.name}
													/>

													<span className="list-group-card-item-text">
														{icon.name}
													</span>
												</li>
											))}

										{!filteredIcons[iconPackName].icons
											.length && (
											<li className="list-group-card-item w-100">
												{Liferay.Language.get(
													'no-results-found'
												)}
											</li>
										)}
									</ul>
								</ClayPanel.Body>
							</ClayPanel>
						</div>
					))}
			</ClayPanel.Group>

			<ClayLayout.SheetFooter>
				<ClayButton onClick={() => setShowAddModal(true)}>
					{Liferay.Language.get('select-icon-packs')}
				</ClayButton>
			</ClayLayout.SheetFooter>

			{showAddModal && (
				<AddIconPackModal
					icons={icons}
					onSelectedPacksChange={setSelectedPacks}
					onVisibleChange={setShowAddModal}
					portletNamespace={portletNamespace}
					saveSiteSettingsURL={saveSiteIconPacksURL}
					selectedPacks={selectedPacks}
					visible={showAddModal}
				/>
			)}
		</>
	);
}
