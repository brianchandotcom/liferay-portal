/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
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

	const hasNextImage = activeImage < images.length - 1;
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
			{images.map((image, index) => (
				<div
					className="image-container m-3"
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
							<div>{`${activeImage + 1}/${images.length}`}</div>

							<div
								className="modal-close"
								onClick={handleModalClose}
							>
								<ClayIcon symbol="times" />
							</div>
						</div>

						<div
							className={classNames(
								'modal-prev position-absolute p-1 display-4',
								{
									'button-unable': !hasPreviousImage,
								}
							)}
							onClick={handlePrevClick}
						>
							<ClayIcon symbol="angle-left" />
						</div>

						<div
							className={classNames(
								'modal-next position-absolute p-1 display-4',
								{
									'button-unable': !hasNextImage,
								}
							)}
							onClick={handleNextClick}
						>
							<ClayIcon symbol="angle-right" />
						</div>

						<div className="align-items-center d-flex h-100 justify-content-center modal-carousel-container overflow-hidden position-relative w-100">
							{images.map((image, index) => (
								<div
									className={classNames(
										'align-items-center d-flex h-100 justify-content-center modal-carousel-container overflow-hidden position-absolute w-100',
										{
											'slide--active':
												index === activeImage,
										}
									)}
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
							{images.map((image, index) => (
								<div
									className={classNames('item m-2', {
										'active rounded-sm':
											index === activeImage,
									})}
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
