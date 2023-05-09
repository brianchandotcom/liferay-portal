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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import {ChartTypes} from '../../enums/chartTypes';
import {PartnershipLevels} from '../../enums/partnershipLevels';
import {partnerLevelProperties} from '../../mock';
import LevelProgressBar from '../LevelProgressBar';
import AuthorizedPartnerIcon from '../icons/AuthorizedPartnerIcon';
import GoldPartnerIcon from '../icons/GoldPartnerIcon';
import PlatinumPartnerIcon from '../icons/PlatinumPartnerIcon';
import SilverPartnerIcon from '../icons/SilverPartnerIcon';

export interface AccountData {
	aRRAmount: number;
	growthARR: number;
	marketingPerformance: number;
	marketingPlan?: boolean;
	newProjectExistingBusiness: number;
	partnerLevel?: string;
	renewalARR: number;
	solutionDeliveryCertification: boolean;
}

interface IPropsPartnerIcon {
	level: any;
}

interface IPropsCheckBoxItem {
	children?: any;
	completed: any;
	text?: any;
	title?: any;
}

interface IPropsPartnershipLevel {
	completed: any;
	data: AccountData;
	headcount: any;
}

const PartnerIcon = ({level}: IPropsPartnerIcon) => {
	if (level === PartnershipLevels.SILVER) {
		return <SilverPartnerIcon />;
	}

	if (level === PartnershipLevels.GOLD) {
		return <GoldPartnerIcon />;
	}

	if (level === PartnershipLevels.PLATINUM) {
		return <PlatinumPartnerIcon />;
	}

	return <AuthorizedPartnerIcon />;
};

const CheckBoxItem = ({
	children,
	completed,
	text,
	title,
}: IPropsCheckBoxItem) => {
	const CheckIcon = () => {
		if (completed) {
			return (
				<ClayIcon
					className="m-0 text-brand-primary"
					symbol="check-circle"
				/>
			);
		}

		return <ClayIcon className="m-0 text-danger" symbol="times-circle" />;
	};

	return (
		<div className="d-flex mb-4">
			<div
				className={classNames('d-flex p-0 align-items-center', {
					'col': !children,
					'col-3': children,
				})}
			>
				<CheckIcon />

				<span
					className={classNames(
						'font-weight-bold text-paragraph-sm',
						{
							'col': !text,
							'col-3': text,
						}
					)}
				>
					{title}
				</span>

				{text && <span className="col text-paragraph">{text}</span>}
			</div>

			{children && <div className="col">{children}</div>}
		</div>
	);
};

const PartnershipLevel = ({
	completed,
	data,
	headcount,
}: IPropsPartnershipLevel) => {
	const getTotalARR = () => {
		if (data.partnerLevel === PartnershipLevels.GOLD) {
			return partnerLevelProperties[data.partnerLevel].growthARR;
		}

		return data.growthARR + data.renewalARR;
	};

	const getHeadcount = () => {
		if (data.partnerLevel) {
			return `${headcount.partnerMarketingUser}/${
				partnerLevelProperties[data.partnerLevel].partnerMarketingUser
			}
             Marketing / ${headcount.partnerSalesUser}/${
				partnerLevelProperties[data.partnerLevel].partnerSalesUser
			} Sales`;
		}

		return '';
	};

	const capitalizeTitle = (title: any) => {
		switch (title) {
			case PartnershipLevels.SILVER:
				return 'Silver';
			case PartnershipLevels.GOLD:
				return 'Gold';
			case PartnershipLevels.PLATINUM:
				return 'Platinum';
			default:
				return 'Authorized';
		}
	};

	return (
		<div>
			<h3
				className={classNames('d-flex', {
					'mb-4': data.partnerLevel === PartnershipLevels.AUTHORIZED,
					'mb-5': data.partnerLevel !== PartnershipLevels.AUTHORIZED,
				})}
			>
				<PartnerIcon level={data.partnerLevel} />

				<span
					className={classNames('ml-2 mr-1', {
						'text-brand-secondary-darken-2':
							data.partnerLevel === PartnershipLevels.GOLD,
						'text-info':
							data.partnerLevel === PartnershipLevels.AUTHORIZED,
						'text-neutral-7':
							data.partnerLevel === PartnershipLevels.SILVER,
						'text-neutral-10':
							data.partnerLevel === PartnershipLevels.PLATINUM,
					})}
				>
					{capitalizeTitle(data.partnerLevel)}
				</span>

				<span className="font-weight-lighter">Partner</span>
			</h3>

			{data.partnerLevel !== PartnershipLevels.AUTHORIZED && (
				<div>
					{data.partnerLevel !== PartnershipLevels.SILVER && (
						<CheckBoxItem completed={completed.arr} title="ARR">
							<LevelProgressBar
								currentValue={data.aRRAmount}
								total={getTotalARR()}
								type={ChartTypes.ARR}
							/>

							{data.partnerLevel === PartnershipLevels.GOLD && (
								<>
									<div className="font-weight-bold text-center text-neutral-5 text-paragraph-sm">
										or
									</div>

									<LevelProgressBar
										currentValue={
											data.newProjectExistingBusiness
										}
										total={
											partnerLevelProperties[
												data.partnerLevel
											].newProjectExistingBusiness
										}
										type={ChartTypes.NP_OR_NB}
									/>
								</>
							)}
						</CheckBoxItem>
					)}

					<CheckBoxItem
						completed={completed.headcount}
						text={getHeadcount()}
						title="Headcount"
					/>

					<CheckBoxItem
						completed={completed.marketingPlan}
						title="Marketing Plan"
					/>

					<CheckBoxItem
						completed={completed.marketingPerformance}
						text={`${data.marketingPerformance} Leads`}
						title="Marketing Performance"
					/>

					<CheckBoxItem
						completed={completed.solutionDeliveryCertification}
						title="Solution Delivery Certification"
					/>
				</div>
			)}
		</div>
	);
};

export default PartnershipLevel;
