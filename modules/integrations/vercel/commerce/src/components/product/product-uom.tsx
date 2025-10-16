/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Card} from '../ui/card';

export default function ProductUOM({
	uom,
}: {
	uom: {
		incrementalOrderQuantity: number;
		key: string;
		name: string;
		price: {priceFormatted: string};
	}[];
}) {
	return (
		<Card className="p-4">
			<h3 className="font-semibold mb-3">UOM</h3>

			<div className="space-y-2">
				<div className="font-medium gap-4 grid grid-cols-4 text-sm">
					<span>Unit</span>

					<span>Key</span>

					<span>Quantity</span>

					<span>Net Price</span>
				</div>

				{uom.map((item, index) => (
					<div
						className="border-b border-border gap-4 grid grid-cols-4 pb-2 text-sm"
						key={index}
					>
						<span>{item.name}</span>

						<span>{item.key}</span>

						<span>{item.incrementalOrderQuantity}</span>

						<span className="font-bold text-slate-900">
							{item.price?.priceFormatted}
						</span>
					</div>
				))}
			</div>
		</Card>
	);
}
