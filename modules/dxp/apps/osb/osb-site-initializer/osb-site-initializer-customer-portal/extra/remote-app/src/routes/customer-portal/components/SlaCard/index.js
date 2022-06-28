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
import i18n from '../../../../common/I18n';
import SlaCardLayout from './components/SlaCardLayout';
import SwitchSlaCardsButton from './components/SwitchSlaCardButton';
import useGetSlaData from './hooks/useGetSlaData';
import useSwitchSlaCards from './hooks/useSwitchSlaCards';

const SlaCard = ({project}) => {
	const {memoizedSlaCards} = useGetSlaData(project);
	const {currentSlaCardPosition, handleSlaCardClick} = useSwitchSlaCards(
		memoizedSlaCards
	);

	return (
		<div className="cp-sla-container position-absolute">
			<h5 className="mb-4">{i18n.translate('support-level')}</h5>

			{memoizedSlaCards?.length ? (
				<div>
					<div
						className={classNames({
							'ml-2': memoizedSlaCards.length > 1,
						})}
					>
						<div
							className={classNames(
								'align-items-center d-flex cp-sla-card-holder',
								{
									'cp-sla-multiple-card ml-2':
										memoizedSlaCards.length > 1,
								}
							)}
						>
							{memoizedSlaCards.map((sla, index) => (
								<SlaCardLayout
									key={sla.title}
									slaDateEnd={sla.endDate}
									slaDateStart={sla.startDate}
									slaLabel={sla.label}
									slaSelected={
										currentSlaCardPosition === index
									}
									slaTitle={sla.title}
								/>
							))}
						</div>
					</div>

					{memoizedSlaCards.length > 1 && (
						<SwitchSlaCardsButton
							handleSlaCardClick={handleSlaCardClick}
						/>
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
