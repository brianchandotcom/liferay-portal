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

import React, {useEffect, useState} from 'react';

import './index.scss';

// interface DamageSummaryProps {
// 	images: string[];
// }

const Carrousel: any = () => {
	const [modalOpen, setModalOpen] = useState(false);
	const [activeImage, setActiveImage] = useState(0);
	const [arrayImages, setArrayImages] = useState<string[]>([]);

	useEffect(() => {
		try {
			const img1 = [
				'http://placekitten.com/200/230',
				'http://placekitten.com/200/210',
			];
			setArrayImages(img1);
		} catch (error) {
			console.warn(error);
		}
	}, []);

	const handleImageClick = (index: number) => {
		setActiveImage(index);
		setModalOpen(true);
	};

	const handleModalClose = () => {
		setModalOpen(false);
	};

	const handlePrevClick = () => {
		setActiveImage((prev) => prev - 1);
	};

	const handleNextClick = () => {
		setActiveImage((prev) => prev + 1);
	};

	return (
		<div className="gallery">
			{arrayImages.map((image, index) => (
				<div
					className="gallery__image-container"
					key={index}
					onClick={() => handleImageClick(index)}
				>
					<img
						alt={`Image ${index}`}
						className="gallery__image"
						src={image}
					/>
				</div>
			))}

			{modalOpen && (
				<div className="gallery__modal">
					<div
						className="gallery__modal-overlay"
						onClick={handleModalClose}
					/>

					<div className="gallery__modal-content">
						<div
							className="gallery__modal-close"
							onClick={handleModalClose}
						>
							&times;
						</div>

						<div
							className="gallery__modal-prev"
							onClick={handlePrevClick}
						>
							&#8249;
						</div>

						<div
							className="gallery__modal-next"
							onClick={handleNextClick}
						>
							&#8250;
						</div>

						<div className="gallery__modal-carousel-container">
							{arrayImages.map((image, index) => (
								<div
									className={`gallery__modal-carousel-slide ${
										index === activeImage
											? 'gallery__modal-carousel-slide--active'
											: ''
									}`}
									key={index}
								>
									{index === activeImage && (
										<img
											alt={`Image ${index}`}
											className="gallery__modal-image"
											src={image}
										/>
									)}
								</div>
							))}
						</div>

						<div className="gallery__modal-preview-bar">
							{arrayImages.map((image, index) => (
								<div
									className={`gallery__modal-preview-bar-item ${
										index === activeImage
											? 'gallery__modal-preview-bar-item--active'
											: ''
									}`}
									key={index}
									onClick={() => setActiveImage(index)}
								>
									<img
										alt={`Image ${index}`}
										className="gallery__modal-preview-image"
										src={image}
									/>
								</div>
							))}
						</div>
					</div>
				</div>
			)}
		</div>
	);
};

export default Carrousel;
