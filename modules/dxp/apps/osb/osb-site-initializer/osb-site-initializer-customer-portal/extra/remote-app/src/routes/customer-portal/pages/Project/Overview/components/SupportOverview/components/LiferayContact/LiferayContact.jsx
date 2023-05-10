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
