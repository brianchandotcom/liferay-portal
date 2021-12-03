/* eslint-disable @liferay/portal/no-global-fetch */
import {useQuery} from '@apollo/client';
import {ClaySelect} from '@clayui/form';
import {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import BaseButton from '../../../../common/components/BaseButton';
import {
	getAccountSubscriptions,
	getAccountSubscriptionsTerms,
} from '../../../../common/services/liferay/graphql/queries';
import {getCurrentEndDate} from '../../../../common/utils';
const CRYSTAL = 'KOR-1776780';

const EnterpriseSearch = () => {
	const [hasErrorDownload, setErrorDownload] = useState(false);
	const [selectedSubscriptions, setSelectedSubscriptions] = useState({
		name: '',
	});
	const [, setSelectedSubscriptionsTerms] = useState();
	const selectedSubscriptionRef = useRef();
	const {data: dataSubscriptionsBase, error: errorSubscriptions} = useQuery(
		getAccountSubscriptions,
		{
			variables: {
				accountSubscriptionGroupERC: `accountSubscriptionGroupERC eq '${CRYSTAL}_enterprise-search'`,
			},
		}
	);

	const {
		data: dataSubscriptionsTermsBase,
		error: errorSubscriptionsTerms,
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
					acc.push(item);

					return acc;
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

	const handleOnClick = useCallback(() => {
		setErrorDownload(false);
		fetch(
			'https://webserver-lrprovisioning-uat.lfr.cloud/o/provisioning-rest/v1.0/accounts/KOR-70938671/product-groups/Enterprise%20Search/product-environment/production/common-license-key?dateEnd=2022-01-01T00:00:00Z&dateStart=2022-01-01T00:00:00Z',
			{
				headers: {
					'Access-Control-Allow-Origin': '*',
					'Content-Type': 'application/json',
					'Okta-Session-ID': '102zsC2MczqSwqZUZNsWbZjRA',
				},
				mode: 'cors',
			}
		)
			.then((response) => response.json())
			.catch(() => setErrorDownload(true));
	}, []);

	if (errorSubscriptions || errorSubscriptionsTerms) {
		return 'Error!';
	}

	return (
		<div>
			<div className="mb-3">
				<h1 className="h1 mb-5">Activation Keys</h1>

				<p className="text-paragraph">
					Select an active Liferay Enterprise Search subscription to
					download the activation key.
				</p>
			</div>

			<div className="d-flex mb-3">
				<div className="mr-3">
					<label className="ml-3" htmlFor="subscription1">
						Subscription
					</label>

					<ClaySelect
						aria-label="Subscription"
						id="subscription1"
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
							id="subscription-term"
							onChange={(event) =>
								setSelectedSubscriptionsTerms(
									event.target.value
								)
							}
							placeholder="Choose the period"
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
				</div>
			</div>

			<BaseButton
				className="btn btn-outline-primary mb-3"
				onClick={handleOnClick}
				prependIcon="download"
				type="button"
			>
				Download Key
			</BaseButton>

			{hasErrorDownload && (
				<div className="w-75">
					<p className="text-paragraph">
						The requested activation key is not yet available. For
						more information about the availability of your
						Enterprise Search activation keys, please
						<span className="text-link-md text-paragraph">
							{' '}
							contact the Support team.
						</span>
					</p>
				</div>
			)}
		</div>
	);
};

export default EnterpriseSearch;
