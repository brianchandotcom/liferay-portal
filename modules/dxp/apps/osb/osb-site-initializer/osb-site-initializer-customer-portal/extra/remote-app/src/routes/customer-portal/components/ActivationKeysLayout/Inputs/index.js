import {useLazyQuery, useQuery} from '@apollo/client';
import {ClaySelect} from '@clayui/form';
import {useEffect, useMemo, useState} from 'react';
import BaseButton from '../../../../../common/components/BaseButton';
import {
	getAccountSubscriptionsGroups,
	getAccountSubscriptionsTerms,
} from '../../../../../common/services/liferay/graphql/queries';
import {getCurrentEndDate} from '../../../../../common/utils';
import {getYearlyTerms} from '../../../utils';

const ActivationKeysInputs = ({accountKey, productKey, productTitle}) => {
	const [
		selectedAccountSubscriptionGroupName,
		setSelectedAccountSubscriptionGroupName,
	] = useState('');
	const [selectDateInterval, setSelectedDateInterval] = useState({});

	const {data: dataAccountSubscriptionGroups} = useQuery(
		getAccountSubscriptionsGroups,
		{
			variables: {
				accountSubscriptionGroupERC: `accountSubscriptionGroupERC eq '${accountKey}_${productKey}'`,
			},
		}
	);
	const [
		fetchAccountSubscriptionsTerms,
		{data: dataAccountSubscriptionsTerms},
	] = useLazyQuery(getAccountSubscriptionsTerms);

	useEffect(() => {
		if (dataAccountSubscriptionGroups) {
			const accountSubscriptionGroups =
				dataAccountSubscriptionGroups?.c?.accountSubscriptions?.items ||
				[];

			if (accountSubscriptionGroups.length) {
				const accountSubscriptionGroupName =
					accountSubscriptionGroups[0].name;

				setSelectedAccountSubscriptionGroupName(
					accountSubscriptionGroupName
				);
				const filterAccountSubscriptionERC = `accountSubscriptionERC eq '${accountKey}_${productKey}_${accountSubscriptionGroupName.toLowerCase()}'`;

				fetchAccountSubscriptionsTerms({
					variables: {
						accountSubscriptionERC: filterAccountSubscriptionERC,
					},
				});
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [accountKey, dataAccountSubscriptionGroups, productKey]);

	const accountSubscriptionsTermDates = useMemo(() => {
		const accountSubscriptionsTerms =
			dataAccountSubscriptionsTerms?.c?.accountSubscriptionTerms?.items ||
			[];

		if (accountSubscriptionsTerms.length) {
			const dateIntervals = getYearlyTerms(accountSubscriptionsTerms[0]);
			setSelectedDateInterval(dateIntervals[0]);

			return dateIntervals;
		}

		return [];
	}, [dataAccountSubscriptionsTerms]);

	const accountSubscriptionGroups =
		dataAccountSubscriptionGroups?.c?.accountSubscriptions?.items || [];

	const updateSelectedAccountSubscriptionGroupName = (name) => {
		setSelectedAccountSubscriptionGroupName(name);

		const filterAccountSubscriptionERC = `accountSubscriptionERC eq '${accountKey}_${productKey}_${name.toLowerCase()}'`;

		fetchAccountSubscriptionsTerms({
			variables: {
				accountSubscriptionERC: filterAccountSubscriptionERC,
			},
		});
	};

	return (
		<div className="mt-3">
			<p className="text-paragraph">
				Select an active Liferay {productTitle} subscription to download
				the activation key.
			</p>

			<div className="d-flex mb-3">
				<label className="mr-3" id="subscription-select">
					<span className="ml-3">Subscription</span>

					<ClaySelect
						onChange={(event) =>
							updateSelectedAccountSubscriptionGroupName(
								event.target.value
							)
						}
						value={selectedAccountSubscriptionGroupName}
					>
						{accountSubscriptionGroups.map(
							(accountSubscriptionGroup) => (
								<ClaySelect.Option
									key={
										accountSubscriptionGroup.accountSubscriptionGroupERC
									}
									label={accountSubscriptionGroup.name}
									value={accountSubscriptionGroup.name}
								/>
							)
						)}
					</ClaySelect>
				</label>

				<label id="subscription-term-select">
					<span className="ml-3">Subscription Term</span>

					<ClaySelect
						onChange={(event) =>
							setSelectedDateInterval(event.target.value)
						}
						value={selectDateInterval}
					>
						{accountSubscriptionsTermDates.map((dateInterval) => {
							const formattedDate = `${getCurrentEndDate(
								dateInterval.startDate
							)} - ${getCurrentEndDate(dateInterval.endDate)}`;

							return (
								<ClaySelect.Option
									className="options"
									key={dateInterval.startDate}
									label={formattedDate}
									value={dateInterval}
								/>
							);
						})}
					</ClaySelect>
				</label>
			</div>

			<BaseButton
				className="btn btn-outline-primary"
				prependIcon="download"
				type="button"
			>
				Download Key
			</BaseButton>
		</div>
	);
};

export default ActivationKeysInputs;
