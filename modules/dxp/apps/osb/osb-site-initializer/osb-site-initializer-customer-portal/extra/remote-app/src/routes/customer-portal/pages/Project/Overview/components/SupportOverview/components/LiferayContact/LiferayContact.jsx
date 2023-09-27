/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../../../../../common/I18n';
import {Skeleton} from '../../../../../../../../../common/components';

const LiferayContact = ({koroneikiAccount, loading}) => (
	<div className="mb-5 ml-xl-9">
		{loading ? (
			<Skeleton className="mb-4" height={22} width={140} />
		) : (
			<h5 className="mb-4 rounded-sm text-neutral-10">
				{i18n.translate('liferay-contact')}
			</h5>
		)}

		{loading ? (
			<Skeleton height={24} width={125} />
		) : (
			<div className="font-weight-bold rounded-sm text-neutral-8 text-paragraph">
				{koroneikiAccount?.liferayContactName}
			</div>
		)}

		{loading ? (
			<Skeleton className="mt-1" height={24} width={100} />
		) : (
			koroneikiAccount?.liferayContactRole && (
				<div className="mt-1 rounded-sm text-neutral-10 text-paragraph">
					{koroneikiAccount?.liferayContactRole}
				</div>
			)
		)}

		{loading ? (
			<Skeleton className="mt-1" height={20} width={150} />
		) : (
			<div className="rounded-sm text-neutral-10 text-paragraph-sm">
				{koroneikiAccount?.liferayContactEmailAddress}
			</div>
		)}
	</div>
);

export default LiferayContact;
