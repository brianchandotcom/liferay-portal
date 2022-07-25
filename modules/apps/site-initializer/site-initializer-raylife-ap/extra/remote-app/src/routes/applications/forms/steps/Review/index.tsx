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

import ClayTable from '@clayui/table';
import React from 'react';

const Review = () => {
	const coverages = [
		{
			name: 'BODILY INJURY',
			premium: '$0.00 ',
			protectionAmount: '$0.00 / $0.00',
		},
		{
			name: 'PROPERTY DAMAGE',
			premium: '$0.00 ',
			protectionAmount: '$0.00 / $0.00',
		},
		{
			name: 'UNINSURED MOTORIST BODILY INJURY',
			premium: '$0.00 ',
			protectionAmount: '$0.00 / $0.00',
		},
		{
			name: 'UNINSURED MOTORIST PROPERTY DAMAGE',
			premium: '$0.00 ',
			protectionAmount: '$0.00 / $0.00',
		},
		{
			name: 'MEDICAL',
			premium: '$0.00 ',
			protectionAmount: '$0.00 / $0.00',
		},
		{
			name: 'VEHICLE 1: COMPREHENSIVE',
			premium: '$0.00 ',
			protectionAmount: '$0.00 / $0.00',
		},
		{
			name: 'VEHICLE 1: COLLISION',
			premium: '$0.00 ',
			protectionAmount: '$0.00 / $0.00',
		},
	];

	return (
		<div className="mx-8">
			<h5 className="text-center">Review Quote</h5>

			<ClayTable borderless hover={false}>
				<ClayTable.Head className="border-bottom">
					<ClayTable.Row className="no-gutters row text-uppercase">
						<ClayTable.Cell className="col-6">
							<span className="text-dark">
								<h6>Coverages</h6>
							</span>
						</ClayTable.Cell>

						<ClayTable.Cell className="col-4">
							<span className="text-dark">
								<h6>Protection Amount</h6>
							</span>
						</ClayTable.Cell>

						<ClayTable.Cell className="col">
							<span className="text-dark">
								<h6>Premium</h6>
							</span>
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body className="table-borderless">
					{coverages.map((coverage) => {
						return (
							<>
								<ClayTable.Row className="no-gutters row">
									<ClayTable.Cell
										className="col-6"
										headingTitle
									>
										<p className="text-secondary">
											{coverage.name}
										</p>
									</ClayTable.Cell>

									<ClayTable.Cell className="col-4">
										<p className="text-secondary">
											{coverage.protectionAmount}
										</p>
									</ClayTable.Cell>

									<ClayTable.Cell className="col">
										<p className="text-secondary">
											{coverage.premium}
										</p>
									</ClayTable.Cell>
								</ClayTable.Row>
							</>
						);
					})}
				</ClayTable.Body>

				<ClayTable.Head className="border-top">
					<ClayTable.Row className="no-gutters row">
						<ClayTable.Cell
							className="col-6 text-uppercase"
							headingCell
						>
							<span className="text-dark">
								<h6>Total Premium</h6>
							</span>
						</ClayTable.Cell>

						<ClayTable.Cell
							className="col-4"
							headingCell
						></ClayTable.Cell>

						<ClayTable.Cell className="col" headingCell>
							<span className="text-dark">
								<h6>$0.00</h6>
							</span>
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>
			</ClayTable>
		</div>
	);
};
export default Review;
