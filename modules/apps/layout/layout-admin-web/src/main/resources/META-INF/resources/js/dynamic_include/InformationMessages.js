/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLink from '@clayui/link';
import ClayPopover from '@clayui/popover';
import {useEventListener} from 'frontend-js-react-web';
import React, {useRef, useState} from 'react';

export default function ({
	linkedLayoutMessage,
	portletNamespace,
	resetPrototypeURL,
	showLinkedLayoutMessage,
	showModifiedLayoutMessage,
}) {
	const [show, setShow] = useState(false);
	const ref = useRef();

	const infoContainerId = `${portletNamespace}infoPopover`;

	useEventListener(
		'click',
		({target}) => {
			if (!target.closest(`#${infoContainerId}`)) {
				setShow(false);
			}
		},
		false,
		window
	);

	return (
		<ClayPopover
			alignPosition="bottom"
			id={infoContainerId}
			onKeyDown={(event) => {
				if (event.key === 'Escape') {
					setShow(false);

					ref.current.focus();
				}
			}}
			onShowChange={setShow}
			show={show}
			trigger={
				<ClayButtonWithIcon
					data-qa-id="info"
					displayType="unstyled"
					onClick={(event) => {
						event.stopPropagation();

						setShow((show) => !show);
					}}
					ref={(node) => {
						ref.current = node;
					}}
					small
					symbol="information-live"
					title={Liferay.Language.get('additional-information')}
				/>
			}
		>
			{showModifiedLayoutMessage && (
				<div>
					<p
						className="message-info mt-0"
						dangerouslySetInnerHTML={{
							__html: Liferay.Language.get(
								'this-page-has-been-changed-since-the-last-update-from-the-site-template'
							),
						}}
					/>

					<ClayLink
						button={{small: true}}
						displayType="primary"
						href={resetPrototypeURL}
					>
						{Liferay.Language.get('reset-changes')}
					</ClayLink>
				</div>
			)}

			{showLinkedLayoutMessage && (
				<p className="message-info my-0">{linkedLayoutMessage}</p>
			)}
		</ClayPopover>
	);
}
