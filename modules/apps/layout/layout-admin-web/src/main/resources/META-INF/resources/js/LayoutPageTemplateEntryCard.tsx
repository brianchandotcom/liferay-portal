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
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {KeyboardEvent, MouseEvent, default as React, useState} from 'react';

interface IProps {
	addLayoutURL: string;
	layoutPageTemplateEntryId: string;
	layoutPageTemplateEntryList: Array<{
		addLayoutURL: string;
		layoutPageTemplateEntryId: string;
		name: string;
		previewLayoutURL: string;
	}>;
	portletNamespace: string;
	subtitle: string;
	thumbnailURL: string;
	title: string;
}

export default function LayoutPageTemplateEntryCard({
	addLayoutURL,
	layoutPageTemplateEntryId,
	layoutPageTemplateEntryList,
	subtitle,
	title,
}: IProps) {
	const {
		observer: previewObserver,
		onOpenChange: onPreviewOpenChange,
		open: previewOpen,
	} = useModal();

	const onClick = () => {
		Liferay.Util.openModal({
			disableAutoClose: true,
			height: '60vh',
			id: 'addLayoutDialog',
			size: 'md',
			title: Liferay.Language.get('add-page'),
			url: addLayoutURL,
		});
	};

	const onKeyDown = (event: KeyboardEvent) => {
		if (event.key === 'Enter' || event.key === 'Space') {
			event.preventDefault();
			event.stopPropagation();
			onClick();
		}
	};

	const onPreviewClick = (event: MouseEvent) => {
		event.stopPropagation();
		onPreviewOpenChange(true);
	};

	return (
		<>
			<div
				className="btn card card-type-asset file-card p-0 position-relative"
				onClick={onClick}
				onKeyDown={onKeyDown}
				role="option"
				tabIndex={0}
			>
				<ClayCard.AspectRatio containerAspectRatio="16/9">
					<div className="aspect-ratio-item aspect-ratio-item-center-middle card-type-asset-icon">
						<ClayIcon symbol="page" />
					</div>
				</ClayCard.AspectRatio>

				<ClayCard.Body className="text-left">
					<ClayCard.Row className="c-gap-2">
						<div className="autofit-col autofit-col-expand autofit-col-shrink">
							<ClayCard.Description
								className="mb-0"
								displayType="title"
							>
								{title}
							</ClayCard.Description>

							<ClayCard.Description displayType="subtitle">
								{subtitle}
							</ClayCard.Description>
						</div>

						<div className="autofit-col">
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'preview-page-template'
								)}
								borderless
								displayType="secondary"
								onClick={onPreviewClick}
								size="sm"
								symbol="view"
							/>
						</div>
					</ClayCard.Row>
				</ClayCard.Body>
			</div>

			{previewOpen && (
				<ClayModal observer={previewObserver} size="full-screen">
					<ClayModal.Header>
						{Liferay.Language.get('preview-page-template')}
					</ClayModal.Header>

					<ClayModal.Body className="p-0">
						<PreviewModalContent
							initialLayoutPageTemplateEntryId={
								layoutPageTemplateEntryId
							}
							layoutPageTemplateEntryList={
								layoutPageTemplateEntryList
							}
						/>
					</ClayModal.Body>
				</ClayModal>
			)}
		</>
	);
}

interface IPreviewModalContentProps {
	initialLayoutPageTemplateEntryId: string;
	layoutPageTemplateEntryList: IProps['layoutPageTemplateEntryList'];
}

function PreviewModalContent({
	initialLayoutPageTemplateEntryId,
	layoutPageTemplateEntryList,
}: IPreviewModalContentProps) {
	const [entryIndex, setEntryIndex] = useState(() =>
		Math.max(
			0,
			layoutPageTemplateEntryList.findIndex(
				(entry) =>
					entry.layoutPageTemplateEntryId ===
					initialLayoutPageTemplateEntryId
			)
		)
	);

	const layoutPageTemplateEntry = layoutPageTemplateEntryList[entryIndex];

	const updateEntryIndex = (direction: 'previous' | 'next') => {
		setEntryIndex((previousIndex) => {
			if (direction === 'previous') {
				return previousIndex === 0
					? layoutPageTemplateEntryList.length - 1
					: previousIndex - 1;
			}

			return previousIndex + (1 % layoutPageTemplateEntryList.length);
		});
	};

	return (
		<div className="bg-dark d-flex flex-column h-100 layout-page-template-entry-preview-modal">
			<div className="bg-white d-flex justify-content-end p-3">
				<ClayButton>
					{Liferay.Language.get('create-page-from-this-template')}
				</ClayButton>
			</div>

			<div className="align-items-center d-flex flex-grow-1 flex-row">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('go-to-previous-template')}
					className="btn-xl ml-1 text-white"
					displayType="unstyled"
					onClick={() => updateEntryIndex('previous')}
					symbol="angle-left"
				/>

				<iframe
					className="align-self-stretch border-0 flex-grow-1"
					src={layoutPageTemplateEntry.previewLayoutURL}
				/>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('go-to-next-template')}
					className="btn-xl mr-1 text-white"
					displayType="unstyled"
					onClick={() => updateEntryIndex('next')}
					symbol="angle-right"
				/>
			</div>

			<header className="bg-secondary-d2 d-flex p-3 text-light">
				<p className="flex-grow-1 m-0 text-center">
					{layoutPageTemplateEntry.name}
				</p>

				<p className="m-0">
					{Liferay.Util.sub(
						Liferay.Language.get('x-of-x'),
						entryIndex + 1,
						layoutPageTemplateEntryList.length
					)}
				</p>
			</header>
		</div>
	);
}
