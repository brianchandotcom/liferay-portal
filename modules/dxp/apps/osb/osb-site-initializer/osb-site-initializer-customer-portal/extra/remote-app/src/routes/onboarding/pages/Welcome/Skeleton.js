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
import Layout from '../../../../common/components/onboarding/components/Layout';

const WelcomeSkeleton = () => {
	return (
		<Layout
			className="align-items-center d-flex flex-column pt-5 px-6"
			footerProps={{
				middleButton: <Skeleton.Rounded height={48} width={110} />,
			}}
			headerSkeleton={
				<div className="p-4">
					<Skeleton className="mb-4" height={8} width={105} />

					<Skeleton height={16} width={425} />
				</div>
			}
		>
			<Skeleton.Square height={200} width={320} />

			<Skeleton
				align="center"
				className="d-flex flex-column justify-content-center my-auto"
				count={2}
				height={8}
				width={400}
			/>
		</Layout>
	);
};
export default WelcomeSkeleton;
