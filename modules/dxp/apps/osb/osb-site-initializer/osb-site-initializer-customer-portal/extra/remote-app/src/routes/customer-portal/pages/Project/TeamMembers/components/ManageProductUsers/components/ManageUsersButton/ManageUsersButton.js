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

const ManageUsersButton = ({href, title}) => (
	<a
		className="align-items-stretch border border-secondary btn d-flex mr-3 p-2 text-neutral-10"
		href={href}
		rel="noopener noreferrer"
		target="_blank"
	>
		<h6 className="font-weight-semi-bold m-0 pr-1">{title}</h6>

		<span className="inline-item inline-item-after mt-0">
			<ClayIcon className="cp-manage-users-icon" symbol="shortcut" />
		</span>
	</a>
);

export default ManageUsersButton;
