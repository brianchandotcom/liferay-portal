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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import {fetch, openToast} from 'frontend-js-web';
import React, {useMemo, useRef, useState} from 'react';

import {getSpritemapPath} from '../../index';

import type {IIconPack, IIconPacks} from '../../types';

interface IProps {
	existingIconPackName?: string;
	icons: IIconPacks;
	onIconsChange: (iconPacks: IIconPacks) => void;
	onVisibleChange: (visible: boolean) => void;
	portletNamespace: string;
	saveFromExistingIconsActionURL: string;
	saveFromSpritemapActionURL: string;
	uploadSpritemap?: boolean;
	visible: boolean;
}

export default function AddIconPackModal({
	existingIconPackName = '',
	icons,
	onIconsChange,
	onVisibleChange,
	portletNamespace,
	saveFromExistingIconsActionURL,
	saveFromSpritemapActionURL,
	uploadSpritemap = true,
	visible,
}: IProps) {
	const svgFileInputRef = useRef<HTMLInputElement>();

	const [iconPackName, setIconPackName] = useState(existingIconPackName);
	const [loading, setLoading] = useState(false);
	const [selectedIcons, setSelectedIcons] = useState({});

	const {observer, onClose} = useModal({
		onClose: () => {
			onVisibleChange(false);
		},
	});

	const handleUploadSpritemapSubmit = () => {
		setLoading(true);

		const formData = new FormData();

		if (
			!(
				svgFileInputRef?.current?.files &&
				svgFileInputRef?.current?.files[0]
			)
		) {
			return;
		}

		formData.append(
			portletNamespace + 'svgFile',
			svgFileInputRef.current.files[0]
		);
		formData.append(portletNamespace + 'name', iconPackName);

		return fetch(saveFromSpritemapActionURL, {
			body: formData,
			method: 'post',
		})
			.then((response: Response) => response.json())
			.then((iconPack: IIconPack) => {
				openToast({
					message: Liferay.Language.get('icon-added'),
					title: Liferay.Language.get('success'),
					toastProps: {
						autoClose: 5000,
					},
					type: 'success',
				});

				const newIcons = {...icons};

				newIcons[iconPackName] = iconPack;

				onIconsChange(newIcons);
				setLoading(false);

				onClose();
			});
	};

	const handleSelectIconsSubmit = () => {
		setLoading(true);

		const formData = new FormData();

		formData.append(
			portletNamespace + 'icons',
			JSON.stringify(selectedIcons)
		);
		formData.append(portletNamespace + 'name', iconPackName);

		return fetch(saveFromExistingIconsActionURL, {
			body: formData,
			method: 'post',
		})
			.then((response: Response) => response.json())
			.then((iconPack: IIconPack) => {
				openToast({
					message: Liferay.Language.get('icon-added'),
					title: Liferay.Language.get('success'),
					toastProps: {
						autoClose: 5000,
					},
					type: 'success',
				});

				const newIcons = {...icons};

				newIcons[iconPackName] = iconPack;

				onIconsChange(newIcons);
				setLoading(false);

				onClose();
			});
	};

	const handleSubmit = uploadSpritemap
		? handleUploadSpritemapSubmit
		: handleSelectIconsSubmit;

	return visible ? (
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header withTitle>
				{Liferay.Language.get('add-icon-pack')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm
					onSubmit={(event) => {
						event.preventDefault();
					}}
				>
					<ClayForm.Group>
						<label htmlFor={portletNamespace + 'name'}>
							{Liferay.Language.get('pack-name')}
						</label>

						<ClayInput
							name={portletNamespace + 'name'}
							onChange={(event) =>
								setIconPackName(event.target.value)
							}
							placeholder="Name"
							readOnly={!!existingIconPackName}
							type="text"
							value={iconPackName}
						/>
					</ClayForm.Group>

					{uploadSpritemap ? (
						<ClayForm.Group>
							<label htmlFor={portletNamespace + 'svgFile'}>
								{Liferay.Language.get('svg-file')}
							</label>

							<ClayInput
								accept=".svg"
								name={portletNamespace + 'svgFile'}
								ref={svgFileInputRef as any}
								type="file"
							/>
						</ClayForm.Group>
					) : (
						<IconPicker
							existingIconPackName={existingIconPackName}
							icons={icons}
							onSelectedIconsChange={setSelectedIcons}
							selectedIcons={selectedIcons}
						/>
					)}
				</ClayForm>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={loading}
							onClick={() => {
								handleSubmit();
							}}
							type="submit"
						>
							{loading ? (
								<ClayLoadingIndicator
									className="d-inline-block m-0"
									small
								/>
							) : (
								Liferay.Language.get('save')
							)}
						</ClayButton>

						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	) : null;
}

interface ISelectedIcons {
	[iconPackName: string]: string[];
}

interface IIconPickerProps {
	existingIconPackName: string;
	icons: IIconPacks;
	onSelectedIconsChange: (selectedIcons: ISelectedIcons) => void;
	selectedIcons: ISelectedIcons;
}

function IconPicker({
	existingIconPackName,
	icons,
	onSelectedIconsChange,
	selectedIcons,
}: IIconPickerProps) {
	const referenceTime = useMemo(
		() => new Date().getTime(),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[icons]
	);

	return (
		<ClayPanel.Group className="mt-4">
			{Object.entries(icons)
				.filter(
					([iconPackName]) => iconPackName !== existingIconPackName
				)
				.map(([iconPackName, {icons}]) => (
					<div className="d-flex" key={iconPackName}>
						<ClayPanel
							collapsable
							displayTitle={`${iconPackName} (${icons?.length})`}
							displayType="secondary"
							showCollapseIcon={true}
							style={{flex: 1}}
						>
							<ClayPanel.Body className="list-group-card">
								<ul className="list-group">
									{icons?.map((icon) => (
										<li
											className="list-group-card-item w-25"
											key={icon.name}
										>
											<ClayButton
												className={classNames({
													'bg-light': selectedIcons[
														iconPackName
													]?.includes(icon.name),
												})}
												displayType={null}
												onClick={() => {
													const selectedIconsFromCurrentPackName =
														selectedIcons[
															iconPackName
														] || [];

													const isSelected = selectedIconsFromCurrentPackName.includes(
														icon.name
													);

													onSelectedIconsChange({
														...selectedIcons,
														[iconPackName]: isSelected
															? selectedIconsFromCurrentPackName.filter(
																	(
																		selectedIcon
																	) =>
																		selectedIcon !==
																		icon.name
															  )
															: [
																	...selectedIconsFromCurrentPackName,
																	icon.name,
															  ],
													});
												}}
											>
												<ClayIcon
													spritemap={`${getSpritemapPath(
														iconPackName
													)}?${referenceTime}`}
													symbol={icon.name}
												/>

												<span className="list-group-card-item-text">
													{icon.name}
												</span>
											</ClayButton>
										</li>
									))}
								</ul>
							</ClayPanel.Body>
						</ClayPanel>
					</div>
				))}
		</ClayPanel.Group>
	);
}
