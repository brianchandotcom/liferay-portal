/* eslint-disable no-unused-vars */
/* eslint-disable no-console */
import {useQuery} from '@apollo/client';
import {ClaySelect} from '@clayui/form';
import {useEffect, useMemo, useRef, useState} from 'react';
import BaseButton from '~/common/components/BaseButton';
import MenuButton from '~/common/components/MenuButton';
import {
	getAccountSubscriptions,
	getAccountSubscriptionsTerms,
} from '~/common/services/liferay/graphql/queries';
import {getCurrentEndDate} from '../../../../common/utils';
const CRYSTAL = 'KOR-1407632';

const EnterpriseSearch = () => {
	const [selectedSubscriptions, setSelectedSubscriptions] = useState({
		name: '',
	});
	const [
		selectedSubscriptionsTerms,
		setSelectedSubscriptionsTerms,
	] = useState();
	const selectedSubscriptionRef = useRef();
	const {
		data: dataSubscriptionsBase,
		error: errorSubscriptions,
		loading: isLoadingSubscriptions,
	} = useQuery(getAccountSubscriptions, {
		variables: {
			accountSubscriptionGroupERC: `accountSubscriptionGroupERC eq '${CRYSTAL}_enterprise-search'`,
		},
	});

	const {
		data: dataSubscriptionsTermsBase,
		error: errorSubscriptionsTerms,
		loading: isLoadingSubscriptionsTerms,
	} = useQuery(getAccountSubscriptionsTerms, {
		variables: {
			accountSubscriptionERC: `accountSubscriptionERC eq '${CRYSTAL}_enterprise-search_${selectedSubscriptions.name.toLowerCase()}'`,
		},
	});

	const dataSubscriptions = useMemo(() => {
		return dataSubscriptionsBase?.c?.accountSubscriptions?.items || [];
	}, [dataSubscriptionsBase]);

	const dataSubscriptionsTerms = useMemo(() => {
		return (
			dataSubscriptionsTermsBase?.c?.accountSubscriptionTerms?.items.reduce(
				(acc, item) => {
					const yearStartDate = new Date(
						item.startDate
					).getFullYear();
					const yearEndDate = new Date(item.endDate).getFullYear();
					if (yearStartDate + 1 < yearEndDate) {
						const yearDateSplitted = new Array(
							yearEndDate - yearStartDate + 1
						)
							.fill()
							.map((_, index, array) => {
								const currentYear = yearStartDate + index;

								const yearNumStartDate = new Date(
									item.startDate
								).setFullYear(currentYear);
								const yearNumEndDate = currentYear + 1;

								const daysEndDate = new Date(
									item.startDate
								).getDate();
								const monthsEndDate = new Date(
									item.startDate
								).getMonth();

								if (index + 2 >= array.length) {
									const yearNumEndDate = new Date(
										item.endDate
									).setFullYear(currentYear + 1);

									return index + 2 === array.length
										? {
												endDate: new Date(
													yearNumEndDate
												),
												startDate: new Date(
													yearNumStartDate
												),
										  }
										: null;
								}

								return {
									endDate: new Date(
										yearNumEndDate,
										monthsEndDate,
										daysEndDate - 1
									),
									startDate: new Date(yearNumStartDate),
								};
							})
							.filter((item) => item);

						return yearDateSplitted;
					}

					return item;
				},
				[]
			) || []
		);
	}, [dataSubscriptionsTermsBase]);

	selectedSubscriptionRef.current = selectedSubscriptions.name;
	useEffect(() => {
		if (!selectedSubscriptionRef.current && dataSubscriptions.length) {
			setSelectedSubscriptions(dataSubscriptions[0]);
		}

		setSelectedSubscriptionsTerms(dataSubscriptionsTerms[0]);
	}, [dataSubscriptions, dataSubscriptionsTerms]);

	if (errorSubscriptions || errorSubscriptionsTerms) {
		return 'Error!';
	}

	return (
		<div>
			<div className="mb-3">
				<h1 className="header mb-5">Activation Keys</h1>

				<p className="paragraph">
					Select an active Liferay Enterprise Search subscription to
					download the activation key.
				</p>
			</div>

			<div className="d-flex mb-3">
				<div className="mr-3">
					<label className="ml-3" htmlFor="subscription">
						Subscription
					</label>

					<ClaySelect
						aria-label="Subscription"
						className="subscription"
						id="subscription"
						onChange={(event) =>
							setSelectedSubscriptions({
								name: event.target.value,
							})
						}
						value={selectedSubscriptions.name}
					>
						{dataSubscriptions.map((item) => (
							<ClaySelect.Option
								key={item.name}
								label={item.name}
								value={item.name}
							/>
						))}
					</ClaySelect>
				</div>

				<div>
					<label className="ml-3" htmlFor="subscription-term">
						Subscription Term
					</label>

					<div className="d-flex">
						<ClaySelect
							aria-label="Subscription Term"
							className="subscription-term"
							id="subscription-term"
							onChange={(event) =>
								setSelectedSubscriptionsTerms(
									event.target.value
								)
							}
						>
							{dataSubscriptionsTerms.map((account) => {
								const formattedDate = `${getCurrentEndDate(
									account.startDate
								)} - ${getCurrentEndDate(account.endDate)}`;

								return (
									<ClaySelect.Option
										className="options"
										key={account.startDate}
										label={formattedDate}
										value={`${account.startDate}-${account.endDate}`}
									/>
								);
							})}
						</ClaySelect>
					</div>

					<MenuButton />
				</div>
			</div>

			<BaseButton displayType="secondary" prependIcon="download">
				Download Key
			</BaseButton>
		</div>
	);
};

export default EnterpriseSearch;
