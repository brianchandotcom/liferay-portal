/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {openConfirmModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';
import {config} from '../config/index';
import {useSelector} from '../contexts/StoreContext';

export function onDiscardDraft(
	event: React.MouseEvent<HTMLButtonElement, MouseEvent>,
	form?: HTMLFormElement
) {
	return openConfirmModal({
		message: Liferay.Language.get(
			'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
		),
		onConfirm: (isConfirmed) => {
			if (!isConfirmed) {
				event.preventDefault();
			}
			else if (form) {
				form.submit();
			}
		},
	});
}

export function useDisabledDiscardDraft() {
	const [enableDiscard, setEnableDiscard] = useState(false);
	const store = useSelector((state) => state);

	const {network} = store;

	useEffect(() => {
		setEnableDiscard(
			network.status === SERVICE_NETWORK_STATUS_TYPES.draftSaved ||
				store.draft ||
				config.isConversionDraft
		);
	}, [network, store.draft]);

	return !enableDiscard;
}

export default function DiscardDraftButton() {
	const disabledDiscardDraft = useDisabledDiscardDraft();

	let draftButtonLabel = Liferay.Language.get('discard-draft');

	if (config.isConversionDraft) {
		draftButtonLabel = Liferay.Language.get('discard-conversion-draft');
	}
	else if (config.singleSegmentsExperienceMode) {
		draftButtonLabel = Liferay.Language.get('discard-variant');
	}

	return (
		<ClayButton
			disabled={disabledDiscardDraft}
			displayType="secondary"
			onClick={onDiscardDraft}
			size="sm"
			type="submit"
		>
			{draftButtonLabel}
		</ClayButton>
	);
}
