/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/components/DefaultPermission.scss';

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import CMSDefaultPermissionService from '../../common/services/CMSDefaultPermissionService';
import DefaultPermissionFormContainer from './DefaultPermissionFormContainer';
import {
	AssetRoleSelectedActions,
	CMSDefaultPermissionObjectEntryDTO,
	DefaultPermissionModalContentProps,
} from './DefaultPermissionTypes';

export default function DefaultPermissionModalContent({
	actions,
	classExternalReferenceCode,
	className,
	closeModal,
	roles,
}: DefaultPermissionModalContentProps) {
	const [currentObjectEntry, setCurrentObjectEntry] =
		useState<CMSDefaultPermissionObjectEntryDTO | null>(null);
	const [currentValues, setCurrentValues] =
		useState<AssetRoleSelectedActions>({});
	const [loading, setLoading] = useState(false);

	const saveHandler = useCallback(() => {
		setLoading(true);

		if (currentObjectEntry) {
			CMSDefaultPermissionService.updateObjectEntry({
				defaultPermissions: JSON.stringify(currentValues),
				externalReferenceCode: currentObjectEntry.externalReferenceCode,
			})
				.then(() => {
					openToast({
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						type: 'success',
					});

					closeModal();
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-system-error-occurred'
						),
						type: 'danger',
					});
				})
				.finally(() => {
					setLoading(false);
				});
		}
		else {
			CMSDefaultPermissionService.addObjectEntry({
				classExternalReferenceCode,
				className,
				defaultPermissions: JSON.stringify(currentValues),
			})
				.then(() => {
					openToast({
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						type: 'success',
					});

					closeModal();
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-system-error-occurred'
						),
						type: 'danger',
					});
				})
				.finally(() => {
					setLoading(false);
				});
		}
	}, [
		classExternalReferenceCode,
		className,
		closeModal,
		currentObjectEntry,
		currentValues,
	]);

	const onChangeHandler = useCallback((data: any) => {
		setCurrentValues(data);
	}, []);

	useEffect(() => {
		setLoading(true);

		CMSDefaultPermissionService.getObjectEntry({
			classExternalReferenceCode,
			className,
		})
			.then((value) => {
				setCurrentObjectEntry(value);
				setCurrentValues(JSON.parse(value.defaultPermissions));
			})
			.catch((error) => {
				console.error(error);
			})
			.finally(() => {
				setLoading(false);
			});
	}, [classExternalReferenceCode, className]);

	return (
		<>
			<ClayModal.Header>
				{sub(
					Liferay.Language.get('edit-x'),
					Liferay.Language.get('default-permissions')
				)}
			</ClayModal.Header>

			<ClayModal.Body className="p-0">
				<DefaultPermissionFormContainer
					actions={actions}
					disabled={loading}
					onChange={onChangeHandler}
					roles={roles}
					values={currentValues}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							data-testid="button-cancel"
							disabled={loading}
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							data-testid="button-save"
							disabled={loading}
							onClick={saveHandler}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
