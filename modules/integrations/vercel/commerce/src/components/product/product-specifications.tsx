/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Fragment} from 'react';

import {Product} from '../../types/product';

function getKeys(product: Product) {
	try {
		const productSpecificationGroups = Object.groupBy(
			product.productSpecifications ?? [],
			(productSpecification) =>
				String(productSpecification.specificationGroupTitle ?? '')
		);

		return productSpecificationGroups;
	}
	catch {
		return {};
	}
}

export default function ProductSpecifications({product}: {product: Product}) {
	const productSpecificationGroups = getKeys(product);

	return (
		<div className="border border-gray-200 overflow-x-auto rounded-lg">
			<table className="border-collapse text-left text-sm w-full">
				<tbody className="divide-gray-200 divide-y">
					{Object.entries(productSpecificationGroups).map(
						([key, productSpecifications], index) => (
							<Fragment key={index}>
								<tr className="bg-gray-50">
									<td
										className="font-semibold px-4 py-2 text-gray-500 tracking-wide uppercase"
										colSpan={2}
									>
										{key}
									</td>
								</tr>

								{productSpecifications?.map(
									(specification, index) => (
										<tr key={index}>
											<td
												className="font-bold px-4 py-2 text-black"
												width={250}
											>
												{
													specification.specificationTitle
												}
											</td>

											<td className="px-4 py-4">
												{specification.value}
											</td>
										</tr>
									)
								)}
							</Fragment>
						)
					)}
				</tbody>
			</table>
		</div>
	);
}
