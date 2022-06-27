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
import SlaCardLayout from '../../Layout';
import useSwitchSlaCards from '../../hooks/useSwitchSlaCards';
import SwitchSlaCardsButton from '../SwitchSlaCardButton';

const CardWithSla = ({setSlaSelected, slaData, slaSelected}) => {
	const handleSlaCardClick = useSwitchSlaCards(setSlaSelected, slaData);

	return (
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
							'cp-sla-multiple-card ml-2': slaData.length > 1,
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
				<SwitchSlaCardsButton handleSlaCardClick={handleSlaCardClick} />
			)}
		</div>
	);
};
export default CardWithSla;
