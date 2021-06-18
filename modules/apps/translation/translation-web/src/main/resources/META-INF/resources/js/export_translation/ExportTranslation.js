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

import ExportTranslationContext from './ExportTranslationContext';
import ExportTranslationModal from './ExportTranslationModal';

function ExportTranslation(props) {
	const [keys, setKeys] = useState();
	const [showModal, setShowModal] = useState();
	const {namespace} = useContext(ExportTranslationContext);
	const bridgeComponentId = `${namespace}ExportForTranslationComponent`;
	const [availableSourceLocales, setAvailableSourceLocales] = useState([]);
	const [defaultSourceLanguageId, setDefaultSourceLanguageId] = useState(
		null
	);

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
				open: (keys) => {
					const getExportTranslationAvailableLocalesURL = Liferay.Util.PortletURL.createPortletURL(
						props.getExportTranslationAvailableLocalesURL,
						{
							key: keys[0],
						}
					);

					fetch(getExportTranslationAvailableLocalesURL.toString())
						.then((res) => res.json())
						.then(({availableLocales, defaultLanguageId}) => {
							setAvailableSourceLocales(availableLocales);
							setKeys(keys);
							setDefaultSourceLanguageId(defaultLanguageId);
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
					availableSourceLocales={availableSourceLocales}
					defaultSourceLanguageId={defaultSourceLanguageId}
					keys={keys}
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
