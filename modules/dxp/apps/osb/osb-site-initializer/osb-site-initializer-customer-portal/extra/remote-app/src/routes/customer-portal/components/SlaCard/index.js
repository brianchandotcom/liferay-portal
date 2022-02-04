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
import {FORMAT_DATE} from '../../../../common/utils/constants/slaCardDate';
import {SLA_NAMES} from '../../../../common/utils/constants/slaCardNames';
import SlaCardLayout from './Layout';

const SlaCard = ({project}) => {
	const [slaData, setSlaData] = useState();
	const [slaSelected, setSlaSelected] = useState('');
	const SLA_DATA = {};

	useEffect(() => {
		if (project) {
			const slaFiltedData = [];
			const slaNewDate = (slaDate) => {
				return new Date(slaDate)?.toLocaleDateString(
					'en-US',
					FORMAT_DATE
				);
			};

			const slaRawData = {
				current: {
					slaDateEnd: slaNewDate(project.slaCurrentEndDate),
					slaDateStart: slaNewDate(project.slaCurrentStartDate),
					slaLabel: SLA_NAMES.current.toUpperCase(),
					slaTitle: project.slaCurrent?.split(' ')[0],
				},
				expired: {
					slaDateEnd: slaNewDate(project.slaExpiredEndDate),
					slaDateStart: slaNewDate(project.slaExpiredStartDate),
					slaLabel: SLA_NAMES.expired.toUpperCase(),
					slaTitle: project.slaExpired?.split(' ')[0],
				},
				future: {
					slaDateEnd: slaNewDate(project.slaFutureEndDate),
					slaDateStart: slaNewDate(project.slaFutureStartDate),
					slaLabel: SLA_NAMES.future.toUpperCase(),
					slaTitle: project.slaFuture?.split(' ')[0],
				},
			};

			if (
				slaRawData.current.slaTitle === slaRawData.expired.slaTitle &&
				slaRawData.current.slaTitle === slaRawData.future.slaTitle
			) {
				slaRawData.current.slaDateStart =
					slaRawData.expired.slaDateStart;
				slaRawData.current.slaDateEnd = slaRawData.future.slaDateEnd;
				slaFiltedData.push(slaRawData.current);
			} else if (
				slaRawData.current.slaTitle === slaRawData.expired.slaTitle
			) {
				slaRawData.current.slaDateStart =
					slaRawData.expired.slaDateStart;
				slaFiltedData.push(slaRawData.current);
				slaFiltedData.push(slaRawData.future);
			} else if (
				slaRawData.current.slaTitle === slaRawData.future.slaTitle
			) {
				slaRawData.current.slaDateEnd = slaRawData.future.slaDateEnd;
				slaFiltedData.push(slaRawData.current);
				slaFiltedData.push(slaRawData.expired);
			} else {
				slaFiltedData.push(slaRawData.current);
				slaFiltedData.push(slaRawData.expired);
				slaFiltedData.push(slaRawData.future);
			}

			setSlaData(slaFiltedData.filter((sla) => sla.slaTitle));

			// eslint-disable-next-line no-console
			console.log(slaFiltedData);

			if (!slaSelected) {
				const slaSelectedCard = slaFiltedData[0]?.slaLabel;
				setSlaSelected(slaSelectedCard);
			}
		}
	}, [project, slaSelected]);

	if (slaData) {
		SLA_DATA.firstData = slaData[0];
		SLA_DATA.secondData = slaData[1];
		SLA_DATA.thirdData = slaData[2];
	}

	const handleSlaCardClick = () => {
		if (slaSelected === slaData[slaData.length - 1]?.slaLabel) {
			setSlaSelected(SLA_DATA.firstData.slaLabel);
		} else if (slaSelected === SLA_DATA.firstData.slaLabel) {
			setSlaSelected(SLA_DATA.secondData.slaLabel);
		} else {
			setSlaSelected(SLA_DATA.thirdData.slaLabel);
		}
	};

	return (
		<div className="cp-sla-container position-absolute">
			<h5 className="mb-4">Support Level</h5>

			{slaData ? (
				<div>
					<div
						className={classNames({
							'ml-2': slaData.length > 1,
						})}
					>
						<div
							className={classNames(
								'align-items-center d-flex cp-sla-card-holder',
								{
									'cp-sla-multiple-card ml-2':
										slaData.length > 1,
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

					{slaData.length > 1 && (
						<div
							className="btn btn-outline-primary d-none hide ml-3 position-relative rounded-circle"
							onClick={handleSlaCardClick}
						>
							<ClayIcon symbol="angle-right" />
						</div>
					)}
				</div>
			) : (
				<div className="bg-neutral-1 cp-n-sla-card rounded-lg">
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
