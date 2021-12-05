import {useQuery} from '@apollo/client';
import {ClaySelect} from '@clayui/form';
import {useEffect, useMemo, useRef, useState} from 'react';
import BaseButton from '../../../../common/components/BaseButton';
import {
	getAccountSubscriptions,
	getAccountSubscriptionsTerms,
} from '../../../../common/services/liferay/graphql/queries';
import {getCurrentEndDate} from '../../../../common/utils';
import {getYearlyTerms} from '../../utils/constants';

const CommerceLicense = ({accountKey}) => {
	const [selectedSubscriptions, setSelectedSubscriptions] = useState({
		name: '',
	});
	const [, setSelectedSubscriptionsTerms] = useState();
	const selectedSubscriptionRef = useRef();

	const {data: dataSubscriptionsBase} = useQuery(getAccountSubscriptions, {
		variables: {
			accountSubscriptionGroupERC: `accountSubscriptionGroupERC eq '${accountKey}_commerce'`,
		},
	});
	const {data: dataSubscriptionsTermsBase} = useQuery(
		getAccountSubscriptionsTerms,
		{
			variables: {
				accountSubscriptionERC: `accountSubscriptionERC eq '${accountKey}_commerce_${selectedSubscriptions.name.toLowerCase()}'`,
			},
		}
	);

	const dataSubscriptions = useMemo(() => {
		return dataSubscriptionsBase?.c?.accountSubscriptions?.items || [];
	}, [dataSubscriptionsBase]);

	const dataSubscriptionsTerms = useMemo(() => {
		return (
			dataSubscriptionsTermsBase?.c?.accountSubscriptionTerms?.items.reduce(
				(acc, item) => {
					acc.push(...getYearlyTerms(item));

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

	return (
		<div>
			<p className="text-paragraph">
				Select an active Liferay Commerce subscription to download the
				activation key.
			</p>

			<div className="d-flex flex-wrap mb-3">
				<div className="mr-3 subscription-form">
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

				<div className="subscription-term-form">
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
				onClick={() => {}}
				prependIcon="download"
				type="button"
			>
				Download Key
			</BaseButton>
		</div>
	);
};
export default CommerceLicense;
