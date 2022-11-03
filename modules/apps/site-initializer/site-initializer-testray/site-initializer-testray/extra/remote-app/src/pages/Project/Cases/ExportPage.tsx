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

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import useStorage from '../../../hooks/useStorage';
import i18n from '../../../i18n';
import {STORAGE_KEYS} from '../../../util/constants';
import BuildFormCases from '../Routines/Builds/BuildForm/BuildFormCases';

const id = new Date().getTime();

const ExportPage = () => {
	const {setTabs} = useHeader();

	const [caseIds, setCaseIds] = useState<number[]>([]);
	const [, setExportCaseIds] = useStorage<number[]>(
		`${STORAGE_KEYS.EXPORT_CASE_IDS}-${id}`,
		[]
	);

	useEffect(() => {
		setTimeout(() => {
			setTabs([]);
		}, 10);
	}, [setTabs]);

	useEffect(() => {
		setExportCaseIds(caseIds);
	}, [caseIds, setExportCaseIds]);

	const {
		form: {onClose},
	} = useFormActions();

	return (
		<Container
			className="container"
			title={i18n.translate('select-cases-to-export')}
		>
			<ClayForm className="container pt-2">
				<BuildFormCases caseIds={caseIds} setCaseIds={setCaseIds} />

				<div className="mt-4">
					<ClayButton.Group spaced>
						<Link
							className={classNames('btn btn-primary', {
								disabled: !caseIds.length,
							})}
							target="_blank"
							to={`/export/${id}`}
						>
							{i18n.translate('export')}
						</Link>

						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							{i18n.translate('cancel')}
						</ClayButton>
					</ClayButton.Group>
				</div>
			</ClayForm>
		</Container>
	);
};

export default ExportPage;
