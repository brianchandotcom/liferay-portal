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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch, openToast} from 'frontend-js-web';
import React, {useState} from 'react';

import type {IIconPacks} from '../../types';

interface IProps {
	deleteIconsPackResourceURL: string;
	iconPackName: string;
	icons: IIconPacks;
	onIconsChange: (newIcons: IIconPacks) => void;
	onVisibleChange: (visible: boolean) => void;
	portletNamespace: string;
	selectedIcon: string;
	visible: boolean;
}

export default function DeleteIconModal({
	deleteIconsPackResourceURL,
	iconPackName,
	icons,
	onIconsChange,
	onVisibleChange,
	portletNamespace,
	selectedIcon,
	visible,
}: IProps) {
	const [loading, setLoading] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			onVisibleChange(false);
		},
	});

	const handleDelete = () => {
		setLoading(true);

		const formData = new FormData();

		formData.append(portletNamespace + 'name', iconPackName);
		formData.append(portletNamespace + 'icon', selectedIcon);

		return fetch(deleteIconsPackResourceURL, {
			body: formData,
			method: 'post',
		}).then(() => {
			openToast({
				message: Liferay.Language.get('icon-deleted'),
				title: Liferay.Language.get('success'),
				toastProps: {
					autoClose: 5000,
				},
				type: 'success',
			});

			const iconPack = icons[iconPackName];

			const newIcons = {
				...icons,
				[iconPackName]: {
					...iconPack,
					icons: iconPack.icons.filter(
						(icon) => icon.name !== selectedIcon
					),
				},
			};

			onIconsChange(newIcons);

			onClose();
		});
	};

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
							placeholder={Liferay.Language.get('name')}
							readOnly
							type="text"
							value={iconPackName}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor={portletNamespace + 'icon'}>
							{Liferay.Language.get('icon-name')}
						</label>

						<ClayInput
							name={portletNamespace + 'icon'}
							placeholder={Liferay.Language.get('icon')}
							readOnly
							type="text"
							value={selectedIcon}
						/>
					</ClayForm.Group>
				</ClayForm>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={loading}
							displayType="danger"
							onClick={() => {
								handleDelete();
							}}
							type="submit"
						>
							{loading ? (
								<ClayLoadingIndicator
									className="d-inline-block m-0"
									small
								/>
							) : (
								Liferay.Language.get('delete')
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
