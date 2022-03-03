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
import ClayModal from '@clayui/modal';

const AnalyticsCloudModal = ({observer}) => {
	return (
		<ClayModal center observer={observer} size="lg">
			<div className="ml-4 mt-4">
				<h1>Set up Analytics Cloud</h1>

				<p>
					We’ll need a few details to finish creating your Analytics
					Cloud Workspace.
				</p>
			</div>
		</ClayModal>
	);
};
export default AnalyticsCloudModal;
