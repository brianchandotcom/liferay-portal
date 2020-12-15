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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {useGlobalContext} from '../../app/components/GlobalContext';
import ImageService from '../../app/services/ImageService';
import {useSelector} from '../../app/store/index';
import {useId} from '../../app/utils/useId';

export const DEFAULT_IMAGE_SIZE_ID = 'auto';

const DEFAULT_IMAGE_SIZE = {
	size: null,
	value: DEFAULT_IMAGE_SIZE_ID,
	width: null,
};

export const ImageSelectorSize = ({
	editableElement = null,
	fileEntryId = DEFAULT_IMAGE_SIZE_ID,
	imageSizeId,
	onImageSizeIdChanged = null,
}) => {
	const imageSizeSelectId = useId();
	const [imageSize, setImageSize] = useState(DEFAULT_IMAGE_SIZE);
	const [imageSizes, setImageSizes] = useState([]);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const globalContext = useGlobalContext();

	useEffect(() => {
		const computedImageSize =
			imageSizes.find((imageSize) => imageSize.value === imageSizeId) ||
			DEFAULT_IMAGE_SIZE;

		// If selected imageSizeId is 'auto', we try to resolve the
		// computed real image size based on current viewport and the image
		// HTMLElement.

		if (computedImageSize.value === DEFAULT_IMAGE_SIZE_ID) {
			const setAutoSize = () => {
				editableElement?.removeEventListener('load', setAutoSize);

				const autoSize =
					imageSizes.find(({mediaQuery}) => {
						const globalWindow = globalContext.window;

						return mediaQuery
							? globalWindow.matchMedia(mediaQuery).matches
							: true;
					}) ||
					imageSizes.find(({value}) => {
						return value === DEFAULT_IMAGE_SIZE_ID;
					}) ||
					DEFAULT_IMAGE_SIZE;

				setImageSize({
					...autoSize,
					width:
						autoSize.width ||
						editableElement?.naturalWidth ||
						editableElement?.getBoundingClientRect().width ||
						globalContext.document.body.getBoundingClientRect()
							.width,
				});
			};

			if (
				!editableElement ||
				editableElement.complete ||
				editableElement.tagName !== 'IMG'
			) {
				setAutoSize();
			}
			else {
				editableElement.addEventListener('load', setAutoSize);

				return () => {
					editableElement.removeEventListener('load', setAutoSize);
				};
			}
		}
		else {
			setImageSize(computedImageSize);
		}
	}, [
		editableElement,
		globalContext,
		imageSizeId,
		imageSizes,
		selectedViewportSize,
	]);

	useEffect(() => {
		ImageService.getAvailableImageConfigurations({
			fileEntryId,
			onNetworkStatus: () => {},
		}).then((availableImageSizes) => {
			setImageSizes(
				[...availableImageSizes].sort(
					(imageSizeA, imageSizeB) =>
						imageSizeA.width - imageSizeB.width
				)
			);
		});
	}, [fileEntryId]);

	return (
		<ClayForm.Group className="mb-3">
			{onImageSizeIdChanged && (
				<ClayForm.Group className="mb-2">
					<label htmlFor={imageSizeSelectId}>
						{Liferay.Language.get('resolution')}
					</label>
					<ClaySelectWithOption
						className={'form-control form-control-sm'}
						id={imageSizeSelectId}
						name={imageSizeSelectId}
						onChange={(event) =>
							onImageSizeIdChanged(event.target.value)
						}
						options={imageSizes}
						value={imageSizeId}
					/>
				</ClayForm.Group>
			)}

			{imageSize.width && (
				<div className="small text-secondary">
					<b>{Liferay.Language.get('width')}:</b>
					<span className="ml-1">{imageSize.width}px</span>
				</div>
			)}

			{imageSize.size && (
				<div className="small text-secondary">
					<b>{Liferay.Language.get('file-size')}:</b>
					<span className="ml-1">
						{Number(imageSize.size).toFixed(2)}kB
					</span>
				</div>
			)}
		</ClayForm.Group>
	);
};

ImageSelectorSize.propTypes = {
	editableElement: PropTypes.object,
	fileEntryId: PropTypes.number.isRequired,
	imageSizeId: PropTypes.string,
	onImageSizeIdChanged: PropTypes.func,
};
