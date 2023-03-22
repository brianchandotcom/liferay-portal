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
import {createPortletURL, fetch, openModal} from 'frontend-js-web';
import {
	KeyboardEvent,
	MouseEvent,
	default as React,
	useEffect,
	useRef,
	useState,
} from 'react';

interface IProps {
	addLayoutURL: string;
	getLayoutPageTemplateEntryListURL: string;
	layoutPageTemplateEntryId: string;
	portletNamespace: string;
	subtitle: string;
	thumbnailURL: string;
	title: string;
}

export default function LayoutPageTemplateEntryCard({
	addLayoutURL,
	getLayoutPageTemplateEntryListURL,
	layoutPageTemplateEntryId,
	subtitle,
	thumbnailURL,
	title,
}: IProps) {
	const {
		observer: previewObserver,
		onOpenChange: onPreviewOpenChange,
		open: previewOpen,
	} = useModal();

	const onClick = () => {
		openModal({
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

	const [entryIndex, setEntryIndex] = useState(0);
	const [
		layoutPageTemplateEntryList,
		setLayoutPageTemplateEntryList,
	] = useState<LayoutPageTemplateEntryList | null>(null);

	const updateEntryIndex = (direction: 'previous' | 'next') => {
		setEntryIndex((previousIndex) => {
			if (!layoutPageTemplateEntryList) {
				return previousIndex;
			}

			if (direction === 'previous') {
				return previousIndex === 0
					? layoutPageTemplateEntryList.length - 1
					: previousIndex - 1;
			}

			return (previousIndex + 1) % layoutPageTemplateEntryList.length;
		});
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
					{thumbnailURL ? (
						<img
							alt="thumbnail"
							className="aspect-ratio-item-center-middle aspect-ratio-item-fluid"
							src={thumbnailURL}
						/>
					) : (
						<div className="aspect-ratio-item aspect-ratio-item-center-middle card-type-asset-icon">
							<ClayIcon symbol="page" />
						</div>
					)}
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
								onKeyDown={(event) => {
									if (
										event.key === 'Enter' ||
										event.key === 'Space'
									) {
										event.stopPropagation();
										onPreviewOpenChange(true);
									}
								}}
								size="sm"
								symbol="view"
								title={Liferay.Language.get(
									'preview-page-template'
								)}
							/>
						</div>
					</ClayCard.Row>
				</ClayCard.Body>
			</div>

			{previewOpen && (
				<ClayModal
					observer={previewObserver}
					onKeyDown={(event) => {
						if (event.key === 'ArrowLeft') {
							updateEntryIndex('previous');
						}
						else if (event.key === 'ArrowRight') {
							updateEntryIndex('next');
						}
					}}
					size="full-screen"
				>
					<ClayModal.Header>
						{Liferay.Language.get('preview-page-template')}
					</ClayModal.Header>

					<ClayModal.Body className="p-0">
						<PreviewModalContent
							addLayoutURL={addLayoutURL}
							entryIndex={entryIndex}
							getLayoutPageTemplateEntryListURL={
								getLayoutPageTemplateEntryListURL
							}
							initialLayoutPageTemplateEntryId={
								layoutPageTemplateEntryId
							}
							layoutPageTemplateEntryList={
								layoutPageTemplateEntryList
							}
							onPreviewOpenChange={onPreviewOpenChange}
							setEntryIndex={setEntryIndex}
							setLayoutPageTemplateEntryList={
								setLayoutPageTemplateEntryList
							}
							updateEntryIndex={updateEntryIndex}
						/>
					</ClayModal.Body>
				</ClayModal>
			)}
		</>
	);
}

type LayoutPageTemplateEntry = {
	layoutPageTemplateEntryId: string;
	name: string;
	previewLayoutURL: string;
};

type LayoutPageTemplateEntryList = LayoutPageTemplateEntry[];

interface IPreviewModalContentProps {
	addLayoutURL: string;
	entryIndex: number;
	getLayoutPageTemplateEntryListURL: string;
	initialLayoutPageTemplateEntryId: string;
	layoutPageTemplateEntryList: LayoutPageTemplateEntry[] | null;
	onPreviewOpenChange: (open: boolean) => void;
	setEntryIndex: (index: number) => void;
	setLayoutPageTemplateEntryList: (
		layoutPageTemplateEntries: LayoutPageTemplateEntry[]
	) => void;
	updateEntryIndex: (direction: 'previous' | 'next') => void;
}

function PreviewModalContent({
	addLayoutURL,
	entryIndex,
	getLayoutPageTemplateEntryListURL,
	initialLayoutPageTemplateEntryId,
	layoutPageTemplateEntryList,
	onPreviewOpenChange,
	setEntryIndex,
	setLayoutPageTemplateEntryList,
	updateEntryIndex,
}: IPreviewModalContentProps) {
	const iframeRef = useRef() as React.MutableRefObject<HTMLIFrameElement | null>;

	const layoutPageTemplateEntry = layoutPageTemplateEntryList
		? layoutPageTemplateEntryList[entryIndex]
		: null;

	useEffect(() => {
		fetch(getLayoutPageTemplateEntryListURL)
			.then((response) => response.json())
			.then(
				(
					nextLayoutPageTemplateEntryList: LayoutPageTemplateEntryList
				) => {
					setEntryIndex(
						nextLayoutPageTemplateEntryList.findIndex(
							(entry) =>
								entry.layoutPageTemplateEntryId ===
								initialLayoutPageTemplateEntryId
						)
					);

					setLayoutPageTemplateEntryList(
						nextLayoutPageTemplateEntryList
					);
				}
			)
			.catch((error) => {
				console.error(error);
			});
	}, [
		getLayoutPageTemplateEntryListURL,
		initialLayoutPageTemplateEntryId,
		setEntryIndex,
		setLayoutPageTemplateEntryList,
	]);

	if (!layoutPageTemplateEntryList || !layoutPageTemplateEntry) {
		return null;
	}

	return (
		<div className="bg-dark d-flex flex-column h-100 layout-page-template-entry-preview-modal">
			<div className="bg-white d-flex justify-content-end p-3">
				<ClayButton
					onClick={() => {
						onPreviewOpenChange(false);

						openModal({
							disableAutoClose: true,
							height: '60vh',
							id: 'addLayoutDialog',
							size: 'md',
							title: Liferay.Language.get('add-page'),
							url: createPortletURL(addLayoutURL, {
								layoutPageTemplateEntryId:
									layoutPageTemplateEntry.layoutPageTemplateEntryId,
							}),
						});
					}}
				>
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
					onLoad={() => {
						const style = {
							cursor: 'not-allowed',
							height: '100%',
							left: '0',
							position: 'fixed',
							top: '0',
							width: '100%',
							zIndex: '100000',
						};

						if (iframeRef.current) {
							const overlay = document.createElement('div');

							const keys = Object.keys(
								style
							) as (keyof typeof style)[];

							keys.forEach((key) => {
								overlay.style[key] = style[key];
							});

							iframeRef.current.removeAttribute('style');
							iframeRef.current.contentDocument?.body.append(
								overlay
							);
						}
					}}
					ref={(ref) => {
						iframeRef.current = ref;

						iframeRef.current?.setAttribute('inert', '');
					}}
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
