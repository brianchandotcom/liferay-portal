/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

'use client';

import clsx from 'clsx';
import {ChevronLeft, ChevronRight} from 'lucide-react';
import Image from 'next/image';
import {useState} from 'react';

import PreviewHTML from '../../components/preview-html';
import {Attachment, Product} from '../../types/product';
import {getSkuDetails, handleImageError} from '../../utils/product';
import {Badge} from '../ui/badge';
import {Card} from '../ui/card';
import ProductQuickActions from './product-quick-actions';
import ProductSpecifications from './product-specifications';
import ProductUOM from './product-uom';

const ProductDetail = ({product}: {product: Product}) => {
	const [selectedImageIndex, setSelectedImageIndex] = useState(0);

	const images = product.images?.length
		? product.images
		: [{src: product.urlImage}];

	const skuDetails = getSkuDetails(product);

	const nextImage = () => {
		setSelectedImageIndex((prev) => (prev + 1) % images.length);
	};

	const prevImage = () => {
		setSelectedImageIndex(
			(prev) => (prev - 1 + images.length) % images.length
		);
	};

	return (
		<div>
			<div className="gap-8 grid grid-cols-1 lg:grid-cols-5">
				<div className="lg:col-span-2">
					<div className="space-y-4">
						<Card>
							<div className="relative">
								<Image
									alt={product.name}
									className="aspect-square object-cover rounded-lg w-full"
									height={500}
									onError={handleImageError}
									quality={100}
									src={images[selectedImageIndex].src}
									width={480}
								/>

								<button
									className="-translate-y-1/2 absolute bg-white/80 hover:bg-white left-2 p-2 rounded-full top-1/2 transform"
									onClick={prevImage}
								>
									<ChevronLeft className="h-4 w-4" />
								</button>

								<button
									className="-translate-y-1/2 absolute bg-white/80 hover:bg-white p-2 right-2 rounded-full top-1/2 transform"
									onClick={nextImage}
								>
									<ChevronRight className="h-4 w-4" />
								</button>
							</div>
						</Card>

						<div className="flex gap-2 overflow-x-auto">
							{images.map((image, index) => (
								<button
									className={clsx(
										'border-2 flex-shrink-0 h-16 overflow-hidden rounded-md w-16',
										{
											'border-primary':
												selectedImageIndex === index,
											'border-transparent':
												selectedImageIndex !== index,
										}
									)}
									key={index}
									onClick={() => setSelectedImageIndex(index)}
								>
									<Image
										alt={(image as Attachment).title || ''}
										className="h-full object-cover w-full"
										height={16}
										onError={handleImageError}
										quality={100}
										src={image.src}
										width={16}
									/>
								</button>
							))}
						</div>
					</div>
				</div>

				<div className="lg:col-span-2 space-y-6">
					<h1 className="font-bold mb-2 text-2xl">{product.name}</h1>

					{!!skuDetails.availability?.stockQuantity && (
						<div>
							{skuDetails.availability?.stockQuantity <= 10 && (
								<Badge
									className="mb-2 mr-2"
									variant="destructive"
								>
									LOW STOCK
								</Badge>
							)}

							<span className="text-sm">
								Only {skuDetails.availability?.stockQuantity}
								&nbsp;left in stock
							</span>
						</div>
					)}

					<div className="text-sm">
						Estimate Incoming Days: &nbsp;
						<b>
							{
								skuDetails?.productConfiguration
									?.availabilityEstimateName
							}
						</b>
					</div>

					<div>
						<p className="mb-1 text-sm">SKU: {skuDetails.sku}</p>
					</div>

					<div className="space-y-2">
						{skuDetails.gtin && (
							<p className="text-sm">GTIN: {skuDetails.gtin}</p>
						)}

						{skuDetails.manufacturerPartNumber && (
							<p className="text-sm">
								MPN: {skuDetails.manufacturerPartNumber}
							</p>
						)}
					</div>

					<PreviewHTML content={product.description ?? ''} />

					{!!skuDetails.skuUnitOfMeasures?.length && (
						<ProductUOM uom={skuDetails?.skuUnitOfMeasures} />
					)}
				</div>

				<div className="lg:col-span-1 space-y-6">
					<Card className="justify-end p-4">
						{skuDetails.discount && (
							<>
								<div className="text-right">
									<div className="text-sm">List Price</div>

									<div className="font-bold text-xl">
										{skuDetails.originalPrice}
									</div>
								</div>

								<div className="text-right">
									<div className="text-sm">Promo Price</div>

									<div className="font-bold text-xl">
										{skuDetails?.price?.finalPrice}
									</div>
								</div>

								{skuDetails.discount && (
									<div className="text-right">
										<div className="text-sm">Discount</div>

										<span className="text-discount text-red-600 text-sm">
											{skuDetails.discountPercent}%
										</span>
									</div>
								)}
							</>
						)}

						<div
							className={clsx('text-right', {
								'border-t': skuDetails.discount,
							})}
						>
							<div className="text-sm">Final Price</div>

							<div className="font-bold text-2xl text-price">
								{skuDetails?.price?.finalPrice}
							</div>
						</div>
					</Card>

					<ProductQuickActions />
				</div>
			</div>

			<div className="mt-12">
				<ProductSpecifications product={product} />
			</div>
		</div>
	);
};

export default ProductDetail;
