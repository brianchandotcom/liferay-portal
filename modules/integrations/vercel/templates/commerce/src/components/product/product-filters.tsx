'use client';

import {useState} from 'react';
import {ChevronDown, ChevronUp} from 'lucide-react';

import {Card} from '@/components/ui/card';
import {Checkbox} from '@/components/ui/checkbox';
import {Button} from '@/components/ui/button';
import {Label} from '@/components/ui/label';
import {useRouter, useSearchParams} from 'next/navigation';

const baseFilters = {
	material: {
		key: 'specificationValues',
		options: [
			'Aluminum',
			'Cast Iron',
			'Ceramic',
			'Neoprene',
			'Plastic',
			'Rubber',
			'Steel',
		],
		title: 'Material',
	},

	warranty: {
		key: 'specificationValues',
		title: 'Warranty',
		options: [
			{
				value: '1',
				label: '1 Year Unlimited Mileage Warranty',
			},
			{
				value: '3',
				label: '3 Year Unlimited Mileage Warranty',
			},
			'Limited Lifetime',
		],
	},
} as const;

type FilterCategory = (typeof baseFilters)[keyof typeof baseFilters];

export const ProductFilters = () => {
	const searchParams = useSearchParams();
	const router = useRouter();
	const specificationValues = searchParams.getAll('specificationValues');

	const [expandedSections, setExpandedSections] = useState<
		Record<string, boolean>
	>({
		category: true,
		priceRange: true,
		lastModified: true,
	});

	const toggleSection = (section: string) => {
		setExpandedSections((prev) => ({
			...prev,
			[section]: !(prev[section] ?? true),
		}));
	};

	const handleCategoryChange = (
		checked: boolean,
		option: FilterCategory['options'][number]
	) => {
		const _searchParams = new URLSearchParams(searchParams);

		const value = typeof option === 'object' ? option.value : option;

		if (!checked && _searchParams.has('specificationValues', value)) {
			_searchParams.delete('specificationValues', value);
		}
		else {
			_searchParams.append('specificationValues', value);
		}

		router.replace(`./?${_searchParams.toString()}`);
	};

	const clearAllFilters = (filterCategory: FilterCategory) => {
		const _searchParams = new URLSearchParams(searchParams);

		for (const option of filterCategory.options) {
			const value = typeof option === 'object' ? option.value : option;

			if (_searchParams.has('specificationValues', value)) {
				_searchParams.delete('specificationValues', value);
			}
		}

		router.replace(`/?${_searchParams.toString()}`);
	};

	const selectAllFilters = (filterCategory: FilterCategory) => {
		const _searchParams = new URLSearchParams(searchParams);

		for (const option of filterCategory.options) {
			const value = typeof option === 'object' ? option.value : option;

			if (!_searchParams.has('specificationValues', value)) {
				_searchParams.append('specificationValues', value);
			}
		}

		router.replace(`/?${_searchParams.toString()}`);
	};

	return (
		<div className="space-y-4">
			{Object.entries(baseFilters).map(([key, filterCategory], index) => {
				const expanded = expandedSections[key] ?? true;

				const ToggleButton = expanded ? ChevronUp : ChevronDown;

				return (
					<Card key={index}>
						<div className="p-4">
							<div className="flex items-center justify-between w-full text-left">
								<div>
									<h3 className="font-semibold">
										{filterCategory.title}
									</h3>

									<div className="flex gap-2 mt-1">
										<Button
											variant="outline"
											size="sm"
											onClick={() =>
												selectAllFilters(filterCategory)
											}
										>
											Select All
										</Button>

										<Button
											variant="outline"
											size="sm"
											onClick={() =>
												clearAllFilters(filterCategory)
											}
										>
											Clear
										</Button>
									</div>
								</div>

								<ToggleButton
									onClick={() => toggleSection(key)}
									className="h-4 w-4 cursor-pointer"
								/>
							</div>

							{expanded && (
								<div className="mt-4 space-y-3">
									{filterCategory.options.map(
										(option, optionIndex) => {
											const optionValue =
												typeof option === 'object'
													? option.value
													: option;

											const optionLabel =
												typeof option === 'object'
													? option.label
													: option;

											return (
												<div
													key={optionIndex}
													className="flex items-center space-x-2"
												>
													<Checkbox
														id={optionValue}
														checked={specificationValues.includes(
															optionValue
														)}
														onCheckedChange={(
															checked
														) =>
															handleCategoryChange(
																checked as boolean,
																option
															)
														}
													/>
													<Label
														htmlFor={optionValue}
														className="text-sm font-normal cursor-pointer"
													>
														{optionLabel}
													</Label>
												</div>
											);
										}
									)}
								</div>
							)}
						</div>
					</Card>
				);
			})}
		</div>
	);
};
