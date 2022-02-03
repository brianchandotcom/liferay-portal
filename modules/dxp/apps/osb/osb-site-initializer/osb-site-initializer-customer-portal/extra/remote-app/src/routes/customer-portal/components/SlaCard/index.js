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
import {useEffect, useState} from 'react';
import {useCustomerPortal} from '../../context';
import SlaCardLayout from './Inputs';

const SlaCard = () => {
	const [slaData, setSlaData] = useState();
	const [slaSelected, setSlaSelected] = useState('');
	const [{project}] = useCustomerPortal();

	useEffect(() => {
		if (project) {
			const formatDate = {
				day: '2-digit',
				month: '2-digit',
				year: 'numeric',
			};
			const slaFiltedData = [];
			const SLA_NAMES = ['Current', 'Expired', 'Future'];

			const slaPathLabel = {
				current: SLA_NAMES[0].toUpperCase(),
				expired: SLA_NAMES[1].toUpperCase(),
				future: SLA_NAMES[2].toUpperCase(),
			};

			const slaNewDate = (slaDate) => {
				return new Date(project[slaDate])?.toLocaleDateString(
					'en-US',
					formatDate
				);
			};
			const slaRawData = SLA_NAMES.map((slaName) => {
				const slaFullTitle = 'sla' + slaName;
				if (project[slaFullTitle]) {
					return {
						slaDateEnd: `${slaNewDate(slaFullTitle + 'EndDate')}`,
						slaDateStart: `${slaNewDate(
							slaFullTitle + 'StartDate'
						)}`,
						slaLabel: slaName?.toUpperCase(),
						slaTitle: project[slaFullTitle]?.split(' ')[0],
					};
				}
			}).filter((sla) => sla);

			const slaRaw = {
				firstSlaRaw: slaRawData[0],
				secondSlaRaw: slaRawData[1],
				thirdSlaRaw: slaRawData[2],
			};
			if (
				slaRaw.firstSlaRaw?.slaLabel === slaPathLabel.current &&
				(slaRaw.secondSlaRaw?.slaTitle ||
					slaRaw.thirdSlaRaw?.slaTitle) ===
					slaRaw.firstSlaRaw?.slaTitle
			) {
				slaFiltedData.push(slaRaw.firstSlaRaw);

				if (
					slaFiltedData[0].slaTitle ===
						slaRaw.secondSlaRaw.slaTitle &&
					slaRaw.secondSlaRaw.slaLabel === slaPathLabel.expired
				) {
					slaFiltedData[0].slaDateStart =
						slaRaw.secondSlaRaw.slaDateStart;
				}
				if (
					slaFiltedData[0].slaTitle ===
						slaRaw.secondSlaRaw.slaTitle &&
					slaRaw.secondSlaRaw.slaLabel === slaPathLabel.future
				) {
					slaFiltedData[0].slaDateEnd =
						slaRaw.secondSlaRaw.slaDateEnd;
				}
				if (slaRaw.thirdSlaRaw) {
					if (
						slaRaw.thirdSlaRaw.slaTitle ===
						slaFiltedData[0].slaTitle
					) {
						slaFiltedData[0].slaDateEnd =
							slaRaw.thirdSlaRaw.slaDateEnd;
					} else {
						slaFiltedData.push(slaRaw.thirdSlaRaw);
					}
				}
			} else {
				slaRawData.map((sla) => slaFiltedData.push(sla));
			}
			if (slaFiltedData.length > 0) {
				setSlaData(slaFiltedData);
			}

			if (!slaSelected) {
				const slaSelectedCard = slaFiltedData[0]?.slaLabel;
				setSlaSelected(slaSelectedCard);
			}
		}
	}, [project, slaSelected]);

	const handleSlaCardClick = () => {
		if (slaSelected === slaData[slaData.length - 1]?.slaLabel) {
			setSlaSelected(slaData[0].slaLabel);
		} else if (slaSelected === slaData[0]?.slaLabel) {
			setSlaSelected(slaData[1].slaLabel);
		} else {
			setSlaSelected(slaData[2].slaLabel);
		}
	};

	return (
		<div className="position-absolute sla-container">
			<h5 className="mb-4">Support Level</h5>

			{slaData ? (
				<div>
					<div
						className={classNames({
							'ml-2': slaData[1],
						})}
					>
						<div
							className={classNames(
								'align-items-center d-flex sla-card-holder',
								{
									'sla-multiple-card ml-2': slaData[1],
								}
							)}
						>
							{slaData &&
								slaData.map((sla) => (
									<SlaCardLayout
										key={sla.slaTitle}
										slaDateEnd={sla.slaDateEnd}
										slaDateStart={sla.slaDateStart}
										slaLabel={sla.slaLabel}
										slaSelected={slaSelected}
										slaTitle={sla.slaTitle}
									/>
								))}
						</div>
					</div>

					{slaData[1] && (
						<div
							className="btn btn-outline-primary d-none hide ml-3 position-relative rounded-circle"
							onClick={handleSlaCardClick}
						>
							<ClayIcon symbol="angle-right" />
						</div>
					)}
				</div>
			) : (
				<div className="bg-neutral-1 n-sla-card rounded-lg">
					<p className="p-3 text-neutral-7 text-paragraph-sm">
						The project&apos;s Support Level is displayed here for
						projects with ticketing support.
					</p>
				</div>
			)}
		</div>
	);
};

export default SlaCard;
