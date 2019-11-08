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
import ClayIcon from '@clayui/icon';
import {useEventListener, useIsMounted} from 'frontend-js-react-web';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useLayoutEffect, useRef, useState} from 'react';

/**
 * Zoom ratio limit that fire the autocenter
 * @type {number}
 */
const MIN_ZOOM_RATIO_AUTOCENTER = 3;

/**
 * Available zoom sizes
 * @type {Array<number>}
 */
const ZOOM_LEVELS = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1];

/**
 * Available reversed zoom sizes
 * @type {Array<number>}
 */
const ZOOM_LEVELS_REVERSED = ZOOM_LEVELS.slice().reverse();

/**
 * Component that create an image preview to allow zoom
 * @review
 */
const ImagePreviewer = ({imageURL}) => {
	const [currentZoom, setCurrentZoom] = useState(1);
	const [imageHeight, setImageHeight] = useState(null);
	const [imageWidth, setImageWidth] = useState(null);
	const [imageMargin, setImageMargin] = useState(null);
	const [zoomInDisabled, setZoomInDisabled] = useState(true);
	const [zoomOutDisabled, setZoomOutDisabled] = useState(false);
	const [zoomRatio, setZoomRatio] = useState(false);

	const image = useRef();
	const imageContainer = useRef();

	const isMounted = useIsMounted();

	const applyZoom = zoom => {
		const imageElement = image.current;

		setImageHeight(imageElement.naturalHeight * zoom);
		setImageWidth(imageElement.naturalWidth * zoom);
		setZoomRatio(zoom / currentZoom);

		updateToolbar(zoom);
	};

	const getFittingZoom = () => {
		const imageElement = image.current;

		return imageElement.width / imageElement.naturalWidth;
	};

	const getImageStyles = () => {
		const imageStyles = {};

		if (imageHeight && imageWidth) {
			imageStyles.height = `${imageHeight}px`;
			imageStyles.maxHeight = `${imageHeight}px`;
			imageStyles.maxWidth = `${imageWidth}px`;
			imageStyles.width = `${imageWidth}px`;
		}

		if (imageMargin) {
			imageStyles.margin = imageMargin;
		}

		return imageStyles;
	};

	const handleImageLoad = () => {
		updateToolbar(getFittingZoom());
	};

	const handlePercentButtonClick = () => {
		if (currentZoom === 1) {
			setImageHeight(null);
			setImageWidth(null);

			updateToolbar(getFittingZoom());
		} else {
			applyZoom(1);
		}
	};

	const handleWindowResize = debounce(() => {
		if (isMounted() && !image.current.style.width) {
			updateToolbar(getFittingZoom());
		}
	}, 250);

	const updateToolbar = zoom => {
		setCurrentZoom(zoom);
		setZoomInDisabled(ZOOM_LEVELS_REVERSED[0] === zoom);
		setZoomOutDisabled(ZOOM_LEVELS[0] >= zoom);
	};

	useEventListener('resize', handleWindowResize, false, window);

	useLayoutEffect(() => {
		const imageContainerElement = imageContainer.current;

		setImageMargin(
			`${imageHeight > imageContainerElement.clientHeight ? 0 : 'auto'} ${
				imageWidth > imageContainerElement.clientWidth ? 0 : 'auto'
			}`
		);

		if (
			zoomRatio &&
			imageContainerElement.clientWidth < image.current.naturalWidth
		) {
			let scrollLeft;
			let scrollTop;

			if (zoomRatio < MIN_ZOOM_RATIO_AUTOCENTER) {
				scrollLeft =
					(imageContainerElement.clientWidth * (zoomRatio - 1)) / 2 +
					imageContainerElement.scrollLeft * zoomRatio;
				scrollTop =
					(imageContainerElement.clientHeight * (zoomRatio - 1)) / 2 +
					imageContainerElement.scrollTop * zoomRatio;
			} else {
				scrollTop =
					(imageHeight - imageContainerElement.clientHeight) / 2;
				scrollLeft =
					(imageWidth - imageContainerElement.clientWidth) / 2;
			}

			imageContainerElement.scrollLeft = scrollLeft;
			imageContainerElement.scrollTop = scrollTop;

			setZoomRatio(null);
		}

	}, [imageHeight, imageWidth, zoomRatio, imageMargin]);

	return (
		<div className="preview-file">
			<div
				className="preview-file-container preview-file-max-height"
				ref={imageContainer}
			>
				<img
					className="preview-file-image"
					onLoad={handleImageLoad}
					ref={image}
					src={imageURL}
					style={getImageStyles()}
				/>
			</div>
			<div className="preview-toolbar-container">
				<ClayButton.Group className="floating-bar">
					<ClayButton
						className="btn-floating-bar"
						disabled={zoomOutDisabled}
						displayType={null}
						monospaced
						onClick={() => {
							applyZoom(
								ZOOM_LEVELS_REVERSED.find(
									zoom => zoom < currentZoom
								)
							);
						}}
						title={Liferay.Language.get('zoom-out')}
					>
						<ClayIcon symbol="hr" />
					</ClayButton>
					<ClayButton
						className="btn-floating-bar btn-floating-bar-text"
						displayType={null}
						onClick={handlePercentButtonClick}
						title={
							currentZoom === 1
								? Liferay.Language.get('zoom-to-fit')
								: Liferay.Language.get('real-size')
						}
					>
						<span className="preview-toolbar-label-percent">
							{Math.round((currentZoom || 0) * 100)}%
						</span>
					</ClayButton>
					<ClayButton
						className="btn-floating-bar"
						disabled={zoomInDisabled}
						displayType={null}
						monospaced
						onClick={() => {
							applyZoom(
								ZOOM_LEVELS.find(zoom => zoom > currentZoom)
							);
						}}
						title={Liferay.Language.get('zoom-in')}
					>
						<ClayIcon symbol="plus" />
					</ClayButton>
				</ClayButton.Group>
			</div>
		</div>
	);
};

ImagePreviewer.propTypes = {
	imageURL: PropTypes.string
};

export default function(props) {
	return <ImagePreviewer {...props} />;
}
