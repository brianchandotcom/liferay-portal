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
import {Skeleton} from '../../../../common/components';
import Layout from '../../../../common/containers/setup-forms/Layout';

const GenerateNewKeySkeleton = () => {
	return (
		<Layout
			footerProps={{
				footerClass: 'mx-5 mb-2',

				leftButton: <Skeleton.Rounded height={48} width={110} />,
				middleButton: <Skeleton.Rounded height={48} width={110} />,
			}}
			headerSkeleton={
				<div className="mb-3 ml-5 mt-4 p-4">
					<Skeleton className="mb-2" height={30} width={425} />

					<Skeleton className="mb-4" height={16} width={390} />
				</div>
			}
			layoutType="cp-generateKey"
		>
			<div className="px-6">
				<div className="d-flex justify-content-between mb-2">
					<div className="mr-3">
						<Skeleton className="mb-1" height={16} width={105} />

						<Skeleton className="mb-4" height={40} width={250} />
					</div>

					<div>
						<Skeleton className="mb-1" height={16} width={105} />

						<Skeleton className="mb-4" height={40} width={250} />
					</div>
				</div>

				<div className="mt-4 w-100">
					<Skeleton className="mb-1" height={16} width={105} />

					<Skeleton className="mb-5" height={40} />
				</div>

				<div>
					<Skeleton className="mb-3 mt-4" height={16} width={105} />

					<div className="w-100">
						<Skeleton className="mb-2" height={96} />

						<Skeleton className="mb-2" height={96} />

						<Skeleton className="mb-2" height={96} />
					</div>
				</div>
			</div>
		</Layout>
	);
};

export default GenerateNewKeySkeleton;
