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
import {useIsMounted} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {useGetFieldValue} from '../../app/contexts/CollectionItemContext';
import {useGlobalContext} from '../../app/contexts/GlobalContext';
import {useSelector} from '../../app/contexts/StoreContext';
import selectLanguageId from '../../app/selectors/selectLanguageId';
import ImageService from '../../app/services/ImageService';
import isMapped from '../../app/utils/editable-value/isMapped';
import resolveEditableValue from '../../app/utils/editable-value/resolveEditableValue';
import {useId} from '../../app/utils/useId';

export const DEFAULT_IMAGE_SIZE_ID = 'auto';

const DEFAULT_IMAGE_SIZE = {
	size: null,
	value: DEFAULT_IMAGE_SIZE_ID,
	width: null,
};

export const ImageSelectorSize = ({
	editableElement = null,
	fieldValue,
	imageSizeId,
	onImageSizeIdChanged = null,
}) => {
	const [fileEntryId, setFileEntryId] = useState(
		fieldValue.fileEntryId || ''
	);
	const getFieldValue = useGetFieldValue();
	const globalContext = useGlobalContext();
	const imageSizeSelectId = useId();
	const [imageSize, setImageSize] = useState(DEFAULT_IMAGE_SIZE);
	const [imageSizes, setImageSizes] = useState([]);
	const isMounted = useIsMounted();
	const languageId = useSelector(selectLanguageId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	useEffect(() => {
		if (fieldValue.fileEntryId) {
			setFileEntryId(fieldValue.fileEntryId);
		}
		else if (isMapped(fieldValue)) {
			resolveEditableValue(fieldValue, languageId, getFieldValue).then(
				(value) => {
					if (isMounted()) {
						setFileEntryId(value?.fileEntryId || '');
					}
				}
			);
		}
	}, [fieldValue, getFieldValue, isMounted, languageId]);

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
					width: parseInt(
						autoSize.width ||
							editableElement?.naturalWidth ||
							editableElement?.getBoundingClientRect().width ||
							globalContext.document.body.getBoundingClientRect()
								.width,
						10
					),
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
		if (!fileEntryId) {
			setImageSizes([]);

			return;
		}

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
						className="form-control form-control-sm"
						id={imageSizeSelectId}
						name={imageSizeSelectId}
						onChange={(event) =>
							onImageSizeIdChanged(event.target.value)
						}
						options={imageSizes.map(({label, value}) => ({
							label,
							value,
						}))}
						value={imageSizeId || DEFAULT_IMAGE_SIZE_ID}
					/>
				</ClayForm.Group>
			)}

			{!!imageSize.width && (
				<div className="small text-secondary">
					<b>{Liferay.Language.get('width')}:</b>
					<span className="ml-1">{imageSize.width}px</span>
				</div>
			)}

			{!!imageSize.size && (
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
	fieldValue: PropTypes.oneOfType([
		PropTypes.shape({
			fileEntryId: PropTypes.string.isRequired,
		}),
		PropTypes.shape({
			classNameId: PropTypes.string.isRequired,
			classPK: PropTypes.string.isRequired,
			fieldId: PropTypes.string.isRequired,
		}),
		PropTypes.shape({
			collectionFieldId: PropTypes.string.isRequired,
		}),
	]).isRequired,
	imageSizeId: PropTypes.string,
	onImageSizeIdChanged: PropTypes.func,
};
