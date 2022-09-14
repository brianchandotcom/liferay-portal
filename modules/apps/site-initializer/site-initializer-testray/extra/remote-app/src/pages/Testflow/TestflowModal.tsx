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
import {useNavigate} from 'react-router-dom';

import BreadcrumbSearch from '../../components/BreadcrumbSearch';
import Form from '../../components/Form';
import Modal from '../../components/Modal';
import {FormModalOptions} from '../../hooks/useFormModal';
import i18n from '../../i18n';

type TestflowModalProps = {
	modal: FormModalOptions;
};

const MAX_ENTITIES_TO_SEARCH = 3;

const TestflowModal: React.FC<TestflowModalProps> = ({
	modal: {observer, onClose, visible},
}) => {
	const navigate = useNavigate();
	const [breadCrumb, setBreadCrumb] = useState([]);

	const [, , buildBreadcrumb] = breadCrumb as {
		label: string;
		value: number;
	}[];

	const onSubmit = () => {
		if (buildBreadcrumb.value) {
			navigate(`/testflow/${buildBreadcrumb.value}/create`);
		}
	};

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={onSubmit}
					primaryButtonProps={{
						disabled: breadCrumb.length !== MAX_ENTITIES_TO_SEARCH,
						title: i18n.translate('analyze'),
					}}
				/>
			}
			observer={observer}
			size="lg"
			title={i18n.translate('select-build')}
			visible={visible}
		>
			<BreadcrumbSearch
				maxEntitiesToSearch={MAX_ENTITIES_TO_SEARCH}
				onBreadcrumbChange={setBreadCrumb}
			/>
		</Modal>
	);
};

export default TestflowModal;
