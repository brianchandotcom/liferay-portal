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

import React, {useState} from 'react';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {TestrayRequirementCase} from '../../../services/rest';
import {searchUtil} from '../../../util/search';
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
				tableProps={{navigateTo: undefined, rowSelectable: true}}
				variables={{
					filter: searchUtil.eq('projectId', projectId),
				}}
			/>
		</Modal>
	);
};

export default withVisibleContent(RequirementCaseLinkModal);
