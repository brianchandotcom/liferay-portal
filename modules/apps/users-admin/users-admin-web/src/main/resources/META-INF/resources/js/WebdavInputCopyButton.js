/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClipboardJS from 'clipboard';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useId, useRef} from 'react';

const WebdavInputCopyButton = ({label, value}) => {
	const id = useId();
	const inputRef = useRef();

	useEffect(() => {
		const clipboard = new ClipboardJS(inputRef.current, {
			text: () => value,
		});

		clipboard.on('success', () => {
			openToast({
				message: Liferay.Language.get('copied-link-to-the-clipboard'),
			});
		});

		clipboard.on('error', () => {
			openToast({
				message: Liferay.Language.get('an-error-occurred'),
				title: Liferay.Language.get('error'),
				type: 'warning',
			});
		});

		return () => clipboard.destroy();
	}, [id, value]);

	return (
		<>
			<label htmlFor={id}>{label}</label>

			<div className="webdav-url-copy-button">
				<ClayForm.Group>
					<ClayInput.Group>
						<ClayInput.GroupItem prepend>
							<ClayInput
								disabled
								id={id}
								type="text"
								value={value}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem append shrink>
							<ClayButtonWithIcon
								aria-label={sub(
									Liferay.Language.get('copy-x'),
									label
								)}
								displayType="secondary"
								ref={inputRef}
								symbol="paste"
								title={sub(
									Liferay.Language.get('copy-x'),
									label
								)}
							/>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			</div>
		</>
	);
};

export default WebdavInputCopyButton;
