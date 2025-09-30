import {Skeleton} from '@/components/ui/skeleton';

export default function Loading() {
	return (
		<div>
			<div className="container mx-auto px-4 py-6">
				<div className="flex flex-col lg:flex-row gap-10">

					{/* Left: Product Gallery */}
					<div className="flex-2 space-y-4">
						<Skeleton className="h-[400px] w-full rounded-md" />{' '}

						{/* Main image */}
						<div className="flex gap-2">
							{[...Array(4)].map((_, i) => (
								<Skeleton
									key={i}
									className="h-16 w-16 rounded-md"
								/>
							))}
						</div>
					</div>

					{/* Right: Product Info */}
					<div className="flex-2 space-y-6">
						<div className="space-y-3">
							<Skeleton className="h-8 w-64" /> {/* Title */}
							<Skeleton className="h-5 w-40" />{' '}

							{/* Estimate / stock info */}
							<Skeleton className="h-5 w-32" /> {/* SKU */}
							<Skeleton className="h-5 w-28" /> {/* MPN */}
							<Skeleton className="h-20 w-full" />{' '}

							{/* Description */}
						</div>

						{/* Options */}
						<div className="space-y-4">
							{[...Array(3)].map((_, i) => (
								<div key={i} className="space-y-2">
									<Skeleton className="h-5 w-32" />{' '}

									{/* Option title */}
									<Skeleton className="h-10 w-full rounded-md" />{' '}

									{/* Select */}
								</div>
							))}
						</div>
					</div>

					{/* Right Sidebar: Purchase Box */}
					<div className="w-full lg:w-80 space-y-6">
						<Skeleton className="h-6 w-32" />{' '}

						{/* Final Price label */}
						<Skeleton className="h-10 w-28" /> {/* Price */}
						<div className="space-y-4">
							<div>
								<Skeleton className="h-5 w-20 mb-2" />{' '}

								{/* Quantity label */}
								<Skeleton className="h-10 w-full rounded-md" />{' '}

								{/* Quantity input */}
							</div>
							<div>
								<Skeleton className="h-5 w-28 mb-2" />{' '}

								{/* UOM label */}
								<Skeleton className="h-10 w-full rounded-md" />{' '}

								{/* Select */}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}
