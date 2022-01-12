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

import ProjectCard from '../../components/ProjectCard';

const HomeSkeleton = () => {
	return (
		<div className="col-8 mx-auto pl-6">
			<div className="d-flex flex-column">
				<div className="d-flex flex-wrap home-projects">
					<ProjectCard.Skeleton />

					<ProjectCard.Skeleton />
				</div>
			</div>
		</div>
	);
};

export default HomeSkeleton;
