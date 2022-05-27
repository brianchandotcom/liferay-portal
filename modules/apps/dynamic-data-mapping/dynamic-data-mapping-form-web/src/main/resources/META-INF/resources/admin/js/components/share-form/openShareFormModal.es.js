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

import ClayButton from '@clayui/button';
import {ClayIconSpriteContext} from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {render} from '@liferay/frontend-js-react-web';
import {sub} from 'frontend-js-web';
import React, {useRef} from 'react';
import {unmountComponentAtNode} from 'react-dom';

import {submitEmailContent} from '../../util/submitEmailContent.es';
import {ShareFormModalBody} from './ShareFormModalBody.es';

export function Modal({
	autocompleteUserURL,
	localizedName,
	onClose,
	portletNamespace,
	shareFormInstanceURL,
	url,
}) {
	const {observer} = useModal({onClose});
	const emailContentRef = useRef({
		addresses: [],
		message: sub(Liferay.Language.get('please-fill-out-this-form-x'), url),
		subject: localizedName[themeDisplay.getLanguageId()],
	});

	const visible = observer.mutation;

	if (visible) {
		return (
			<ClayModal observer={observer}>
				<ClayModal.Header>
					{Liferay.Language.get('share')}
				</ClayModal.Header>

				<ClayModal.Body>
					<ShareFormModalBody
						autocompleteUserURL={autocompleteUserURL}
						emailContent={emailContentRef}
						localizedName={localizedName}
						url={url}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								displayType="primary"
								onClick={() => {
									submitEmailContent({
										addresses:
											emailContentRef.current.addresses,
										message:
											emailContentRef.current.message,
										portletNamespace,
										shareFormInstanceURL,
										subject:
											emailContentRef.current.subject,
									});

									onClose();
								}}
							>
								{Liferay.Language.get('done')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayModal>
		);
	}

	return null;
}

let container;

export function openShareFormModal({spritemap, ...data}) {
	if (container) {
		cleanUp();
	}

	container = document.createElement('div');

	document.body.appendChild(container);

	render(
		<ClayIconSpriteContext.Provider value={spritemap}>
			<Modal onClose={cleanUp} {...data} />
		</ClayIconSpriteContext.Provider>,
		data,
		container
	);

	Liferay.once('destroyPortlet', cleanUp);
}

function cleanUp() {
	if (container) {
		unmountComponentAtNode(container);
		document.body.removeChild(container);

		container = null;
	}
}
