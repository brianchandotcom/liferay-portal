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
import i18n from '../../../../../../../common/I18n';
import Button from '../../../../../../../common/components/Button';

const ConfirmationMessageModal = ({confirmKeyNoLongerVisible, observer}) => {
	return (
		<ClayModal center observer={observer}>
			<div className="pt-4 px-4">
				<div className="flex-row mb-1">
					<div className="d-flex justify-content-between">
						<h2 className="text-neutral-10">
							{i18n.translate('deactivated-key-s-request')}
						</h2>

						<Button
							appendIcon="times"
							aria-label="close"
							className="align-self-start"
							displayType="unstyled"
							onClick={confirmKeyNoLongerVisible}
						/>
					</div>

					<p className="mb-6 mt-5 text-neutral-10">
						{i18n.translate(
							'a-request-was-just-sent-to-deactivate-the-selected-activation-keys-from-now-on-they-will-be-hidden-and-no-longer-be-visible'
						)}
					</p>
				</div>

				<div className="d-flex justify-content-end my-4">
					<Button
						className="bg-danger d-flex ml-2"
						onClick={confirmKeyNoLongerVisible}
					>
						{i18n.translate('continue')}
					</Button>
				</div>
			</div>
		</ClayModal>
	);
};

export default ConfirmationMessageModal;
