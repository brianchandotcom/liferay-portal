/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useEffect, useState} from 'react';
import {IconBreadcrumbs} from '~/common/icons';
import i18n from '../../../../common/I18n';
import Skeleton from '../../../../common/components/Skeleton';
import useCurrentKoroneikiAccount from '../../../../common/hooks/useCurrentKoroneikiAccount';
import useKoroneikiAccounts from '../../../../common/hooks/useKoroneikiAccounts';
import PopoverIcon from '../ActivationStatus/DXPCloud/components/PopoverIcon';
import Dropdown from './components/Dropdown';

const ProjectBreadcrumb = () => {
	const [initialTotalCount, setInitialTotalCount] = useState(0);
	const [projectStatus, setProjectStatus] = useState('');

	const {
		data: currentKoroneikiAccountData,
		loading: currentKoroneikiAccountLoading,
	} = useCurrentKoroneikiAccount();

	const selectedKoroneikiAccount =
		currentKoroneikiAccountData?.koroneikiAccountByExternalReferenceCode;

	const {
		data,
		fetchMore,
		fetching,
		loading,
		onSearch,
		searching,
	} = useKoroneikiAccounts({
		selectedFilterCategory: {
			filter: (searchBuilder) => searchBuilder,
			pageSize: 5,
		},
	});

	useEffect(() => {
		if (data?.c.koroneikiAccounts.totalCount > initialTotalCount) {
			setInitialTotalCount(data.c.koroneikiAccounts.totalCount);
		}

		setProjectStatus(selectedKoroneikiAccount?.status);
	}, [
		data?.c.koroneikiAccounts.totalCount,
		initialTotalCount,
		selectedKoroneikiAccount?.status,
	]);

	if (currentKoroneikiAccountLoading || loading) {
		return <Skeleton height={30} width={264} />;
	}

	return (
		<div className="align-items-center bg-neutral-1 cp-breadcrumbs-container d-flex justify-content-between mb-3 p-3">
			<div>
				<IconBreadcrumbs />
			</div>

			<div className="cp-breadcrumbs-dropdown">
				<Dropdown
					fetching={fetching}
					initialTotalCount={initialTotalCount}
					koroneikiAccounts={data?.c.koroneikiAccounts}
					onIntersecting={() =>
						fetchMore({
							variables: {
								page: data?.c.koroneikiAccounts.page + 1,
							},
						})
					}
					onSearch={onSearch}
					searching={searching}
					selectedKoroneikiAccount={selectedKoroneikiAccount}
				/>

				<div
					className={classNames('cp-breadcrumbs-popover', {
						[`cp-breadcrumbs-popover-${projectStatus?.toLowerCase()}`]: projectStatus,
					})}
				>
					<PopoverIcon
						symbol="simple-circle"
						title={i18n.translate(`${projectStatus}`)}
					/>

					<span className="cp-breadcrumbs-status text-paragraph-sm">
						{i18n.translate(`${projectStatus}`)}
					</span>
				</div>
			</div>
		</div>
	);
};

export default ProjectBreadcrumb;
