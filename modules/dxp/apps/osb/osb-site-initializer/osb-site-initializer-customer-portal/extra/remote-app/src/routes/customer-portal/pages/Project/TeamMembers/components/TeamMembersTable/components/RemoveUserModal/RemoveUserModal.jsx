/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '@clayui/core';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {memo} from 'react';
import i18n from '../../../../../../../../../common/I18n';

const RemoveUserModal = ({
	children,
	modalTitle,
	observer,
	onClose,
	onRemove,
	removing,
}) => (
	<ClayModal center className="remove-user-modal" observer={observer}>
		<ClayModal.Header className="h-100 p-4">
			<h2 className="mb-0 text-neutral-10">{modalTitle}</h2>
		</ClayModal.Header>

		<ClayModal.Body className="px-4 py-3">{children}</ClayModal.Body>

		<ClayModal.Footer
			className="p-4"
			last={
				<div className="d-flex justify-content-end">
					<Button displayType="secondary" onClick={onClose}>
						{i18n.translate('cancel')}
					</Button>

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
				</div>
			}
		/>
	</ClayModal>
);

export default memo(RemoveUserModal);
