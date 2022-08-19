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
import {useParams} from 'react-router-dom';

import Form from '../../../../components/Form';
import Container from '../../../../components/Layout/Container';
import ListView from '../../../../components/ListView';
import Modal from '../../../../components/Modal';
import {withVisibleContent} from '../../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';
import {filters} from '../../../../schema/filter';
import {searchUtil} from '../../../../util/search';
import useSuiteActions from '../../Suites/useSuiteActions';

type BuildSelectSuitesModalProps = {
	modal: FormModalOptions;
};

const BuildSelectSuitesModal: React.FC<BuildSelectSuitesModalProps> = ({
	modal: {observer, onClose, onSave, visible},
}) => {
	const [state, setState] = useState<any>({});
	const {projectId} = useParams();

	const {actions} = useSuiteActions();

	return (
		<Modal
			last={
				<Form.Footer
					isModal
					onClose={onClose}
					onSubmit={() => onSave(state)}
					primaryButtonTitle={i18n.translate('select-suites')}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate('select-suites')}
			visible={visible}
		>
			<Container>
				<ListView
					managementToolbarProps={{
						filterFields: filters.suites as any,
						title: i18n.translate('suites'),
					}}
					onContextChange={(selectedRows) => setState(selectedRows)}
					resource="/suites"
					tableProps={{
						actions,
						columns: [
							{
								clickable: true,
								key: 'name',
								sorteable: true,
								value: i18n.translate('name'),
							},
							{
								key: 'description',
								value: i18n.translate('description'),
							},
						],
						rowSelectable: true,
					}}
					variables={{
						filter: searchUtil.eq('projectId', projectId as string),
					}}
				/>
			</Container>
		</Modal>
	);
};

export default withVisibleContent(BuildSelectSuitesModal);
