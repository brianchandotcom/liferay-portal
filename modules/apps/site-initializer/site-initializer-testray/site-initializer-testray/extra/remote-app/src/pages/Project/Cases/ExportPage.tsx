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

import ClayForm from '@clayui/form';
import {useEffect, useState} from 'react';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import useStorage from '../../../hooks/useStorage';
import i18n from '../../../i18n';
import {Liferay} from '../../../services/liferay';
import {STORAGE_KEYS} from '../../../util/constants';
import BuildFormCases from '../Routines/Builds/BuildForm/BuildFormCases';

const id = new Date().getTime();

const ExportPage = () => {
	const {setTabs} = useHeader();

	const [caseIds, setCaseIds] = useState<number[]>([]);
	const [, setExportCaseIds] = useStorage<number[]>(
		`${STORAGE_KEYS.EXPORT_CASE_IDS}-${id}`,
		[],
		sessionStorage
	);

	useEffect(() => {
		setTimeout(() => {
			setTabs([]);
		}, 10);
	}, [setTabs]);

	const {
		form: {onClose, submitting},
	} = useFormActions();

	const onSubmit = () => {
		if (caseIds.length) {
			setExportCaseIds(caseIds);

			return window.open(`/group/testray#/export/${id}`, '_blank');
		}

		return Liferay.Util.openToast({
			message: i18n.translate('mark-at-least-one-case-to-export'),
			type: 'danger',
		});
	};

	return (
		<Container
			className="container"
			title={i18n.translate('select-cases-to-export')}
		>
			<ClayForm className="container pt-2">
				<BuildFormCases caseIds={caseIds} setCaseIds={setCaseIds} />

				<div className="mt-4">
					<Form.Footer
						onClose={onClose}
						onSubmit={onSubmit}
						primaryButtonProps={{
							loading: submitting,
							title: 'Export',
						}}
					/>
				</div>
			</ClayForm>
		</Container>
	);
};

export default ExportPage;
