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

import Skeleton from '../../../../common/components/Skeleton';

const ProjectContactsSkeleton = () => {
	return (
		<div className="container mb-5 mx-0 project-contacs-container">
			<div className="row">
				<div className="col-5"></div>

				<div className="col-7">
					<Skeleton className="mb-4" height={20} width={150} />

					<Skeleton className="mb-1" height={24} width={130} />

					<Skeleton className="mb-1" height={24} width={144} />

					<Skeleton height={16} width={188} />
				</div>
			</div>
		</div>
	);
};

export default ProjectContactsSkeleton;
