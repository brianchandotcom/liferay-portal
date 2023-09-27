/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {useContext, useEffect, useState} from 'react';

import EditCategoriesContext from './EditCategoriesContext.es';
import EditCategoriesModal from './EditCategoriesModal.es';

function EditCategories(props) {
	const [fileEntries, setFileEntries] = useState();
	const [selectAll, setSelectAll] = useState();
	const [folderId, setFolderId] = useState();
	const [showModal, setShowModal] = useState();
	const {namespace} = useContext(EditCategoriesContext);

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	useEffect(() => {
		const bridgeComponentId = `${namespace}EditCategoriesComponent`;

		if (!Liferay.component(bridgeComponentId)) {
			Liferay.component(
				bridgeComponentId,
				{
					open: (fileEntries, selectAll, folderId) => {
						setFileEntries(fileEntries);
						setSelectAll(selectAll);
						setFolderId(folderId);
						setShowModal(true);
					},
				},
				{
					destroyOnNavigate: true,
				}
			);
		}

		return () => {
			Liferay.destroyComponent(bridgeComponentId);
		};
	}, [namespace]);

	return (
		<>
			{showModal && (
				<EditCategoriesModal
					{...props}
					fileEntries={fileEntries}
					folderId={folderId}
					observer={observer}
					onModalClose={onClose}
					selectAll={selectAll}
				/>
			)}
		</>
	);
}

export default function ({context, props}) {
	return (
		<EditCategoriesContext.Provider value={context}>
			<EditCategories {...props} />
		</EditCategoriesContext.Provider>
	);
}
