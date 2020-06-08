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
import {fetch} from 'frontend-js-web';
import React, {useContext, useState} from 'react';

import ExportTranslationContext from './ExportTranslationContext.es';
import ExportTranslationModal from './ExportTranslationModal.es';

function ExportTranslation(props) {
	const [articleIds, setArticleIds] = useState();
	const [showModal, setShowModal] = useState();
	const {namespace} = useContext(ExportTranslationContext);
	const bridgeComponentId = `${namespace}ExportForTranslationComponent`;
	const [availableSourceLocales, setAvailableSourceLocales] = useState([]);

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	if (!Liferay.component(bridgeComponentId)) {
		Liferay.component(
			bridgeComponentId,
			{
				open: (articleIds) => {
					const getExportTranslationAvailableLocalesURL = Liferay.Util.PortletURL.createPortletURL(
						props.getExportTranslationAvailableLocalesURL,
						{
							articleId: articleIds[0],
						}
					);

					fetch(getExportTranslationAvailableLocalesURL.toString())
						.then((res) => res.json())
						.then((availableLocales) => {
							setAvailableSourceLocales(availableLocales);
							setArticleIds(articleIds);
							setShowModal(true);
						});
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
				<ExportTranslationModal
					{...props}
					articleIds={articleIds}
					availableSourceLocales={availableSourceLocales}
					observer={observer}
					onModalClose={onClose}
				/>
			)}
		</>
	);
}

export default function ({context, props}) {
	return (
		<ExportTranslationContext.Provider value={context}>
			<ExportTranslation {...props} />
		</ExportTranslationContext.Provider>
	);
}
