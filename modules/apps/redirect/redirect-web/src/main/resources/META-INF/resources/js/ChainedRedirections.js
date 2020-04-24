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

/* Copy cat
/Users/jorgegonzalez/projects/liferay-community/liferay-portal/modules/apps/document-library/document-library-web/src/main/resources/META-INF/resources/document_library/js/checkin/Checkin.es.js
*/

import {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ChainedRedirectionsModal from './ChainedRedirectionsModal';

function ChainedRedirections({portletNamespace, ...restProps}) {
	const [showModal, setShowModal] = useState(false);
	const [callback, setCallback] = useState();
	const BRIDGE_COMPONENT_ID = `${portletNamespace}RedirectsChainedRedirections`;

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	if (!Liferay.component(BRIDGE_COMPONENT_ID)) {
		Liferay.component(
			BRIDGE_COMPONENT_ID,
			{
				open: (callback) => {
					setCallback(() => callback);
					setShowModal(true);
				},
			},
			{
				destroyOnNavigate: true,
			}
		);
	}

	return (
		<>
			{showModal && (
				<ChainedRedirectionsModal
					{...restProps}
					callback={callback}
					observer={observer}
					onModalClose={onClose}
				/>
			)}
		</>
	);
}

ChainedRedirections.propTypes = {
	portletNamespace: PropTypes.string.isRequired,
};

export default function (props) {
	return (
		<ChainedRedirections
			{...props}
			portletNamespace={`_${props.portletNamespace}_`}
		/>
	);
}
