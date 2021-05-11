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
import {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayToolbar from '@clayui/toolbar';
import Cropper from 'cropperjs';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import '../css/image_editor.scss';

import 'cropperjs/dist/cropper.css';

const ratios = [
	{
		label: 'Free',
	},
	{
		label: '16:10',
		value: 16 / 10,
	},
	{
		label: '16:9',
		value: 16 / 9,
	},
	{
		label: '4:3',
		value: 4 / 3,
	},
	{
		label: '1:1',
		value: 1 / 1,
	},
	{
		label: '2:3',
		value: 2 / 3,
	},
];

const zoomSteps = [12.5, 25, 50, 100, 150, 200];

const noop = () => {};

export default ({
	imageId,
	imageSrc,
	itemReturnType,
	onCancel = noop,
	onSave = noop,
	saveURL,
}) => {
	const ref = useRef();

	const [currentZoom, setCurrentZoom] = useState(100);

	const handleAspectRationChange = (event) => {
		const value = event.target.value;

		ref.current?.cropper?.setAspectRatio(value);
	};

	const handleReset = () => {
		ref.current?.cropper?.reset();
		onCancel();
	};

	const handleRotate = () => {
		ref.current?.cropper?.rotate(90);
	};

	const handleSave = () => {
		const canvas = ref.current?.cropper?.getCroppedCanvas();

		canvas.toBlob((blob) => {
			const formData = new FormData();

			formData.append('fileEntryId', imageId);
			formData.append('imageBlob', blob, imageId);
			formData.append('returnType', itemReturnType);

			fetch(saveURL, {
				body: formData,
				method: 'POST',
			})
				.then((response) => response.json())
				.then((response) => {
					onSave(response);
				});
		});
	};

	const handleZoomIn = () => {
		const newZoomValue = zoomSteps.reduce((a, b) => {
			return currentZoom < a ? a : b;
		});

		ref.current?.cropper?.zoomTo(newZoomValue / 100);
	};

	const handleZoomOut = () => {
		const newZoomValue = zoomSteps.reduce((a, b) => {
			return currentZoom > b ? b : a;
		});

		const cropBoxDdata = ref.current?.cropper?.getCropBoxData();
		const imageData = ref.current?.cropper?.getImageData();

		if (
			imageData.width <= cropBoxDdata.width ||
			imageData.height <= cropBoxDdata.height
		) {
			return;
		}

		ref.current?.cropper?.zoomTo(newZoomValue / 100);
	};

	useEffect(() => {
		const imageElement = ref.current;

		if (imageElement !== null) {
			new Cropper(imageElement, {
				autoCrop: true,
				autoCropArea: 1,
				background: false,
				center: false,
				dragMode: 'move',
				enable: true,
				guides: false,
				rotateTo: 0,
				scaleX: 1,
				scaleY: 1,
				viewMode: 1,
				zoomTo: 0,
			});

			const handleCropReady = () => {
				const imageData = imageElement?.cropper?.getImageData();

				const currentZoom =
					(imageData.width * 100) / imageData.naturalWidth;

				setCurrentZoom(parseFloat(currentZoom.toFixed(1)));
			};

			const handleZoomChange = (event) => {
				const newZoomValue = event.detail.ratio * 100;

				if (newZoomValue > zoomSteps[zoomSteps.length - 1]) {
					event.preventDefault();

					return imageElement?.cropper?.zoomTo(
						zoomSteps[zoomSteps.length - 1] / 100
					);
				}

				if (newZoomValue < zoomSteps[0]) {
					event.preventDefault();

					return imageElement?.cropper?.zoomTo(zoomSteps[0] / 100);
				}

				setCurrentZoom(parseFloat(newZoomValue.toFixed(1)));
			};

			imageElement.addEventListener('ready', handleCropReady);
			imageElement.addEventListener('zoom', handleZoomChange);

			return () => {
				imageElement?.removeEventListener('cropready', handleCropReady);
				imageElement?.removeEventListener(
					'zoomChange',
					handleZoomChange
				);
				imageElement?.cropper?.destroy();
			};
		}
	}, []);

	return (
		<div className="image-editor">
			<div style={{height: '100%'}}>
				<img
					ref={ref}
					src={imageSrc}
					style={{
						display: 'block',
						maxWidth: '100%',
						opacity: 0,
					}}
				/>
			</div>

			<ClayToolbar className="rounded">
				<ClayToolbar.Nav>
					<ClayToolbar.Item>
						<ClayToolbar.Section>
							<ClayInput.Group>
								<ClayInput.GroupItem prepend shrink>
									<ClayInput.GroupText>
										<ClayIcon symbol="transform" />
									</ClayInput.GroupText>
								</ClayInput.GroupItem>

								<ClayInput.GroupItem append>
									<ClaySelectWithOption
										onChange={handleAspectRationChange}
										options={ratios}
									/>
								</ClayInput.GroupItem>
							</ClayInput.Group>
						</ClayToolbar.Section>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<ClayToolbar.Section>
							<ClayButtonWithIcon
								displayType={null}
								onClick={handleRotate}
								symbol="reload"
							/>
						</ClayToolbar.Section>
					</ClayToolbar.Item>

					<ClayToolbar.Item expand>
						<ClayToolbar.Section>
							<ClayButton.Group spaced>
								<ClayButtonWithIcon
									displayType={null}
									onClick={handleZoomOut}
									symbol="hr"
								/>

								<span className="zoom-percent">
									{currentZoom}%
								</span>

								<ClayButtonWithIcon
									displayType={null}
									onClick={handleZoomIn}
									symbol="plus"
								/>
							</ClayButton.Group>
						</ClayToolbar.Section>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<ClayToolbar.Section>
							<ClayButton
								displayType={null}
								onClick={handleReset}
							>
								Cancel
							</ClayButton>

							<ClayButton onClick={handleSave}>Save</ClayButton>
						</ClayToolbar.Section>
					</ClayToolbar.Item>
				</ClayToolbar.Nav>
			</ClayToolbar>
		</div>
	);
};
