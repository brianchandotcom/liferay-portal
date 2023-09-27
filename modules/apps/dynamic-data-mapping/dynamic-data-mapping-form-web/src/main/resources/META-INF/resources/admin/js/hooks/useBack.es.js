/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Context as ModalContext} from '@clayui/modal';
import {useEventListener} from '@liferay/frontend-js-react-web';
import {useConfig} from 'data-engine-js-components-web';
import React, {useCallback, useContext} from 'react';

import {useAutoSave} from './useAutoSave.es';

/**
 * useBack is a hook that adds middleware to the Back button of the DXP Control
 * Menu to alert the user if there is any change in the form that has not
 * been saved.
 */
export function useBack() {
	const {portletNamespace} = useConfig();
	const [{onClose}, dispatch] = useContext(ModalContext);

	const {isSaved} = useAutoSave();

	const onBack = useCallback(
		(event) => {
			if (isSaved()) {
				return;
			}

			event.preventDefault();

			const href = event.currentTarget.href;

			dispatch({
				payload: {
					body: Liferay.Language.get(
						'any-unsaved-changes-will-be-lost-are-you-sure-you-want-to-leave'
					),
					footer: [
						null,
						null,
						<ClayButton.Group key={3} spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => {
									window.location.href = href;
								}}
							>
								{Liferay.Language.get('leave')}
							</ClayButton>

							<ClayButton displayType="primary" onClick={onClose}>
								{Liferay.Language.get('stay')}
							</ClayButton>
						</ClayButton.Group>,
					],
					header: Liferay.Language.get('leave-form'),
					size: 'sm',
				},
				type: 1,
			});
		},
		[dispatch, isSaved, onClose]
	);

	useEventListener(
		'click',
		onBack,
		true,
		document.querySelector(
			`#${portletNamespace}controlMenu .sites-control-group span .lfr-icon-item`
		)
	);
}
