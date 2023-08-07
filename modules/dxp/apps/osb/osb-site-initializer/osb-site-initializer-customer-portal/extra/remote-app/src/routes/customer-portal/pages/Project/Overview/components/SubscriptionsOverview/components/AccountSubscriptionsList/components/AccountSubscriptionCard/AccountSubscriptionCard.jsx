/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import classNames from 'classnames';
import {memo, useMemo} from 'react';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';

import PopoverIconButton from '~/routes/customer-portal/components/PopoverIconButton';
import i18n from '../../../../../../../../../../../common/I18n';
import {
	Skeleton,
	StatusTag,
} from '../../../../../../../../../../../common/components';
import {useGetAccountSubscriptionUsage} from '../../../../../../../../../../../common/services/liferay/graphql/account-subscription-usage';
import {
	FORMAT_DATE_TYPES,
	SLA_STATUS_TYPES,
} from '../../../../../../../../../../../common/utils/constants';
import {
	PRODUCT_DISPLAY_EXCEPTION,
	SUBSCRIPTION_TYPES,
} from '../../../../../../../../../../../common/utils/constants/subscriptionCardsCount';
import getDateCustomFormat from '../../../../../../../../../../../common/utils/getDateCustomFormat';

const AccountSubscriptionCard = ({
	loading,
	logoPath: IconSVG,
	onClick,
	selectedAccountSubscriptionGroup,
	...accountSubscription
}) => {
	const instanceSize = Number(accountSubscription.instanceSize ?? 0);

	const {articleWhatIsMyInstanceSizingValueURL} = useAppPropertiesContext();

	const getDatesDisplay = () =>
		`${getDateCustomFormat(
			accountSubscription.startDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		)} - ${getDateCustomFormat(
			accountSubscription.endDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		)}`;

	const {data: accountSubscriptionUsageData} = useGetAccountSubscriptionUsage(
		accountSubscription?.accountKey,
		accountSubscription?.productKey
	);

	const currentConsumption = useMemo(
		() =>
			accountSubscriptionUsageData?.getAccountSubscriptionUsage
				?.currentConsumption,
		[accountSubscriptionUsageData]
	);

	const DisplayOnCard = {
		Blank: null,
		Purchased: (
			<>
				{accountSubscription?.quantity && (
					<span className="align-items-center d-flex justify-content-center m-0">
						{accountSubscription?.quantity}
					</span>
				)}
			</>
		),
		PurchasedAndProvisioned: (
			<p className="d-flex justify-content-center m-0">
				{currentConsumption !== undefined
					? `${currentConsumption}/${accountSubscription?.quantity}`
					: `0/${accountSubscription?.quantity}`}
			</p>
		),
	};

	const displayQuantityOnCard = (subscriptionType, productName) => {
		const isPurchasedAndProvisioned = SUBSCRIPTION_TYPES.PurchasedAndProvisioned.includes(
			subscriptionType
		);
		const isPurchased = SUBSCRIPTION_TYPES.Purchased.includes(
			subscriptionType
		);

		if (isPurchasedAndProvisioned) {
			return PRODUCT_DISPLAY_EXCEPTION.purchasedProduct.includes(
				productName
			)
				? DisplayOnCard.Purchased
				: DisplayOnCard.PurchasedAndProvisioned;
		}

		if (isPurchased) {
			return PRODUCT_DISPLAY_EXCEPTION.blankProducts.includes(productName)
				? DisplayOnCard.Blank
				: DisplayOnCard.Purchased;
		}

		return PRODUCT_DISPLAY_EXCEPTION.nonBlankProducts.includes(productName)
			? DisplayOnCard.Purchased
			: DisplayOnCard.Blank;
	};

	return (
		<ClayCard
			className={classNames(
				'border border-light mb-0 mr-4 mt-4 shadow-none',
				{
					'card-interactive': !loading,
				}
			)}
			onClick={onClick}
		>
			<ClayCard.Body className="cp-account-subscription-card d-flex flex-column justify-content-between pb-3">
				{loading ? (
					<Skeleton className="mb-3 py-1" height={45} width={48} />
				) : (
					IconSVG && (
						<div className="mb-3 py-1 text-center">
							<IconSVG height={45} width={45} />
						</div>
					)
				)}

				{loading ? (
					<Skeleton
						className="cp-account-subscription-card-name mb-1"
						height={20}
						width={90}
					/>
				) : (
					<h5 className="align-items-center cp-account-subscription-card-name d-flex justify-content-center mb-1 text-center">
						{accountSubscription.name}
					</h5>
				)}

				{displayQuantityOnCard(
					selectedAccountSubscriptionGroup?.name,
					accountSubscription?.name
				)}

				<div>
					{loading ? (
						<Skeleton className="mb-1" height={13} width={80} />
					) : (
						instanceSize > 0 && (
							<p className="mb-1 text-center text-neutral-7 text-paragraph-sm">
								{`${i18n.translate('instance-size')}: `}

								{accountSubscription.instanceSize}

								<PopoverIconButton
									popoverLink={{
										textLink: i18n.translate(
											'learn-more-about-instance-sizing'
										),
										url: articleWhatIsMyInstanceSizingValueURL,
									}}
								/>
							</p>
						)
					)}

					{loading ? (
						<Skeleton className="mb-3" height={24} width={160} />
					) : (
						accountSubscription.startDate &&
						accountSubscription.endDate && (
							<p className="mb-3 text-center">
								{getDatesDisplay()}
							</p>
						)
					)}

					{loading ? (
						<Skeleton height={20} width={38} />
					) : (
						<div className="d-flex justify-content-center">
							<StatusTag
								currentStatus={
									SLA_STATUS_TYPES[
										accountSubscription?.subscriptionStatus?.toLowerCase()
									]
								}
							/>
						</div>
					)}
				</div>
			</ClayCard.Body>
		</ClayCard>
	);
};

export default memo(AccountSubscriptionCard);
