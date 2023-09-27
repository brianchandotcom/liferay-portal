/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ChainedRedirectionsModal from './ChainedRedirectionsModal';

export default function ChainedRedirections({portletNamespace, ...restProps}) {
	const [redirectEntryChainCause, setRedirectEntryChainCause] = useState(
		null
	);
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
				open: (redirectEntryChainCause, callback) => {
					setCallback(() => callback);
					setRedirectEntryChainCause(redirectEntryChainCause);
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
					redirectEntryChainCause={redirectEntryChainCause}
				/>
			)}
		</>
	);
}

ChainedRedirections.propTypes = {
	portletNamespace: PropTypes.string.isRequired,
};
