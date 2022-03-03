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
import Button from '../../../../common/components/Button';

const AnalyticsCloudModal = ({observer, onClose}) => {
	return (
		<ClayModal center observer={observer} size="md">
			<div className="m-4">
				<div className="d-flex justify-content-between">
					<div className="flex-row">
						<h1>Set up Analytics Cloud</h1>

						<p>
							We’ll need a few details to finish creating your
							Analytics Cloud Workspace.
						</p>
					</div>

					<Button
						appendIcon="times"
						aria-label="close"
						className="align-self-start"
						displayType="unstyled"
						onClick={onClose}
					/>
				</div>

				<div className="d-flex justify-content-between my-2">
					<Button displayType="unstyled" onClick={onClose}>
						Cancel
					</Button>

					<Button displayType="primary">Submit</Button>
				</div>
			</div>
		</ClayModal>
	);
};
export default AnalyticsCloudModal;
