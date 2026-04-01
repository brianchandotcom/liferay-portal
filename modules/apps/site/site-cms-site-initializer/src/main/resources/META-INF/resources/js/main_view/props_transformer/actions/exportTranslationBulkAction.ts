/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBulkActionFDSData} from '../../../common/types/BulkActionTask';
import {openActionNotAllowedModal} from '../../../common/utils/openActionNotAllowedModal';
import {openCMSModal} from '../../../common/utils/openCMSModal';
import ExportTranslationModalContent from '../../modal/ExportTranslationModalContent';
import {AdditionalProps} from '../AssetsFDSPropsTransformer';

const exportTranslationBulkAction = ({
	additionalProps,
	apiURL,
	selectedData,
}: {
	additionalProps: AdditionalProps;
	apiURL?: string;
	dataSetId?: string;
	selectedData: Required<IBulkActionFDSData>;
}) => {
	const isContent = selectedData.items.every((item) => {
		const objectFolderERC =
			item.embedded?.systemProperties?.objectDefinitionBrief
				?.objectFolderExternalReferenceCode;
		const entryFolderERC =
			item.embedded?.objectEntryFolderExternalReferenceCode;

		return (
			objectFolderERC === 'L_CMS_CONTENT_STRUCTURES' ||
			entryFolderERC === 'L_CONTENTS'
		);
	});

	if (!isContent) {
		return openActionNotAllowedModal();
	}

	return openCMSModal({
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			ExportTranslationModalContent({
				apiURL,
				availableExportFileFormats:
					additionalProps.availableExportFileFormats,
				availableSourceLocales: additionalProps.availableLocales,
				availableTargetLocales: additionalProps.availableLocales,
				closeModal,
				defaultSourceLanguageId: Liferay.ThemeDisplay.getLanguageId(),
				selectedData,
			}),
	});
};

export default exportTranslationBulkAction;
