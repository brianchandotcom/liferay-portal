/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import SearchBuilder from '../../../core/SearchBuilder';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {TestrayRequirementCase} from '../../../services/rest';
import {CaseListView} from '../Cases';

type RequirementCaseLinkModalProps = {
	items: TestrayRequirementCase[];
	modal: FormModalOptions;
	projectId: string;
};

export type State = {caseId?: number; requirementId?: number}[];

const RequirementCaseLinkModal: React.FC<RequirementCaseLinkModalProps> = ({
	items,
	modal: {observer, onClose, onSave, visible},
	projectId,
}) => {
	const [state, setState] = useState<State>([]);

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={() => onSave({items, state})}
					primaryButtonProps={{title: i18n.translate('select-cases')}}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate('select-cases')}
			visible={visible}
		>
			<CaseListView
				listViewProps={{
					initialContext: {
						selectedRows: items.map(
							({case: Case}) => Case?.id as number
						),
					},
					managementToolbarProps: {
						title: i18n.translate('cases'),
					},
					onContextChange: (context) => {
						setState(
							context.selectedRows.map((caseId) => ({caseId}))
						);
					},
				}}
				tableProps={{rowSelectable: true}}
				variables={{
					filter: SearchBuilder.eq('projectId', projectId),
				}}
			/>
		</Modal>
	);
};

export default withVisibleContent(RequirementCaseLinkModal);
