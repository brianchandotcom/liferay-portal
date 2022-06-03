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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {fetch, openToast, sub} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import AddIconPackModal from './AddIconPackModal';
import DeleteIconModal from './DeleteIconModal';

import type {IIconPacks} from '../../types';

interface IProps {
	deleteIconsPackResourceURL: string;
	deleteIconsPackURL: string;
	icons: IIconPacks;
	portletNamespace: string;
	saveFromExistingIconsActionURL: string;
	saveFromSpritemapActionURL: string;
}

export default function InstanceIconConfiguration({
	deleteIconsPackResourceURL,
	deleteIconsPackURL,
	icons: initialIcons,
	portletNamespace,
	saveFromExistingIconsActionURL,
	saveFromSpritemapActionURL,
}: IProps) {
	const [searchQuery, setSearchQuery] = useState('');
	const [icons, setIcons] = useState(initialIcons);
	const [addModal, setAddModal] = useState<{
		existingIconPackName?: string;
		uploadSpritemap?: boolean;
		visible?: boolean;
	}>({
		existingIconPackName: '',
		uploadSpritemap: false,
		visible: false,
	});
	const [deleteModal, setDeleteModal] = useState<{
		iconPackName: string;
		selectedIcon: string;
		visible: boolean;
	}>({
		iconPackName: '',
		selectedIcon: '',
		visible: false,
	});

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

	const handleDelete = (iconPackName: string) => {
		if (
			!confirm(
				sub(
					Liferay.Language.get(
						'are-you-sure-you-want-to-delete-the-x-icon-pack'
					),
					[iconPackName]
				)
			)
		) {
			return;
		}

		const formData = new FormData();

		formData.append(portletNamespace + 'name', iconPackName);

		return fetch(deleteIconsPackURL, {body: formData, method: 'post'}).then(
			() => {
				openToast({
					message: Liferay.Language.get('icon-pack-deleted'),
					title: Liferay.Language.get('success'),
					toastProps: {
						autoClose: 5000,
					},
					type: 'success',
				});

				const newIcons = {...icons};

				delete newIcons[iconPackName];

				setIcons(newIcons);
			}
		);
	};

	return (
		<>
			<label className="form-control-label">
				<span className="form-control-label-text">
					{Liferay.Language.get('search-icons')}
				</span>

				<ClayInput
					onChange={(event) => setSearchQuery(event.target.value)}
					onKeyPress={(event) =>
						event.key === 'Enter' && event.preventDefault()
					}
					placeholder={Liferay.Language.get('search-icons')}
					type="text"
					value={searchQuery}
				/>
			</label>

			<ClayPanel.Group className="mt-4">
				{iconPackNames.map((iconPackName) => (
					<div className="d-flex" key={iconPackName}>
						<ClayPanel
							collapsable
							displayTitle={`${iconPackName} (${filteredIcons[iconPackName]?.icons.length})`}
							displayType="secondary"
							showCollapseIcon={true}
							style={{flex: 1}}
						>
							<ClayPanel.Body className="list-group-card">
								{filteredIcons[iconPackName].editable && (
									<ClayDropDownWithItems
										items={[
											{
												label: Liferay.Language.get(
													'add-icon-pack-from-spritemap'
												),
												onClick: () =>
													setAddModal({
														existingIconPackName: iconPackName,
														uploadSpritemap: true,
														visible: true,
													}),
											},
											{
												label: Liferay.Language.get(
													'add-icon-pack-from-existing-icons'
												),
												onClick: () =>
													setAddModal({
														existingIconPackName: iconPackName,
														uploadSpritemap: false,
														visible: true,
													}),
											},
										]}
										trigger={
											<ClayButton
												className="mt-2"
												displayType="secondary"
												small
											>
												{Liferay.Language.get(
													'add-icons'
												)}
											</ClayButton>
										}
									/>
								)}

								<ul className="list-group">
									{filteredIcons[iconPackName].icons
										.sort()
										.map((icon) => (
											<li
												className="list-group-card-item w-25"
												key={icon.name}
											>
												<ClayButton
													disabled={
														!filteredIcons[
															iconPackName
														].editable
													}
													displayType={null}
													onClick={() =>
														setDeleteModal({
															iconPackName,
															selectedIcon:
																icon.name,
															visible: true,
														})
													}
													title={
														!filteredIcons[
															iconPackName
														].editable
															? Liferay.Language.get(
																	'system-icon-not-editable'
															  )
															: ''
													}
												>
													<svg
														className="lexicon-icon"
														role="presentation"
													>
														<use
															xlinkHref={`/o/icons/pack/${iconPackName}.svg?${referenceTime}#${icon.name}`}
														></use>
													</svg>

													<span className="list-group-card-item-text">
														{icon.name}
													</span>
												</ClayButton>
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

						<ClayButtonWithIcon
							borderless
							className="ml-2 mt-1"
							disabled={!filteredIcons[iconPackName].editable}
							displayType="secondary"
							onClick={() => handleDelete(iconPackName)}
							small
							symbol="times-circle"
							title={
								!filteredIcons[iconPackName].editable
									? Liferay.Language.get(
											'system-icons-not-removable'
									  )
									: ''
							}
						/>
					</div>
				))}
			</ClayPanel.Group>

			<ClayLayout.SheetFooter>
				<ClayDropDownWithItems
					items={[
						{
							label: Liferay.Language.get(
								'add-icon-pack-from-spritemap'
							),
							onClick: () =>
								setAddModal({
									uploadSpritemap: true,
									visible: true,
								}),
						},
						{
							label: Liferay.Language.get(
								'add-icon-pack-from-existing-icons'
							),
							onClick: () =>
								setAddModal({
									uploadSpritemap: false,
									visible: true,
								}),
						},
					]}
					trigger={
						<ClayButton>
							{Liferay.Language.get('add-icon-pack')}
						</ClayButton>
					}
				/>
			</ClayLayout.SheetFooter>

			{addModal.visible && (
				<AddIconPackModal
					existingIconPackName={addModal.existingIconPackName}
					icons={icons}
					onIconsChange={setIcons}
					onVisibleChange={(visible) =>
						setAddModal((previousModal) => ({
							...previousModal,
							visible,
						}))
					}
					portletNamespace={portletNamespace}
					saveFromExistingIconsActionURL={
						saveFromExistingIconsActionURL
					}
					saveFromSpritemapActionURL={saveFromSpritemapActionURL}
					uploadSpritemap={addModal.uploadSpritemap}
					visible={addModal.visible}
				/>
			)}

			{deleteModal.visible && (
				<DeleteIconModal
					deleteIconsPackResourceURL={deleteIconsPackResourceURL}
					iconPackName={deleteModal.iconPackName}
					icons={icons}
					onIconsChange={setIcons}
					onVisibleChange={(visible) =>
						setDeleteModal((previousModal) => ({
							...previousModal,
							visible,
						}))
					}
					portletNamespace={portletNamespace}
					selectedIcon={deleteModal.selectedIcon}
					visible={deleteModal.visible}
				/>
			)}
		</>
	);
}
