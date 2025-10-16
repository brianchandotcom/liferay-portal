/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

'use client';

import {ChevronDown, ChevronUp} from 'lucide-react';
import {useRouter, useSearchParams} from 'next/navigation';
import {useState} from 'react';

import {Button} from '../ui/button';
import {Card} from '../ui/card';
import {Checkbox} from '../ui/checkbox';
import {Label} from '../ui/label';

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
		options: [
			{
				label: '1 Year Unlimited Mileage Warranty',
				value: '1',
			},
			{
				label: '3 Year Unlimited Mileage Warranty',
				value: '3',
			},
			'Limited Lifetime',
		],
		title: 'Warranty',
	},
} as const;

type FilterCategory = (typeof baseFilters)[keyof typeof baseFilters];

export function ProductFilters() {
	const router = useRouter();
	const searchParams = useSearchParams();
	const specificationValues = searchParams.getAll('specificationValues');

	const [expandedSections, setExpandedSections] = useState<
		Record<string, boolean>
	>({
		category: true,
		lastModified: true,
		priceRange: true,
	});

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

	const toggleSection = (section: string) => {
		setExpandedSections((prev) => ({
			...prev,
			[section]: !(prev[section] ?? true),
		}));
	};

	return (
		<div className="space-y-4">
			{Object.entries(baseFilters).map(([key, filterCategory], index) => {
				const expanded = expandedSections[key] ?? true;

				const ToggleButton = expanded ? ChevronUp : ChevronDown;

				return (
					<Card key={index}>
						<div className="p-4">
							<div className="flex items-center justify-between text-left w-full">
								<div>
									<h3 className="font-semibold">
										{filterCategory.title}
									</h3>

									<div className="flex gap-2 mt-1">
										<Button
											onClick={() =>
												selectAllFilters(filterCategory)
											}
											size="sm"
											variant="outline"
										>
											Select All
										</Button>

										<Button
											onClick={() =>
												clearAllFilters(filterCategory)
											}
											size="sm"
											variant="outline"
										>
											Clear
										</Button>
									</div>
								</div>

								<ToggleButton
									className="cursor-pointer h-4 w-4"
									onClick={() => toggleSection(key)}
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
													className="flex items-center space-x-2"
													key={optionIndex}
												>
													<Checkbox
														checked={specificationValues.includes(
															optionValue
														)}
														id={optionValue}
														onCheckedChange={(
															checked: boolean
														) =>
															handleCategoryChange(
																checked,
																option
															)
														}
													/>

													<Label
														className="cursor-pointer font-normal text-sm"
														htmlFor={optionValue}
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
}
