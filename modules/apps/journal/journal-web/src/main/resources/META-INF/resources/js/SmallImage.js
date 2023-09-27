/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {openSelectionModal, sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

const SMALL_IMAGE_SOURCES = {
	documentsAndMedia: 2,
	none: 1,
	url: 3,
	userComputer: 4,
};

const SMALL_IMAGE_SOURCE_OPTIONS = [
	{label: Liferay.Language.get('no-image'), value: SMALL_IMAGE_SOURCES.none},
	{label: Liferay.Language.get('from-url'), value: SMALL_IMAGE_SOURCES.url},
	{
		label: Liferay.Language.get('from-your-computer'),
		value: SMALL_IMAGE_SOURCES.userComputer,
	},
	{
		label: Liferay.Language.get('from-documents-and-media'),
		value: SMALL_IMAGE_SOURCES.documentsAndMedia,
	},
];

export default function SmallImage({
	itemSelectorURL,
	portletNamespace,
	previewURL: initialPreviewURL,
	smallImageId: initialSmallImageId,
	smallImageName: initialSmallImageName,
	smallImageSource: initialSmallImageSource,
	smallImageURL: initialSmallImageURL,
}) {
	const [previewURL, setPreviewURL] = useState(initialPreviewURL);
	const [smallImageId, setSmallImageId] = useState(initialSmallImageId);
	const [smallImageName, setSmallImageName] = useState(initialSmallImageName);
	const [smallImageSource, setSmallImageSource] = useState(
		initialSmallImageSource
	);
	const [smallImageURL, setSmallImageURL] = useState(initialSmallImageURL);

	const fileInputRef = useRef();

	const openItemSelector = () => {
		openSelectionModal({
			onSelect: (selectedItem) => {
				const item = JSON.parse(selectedItem.value);

				setSmallImageId(item.fileEntryId);
				setSmallImageName(item.title);
			},
			selectEventName: `${portletNamespace}selectImage`,
			title: Liferay.Language.get('select-image'),
			url: itemSelectorURL,
		});
	};

	return (
		<div className="mb-3">
			<ClayForm.Group>
				<label
					className="sr-only"
					htmlFor={`${portletNamespace}smallImageSource`}
				>
					{Liferay.Language.get('image-source')}
				</label>

				<ClaySelectWithOption
					id={`${portletNamespace}smallImageSource`}
					name={`${portletNamespace}smallImageSource`}
					onChange={(event) => {
						setSmallImageSource(parseInt(event.target.value, 10));
						setSmallImageName('');
						setPreviewURL('');
					}}
					options={SMALL_IMAGE_SOURCE_OPTIONS}
					value={smallImageSource}
				/>
			</ClayForm.Group>

			{previewURL ? (
				<div className="aspect-ratio aspect-ratio-16-to-9 mb-2 rounded">
					<img
						alt={Liferay.Language.get('preview')}
						className="aspect-ratio-item-fluid"
						src={previewURL}
					/>
				</div>
			) : null}

			{smallImageSource === SMALL_IMAGE_SOURCES.userComputer ? (
				<ClayForm.Group>
					<label
						className="sr-only"
						htmlFor={`${portletNamespace}smallFile`}
					>
						{Liferay.Language.get('image')}
					</label>

					<ClayInput.Group small>
						<ClayInput.GroupItem>
							<input
								className="sr-only"
								id={`${portletNamespace}smallFile`}
								name={`${portletNamespace}smallFile`}
								onChange={(event) =>
									setSmallImageName(
										event.target.files?.[0]?.name || ''
									)
								}
								ref={fileInputRef}
								type="file"
							/>

							<ClayInput
								onClick={() => fileInputRef.current?.click()}
								placeholder={Liferay.Language.get(
									'select-image'
								)}
								readOnly
								sizing="sm"
								value={smallImageName}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem shrink>
							<ClayButton
								displayType="secondary"
								monospaced
								onClick={() => fileInputRef.current?.click()}
								size="sm"
								title={sub(
									smallImageName
										? Liferay.Language.get('change-x')
										: Liferay.Language.get('select-x'),
									Liferay.Language.get('image')
								)}
							>
								<ClayIcon
									className="mt-0"
									symbol={smallImageName ? 'change' : 'plus'}
								/>
							</ClayButton>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			) : null}

			{smallImageSource === SMALL_IMAGE_SOURCES.url ? (
				<ClayForm.Group>
					<label
						className="sr-only"
						htmlFor={`${portletNamespace}smallImageURL`}
					>
						{Liferay.Language.get('image-url')}
					</label>

					<ClayInput
						id={`${portletNamespace}smallImageURL`}
						name={`${portletNamespace}smallImageURL`}
						onChange={(event) =>
							setSmallImageURL(event.target.value)
						}
						placeholder={Liferay.Language.get('url')}
						sizing="sm"
						value={smallImageURL}
					/>
				</ClayForm.Group>
			) : null}

			{smallImageSource === SMALL_IMAGE_SOURCES.documentsAndMedia ? (
				<ClayForm.Group>
					<label
						className="sr-only"
						htmlFor={`${portletNamespace}smallImageId`}
					>
						{Liferay.Language.get('image')}
					</label>

					<ClayInput.Group small>
						<ClayInput.GroupItem>
							<input
								name={`${portletNamespace}smallImageId`}
								readOnly
								type="hidden"
								value={smallImageId}
							/>

							<ClayInput
								id={`${portletNamespace}smallImageId`}
								onClick={() => openItemSelector()}
								placeholder={Liferay.Language.get(
									'select-image'
								)}
								readOnly
								sizing="sm"
								value={smallImageName}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem shrink>
							<ClayButton
								displayType="secondary"
								monospaced
								onClick={() => openItemSelector()}
								size="sm"
								title={sub(
									smallImageName
										? Liferay.Language.get('change-x')
										: Liferay.Language.get('select-x'),
									Liferay.Language.get('image')
								)}
							>
								<ClayIcon
									className="mt-0"
									symbol={smallImageName ? 'change' : 'plus'}
								/>
							</ClayButton>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			) : null}
		</div>
	);
}
