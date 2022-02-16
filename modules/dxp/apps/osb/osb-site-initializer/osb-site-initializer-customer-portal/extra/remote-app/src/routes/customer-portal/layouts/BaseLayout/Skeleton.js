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

import QuickLinksPanel from '../../containers/QuickLinksPanel';
import SideMenu from '../../containers/SideMenu';

const LayoutSkeleton = ({children}) => {
	return (
		<div className="d-flex position-relative w-100">
			<SideMenu.Skeleton />

			<div className="d-flex flex-fill pt-4">
				<div className="w-100">{children}</div>

				<QuickLinksPanel.Skeleton />
			</div>
		</div>
	);
};

export default LayoutSkeleton;
