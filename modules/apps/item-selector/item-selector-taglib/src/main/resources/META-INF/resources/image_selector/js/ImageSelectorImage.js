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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

const STR_BOTH = 'both';
const STR_HORIZONTAL = 'horizontal';
const STR_VERTICAL = 'vertical';

const noop = () => {};

const ImageSelectorImage = ({
	direction = STR_VERTICAL,
	imageSrc,
	isCroppable,
	onImageCrop = noop,
	portletNamespace,
}) => {
	const containerRef = useRef();
	const imageRef = useRef();

	const [imagePosition, setImagePosition] = useState({x: 0, y: 0});
	const [imageRelativePosition, setImageRelativePosition] = useState();
	const [isDragging, setIsDragging] = useState();

	const getImageStyle = () => {
		let imageStyle = {};

		if (isCroppable) {
			imageStyle = {
				cursor: isDragging ? 'move' : 'unset',
				left: imagePosition.x,
				position: 'relative',
				top: imagePosition.y,
			};
		}

		return imageStyle;
	};

	const handleMouseDown = (event) => {
		event.stopPropagation();
		event.preventDefault();

		const imageContainer = containerRef.current;
		const position = imageContainer.getBoundingClientRect();

		setImageRelativePosition({
			x: event.pageX - position.left,
			y: event.pageY - position.top,
		});

		setIsDragging(true);
	};

	const handleMouseMove = (event) => {
		event.stopPropagation();
		event.preventDefault();

		const imageContainer = containerRef.current;
		const image = imageRef.current;

		const imageContainerPosition = imageContainer.getBoundingClientRect();

		let horizontalPos = imagePosition.x;
		let verticalPos = imagePosition.y;

		if (direction === STR_HORIZONTAL || direction === STR_BOTH) {
			const horizontalDiff =
				event.pageX -
				imageContainerPosition.left -
				imageRelativePosition.x;
			horizontalPos = horizontalPos + horizontalDiff;

			if (
				horizontalPos >= 0 ||
				horizontalPos < imageContainer.offsetWidth - image.offsetWidth
			) {
				event.preventDefault();

				return;
			}
		}

		if (direction === STR_VERTICAL || direction === STR_BOTH) {
			const verticalDiff =
				event.pageY -
				imageContainerPosition.top -
				imageRelativePosition.y;
			verticalPos = verticalPos + verticalDiff;

			if (
				verticalPos >= 0 ||
				verticalPos < imageContainer.offsetHeight - image.offsetHeight
			) {
				event.preventDefault();

				return;
			}
		}

		setImagePosition({
			x: horizontalPos,
			y: verticalPos,
		});
	};

	const handleMouseUp = (event) => {
		event.stopPropagation();
		event.preventDefault();

		setIsDragging(false);

		const cropRegion = Liferay.Util.getCropRegion(imageRef.current, {
			height: containerRef.current.offsetHeight,
			x: Math.abs(imagePosition.x),
			y: Math.abs(imagePosition.y),
		});

		onImageCrop(cropRegion);
	};

	useEffect(() => {
		if (isDragging) {
			document.addEventListener('mousemove', handleMouseMove);
			document.addEventListener('mouseup', handleMouseUp);
		}

		return () => {
			document.removeEventListener('mousemove', handleMouseMove);
			document.removeEventListener('mouseup', handleMouseUp);
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [isDragging]);

	return (
		<div
			className={classNames('image-wrapper', {
				cropper: isCroppable,
			})}
			ref={containerRef}
		>
			<img
				alt={Liferay.Language.get('current-image')}
				className="current-image"
				id={`${portletNamespace}image`}
				onMouseDown={handleMouseDown}
				ref={imageRef}
				src={imageSrc}
				style={getImageStyle()}
			/>
		</div>
	);
};

ImageSelectorImage.propTypes = {
	direction: PropTypes.string,
	imageSrc: PropTypes.string.isRequired,
	isCroppable: PropTypes.bool,
	onImageCrop: PropTypes.func,
	portletNamespace: PropTypes.string.isRequired,
};

export default ImageSelectorImage;
