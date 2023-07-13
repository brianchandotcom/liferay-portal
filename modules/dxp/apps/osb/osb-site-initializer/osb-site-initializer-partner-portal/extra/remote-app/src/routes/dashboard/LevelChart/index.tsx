/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect, useState} from 'react';

import Container from '../../../common/components/dashboard/components/Container';
import PartnershipLevel, {
	AccountData,
} from '../../../common/components/dashboard/components/PartnershipLevel';
import {PartnerRoles} from '../../../common/components/dashboard/enums/partnerRoles';
import {PartnershipLevels} from '../../../common/components/dashboard/enums/partnershipLevels';
import {partnerLevelProperties} from '../../../common/components/dashboard/mock';
import ClayIconProvider from '../../../common/components/dashboard/utils/ClayIconProvider';
import {Liferay} from '../../../common/services/liferay';

interface CheckedProperties {
	[keys: string]: boolean;
}

interface Headcount {
	partnerMarketingUser?: number;
	partnerSalesUser?: number;
}
interface AccountType {
	accountName: string;
	actions: {
		delete: any;
	};
	closeDate: string;
	currency: {
		key: string;
		name: string;
	};
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	growthArr: number;
	opportunityName: string;
	opportunityOwner: string;
	ownerName: string;
	renewalArr: number;
	stage: string;
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	type: string;
}

const LevelChart = () => {
	const [data, setData] = useState<AccountData>();
	const [headcount, setHeadcount] = useState<Headcount>();
	const [completed, setCompleted] = useState<CheckedProperties>();
	const [opportunitysfs, setOpportunitysfs] = useState<any>();
	const [totalAmount, setTotalAmount] = useState<any>();
	const [loading, setLoading] = useState(false);

	const getAccountInformation = async () => {
		setLoading(true);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const myUserAccountsRequest = await fetch(
			'/o/headless-admin-user/v1.0/my-user-account',
			{
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			}
		);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const getOpportunitysfs = await fetch(
			`/o/c/opportunitysfs/?pageSize=200 `,
			{
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			}
		);

		if (myUserAccountsRequest.ok) {
			const {accountBriefs} = await myUserAccountsRequest.json();

			if (accountBriefs.length) {
				// eslint-disable-next-line @liferay/portal/no-global-fetch
				const accountRequest = await fetch(
					`/o/headless-admin-user/v1.0/accounts/${accountBriefs[0].id}`,
					{
						headers: {
							'accept': 'application/json',
							'x-csrf-token': Liferay.authToken,
						},
					}
				);

				// eslint-disable-next-line @liferay/portal/no-global-fetch
				const accountUsersRequest = await fetch(
					`/o/headless-admin-user/v1.0/accounts/${accountBriefs[0].id}/user-accounts`,
					{
						headers: {
							'accept': 'application/json',
							'x-csrf-token': Liferay.authToken,
						},
					}
				);

				const checkedProperties: CheckedProperties = {};

				if (accountRequest.ok) {
					const accountData = await accountRequest.json();
					const opportunitsData = await getOpportunitysfs.json();

					const accountERC = accountData.externalReferenceCode;

					const accountByOpportunity = opportunitsData.items.reduce(
						(newArray: any, data: AccountType) => {
							if (data.accountName === accountERC) {
								return [...newArray, data];
							}

							return newArray;
						},
						[]
					);

					const getAllGrowthArr: number = accountByOpportunity.reduce(
						(newValue: number, data: AccountType) =>
							newValue + data.growthArr,
						0
					);

					const getAllrenewalArr: number = accountByOpportunity.reduce(
						(newValue: number, data: AccountType) =>
							newValue + data.renewalArr,
						0
					);

					const aRRAmount = getAllGrowthArr + getAllrenewalArr;

					const dataResults = {
						aRRAmountTotal: aRRAmount,
						growthArrTotal: getAllGrowthArr,
						renewalArrTotal: getAllrenewalArr,
					};
					setTotalAmount(dataResults);

					if (
						accountData.partnerLevel !==
						PartnershipLevels.AUTHORIZED
					) {
						checkedProperties['solutionDeliveryCertification'] =
							accountData.solutionDeliveryCertification;

						checkedProperties['marketingPlan'] =
							accountData.marketingPlan;

						checkedProperties['marketingPerformance'] = Boolean(
							accountData.marketingPerformance
						);

						if (
							accountData.partnerLevel === PartnershipLevels.GOLD
						) {
							const hasMatchingARR =
								getAllGrowthArr >=
								partnerLevelProperties[accountData.partnerLevel]
									.goalARR;

							const hastMatchingNPOrNB =
								accountData.newProjectExistingBusiness >=
								partnerLevelProperties[accountData.partnerLevel]
									.newProjectExistingBusiness;

							checkedProperties['arr'] =
								hasMatchingARR || hastMatchingNPOrNB;
						}

						if (
							accountData.partnerLevel ===
								PartnershipLevels.PLATINUM &&
							aRRAmount > 0
						) {
							checkedProperties['arr'] = true;
						}

						if (accountUsersRequest.ok) {
							const {
								items: accountUsers,
							} = await accountUsersRequest.json();

							const countHeadcount = {
								partnerMarketingUser: 0,
								partnerSalesUser: 0,
							};

							accountUsers.forEach((user: any) => {
								if (
									user.accountBriefs[0].roleBriefs.find(
										(role: any) =>
											role.name ===
											PartnerRoles.MARKETING_USER
									)
								) {
									countHeadcount['partnerMarketingUser'] += 1;
								}

								if (
									user.accountBriefs[0].roleBriefs.find(
										(role: any) =>
											role.name ===
											PartnerRoles.SALES_USER
									)
								) {
									countHeadcount['partnerSalesUser'] += 1;
								}
							});

							if (
								countHeadcount.partnerMarketingUser >=
									partnerLevelProperties[
										accountData.partnerLevel
									].partnerMarketingUser &&
								countHeadcount.partnerSalesUser >=
									partnerLevelProperties[
										accountData.partnerLevel
									].partnerSalesUser
							) {
								checkedProperties['headcount'] = true;
							}

							setHeadcount(countHeadcount);
						}
					}

					setData(accountData);
					setOpportunitysfs(accountByOpportunity);
					setCompleted(checkedProperties);
				}
			}
		}
		setLoading(false);
	};

	useEffect(() => {
		getAccountInformation();
	}, []);

	const BuildPartnershipLevel = () => {
		if (loading) {
			return <ClayLoadingIndicator className="mb-10 mt-9" size="md" />;
		}

		if (!data) {
			return (
				<ClayAlert
					className="mb-8 mt-8 mx-auto text-center w-50"
					displayType="info"
					title="Info:"
				>
					No Data Available
				</ClayAlert>
			);
		}

		return (
			<PartnershipLevel
				completed={completed}
				data={data}
				headcount={headcount}
				opportunity={opportunitysfs}
				totalAmount={totalAmount}
			/>
		);
	};

	return (
		<ClayIconProvider>
			<Container title="Partnership Level">
				<BuildPartnershipLevel />
			</Container>
		</ClayIconProvider>
	);
};

export default LevelChart;
