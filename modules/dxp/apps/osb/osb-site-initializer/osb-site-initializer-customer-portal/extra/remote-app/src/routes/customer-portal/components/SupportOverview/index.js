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

import LiferayContact from '../LiferayContact';
import SlaCardList from '../SlaCardList';
import SupportOverviewSkeleton from './Skeleton';

const SupportOverview = ({koroneikiAccount}) => (
	<div className="container cp-project-contacs-container mb-5 mx-0">
		<div className="row">
			<div className="col-5 pb-8">
				<SlaCardList koroneikiAccount={koroneikiAccount} />
			</div>

			<div className="col-7">
				<LiferayContact koroneikiAccount={koroneikiAccount} />
			</div>
		</div>
	</div>
);

SupportOverview.Skeleton = SupportOverviewSkeleton;

export default SupportOverview;
