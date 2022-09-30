/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import React from 'react';

import Container from '../../common/components/container';
import {mdf} from '../../common/mock/mock';
import {naNToZero} from '../../common/utils';

const MDFbarProgress = ({
	items,
	MdfbarClassNames = {
		approved: 'approved',
		pending: 'pending',
	},
}) => {
	const total = items.reduce(
		(prevValue, currentValue) => prevValue + currentValue[1].qtd,
		0
	);

	return (
		<>
			<div className="progress-bar-border">
				<div className="mdf-progress-bar">
					{items.map((item, index) => {
						const [label, value] = item;

						const percent = naNToZero((value.qtd / total) * 100);

						return (
							<div
								className={classNames(
									'progress-bar-item',
									MdfbarClassNames[label],
									{
										approvedItem: index === 0,
									}
								)}
								key={index}
								style={{width: `${percent}%`}}
								title={`${value.qtd} ${label}`}
							/>
						);
					})}
				</div>
			</div>

			<div className="d-flex mdf-progress-bar">
				{items.map((item, index) => {
					const [label, value] = item;
					const percent = naNToZero((value.qtd / total) * 100);

					return (
						<div
							className="d-flex flex-row"
							key={index}
							style={{width: `${percent}%`}}
						>
							<div className="align-items-center">
								<div className="d-flex flex-column">
									<span
										className="font-family-sans-serif mx-1"
										title={value.qtd}
									>
										{value.qtd} {label}
									</span>

									<span className="font-family-sans-serif mx-1">
										{value.total}
									</span>
								</div>
							</div>
						</div>
					);
				})}
			</div>
		</>
	);
};

const LegendMDF = () => {
	const label = {
		aproved: 'Approved',
		requested: 'Requested',
	};

	return (
		<>
			<div className="d-flex mdf-progress-bar">
				<div className="d-flex flex-row">
					<div className="align-items-center d-flex">
						<div className="approved legend-bar-item" />
					</div>

					<span className="legend-item-label ml-1 mr-2 mt-1 text-neutral-6">
						{label.aproved}
					</span>
				</div>

				<div className="d-flex flex-row">
					<div className="align-items-center d-flex">
						<div className="legend-bar-item requested" />
					</div>

					<span className="legend-item-label ml-1 mr-2 mt-1 text-neutral-6">
						{label.requested}
					</span>
				</div>
			</div>
		</>
	);
};

export default function () {
	const sortedItems = Object.entries(mdf.ProgressMdf);

	const sortedItemsClain = Object.entries(mdf.ProgressClain);

	const totalRequest =
		mdf.ProgressMdf.approved.qtd + mdf.ProgressMdf.pending.qtd;

	const totalClain =
		mdf.ProgressClain.approved.qtd + mdf.ProgressClain.pending.qtd;

	return (
		<Container
			className="mdf-chart-card-height"
			footer={
				<>
					<ClayButton className="mr-4 mt-2" displayType="primary">
						New MDF Request
					</ClayButton>
					<ClayButton
						className="border-brand-primary-darken-1 mt-2 text-brand-primary-darken-1"
						displayType="secondary"
					>
						View all
					</ClayButton>
				</>
			}
			title="MDF Requests"
		>
			<div className="d-flex flex-column flex-content-between h-100">
				<div className="d-flex flex-column h-100 mb-5">
					<div className="d-flex flex-row justify-content-between">
						<div className="font-weight-bold h5">Request Funds</div>

						<div>
							<span className="font-weight-bold text-neutral-9">
								{totalRequest} &nbsp;
							</span>
							total requests &nbsp; | &nbsp;
							<span className="font-weight-bold text-neutral-9">
								USD $92.993,29
							</span>
						</div>
					</div>

					<MDFbarProgress items={sortedItems} />
				</div>

				<div className="d-flex flex-column h-100 mb-3">
					<div className="d-flex flex-row justify-content-between">
						<div className="font-weight-bold h5">Clain Funds</div>

						<div>
							<span className="font-weight-bold text-neutral-9">
								{totalClain} &nbsp;
							</span>
							total &nbsp;| &nbsp;
							<span className="font-weight-bold text-neutral-9">
								USD $12.000,50
							</span>
						</div>
					</div>

					<MDFbarProgress items={sortedItemsClain} />
				</div>

				<div className="d-flex flex-column h-100 mt-5">
					<LegendMDF />
				</div>
			</div>
		</Container>
	);
}
