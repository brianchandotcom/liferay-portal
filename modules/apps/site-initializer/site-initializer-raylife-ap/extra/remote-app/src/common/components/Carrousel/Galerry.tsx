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

import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import './index.scss';

interface DamageSummaryProps {
	images: string[];
	size: {
		height: string;
		width: string;
	};
}

const Galerry: React.FC<DamageSummaryProps> = ({images, size}) => {
	const [modalOpen, setModalOpen] = useState(false);
	const [activeImage, setActiveImage] = useState(0);

	const hasNextImage = activeImage < images?.length - 1;
	const hasPreviousImage = activeImage > 0;

	const handleImageClick = (index: number) => {
		setActiveImage(index);
		setModalOpen(true);
	};

	const handleModalClose = () => {
		setModalOpen(false);
	};

	const handlePrevClick = () => {
		if (hasPreviousImage) {
			setActiveImage(activeImage - 1);
		}
	};

	const handleNextClick = () => {
		if (hasNextImage) {
			setActiveImage(activeImage + 1);
		}
	};

	return (
		<div className="d-flex flex-wrap gallery-container">
			{images?.map((image, index) => (
				<div
					className="10px; image-container m-3"
					key={index}
					onClick={() => handleImageClick(index)}
				>
					<img
						alt={`Image ${index}`}
						className="image image-cover"
						src={image}
						style={{
							height: size.height,
							width: size.width,
						}}
					/>
				</div>
			))}

			{modalOpen && (
				<div className="align-items-center d-flex h-100 justify-content-center modal position-fixed w-100">
					<div
						className="h-100 modal-overlay position-absolute w-100"
						onClick={handleModalClose}
					/>

					<div className="bg-neutral-0 d-flex flex-column justify-content-between modal-content p-3 position-relative rounded-sm">
						<div className="align-items-center d-flex justify-content-between text-brand-secondary-darken-5">
							<div>
								{activeImage + 1}/{images?.length}
							</div>

							<div
								className="modal-close"
								onClick={handleModalClose}
							>
								<ClayIcon symbol="times" />
							</div>
						</div>

						<div
							className={`modal-prev position-absolute p-1 display-4
							${!hasPreviousImage ? 'button-unable' : ''}`}
							onClick={handlePrevClick}
						>
							<ClayIcon symbol="angle-left" />
						</div>

						<div
							className={`modal-next position-absolute p-1 display-4
							${!hasNextImage ? 'button-unable' : ''}`}
							onClick={handleNextClick}
						>
							<ClayIcon symbol="angle-right" />
						</div>

						<div className="align-items-center d-flex h-100 justify-content-center modal-carousel-container overflow-hidden position-relative w-100">
							{images?.map((image, index) => (
								<div
									className={`align-items-center d-flex h-100 justify-content-center modal-carousel-container overflow-hidden position-absolute w-100 ${
										index === activeImage
											? 'slide--active'
											: ''
									}`}
									key={index}
								>
									{index === activeImage && (
										<img
											alt={`Image ${index}`}
											className="image"
											src={image}
										/>
									)}
								</div>
							))}
						</div>

						<div className="bg-brand-primary-lighten-6 d-flex gallery-modal-preview-bar justify-content-center">
							{images?.map((image, index) => (
								<div
									className={`item m-2 ${
										index === activeImage
											? 'active rounded-sm'
											: ''
									}`}
									key={index}
									onClick={() => setActiveImage(index)}
								>
									<img
										alt={`Image ${index}`}
										className="image rounded-sm"
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

export default Galerry;
