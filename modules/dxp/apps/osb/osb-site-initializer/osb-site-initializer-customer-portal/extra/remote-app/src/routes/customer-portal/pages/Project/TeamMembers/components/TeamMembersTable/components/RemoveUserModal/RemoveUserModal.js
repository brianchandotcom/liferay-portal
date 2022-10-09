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

import {Button} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {memo} from 'react';
import i18n from '../../../../../../../../../common/I18n';

const RemoveUserModal = ({observer, onClose, onRemove, removing}) => (
	<ClayModal center className="modal-danger" observer={observer}>
		<ClayModal.Header>
			<span className="modal-title-indicator">
				<ClayIcon symbol="exclamation-full" />
			</span>

			{i18n.translate('remove-user')}
		</ClayModal.Header>

		<ClayModal.Body>
			<p className="my-5 text-neutral-10">
				{i18n.translate(
					'are-you-sure-you-want-to-remove-this-team-member-from-the-project'
				)}
			</p>
		</ClayModal.Body>

		<ClayModal.Footer
			first={
				<Button displayType="secondary" onClick={onClose}>
					{i18n.translate('cancel')}
				</Button>
			}
			last={
				<Button
					className={classNames('bg-danger d-flex ml-3', {
						'cp-deactivate-loading': removing,
					})}
					disabled={removing}
					onClick={onRemove}
				>
					{removing ? (
						<>
							<span className="cp-spinner mr-2 mt-1 spinner-border spinner-border-sm" />
							{i18n.translate('removing')}
						</>
					) : (
						`${i18n.translate('remove')}`
					)}
				</Button>
			}
		/>
	</ClayModal>
);

export default memo(RemoveUserModal);
