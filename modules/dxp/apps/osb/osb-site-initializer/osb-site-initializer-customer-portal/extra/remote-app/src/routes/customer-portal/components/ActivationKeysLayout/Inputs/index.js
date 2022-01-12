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

import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';
import client from '../../../../../apolloClient';
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

	const [accountSubscriptions, setAccountSubscriptions] = useState([]);

	const [
		selectedAccountSubscriptionName,
		setSelectedAccountSubscriptionName,
	] = useState('');

	const [
		accountSubscriptionsTermsDates,
		setAccountSubscriptionsTermsDates,
	] = useState([]);
	const [selectDateInterval, setSelectedDateInterval] = useState();

	const [hasLicenseDownloadError, setLicenseDownloadError] = useState(false);

	useEffect(() => {
		const fetchAccountSubscriptions = async () => {
			const {data} = await client.query({
				query: getAccountSubscriptions,
				variables: {
					filter: `accountSubscriptionGroupERC eq '${accountKey}_${productKey}'`,
				},
			});

			if (data) {
				const items = data.c?.accountSubscriptions?.items;
				setAccountSubscriptions(data.c?.accountSubscriptions?.items);

				setSelectedAccountSubscriptionName(items[0].name);
			}
		};

		fetchAccountSubscriptions();
	}, [accountKey, productKey]);

	useEffect(() => {
		const getSubscriptionTerms = async () => {
			const filterAccountSubscriptionERC = `accountSubscriptionERC eq '${accountKey}_${productKey}_${selectedAccountSubscriptionName.toLowerCase()}'`;

			const {data} = await client.query({
				query: getAccountSubscriptionsTerms,
				variables: {
					filter: filterAccountSubscriptionERC,
				},
			});

			if (data) {
				const accountSubscriptionsTerms =
					data.c?.accountSubscriptionTerms?.items || [];

				if (accountSubscriptionsTerms.length) {
					const dateIntervals = getYearlyTerms(
						accountSubscriptionsTerms[0]
					);

					setAccountSubscriptionsTermsDates(dateIntervals);
					setSelectedDateInterval(dateIntervals[0]);
				}
			}
		};

		if (selectedAccountSubscriptionName) {
			getSubscriptionTerms();
		}
	}, [accountKey, productKey, selectedAccountSubscriptionName]);

	useEffect(() => {
		if (selectedAccountSubscriptionName && selectDateInterval) {
			setLicenseDownloadError(false);
		}
	}, [selectDateInterval, selectedAccountSubscriptionName]);

	const handleClick = async () => {
		const license = await fetchLicense(
			accountKey,
			selectDateInterval.endDate.toISOString(),
			selectDateInterval.startDate.toISOString(),
			selectedAccountSubscriptionName.toLowerCase(),
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
								setSelectedAccountSubscriptionName(
									event.target.value
								)
							}
							value={selectedAccountSubscriptionName}
						>
							{accountSubscriptions.map((accountSubscription) => (
								<ClaySelect.Option
									key={
										accountSubscription.accountSubscriptionGroupERC
									}
									label={accountSubscription.name}
									value={accountSubscription.name}
								/>
							))}
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
							onChange={(event) => {
								setSelectedDateInterval(
									accountSubscriptionsTermsDates[
										event.target.value
									]
								);
							}}
						>
							{accountSubscriptionsTermsDates.map(
								(dateInterval, index) => {
									const formattedDate = `${getCurrentEndDate(
										dateInterval.startDate
									)} - ${getCurrentEndDate(
										dateInterval.endDate
									)}`;

									return (
										<ClaySelect.Option
											className="options"
											key={index}
											label={formattedDate}
											value={index}
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
					!(selectedAccountSubscriptionName && selectDateInterval)
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
