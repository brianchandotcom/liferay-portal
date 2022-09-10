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
import CardMessage from './components/CardMessage/CardMessage';
import SlaCard from './components/SlaCard';
import SwitchSlaCardsButton from './components/SwitchSlaCardButton';
import useCardPosition from './hooks/useCardPosition';
import useSlaCards from './hooks/useSlaCards';

const SlaCardsList = ({koroneikiAccount, loading}) => {
	const slaCards = useSlaCards(koroneikiAccount);
	const {changePosition, currentPosition, lastPosition} = useCardPosition(
		slaCards?.length
	);

	const getSlaCards = () =>
		slaCards?.map((slaCard, index) => (
			<SlaCard
				{...slaCard}
				active={currentPosition === index}
				key={`${slaCard.title}-${index}`}
				last={lastPosition === index}
			/>
		));

	if (loading) {
		return (
			<div className="mb-6">
				<Skeleton className="mb-4" height={22} width={140} />

				<Skeleton height={84} width={215} />
			</div>
		);
	}

	return (
		<div className="cp-sla-container mb-6">
			<h5 className="mb-4">{i18n.translate('support-level')}</h5>

			{slaCards?.length ? (
				<>
					<div
						className={classNames('d-flex', {
							'ml-3': slaCards.length > 1,
						})}
					>
						{getSlaCards()}
					</div>

					{slaCards.length > 1 && (
						<SwitchSlaCardsButton
							handleClick={() => changePosition()}
						/>
					)}
				</>
			) : (
				<CardMessage />
			)}
		</div>
	);
};

export default SlaCardsList;
