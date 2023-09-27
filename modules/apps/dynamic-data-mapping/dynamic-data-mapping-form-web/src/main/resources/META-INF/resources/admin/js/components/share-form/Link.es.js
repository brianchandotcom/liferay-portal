/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import ClipboardJS from 'clipboard';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {selectText} from '../../util/dom';

const useClipboardJS = (onSuccess) => {
	useEffect(() => {
		const clipboardJS = new ClipboardJS('.ddm-copy-clipboard');

		clipboardJS.on('success', onSuccess);

		return () => {
			clipboardJS.destroy();
		};
	}, [onSuccess]);
};

const Link = ({url}) => {
	const [success, setSuccess] = useState(false);
	const inputRef = useRef(null);

	useClipboardJS(useCallback(() => setSuccess(!success), [success]));

	useEffect(() => {
		if (success) {
			selectText(inputRef.current);
		}
	}, [success]);

	return (
		<div
			className={classNames('share-form-modal-item-link form-group m-0', {
				'has-success': success,
			})}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput readOnly ref={inputRef} value={url} />

					{success && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								{Liferay.Language.get('copied-to-clipboard')}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayButton
						aria-label={
							success
								? Liferay.Language.get('copied')
								: Liferay.Language.get('copy')
						}
						className="ddm-copy-clipboard"
						data-clipboard-text={url}
						displayType={success ? 'success' : 'secondary'}
					>
						{success ? (
							<span className="pl-2 pr-2 publish-button-success-icon">
								<ClayIcon symbol="check" />
							</span>
						) : (
							<span className="publish-button-text">
								{Liferay.Language.get('copy')}
							</span>
						)}
					</ClayButton>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</div>
	);
};

export default Link;
