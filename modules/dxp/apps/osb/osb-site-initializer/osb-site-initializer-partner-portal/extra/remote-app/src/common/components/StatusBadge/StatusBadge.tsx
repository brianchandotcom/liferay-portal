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

import ClayIcon from '@clayui/icon';

import {Status} from '../../utils/constants/status';

type StatusClassname = {
	[key in string]: string;
};

interface Props {
	status: string;
}

const statusClassName: StatusClassname = {
	[Status.DRAFT.name]: 'text-neutral-5',
	[Status.PENDING.name]: 'text-brand-secondary',
	[Status.APPROVED.name]: 'text-success',
	[Status.REQUEST_MORE_INFO.name]: 'text-info',
	[Status.REJECT.name]: 'text-danger',
	[Status.EXPIRED.name]: 'text-danger',
	[Status.MARKETING_DIRECTOR_REVIEW.name]: 'text-brand-secondary-darken-3',
	[Status.CANCELED.name]: 'text-neutral-10',
	[Status.CLAIM_PAID.name]: 'text-brand-primary-lighten-2',
	[Status.IN_FINANCE_REVIEW.name]: 'text-brand-secondary-darken-3',
	[Status.IN_DIRECTOR_REVIEW.name]: 'text-brand-secondary-darken-3',
};

const StatusBadge = ({status}: Props) => {
	return (
		<>
			<ClayIcon
				className={statusClassName[status]}
				symbol="simple-circle"
			/>
			{status}
		</>
	);
};

export default StatusBadge;
