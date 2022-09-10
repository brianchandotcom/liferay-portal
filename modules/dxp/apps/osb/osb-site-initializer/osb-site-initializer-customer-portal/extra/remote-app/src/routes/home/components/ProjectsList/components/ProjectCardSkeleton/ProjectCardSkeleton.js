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
import {memo} from 'react';
import {Skeleton} from '../../../../../../common/components';

const ProjectCardSkeleton = () => (
	<ClayCard className="border border-brand-primary-lighten-4 cp-project-card mb-4 mr-5 shadow-none">
		<ClayCard.Body className="mx-2 my-3 py-4">
			<ClayCard.Row className="flex-column">
				<div>
					<Skeleton className="mb-1" height={34} width={300} />
				</div>

				<div className="mt-6 pt-3">
					<Skeleton height={20} width={54} />

					<Skeleton className="mt-1" height={24} width={137} />
				</div>
			</ClayCard.Row>

			<div className="d-flex justify-content-between">
				<ClayCard.Description
					displayType="text"
					tag="div"
					title={null}
					truncate={false}
				></ClayCard.Description>
			</div>
		</ClayCard.Body>
	</ClayCard>
);

export default memo(ProjectCardSkeleton);
