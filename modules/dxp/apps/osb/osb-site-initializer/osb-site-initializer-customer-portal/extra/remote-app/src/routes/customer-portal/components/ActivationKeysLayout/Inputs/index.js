import {useLazyQuery, useQuery} from '@apollo/client';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useEffect, useMemo, useState} from 'react';
import BaseButton from '../../../../../common/components/BaseButton';
import {useApplicationProvider} from '../../../../../common/context/ApplicationPropertiesProvider';
import {
	getAccountSubscriptions,
	getAccountSubscriptionsTerms,
} from '../../../../../common/services/liferay/graphql/queries';
import {fetchLicense} from '../../../../../common/services/liferay/raysource-api';
import {downloadFromBlob, getCurrentEndDate} from '../../../../../common/utils';
import {getYearlyTerms} from '../../../utils';
import {EXTENSIONS_FILE_TYPE, STATUS_CODE} from '../../../utils/constants';

const ActivationKeysInputs = ({
	accountKey,
	productKey,
	productTitle,
	sessionId,
}) => {
	const {
		createSupportRequest,
		licenseKeyDownloadURL,
	} = useApplicationProvider();

	const [
		selectedAccountSubscriptionGroupName,
		setSelectedAccountSubscriptionGroupName,
	] = useState('');
	const [selectDateInterval, setSelectedDateInterval] = useState({});
	const [hasLicenseDownloadError, setLicenseDownloadError] = useState(false);

	const {data: dataAccountSubscriptions} = useQuery(getAccountSubscriptions, {
		variables: {
			accountSubscriptionGroupERC: `accountSubscriptionGroupERC eq '${accountKey}_${productKey}'`,
		},
	});
	const [
		fetchAccountSubscriptionsTerms,
		{data: dataAccountSubscriptionsTerms},
	] = useLazyQuery(getAccountSubscriptionsTerms);

	useEffect(() => {
		if (dataAccountSubscriptions) {
			const accountSubscriptionGroups =
				dataAccountSubscriptions?.c?.accountSubscriptions?.items || [];

			if (accountSubscriptionGroups.length) {
				const accountSubscriptionGroupName =
					accountSubscriptionGroups[0].name;

				setSelectedAccountSubscriptionGroupName(
					accountSubscriptionGroupName
				);
				const filterAccountSubscriptionERC = `accountSubscriptionERC eq '${accountKey}_${productKey}_${accountSubscriptionGroupName.toLowerCase()}'`;

				fetchAccountSubscriptionsTerms({
					variables: {
						filter: filterAccountSubscriptionERC,
					},
				});
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [accountKey, dataAccountSubscriptions, productKey]);

	useEffect(() => {
		if (selectedAccountSubscriptionGroupName && selectDateInterval) {
			setLicenseDownloadError(false);
		}
	}, [selectDateInterval, selectedAccountSubscriptionGroupName]);

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
		dataAccountSubscriptions?.c?.accountSubscriptions?.items || [];

	const updateSelectedAccountSubscriptionGroupName = (name) => {
		setSelectedAccountSubscriptionGroupName(name);

		const filterAccountSubscriptionERC = `accountSubscriptionERC eq '${accountKey}_${productKey}_${name.toLowerCase()}'`;

		fetchAccountSubscriptionsTerms({
			variables: {
				accountSubscriptionERC: filterAccountSubscriptionERC,
			},
		});
	};

	const handleClick = async () => {
		const license = await fetchLicense(
			accountKey,
			selectDateInterval.endDate,
			selectDateInterval.startDate,
			licenseKeyDownloadURL,
			encodeURI(productTitle),
			sessionId
		);
		if (license.status === STATUS_CODE.SUCCESS) {
			const contentType = license.headers.get('content-type');
			const extensionFile = EXTENSIONS_FILE_TYPE[contentType] || '.txt';
			const licenseBlob = await license.blob();

			return downloadFromBlob(licenseBlob, `license${extensionFile}`);
		}

		setLicenseDownloadError(true);
	};

	return (
		<div className="mt-3">
			<p className="text-paragraph">
				Select an active Liferay {productTitle} subscription to download
				the activation key.
			</p>

			<div className="d-flex mb-3">
				<label className="mr-3" id="subscription-select">
					Subscription
					<div className="position-relative">
						<ClayIcon
							className="select-icon"
							symbol="caret-bottom"
						/>

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
					</div>
				</label>

				<label id="subscription-term-select">
					Subscription Term
					<div className="position-relative">
						<ClayIcon
							className="select-icon"
							symbol="caret-bottom"
						/>

						<ClaySelect
							onChange={(event) =>
								setSelectedDateInterval(event.target.value)
							}
							value={selectDateInterval}
						>
							{accountSubscriptionsTermDates.map(
								(dateInterval) => {
									const formattedDate = `${getCurrentEndDate(
										dateInterval.startDate
									)} - ${getCurrentEndDate(
										dateInterval.endDate
									)}`;

									return (
										<ClaySelect.Option
											className="options"
											key={dateInterval.startDate}
											label={formattedDate}
											value={dateInterval}
										/>
									);
								}
							)}
						</ClaySelect>
					</div>
				</label>
			</div>

			<BaseButton
				className="btn btn-outline-primary"
				disabled={
					hasLicenseDownloadError ||
					!(
						selectedAccountSubscriptionGroupName &&
						Object.keys(selectDateInterval).length > 0
					)
				}
				onClick={handleClick}
				prependIcon="download"
				type="button"
			>
				Download Key
			</BaseButton>

			{hasLicenseDownloadError && (
				<p className="mt-3 text-neutral-7 text-paragraph">
					{`The requested activation key is not yet available. For more
					information about the availability of your Enterprise Search
					activation keys, please `}

					<a
						href={createSupportRequest}
						rel="noreferrer"
						target="_blank"
					>
						<u className="font-weight-bold text-neutral-9">
							contact the Support team
						</u>
					</a>
				</p>
			)}
		</div>
	);
};

export default ActivationKeysInputs;
