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
import i18n from '../../../../common/I18n';
import {FORMAT_DATE} from '../../../../common/utils/constants/slaCardDate';
import {SLA_CARD_NAMES} from '../../../../common/utils/constants/slaCardNames';
import getDateCustomFormat from '../../../../common/utils/getDateCustomFormat';
import SlaCardLayout from './Layout';

const SlaCard = ({project}) => {
	const [slaData, setSlaData] = useState();
	const [slaSelected, setSlaSelected] = useState();
	const [slaPosition, setSlaPosition] = useState(0);

	useEffect(() => {
		const {
			slaCurrent,
			slaCurrentEndDate,
			slaCurrentStartDate,
			slaExpired,
			slaExpiredEndDate,
			slaExpiredStartDate,
			slaFuture,
			slaFutureEndDate,
			slaFutureStartDate,
		} = project;

		const slaFiltedData = [];

		const slaRawData = {
			current: {
				dateEnd: getDateCustomFormat(slaCurrentEndDate, FORMAT_DATE),
				dateStart: getDateCustomFormat(
					slaCurrentStartDate,
					FORMAT_DATE
				),
				label: SLA_CARD_NAMES.current,
				title: slaCurrent?.split(' ')[0],
			},
			expired: {
				dateEnd: getDateCustomFormat(slaExpiredEndDate, FORMAT_DATE),
				dateStart: getDateCustomFormat(
					slaExpiredStartDate,
					FORMAT_DATE
				),
				label: SLA_CARD_NAMES.expired,
				title: slaExpired?.split(' ')[0],
			},
			future: {
				dateEnd: getDateCustomFormat(slaFutureEndDate, FORMAT_DATE),
				dateStart: getDateCustomFormat(slaFutureStartDate, FORMAT_DATE),
				label: SLA_CARD_NAMES.future,
				title: slaFuture?.split(' ')[0],
			},
		};

		if (
			slaRawData.current.title === slaRawData.expired.title &&
			slaRawData.current.title === slaRawData.future.title
		) {
			slaRawData.current.dateStart = slaRawData.expired.dateStart;
			slaRawData.current.dateEnd = slaRawData.future.dateEnd;
			slaFiltedData.push(slaRawData.current);
		} else if (slaRawData.current.title === slaRawData.expired.title) {
			slaRawData.current.dateStart = slaRawData.expired.dateStart;
			slaFiltedData.push(slaRawData.current);
			slaFiltedData.push(slaRawData.future);
		} else if (slaRawData.current.title === slaRawData.future.title) {
			slaRawData.current.dateEnd = slaRawData.future.dateEnd;
			slaFiltedData.push(slaRawData.current);
			slaFiltedData.push(slaRawData.expired);
		} else {
			slaFiltedData.push(slaRawData.current);
			slaFiltedData.push(slaRawData.expired);
			slaFiltedData.push(slaRawData.future);
		}

		const slaSelectedCards = slaFiltedData.filter((sla) => sla.title);

		setSlaData(slaSelectedCards);

		if (!slaSelected) {
			setSlaSelected(slaSelectedCards[0]?.label);
		}
	}, [project, slaSelected]);

	const handleSlaCardClick = () => {
		const nextPosition = slaPosition + 1;

		if (slaData[nextPosition]) {
			setSlaSelected(slaData[nextPosition].label);
			setSlaPosition(nextPosition);
		} else {
			setSlaSelected(slaData[0].label);
			setSlaPosition(0);
		}
	};

	return (
		<div className="cp-sla-container position-absolute">
			<h5 className="mb-4">{i18n.translate('support-level')}</h5>

			{slaData?.length ? (
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
							{slaData.map((sla) => (
								<SlaCardLayout
									key={sla.title}
									slaDateEnd={sla.dateEnd}
									slaDateStart={sla.dateStart}
									slaLabel={sla.label}
									slaSelected={slaSelected}
									slaTitle={sla.title}
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
					<p className="px-3 py-2 text-neutral-7 text-paragraph-sm">
						{i18n.translate(
							"the-project's-support-level-is-displayed-here-for-projects-with-ticketing-support"
						)}
					</p>
				</div>
			)}
		</div>
	);
};

export default SlaCard;
