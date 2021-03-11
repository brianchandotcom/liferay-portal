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
import {useEventListener} from '@liferay/frontend-js-react-web';
import {openToast} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

export default function ({
	enableDisablePropagationURL,
	portletNamespace,
	readyForPropagation,
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
			<div>
				<p
					className="message-info mt-0"
					dangerouslySetInnerHTML={{
						__html: Liferay.Language.get(
							'each-page-modification-can-trigger-a-propagation-from-the-site-template-to-the-connected-sites'
						),
					}}
				/>

				<ClayLink
					button={{small: true}}
					displayType="primary"
					href={enableDisablePropagationURL}
					onClick={() => {
						openToast({
							autoClose: 10000,
							message: readyForPropagation
								? Liferay.Language.get(
										'propagation-is-disabled-connected-sites-might-not-have-been-updated-yet-propagation-is-only-triggered-when-a-site-created-from-the-template-is-visited'
								  )
								: Liferay.Language.get(
										'propagation-is-enabled-connected-sites-will-be-updated-once-a-site-page-is-visited'
								  ),
							type: 'info',
						});
					}}
				>
					{readyForPropagation
						? Liferay.Language.get('disable-propagation')
						: Liferay.Language.get('ready-for-propagation')}
				</ClayLink>
			</div>
		</ClayPopover>
	);
}
