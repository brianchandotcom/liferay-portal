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

import {useMemo} from 'react';
import i18n from '../../../../../../../common/I18n';
import Skeleton from '../../../../../../../common/components/Skeleton';
import {PRODUCT_TYPES} from '../../../../../utils/constants/productTypes';
import ManageUsersButton from './components/ManageUsersButton/ManageUsersButton';
import useActiveAccountSubscriptionGroups from './hooks/useActiveAccountSubscriptionGroups';

const ManageProductUsers = ({koroneikiAccount, loading}) => {
	const {
		data,
		loading: accountSubscriptionGroupsLoading,
	} = useActiveAccountSubscriptionGroups(
		koroneikiAccount?.accountKey,
		loading,
		[
			PRODUCT_TYPES.analyticsCloud,
			PRODUCT_TYPES.dxpCloud,
			PRODUCT_TYPES.liferayExperienceCloud,
		]
	);

	const accountSubscriptionGroups = data?.c.accountSubscriptionGroups.items;
	const accountSubscriptionGroupLiferayExperienceCloud = useMemo(
		() =>
			accountSubscriptionGroups?.find(
				({name}) => name === PRODUCT_TYPES.liferayExperienceCloud
			),
		[accountSubscriptionGroups]
	);

	const getManageUsersButton = () => {
		if (accountSubscriptionGroupLiferayExperienceCloud) {
			return (
				<ManageUsersButton
					href={
						accountSubscriptionGroupLiferayExperienceCloud.manageContactsURL
					}
					title={i18n.translate(
						'manage-liferay-experience-cloud-users'
					)}
				/>
			);
		}

		return (
			<div className="d-flex">
				{accountSubscriptionGroups?.map(
					({manageContactsURL, name}, index) => {
						if (name === PRODUCT_TYPES.dxpCloud) {
							return (
								<ManageUsersButton
									href={manageContactsURL}
									key={index}
									title={i18n.translate(
										'manage-lxc-sm-users'
									)}
								/>
							);
						}

						return (
							<ManageUsersButton
								href={manageContactsURL}
								key={index}
								title={i18n.translate(
									'manage-analytics-cloud-users'
								)}
							/>
						);
					}
				)}
			</div>
		);
	};

	return (
		<div className="bg-brand-primary-lighten-6 cp-manage-product-users mt-5 p-4 rounded-lg">
			{accountSubscriptionGroupsLoading ? (
				<Skeleton height={25} width={224} />
			) : (
				<h4 className="mb-0">
					{accountSubscriptionGroupLiferayExperienceCloud
						? i18n.translate(
								'manage-liferay-experience-cloud-users'
						  )
						: i18n.translate('manage-product-users')}
				</h4>
			)}

			{accountSubscriptionGroupsLoading ? (
				<Skeleton className="mb-3 mt-2" height={20} width={320} />
			) : (
				<p className="mt-2 text-neutral-7 text-paragraph-sm">
					{i18n.translate(
						'manage-roles-and-permissions-of-users-within-each-product'
					)}
				</p>
			)}

			{accountSubscriptionGroupsLoading ? (
				<Skeleton height={34} width={210} />
			) : (
				getManageUsersButton()
			)}

			{!accountSubscriptionGroupsLoading &&
				!accountSubscriptionGroups?.length && (
					<p className="mb-0 text-neutral-7 text-paragraph-sm">
						There is no active product
					</p>
				)}
		</div>
	);
};

export default ManageProductUsers;
