import {Product} from 'liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0';
import Image from 'next/image';

import {Card} from '@/components/ui/card';
import {Badge} from '@/components/ui/badge';
import {getSkuDetails, handleImageError} from '@/lib/product';
import Link from 'next/link';

type ProductListViewProps = {
	product: Product;
};

export default function ProductListView({product}: ProductListViewProps) {
	const productPrice = getSkuDetails(product);

	return (
		<Card className="group overflow-hidden cursor-pointer transition-all duration-300 hover:shadow-sm bg-product-card border-product-border">
			<Link href={`/product/${product.urls?.en_US}`}>
				<div className="bg-white flex items-center p-4 gap-4">
					<div className="relative w-20 h-20 flex-shrink-0">
						<Image
							alt={product.name as string}
							className="w-full h-full object-cover rounded-md"
							height={48}
							quality={100}
							src={product.urlImage as string}
							unoptimized
							width={48}
							onError={handleImageError}
						/>
					</div>

					<div className="flex-1 space-y-2">
						<div className="flex items-start justify-between">
							<div className="space-y-1">
								<div className="text-sm flex gap-2 items-center">
									<Badge className="bg-success bg-slate-500 text-white">
										AVAILABLE
									</Badge>

									{product.productConfiguration
										?.availabilityEstimateName && (
										<p>
											Incoming Date:{' '}
											{
												product.productConfiguration
													?.availabilityEstimateName
											}
										</p>
									)}
								</div>

								<h3 className="font-medium text-foreground group-hover:text-primary transition-colors">
									{product.name}
								</h3>

								<p className="text-sm">
									{product.description ||
										'Short description duis semper tellus egestas ex porttitor scelerisque. Donec scelerisque suscipit massa, et pellentesque tortor cursus sed...'}
								</p>
							</div>

							<div className="text-right">
								<div className="text-xl font-semibold text-foreground mb-2 truncate">
									{productPrice.finalPrice}
								</div>
							</div>
						</div>

						<div className="flex items-center justify-between">
							<div className="space-y-1 text-sm">
								<div>SKU: {productPrice.sku}</div>

								{productPrice.gtin && (
									<div>GTIN: {productPrice.gtin}</div>
								)}

								{productPrice.mfrPartNumber && (
									<div>
										Mfr. Part Number:{' '}
										{productPrice.mfrPartNumber}
									</div>
								)}
							</div>
						</div>
					</div>
				</div>
			</Link>
		</Card>
	);
}
