/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useState} from 'react';

type CarouselProps = {images: string[]};

const Carousel: React.FC<CarouselProps> = ({images}) => {
	const [currentIndex, setCurrentIndex] = useState(0);

	const handleNext = () =>
		setCurrentIndex((prevIndex) =>
			prevIndex === images.length - 1 ? 0 : prevIndex + 1
		);

	const handlePrev = () =>
		setCurrentIndex((prevIndex) =>
			prevIndex === 0 ? images.length - 1 : prevIndex - 1
		);

	const handleSelectImage = (index: number) => setCurrentIndex(index);

	return (
		<div className="marketplace-carousel">
			<div className="align-items-center carousel d-flex justify-content-center m-0 rounded">
				<div className="carousel-border left" onClick={handlePrev} />

				<div className="carousel-images d-flex justify-content-between">
					<img
						alt={`Slide ${currentIndex}`}
						className="carousel-image rounded"
						draggable={false}
						src={images[currentIndex]}
					/>
				</div>

				<div className="carousel-border right" onClick={handleNext} />
			</div>

			<div className="d-flex justify-content-start overflow-auto">
				{images.map((image, index) => (
					<img
						alt={`Thumbnail ${index}`}
						className={classNames(
							'gallery-image mt-5 mb-2 mx-1 rounded',
							{
								selected: index === currentIndex,
							}
						)}
						draggable={false}
						key={index}
						onClick={() => handleSelectImage(index)}
						src={image}
					/>
				))}
			</div>
		</div>
	);
};

export default Carousel;
