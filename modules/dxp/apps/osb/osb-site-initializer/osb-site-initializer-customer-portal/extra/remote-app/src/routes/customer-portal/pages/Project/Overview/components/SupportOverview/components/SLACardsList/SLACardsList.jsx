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
import SLACard from './components/SLACard';
import SLACardMessage from './components/SLACardMessage/SLACardMessage';
import SwitchSLACardButton from './components/SwitchSLACardButton';
import useSLACardPosition from './hooks/useSLACardPosition';
import useSLACards from './hooks/useSLACards';

const SLACardsList = ({koroneikiAccount, loading}) => {
	const slaCards = useSLACards(koroneikiAccount);
	const {changePosition, currentPosition, lastPosition} = useSLACardPosition(
		slaCards?.length
	);

	const getSLACards = () =>
		slaCards?.map((slaCard, index) => (
			<SLACard
				{...slaCard}
				active={currentPosition === index}
				key={`${slaCard.title}-${index}`}
				last={lastPosition === index}
				unique={slaCards.length < 2}
			/>
		));

	return (
		<div className="mb-5">
			{loading ? (
				<Skeleton className="mb-4" height={22} width={140} />
			) : (
				<h5 className="mb-4">{i18n.translate('support-level')}</h5>
			)}

			{loading ? (
				<Skeleton height={84} width={200} />
			) : slaCards?.length ? (
				<div
					className={classNames({
						'cp-sla-container ml-3': slaCards.length > 1,
					})}
				>
					<div className="d-flex">{getSLACards()}</div>

					{slaCards.length > 1 && (
						<SwitchSLACardButton
							handleClick={() => changePosition()}
						/>
					)}
				</div>
			) : (
				<SLACardMessage />
			)}
		</div>
	);
};

export default SLACardsList;
