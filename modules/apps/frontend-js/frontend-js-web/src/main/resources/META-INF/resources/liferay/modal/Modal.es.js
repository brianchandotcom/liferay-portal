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

import ClayModal, {useModal} from '@clayui/modal';
import {render} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const openModal = props => {
	// Mount in detached node; Clay will take care of appending to `document.body`.
	// See: https://github.com/liferay/clay/blob/master/packages/clay-shared/src/Portal.tsx
	render(Modal, props, document.createElement('div'));
};

const Modal = ({id, size, title, url}) => {
	const [visible, setVisible] = useState(true);

	const {observer} = useModal({
		onClose: () => {
			setVisible(false);
		},
	});

	const getIframeUrl = () => {
		if (!url) {
			return null;
		}

		const iframeURL = new URL(url);

		const namespace = iframeURL.searchParams.get('p_p_id');

		iframeURL.searchParams.set(
			`_${namespace}_bodyCssClass`,
			'dialog-iframe-popup'
		);

		return iframeURL.toString();
	};

	return (
		<>
			{visible && (
				<ClayModal id={id} observer={observer} size={size}>
					<ClayModal.Header>{title}</ClayModal.Header>
					<ClayModal.Body url={getIframeUrl()} />
				</ClayModal>
			)}
		</>
	);
};

Modal.propTypes = {
	id: PropTypes.string,
	size: PropTypes.oneOf(['full-screen', 'lg', 'sm']),
	title: PropTypes.string,
	url: PropTypes.string,
};

export {Modal, openModal};
