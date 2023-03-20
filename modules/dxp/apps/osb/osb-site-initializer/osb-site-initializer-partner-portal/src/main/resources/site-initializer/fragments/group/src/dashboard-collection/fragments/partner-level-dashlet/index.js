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
import React, {useEffect, useState} from 'react';

import Container from '../../common/components/container';
import AuthorizedPartnerIcon from '../../common/components/icons/AuthorizedPartnerIcon';
import GoldPartnerIcon from '../../common/components/icons/GoldPartnerIcon';
import PlatinumPartnerIcon from '../../common/components/icons/PlatinumPartnerIcon';
import SilverPartnerIcon from '../../common/components/icons/SilverPartnerIcon';
import LevelProgressBar from '../../common/components/level-progress-bar';
import {ChartTypes} from '../../common/enums/chartTypes';
import {PartnershipLevels} from '../../common/enums/partnershipLevels';
import {partnerLevelData} from '../../common/mock/mock';
import ClayIconProvider from '../../common/utils/ClayIconProvider';

const PartnerIcon = ({level}) => {
	if (level === PartnershipLevels.SILVER) {
		return <SilverPartnerIcon />;
	}
	else if (level === PartnershipLevels.GOLD) {
		return <GoldPartnerIcon />;
	}
	else if (level === PartnershipLevels.PLATINUM) {
		return <PlatinumPartnerIcon />;
	}

	return <AuthorizedPartnerIcon />;
};

const CheckBoxItem = ({children, completed, text, title}) => {
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
				<ClayIconProvider>
					<CheckIcon />
				</ClayIconProvider>

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

const levelProperties = {
	Gold: {
		arr: {
			arr: 125,
			npOrNb: 2,
		},
		headcount: {
			marketing: 1,
			sales: 3,
		},
	},
	Platinum: {
		arr: {
			arr: 250,
		},
		headcount: {
			marketing: 1,
			sales: 5,
		},
	},
	Silver: {
		headcount: {
			marketing: 1,
			sales: 1,
		},
	},
};

export default function () {
	const [data] = useState(partnerLevelData);
	const [completed, setCompleted] = useState({});
	const level = PartnershipLevels.PLATINUM;

	useEffect(() => {
		const checkedItems = {};

		if (level !== PartnershipLevels.AUTHORIZED) {
			if (data.certification) {
				checkedItems['certification'] = true;
			}

			if (
				data.headcount.marketing ===
					levelProperties[level].headcount.marketing &&
				data.headcount.sales === levelProperties[level].headcount.sales
			) {
				checkedItems['headcount'] = true;
			}

			if (level !== PartnershipLevels.SILVER) {
				if (data.marketing) {
					checkedItems['marketing'] = true;
				}

				if (data.arr.arr === levelProperties[level].arr.arr) {
					checkedItems['arr'] = true;
				}

				if (
					level === PartnershipLevels.GOLD &&
					data.arr.npOrNb === levelProperties[level].arr.npOrNb
				) {
					checkedItems['arr'] = true;
				}
			}
		}

		setCompleted(checkedItems);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const getHeadcount = () => {
		return `${levelProperties[level].headcount.marketing} Marketing / ${levelProperties[level].headcount.sales} Sales`;
	};

	return (
		<Container title="Partnership Level">
			<h3 className="d-flex mb-5">
				<PartnerIcon level={level} />

				<span
					className={classNames('ml-2', {
						'text-brand-secondary-darken-2':
							level === PartnershipLevels.GOLD,
						'text-info': level === PartnershipLevels.AUTHORIZED,
						'text-neutral-7': level === PartnershipLevels.SILVER,
						'text-neutral-10': level === PartnershipLevels.PLATINUM,
					})}
				>
					{level}
				</span>

				<span className="font-weight-lighter">&nbsp;Partner</span>
			</h3>

			{level !== PartnershipLevels.AUTHORIZED && (
				<>
					{level !== PartnershipLevels.SILVER && (
						<>
							<CheckBoxItem completed={completed.arr} title="ARR">
								<LevelProgressBar
									currentValue={data.arr.arr}
									total={levelProperties[level].arr.arr}
									type={ChartTypes.ARR}
								/>

								{level === PartnershipLevels.GOLD && (
									<>
										<div className="font-weight-bold text-center text-neutral-5 text-paragraph-sm">
											or
										</div>

										<LevelProgressBar
											currentValue={data.arr.npOrNb}
											total={
												levelProperties[level].arr
													.npOrNb
											}
											type={ChartTypes.NP_OR_NB}
										/>
									</>
								)}
							</CheckBoxItem>
						</>
					)}

					<CheckBoxItem
						completed={completed.headcount}
						text={getHeadcount()}
						title="Headcount"
					/>

					{level !== PartnershipLevels.SILVER && (
						<CheckBoxItem
							completed={completed.marketing}
							text={data.marketing}
							title="Marketing"
						/>
					)}

					<CheckBoxItem
						completed={completed.certification}
						title={data.certification}
					/>
				</>
			)}
		</Container>
	);
}
