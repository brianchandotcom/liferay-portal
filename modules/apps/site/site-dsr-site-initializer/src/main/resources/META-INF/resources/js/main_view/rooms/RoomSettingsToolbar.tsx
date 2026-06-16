/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLink from '@clayui/link';
import ClayToolbar from '@clayui/toolbar';
import {sessionStorage} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

const SUCCESS_MESSAGE_SESSION_KEY =
	'com.liferay.site.dsr.site.initializer.roomSettingsSuccessMessage';

export default function RoomSettingsToolbar({
	backURL,
	headerTitle,
}: {
	backURL: string;
	headerTitle: string;
}) {
	const [formId, setFormId] = useState<string | undefined>();

	const getForm = useCallback((): HTMLFormElement | null => {
		let form = document.querySelector('.lfr-main-form-container');

		if (!form) {
			form = document.querySelector('.lfr-layout-structure-item-form');
		}

		return form as HTMLFormElement | null;
	}, []);

	const handleCancelClick = useCallback(() => {
		sessionStorage.removeItem(SUCCESS_MESSAGE_SESSION_KEY);
	}, []);

	const handleSaveClick = useCallback(() => {
		const form = getForm();

		if (form?.checkValidity?.()) {
			sessionStorage.setItem(
				SUCCESS_MESSAGE_SESSION_KEY,
				Liferay.Language.get('your-request-completed-successfully'),
				sessionStorage.TYPES.NECESSARY
			);
		}
	}, [getForm]);

	useEffect(() => {
		const form = getForm();

		if (form) {
			setFormId(form.id);
		}

		// A message still present on the editor means the previous save did not
		// redirect (it failed validation), so discard it to avoid showing a
		// stale success message later.

		sessionStorage.removeItem(SUCCESS_MESSAGE_SESSION_KEY);
	}, [getForm]);

	return (
		<ClayToolbar className="bg-white px-md-4 sticky-top top-bar">
			<div className="container-fluid">
				<ClayToolbar.Nav>
					<ClayToolbar.Item>
						<ClayToolbar.Action
							aria-label={Liferay.Language.get('back')}
							href={backURL}
							onClick={handleCancelClick}
							symbol="angle-left"
							title={Liferay.Language.get('back')}
						/>
					</ClayToolbar.Item>

					<ClayToolbar.Item className="text-left" expand>
						<ClayToolbar.Section>
							<h1 className="font-weight-semi-bold m-0 text-5 text-dark">
								{headerTitle}
							</h1>
						</ClayToolbar.Section>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<ClayLink
							button
							displayType="secondary"
							href={backURL}
							onClick={handleCancelClick}
							small
						>
							{Liferay.Language.get('cancel')}
						</ClayLink>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<ClayButton
							form={formId}
							onClick={handleSaveClick}
							size="sm"
							type="submit"
						>
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayInput
							form={formId}
							name="redirect"
							type="hidden"
							value={backURL}
						/>
					</ClayToolbar.Item>
				</ClayToolbar.Nav>
			</div>
		</ClayToolbar>
	);
}
