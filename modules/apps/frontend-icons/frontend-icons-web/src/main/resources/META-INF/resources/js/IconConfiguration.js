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
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {fetch, openToast} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import AddIconPackModal from './AddIconPackModal';
import DeleteIconModal from './DeleteIconModal';

export default function IconConfiguration({
	deleteIconsPackResourceURL,
	deleteIconsPackURL,
	icons: initialIcons,
	portletNamespace,
	saveFromExistingIconsActionURL,
	saveFromSpritemapActionURL,
}) {
	const [searchQuery, setSearchQuery] = useState('');
	const [icons, setIcons] = useState(initialIcons);
	const [addModal, setAddModal] = useState({visible: false});
	const [deleteModal, setDeleteModal] = useState({visible: false});

	const iconPackNames = Object.keys(icons);

	const filteredIcons = useMemo(() => {
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
								.includes(searchQuery.toLocaleLowerCase())
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

	const handleDelete = (iconPackName) => {
		if (
			!confirm(
				Liferay.Util.sub(
					Liferay.Language.get(
						'are-you-sure-you-want-to-delete-the-x-icon-pack',
						[iconPackName]
					)
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
		<ClayLayout.Sheet>
			<ClayLayout.ContentRow className="mb-5" containerElement="h2">
				<ClayLayout.ContentCol containerElement="span" expand>
					{Liferay.Language.get('frontend-icons-configuration-name')}
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<h4>{Liferay.Language.get('icon-packs')}</h4>

			<label className="form-control-label">
				<span className="form-control-label-text">
					{Liferay.Language.get('search-icons')}
				</span>

				<ClayInput
					onChange={(event) => setSearchQuery(event.target.value)}
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
												label:
													'Add Icons Pack from spritemap',
												onClick: () =>
													setAddModal({
														existingIconPackName: iconPackName,
														uploadSpritemap: true,
														visible: true,
													}),
											},
											{
												label:
													'Add Icons Pack from existing icons',
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
												Add Icons
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
													<ClayIcon
														spritemap={`/o/icons/${iconPackName}.svg?${referenceTime}`}
														symbol={icon.name}
													/>

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
							disabled={!filteredIcons[iconPackName].editable}
							displayType="secondary ml-2 mt-1"
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
							label: 'Add Icon Pack from spritemap',
							onClick: () =>
								setAddModal({
									uploadSpritemap: true,
									visible: true,
								}),
						},
						{
							label: 'Add Icon Pack from existing icons',
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
					portletNamespace={portletNamespace}
					saveFromExistingIconsActionURL={
						saveFromExistingIconsActionURL
					}
					saveFromSpritemapActionURL={saveFromSpritemapActionURL}
					setIcons={setIcons}
					setVisible={(visible) =>
						setAddModal((previousModal) => ({
							...previousModal,
							visible,
						}))
					}
					uploadSpritemap={addModal.uploadSpritemap}
					visible={addModal.visible}
				/>
			)}

			{deleteModal.visible && (
				<DeleteIconModal
					deleteIconsPackResourceURL={deleteIconsPackResourceURL}
					iconPackName={deleteModal.iconPackName}
					icons={icons}
					portletNamespace={portletNamespace}
					selectedIcon={deleteModal.selectedIcon}
					setIcons={setIcons}
					setVisible={(visible) =>
						setDeleteModal((previousModal) => ({
							...previousModal,
							visible,
						}))
					}
					visible={deleteModal.visible}
				/>
			)}
		</ClayLayout.Sheet>
	);
}
