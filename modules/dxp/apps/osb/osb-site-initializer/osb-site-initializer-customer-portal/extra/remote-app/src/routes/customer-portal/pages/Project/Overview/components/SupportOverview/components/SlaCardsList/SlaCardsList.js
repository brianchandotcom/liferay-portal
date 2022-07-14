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
import i18n from '../../../../../../../../../common/I18n';
import {Skeleton} from '../../../../../../../../../common/components';
import SlaCard from './components/SlaCard';
import SwitchSlaCardsButton from './components/SwitchSlaCardButton';
import useCardPosition from './hooks/useCardPosition';
import useSlaCards from './hooks/useSlaCards';

const SlaCardsList = ({koroneikiAccount}) => {
	const slaCards = useSlaCards(koroneikiAccount);
	const {changePosition, currentPosition} = useCardPosition();

	return (
		<div>
			{!koroneikiAccount ? (
				<div className="cp-sla-container position-absolute">
					<Skeleton height={22} width={201} />

					<Skeleton className="mt-3" height={80} width={201} />
				</div>
			) : (
				<div className="cp-sla-container position-absolute">
					<h5 className="mb-4">{i18n.translate('support-level')}</h5>

					{slaCards?.length ? (
						<div>
							<div
								className={classNames({
									'ml-2': slaCards.length > 1,
								})}
							>
								<div
									className={classNames(
										'align-items-center d-flex cp-sla-card-holder',
										{
											'cp-sla-multiple-card ml-2':
												slaCards.length > 1,
										}
									)}
								>
									{slaCards.map((slaCard, index) => (
										<SlaCard
											key={slaCard.title}
											selected={currentPosition === index}
											{...slaCard}
											koroneikiAccount={koroneikiAccount}
										/>
									))}
								</div>
							</div>

							{slaCards.length > 1 && (
								<SwitchSlaCardsButton
									handleClick={() => changePosition(slaCards)}
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
			)}
		</div>
	);
};

export default SlaCardsList;
