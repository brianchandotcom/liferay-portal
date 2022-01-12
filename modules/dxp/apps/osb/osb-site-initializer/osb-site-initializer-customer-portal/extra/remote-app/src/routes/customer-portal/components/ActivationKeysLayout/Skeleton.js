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

const ActivationKeysSkeleton = () => {
	return (
		<div>
			<Skeleton className="my-4" height={40} width={305} />

			<Skeleton className="mt-3" height={24} width={620} />

			<div className="d-flex mt-3">
				<div className="mr-3">
					<Skeleton className="mb-1" height={24} width={90} />

					<Skeleton.Rounded height={48} width={247} />
				</div>

				<div>
					<Skeleton className="mb-1" height={24} width={130} />

					<Skeleton height={48} width={360} />
				</div>
			</div>
		</div>
	);
};

export default ActivationKeysSkeleton;
