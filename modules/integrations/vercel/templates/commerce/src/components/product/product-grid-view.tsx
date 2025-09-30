import {Product} from 'liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0';
import {Card} from '@/components/ui/card';
import {Badge} from '@/components/ui/badge';
import Image from 'next/image';
import {getSkuDetails, handleImageError} from '@/lib/product';
import Link from 'next/link';

type ProductGridViewProps = {
	product: Product;
};

export default function ProductGridView({product}: ProductGridViewProps) {
	const productPrice = getSkuDetails(product);

	return (
		<Card className="group relative overflow-hidden cursor-pointer transition-all duration-300 hover:shadow-lg bg-product-card border-product-border bg-white">
			<Link href={`/product/${product.urls?.en_US}`}>
				<div className="relative">
					<Image
						alt={product.name as string}
						className="w-full h-48 object-cover transition-transform duration-300 group-hover:scale-105"
						height={48}
						quality={100}
						src={product.urlImage as string}
						unoptimized
						width={48}
						onError={handleImageError}
					/>

					{productPrice.available && (
						<Badge className="absolute top-3 left-3 bg-success bg-slate-500 text-white">
							AVAILABLE
						</Badge>
					)}
				</div>

				<div className="p-4 space-y-3">
					<div className="text-sm">
						{product.externalReferenceCode}
					</div>

					<h3 className="font-medium text-foreground group-hover:text-primary transition-colors truncate">
						{product.name}
					</h3>

					<div className="flex items-center gap-2">
						{productPrice.discount && (
							<span className="text-sm line-through">
								{productPrice.originalPrice}
							</span>
						)}

						<span className="font-semibold text-lg">
							{productPrice.finalPrice}
						</span>
						{productPrice.discount && (
							<Badge variant="destructive" className="text-xs">
								-{productPrice.discountPercent}%
							</Badge>
						)}
					</div>
				</div>
			</Link>
		</Card>
	);
}
