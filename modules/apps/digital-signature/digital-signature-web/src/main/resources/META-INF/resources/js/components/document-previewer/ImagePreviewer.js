/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useEventListener, useIsMounted} from '@liferay/frontend-js-react-web';
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
const ImagePreviewer = ({alt, imageURL}) => {
	const [currentZoom, setCurrentZoom] = useState(1);
	const [imageHeight, setImageHeight] = useState(null);
	const [imageWidth, setImageWidth] = useState(null);
	const [imageMargin, setImageMargin] = useState(null);
	const [zoomInDisabled, setZoomInDisabled] = useState(true);
	const [zoomOutDisabled, setZoomOutDisabled] = useState(false);
	const [zoomRatio, setZoomRatio] = useState(false);

	const imageRef = useRef();
	const imageContainerRef = useRef();

	const isMounted = useIsMounted();

	const updateToolbar = (zoom) => {
		setCurrentZoom(zoom);
		setZoomInDisabled(ZOOM_LEVELS_REVERSED[0] === zoom);
		setZoomOutDisabled(ZOOM_LEVELS[0] >= zoom);
	};

	const applyZoom = (zoom) => {
		const imageElement = imageRef.current;

		setImageHeight(imageElement.naturalHeight * zoom);
		setImageWidth(imageElement.naturalWidth * zoom);
		setZoomRatio(zoom / currentZoom);

		updateToolbar(zoom);
	};

	const getFittingZoom = () => {
		const imageElement = imageRef.current;

		return imageElement.width / imageElement.naturalWidth;
	};

	const getImageStyles = () => {
		const imageStyles = {};

		if (imageHeight && imageWidth) {
			imageStyles.height = imageHeight;
			imageStyles.maxHeight = imageHeight;
			imageStyles.maxWidth = imageWidth;
			imageStyles.width = imageWidth;
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
		}
		else {
			applyZoom(1);
		}
	};

	const handleWindowResize = debounce(() => {
		if (isMounted() && !imageRef.current.style.width) {
			updateToolbar(getFittingZoom());
		}
	}, 250);

	useEventListener('resize', handleWindowResize, false, window);

	useLayoutEffect(() => {
		const imageContainerElement = imageContainerRef.current;

		setImageMargin(
			`${imageHeight > imageContainerElement.clientHeight ? 0 : 'auto'} ${
				imageWidth > imageContainerElement.clientWidth ? 0 : 'auto'
			}`
		);

		if (
			zoomRatio &&
			(imageContainerElement.clientWidth <
				imageRef.current.naturalWidth ||
				imageContainerElement.clientHeight <
					imageRef.current.naturalHeight)
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
			}
			else {
				scrollTop =
					(imageHeight - imageContainerElement.clientHeight) / 2;
				scrollLeft =
					(imageWidth - imageContainerElement.clientWidth) / 2;
			}

			imageContainerElement.scrollLeft = scrollLeft;
			imageContainerElement.scrollTop = scrollTop;

			setZoomRatio(null);
		}

		if (!imageRef.current.style.width) {
			updateToolbar(getFittingZoom());
		}
	}, [imageHeight, imageWidth, zoomRatio, imageMargin]);

	return (
		<div className="preview-file">
			<div
				className="d-flex preview-file-container preview-file-max-height"
				ref={imageContainerRef}
			>
				<div className="image-container">
					<img
						alt={alt}
						className="preview-file-image"
						onLoad={handleImageLoad}
						ref={imageRef}
						src={imageURL}
						style={getImageStyles()}
					/>
				</div>
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
									(zoom) => zoom < currentZoom
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
								ZOOM_LEVELS.find((zoom) => zoom > currentZoom)
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
	imageURL: PropTypes.string,
};

export default ImagePreviewer;
