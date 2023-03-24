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
import PartenerIcon from '../../common/components/icons/partenerIcon';
import {level} from '../../common/mock/mock';
import {naNToZero} from '../../common/utils';

import './index.css';

const label = {
	sales: "Sales Revenue QSP's",
	suplemental: "Supplemental QSP's",
};

const LevelProgressBar = ({
	items,
	total,
	taskbarClassNames = {
		achieved: 'achieved',
		remaining: 'remaining ',
		supplemental: 'supplemental',
	},
}) => (
	<div className="progress-bar-border">
		<div className="level-progress-bar">
			{items.map(([label, value], index) => {
				const percent = naNToZero(value.qtd / total) * 100;

				return (
					<div
						className={classNames(
							'progress-bar-item',
							taskbarClassNames[label],
							{
								achievedItem: index === 0,
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
);

const Legend = () => (
	<div className="d-flex level-progress-bar">
		<div className="d-flex flex-row">
			<div className="align-items-center d-flex">
				<div className="achieved legend-bar-item" />
			</div>

			<span className="legend-item-label ml-1 mr-2 mt-1 text-neutral-6">
				{label.sales}
			</span>
		</div>

		<div className="d-flex flex-row">
			<div className="align-items-center d-flex">
				<div className="legend-bar-item supplemental" />
			</div>

			<span className="legend-item-label ml-1 mr-2 mt-1 text-neutral-6">
				{label.suplemental}
			</span>
		</div>
	</div>
);

export default function () {
	return (
		<Container
			className="level-chart-card-height"
			footer={
				<ClayButton className="mr-1 mt-2" displayType="primary">
					Request level change
				</ClayButton>
			}
			title="Level"
		>
			<div className="flex-row mb-4">
				<div>
					<span className="label-gold mt-1">
						<PartenerIcon />
						Gold
					</span>

					<span className="font-weight-lighter h3 ml-2 mt-1">
						Partner
					</span>
				</div>
			</div>

			<div>
				<div className="mb-4">
					<div className="d-flex flex-row justify-content-between">
						<div className="font-weight-bold h5">New Business</div>

						<div>
							<span className="font-weight-bold text-neutral-9">
								350
							</span>

							<span className="text-neutral-5"> / 450 </span>
						</div>
					</div>

					<LevelProgressBar
						items={level.progressNewMoc}
						total={level.totalLevel.newBusiness}
					/>

					<div className="d-flex flex-row label-progress text-neutral-7">
						<span className="font-weight-bold mr-1 number">
							100
						</span>
						more points for Platinum
					</div>
				</div>

				<div className="mb-2">
					<div className="d-flex flex-row justify-content-between">
						<div className="font-weight-bold h5">
							Total Business
						</div>

						<div>
							<span className="font-weight-bold">420</span>

							<span className="text-neutral-5"> / 600 </span>
						</div>
					</div>

					<LevelProgressBar
						items={level.progressTotalMoc}
						total={level.totalLevel.business}
					/>

					<div className="d-flex flex-row label-progress text-neutral-7">
						<span className="font-weight-bold mr-1 number">
							180
						</span>

						<span className="font-family-source-sans-pro text-neutral-7">
							more points for Platinum
						</span>
					</div>
				</div>
			</div>

			<div className="mt-4">
				<Legend />
			</div>
		</Container>
	);
}
