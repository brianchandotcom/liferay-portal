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

import {RequestStatus} from '../../../../common/enums/requestStatus';

interface IProps {
	status: RequestStatus;
}

type StatusClassname = {
	[key in RequestStatus]: string;
};

const statusClassName: StatusClassname = {
	[RequestStatus.DRAFT]: 'text-neutral-5',
	[RequestStatus.PENDING]: 'text-brand-secondary',
	[RequestStatus.APPROVED]: 'text-success',
	[RequestStatus.REQUEST_MORE_INFO]: 'text-info',
	[RequestStatus.REJECT]: 'text-danger',
	[RequestStatus.EXPIRED]: 'text-danger',
	[RequestStatus.MARKETING_DIRECTOR_REVIEW]: 'text-brand-secondary-darken-3',
	[RequestStatus.CANCELED]: 'text-neutral-10',
};

const RequestStatusBadge = ({status}: IProps) => {
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

export default RequestStatusBadge;
