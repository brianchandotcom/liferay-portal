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

import ClayCard from '@clayui/card';
import Skeleton from '../../../../common/components/Skeleton';

const ProjectCardSkeleton = () => {
	return (
		<ClayCard className="m-0 project-card">
			<ClayCard.Body className="d-flex flex-column h-100 justify-content-between">
				<ClayCard.Description displayType="title" tag="div">
					<Skeleton height={32} width={136} />
				</ClayCard.Description>

				<div className="d-flex justify-content-between">
					<ClayCard.Description
						displayType="text"
						tag="div"
						title={null}
						truncate={false}
					>
						<Skeleton.Rounded height={20} width={49} />

						<Skeleton className="my-1" height={24} width={84} />
					</ClayCard.Description>

					<Skeleton className="my-1" height={24} width={75} />
				</div>
			</ClayCard.Body>
		</ClayCard>
	);
};

export default ProjectCardSkeleton;
