/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider, useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import ExportFormModal from './ExportFormModal';

const ExportForm: React.FC<IProps> = ({
	csvExport,
	fileExtensions,
	portletNamespace,
}) => {
	const [exportFormURL, setExportFormURL] = useState<string>('');
	const [visibleModal, setVisibleModal] = useState<boolean>(false);

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		const openExportFormModal = ({
			exportFormURL,
		}: {
			exportFormURL: string;
		}) => {
			setExportFormURL(exportFormURL);
			setVisibleModal(true);
		};

		Liferay.on('openExportFormModal', openExportFormModal);

		return () => {
			Liferay.detach('openExportFormModal');
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ExportFormModal
					csvExport={csvExport}
					exportFormURL={exportFormURL}
					fileExtensions={fileExtensions}
					observer={observer}
					onClose={onClose}
					portletNamespace={portletNamespace}
				/>
			)}
		</ClayModalProvider>
	);
};

interface IProps {
	csvExport: string;
	fileExtensions: string[];
	portletNamespace: string;
}

export default ExportForm;
