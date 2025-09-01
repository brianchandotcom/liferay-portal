/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OpenToastProps, openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import {
	getFormattedLabel,
	getFormattedLink,
} from '../../../common/utils/getFormattedText';
import restoreItemAction from '../actions/restoreItemAction';

const recycleBinToastInfo = {
	className: 'recycle-bin-link',
	linkText: Liferay.Language.get('recycle-bin'),
	url: '/cms/recycle-bin',
};

export default function displayUndoDeleteSuccessToast(
	label: string,
	loadData: () => {},
	method: string,
	restoreURL: string
) {
	const {className, linkText, url} = recycleBinToastInfo;

	const formmatedRecycleBinLink = getFormattedLink(
		className,
		linkText,
		Liferay.ThemeDisplay.getPathFriendlyURLPublic() + url
	);
	const formattedItemLabel = getFormattedLabel(label);

	const openToastSuccessProps: OpenToastProps = {
		message: sub(
			Liferay.Language.get('x-was-moved-to-the-x'),
			formattedItemLabel,
			formmatedRecycleBinLink
		),
		type: 'success',
	};

	const undoButtonLabel = Liferay.Language.get('undo');
	const undoButtonClassName = 'cms-delete-item-undo-button';

	openToastSuccessProps.message =
		openToastSuccessProps.message +
		`<div class="alert-footer">
				<div class="btn-group" role="group">
					<button class="btn btn-sm btn-success alert-btn ${undoButtonClassName}">${undoButtonLabel}</button>
				</div>
		</div>`;

	async function handleOnClick() {
		await restoreItemAction(label, loadData, method, restoreURL);
	}

	openToastSuccessProps.onClick = ({event, onClose: closeToast}) => {
		const {target} = event;

		if (
			target instanceof HTMLElement &&
			target.classList.contains(undoButtonClassName)
		) {
			handleOnClick();

			// @ts-ignore

			closeToast();
		}
	};

	openToast(openToastSuccessProps);
}
