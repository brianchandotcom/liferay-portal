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

import classNames from 'classnames';
import {useEffect, useState} from 'react';
import client from '../../../../apolloClient';
import {getKoroneikiAccounts} from '../../../../common/services/liferay/graphql/queries';
import SlaCardLayout from './Inputs';

const SlaCard = ({accountKey}) => {
	const [slaData, setSlaData] = useState();
	const [slaSelected, setSlaSelected] = useState('');

	useEffect(() => {
		const fetchkoroneikeAccount = async () => {
			const {data} = await client.query({
				query: getKoroneikiAccounts,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});
			if (data) {
				const dataPath = data.c.koroneikiAccounts.items[0];
				const formatDate = {
					day: '2-digit',
					month: '2-digit',
					year: 'numeric',
				};
				const slaFiltedData = [];
				const slaName = {
					current: 'Current',
					expired: 'Expired',
					future: 'Future',
				};
				const slaPathLabel = {
					current: slaName.current.toUpperCase(),
					expired: slaName.expired.toUpperCase(),
					future: slaName.future.toUpperCase(),
				};

				const slaNewDate = (sla) => {
					return new Date(dataPath[sla]).toLocaleDateString(
						'en-US',
						formatDate
					);
				};
				const slaGetData = (sla) => {
					const slaFullTitle = 'sla' + sla;

					return {
						slaDateEnd: `${slaNewDate(slaFullTitle + 'EndDate')}`,
						slaDateStart: `${slaNewDate(
							slaFullTitle + 'StartDate'
						)}`,
						slaLabel: sla.toUpperCase(),
						slaTitle: dataPath[slaFullTitle].split(' ')[0],
					};
				};

				const slaRawData = [
					slaGetData(slaName.current),
					slaGetData(slaName.expired),
					slaGetData(slaName.future),
				].filter((sla) => sla.slaTitle);
				const slaRaw = {
					first: slaRawData[0],
					second: slaRawData[1],
					third: slaRawData[2],
				};
				if (
					slaRaw.first?.slaLabel === slaPathLabel.current &&
					(slaRaw.second?.slaTitle || slaRaw.third?.slaTitle) ===
						slaRaw.first?.slaTitle
				) {
					slaFiltedData.push(slaRaw.first);

					if (
						slaFiltedData[0].slaTitle === slaRaw.second.slaTitle &&
						slaRaw.second.slaLabel === slaPathLabel.expired
					) {
						slaFiltedData[0].slaDateStart =
							slaRaw.second.slaDateStart;
					}
					if (
						slaFiltedData[0].slaTitle === slaRaw.second.slaTitle &&
						slaRaw.second.slaLabel === slaPathLabel.future
					) {
						slaFiltedData[0].slaDateEnd = slaRaw.second.slaDateEnd;
					}
					if (slaRaw.third) {
						if (
							slaRaw.third.slaTitle === slaFiltedData[0].slaTitle
						) {
							slaFiltedData[0].slaDateEnd =
								slaRaw.third.slaDateEnd;
						} else {
							slaFiltedData.push(slaRaw.third);
						}
					}
				} else {
					slaRawData.map((sla) => slaFiltedData.push(sla));
				}
				setSlaData(slaFiltedData);

				if (!slaSelected) {
					const slaSelectedCard = slaFiltedData[0].slaLabel;
					setSlaSelected(slaSelectedCard);
				}
			}
		};

		fetchkoroneikeAccount();
	}, [accountKey, slaSelected]);

	const handleClick = () => {
		const slaLength = slaData.length;
		if (slaSelected === slaData[slaLength - 1].slaLabel) {
			setSlaSelected(slaData[0].slaLabel);
		} else if (slaSelected === slaData[0].slaLabel) {
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
							onClick={handleClick}
						>
							<svg
								className="bi bi-arrow-right-short"
								fill="currentColor"
								height="26"
								viewBox="0 0 16 16"
								width="26"
								xmlns="http://www.w3.org/2000/svg"
							>
								<path
									d="M4 8a.5.5 0 0 1 .5-.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5A.5.5 0 0 1 4 8z"
									fillRule="evenodd"
								/>
							</svg>
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
