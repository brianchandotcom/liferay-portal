/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {TestrayRequirementCase} from '../../../services/rest';
import {RequirementListView} from '../Requirements';

type CaseRequirementLinkModalProps = {
	items: TestrayRequirementCase[];
	modal: FormModalOptions;
};

export type State = {caseId?: number; requirementId?: number}[];

const CaseRequirementLinkModal: React.FC<CaseRequirementLinkModalProps> = ({
	items = [],
	modal: {observer, onClose, onSave, visible},
}) => {
	const [state, setState] = useState<State>([]);

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={() => onSave({items, state})}
					primaryButtonProps={{
						title: i18n.translate('select-requirements'),
					}}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate('select-requirements')}
			visible={visible}
		>
			<RequirementListView
				listViewProps={{
					initialContext: {
						selectedRows: items.map(
							({requirement}) => requirement?.id as number
						),
					},
					managementToolbarProps: {
						title: i18n.translate('requirements'),
					},
					onContextChange: ({selectedRows}) => {
						setState(
							selectedRows.map((requirementId) => ({
								requirementId,
							}))
						);
					},
				}}
				tableProps={{navigateTo: undefined, rowSelectable: true}}
			/>
		</Modal>
	);
};

export default withVisibleContent(CaseRequirementLinkModal);
