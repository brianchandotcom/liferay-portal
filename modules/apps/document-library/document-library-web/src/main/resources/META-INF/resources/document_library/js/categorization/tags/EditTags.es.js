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

import {useModal} from '@clayui/modal';
import React, {useState, useContext} from 'react';

import EditTagsContext from './EditTagsContext.es';
import EditTagsModal from './EditTagsModal.es';

function EditTags(props) {
	const [fileEntires, setFileEntries] = useState();
	const [selectAll, setSelectAll] = useState();
	const [folderId, setFolderId] = useState();
	const [showModal, setShowModal] = useState();
	const {namespace} = useContext(EditTagsContext);
	const bridgeComponentId = `${namespace}EditTagsComponent`;

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose
	});

	if (!Liferay.component(bridgeComponentId)) {
		Liferay.component(
			bridgeComponentId,
			{
				open: (fileEntries, selectAll, folderId) => {
					setFileEntries(fileEntries);
					setSelectAll(selectAll);
					setFolderId(folderId);
					setShowModal(true);
				}
			},
			{
				destroyOnNavigate: true
			}
		);
	}

	return (
		<>
			{showModal && (
				<EditTagsModal
					{...props}
					fileEntries={fileEntires}
					folderId={folderId}
					observer={observer}
					onModalClose={onClose}
					selectAll={selectAll}
				/>
			)}
		</>
	);
}

export default function({context, props}) {
	return (
		<EditTagsContext.Provider value={context}>
			<EditTags {...props} />
		</EditTagsContext.Provider>
	);
}
