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

import {ClayModalProvider, useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import ExportFormModal from './ExportFormModal';
import {TFileExtensions} from './types';

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
	fileExtensions: TFileExtensions;
	portletNamespace: string;
}

export default ExportForm;
