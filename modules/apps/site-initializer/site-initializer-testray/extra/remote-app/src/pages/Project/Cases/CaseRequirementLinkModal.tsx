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
import {RequirementListView} from '../Requirements';

type CaseRequirementLinkModalProps = {
	modal: FormModalOptions;
};

export type State = {caseId?: number; requirementId?: number}[];

const CaseRequirementLinkModal: React.FC<CaseRequirementLinkModalProps> = ({
	modal: {observer, onClose, onSave, visible},
}) => {
	const [state, setState] = useState<State>([]);

	return (
		<Modal
			last={
				<Form.Footer
					isModal
					onClose={onClose}
					onSubmit={() => onSave(state)}
					primaryButtonTitle={i18n.translate('select-requirements')}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate('select-requirements')}
			visible={visible}
		>
			<RequirementListView
				listViewProps={{
					managementToolbarProps: {
						title: i18n.translate('requirements'),
					},
					onContextChange: ({selectedRows}) =>
						setState(
							selectedRows.map((requirementId) => ({
								requirementId,
							}))
						),
				}}
				tableProps={{navigateTo: undefined, rowSelectable: true}}
			/>
		</Modal>
	);
};

export default withVisibleContent(CaseRequirementLinkModal);
