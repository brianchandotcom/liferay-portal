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
import {Button} from '../../../../components';

const ConfirmationMessageModal = ({handlePage, observer}) => {
	return (
		<ClayModal center observer={observer}>
			<div className="d-flex flex-column p-4">
				<div className="mb-5">
					<p className="h2 mb-1">Set up Analytics Cloud</p>

					<p className="text-paragraph-sm">
						We’ll need a few details to finish creating your
						Analytics Cloud Workspace.
					</p>
				</div>

				<div className="mb-3 mt-1">
					<p className="h5">Thank you for submitting this request!</p>

					<p>
						Your Analytics Cloud workspace will be provisioned in
						1-2 business days. An email will be sent once your
						workspace is ready.
					</p>
				</div>

				<div className="d-flex justify-content-center mb-1">
					<Button onClick={handlePage}>Done</Button>
				</div>
			</div>
		</ClayModal>
	);
};

export default ConfirmationMessageModal;
