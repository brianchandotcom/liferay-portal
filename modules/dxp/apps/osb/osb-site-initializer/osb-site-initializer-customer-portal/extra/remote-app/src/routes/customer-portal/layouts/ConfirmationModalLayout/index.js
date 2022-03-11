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
import {Button} from '../../../../common/components';

const ConfirmationModalLayout = ({
	children,
	customHelper,
	footerProps,
	observer,
	onClose,
	title,
}) => {
	return (
		<ClayModal center observer={observer}>
			<div className="pt-4 px-4">
				<div className="flex-row mb-1">
					<div className="d-flex justify-content-between">
						<h2 className="text-neutral-10">{title}</h2>

						<Button
							appendIcon="times"
							aria-label="close"
							className="align-self-start"
							displayType="unstyled"
							onClick={onClose}
						/>
					</div>

					{children}
				</div>

				<div className="d-flex justify-content-end my-4">
					{footerProps?.cancelButton}

					{footerProps?.confirmationButton}
				</div>
			</div>

			{customHelper}
		</ClayModal>
	);
};

export default ConfirmationModalLayout;
