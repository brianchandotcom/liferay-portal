/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBulkActionFDSData} from '../../../common/types/BulkActionTask';
import {OBJECT_DEFINITION_CLASS_NAME} from '../../../common/utils/constants';
import {openCMSModal} from '../../../common/utils/openCMSModal';
import AssignDefaultWorkflowModalContent, {
	StructureWorkflowItem,
} from '../../modal/AssignDefaultWorkflowModalContent';
import {triggerAssetBulkAction} from './triggerAssetBulkAction';

export default function assignStructureDefaultWorkflowBulkAction({
	apiURL,
	selectedData,
	structureWorkflows,
}: {
	apiURL?: string;
	selectedData: IBulkActionFDSData;
	structureWorkflows: StructureWorkflowItem[];
}) {
	try {
		openCMSModal({
			contentComponent: ({closeModal}: {closeModal: () => void}) =>
				AssignDefaultWorkflowModalContent({
					closeModal,
					structureWorkflows,
					submitModal(workflow) {
						selectedData.items = selectedData.items?.map(
							(item) => ({
								...item,
								className: OBJECT_DEFINITION_CLASS_NAME,
							})
						);

						triggerAssetBulkAction({
							apiURL,
							keyValues: {
								workflow,
							},
							selectedData,
							type: 'AssignStructureDefaultWorkflowBulkSelectionAction',
						});
					},
				}),
			size: 'md',
		});
	}
	catch (error) {
		console.error('Error assigning workflow:', error);

		Liferay.Util.openToast({
			message: Liferay.Language.get('an-error-occurred'),
			type: 'danger',
		});
	}
}
