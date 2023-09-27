/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {STORAGE_KEYS} from '~/core/Storage';
import {CONSENT_TYPE} from '~/util/enum';

import Container from '../../../components/Layout/Container';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import useStorage from '../../../hooks/useStorage';
import i18n from '../../../i18n';
import BuildFormCases from '../Routines/Builds/BuildForm/BuildFormCases';

const id = new Date().getTime();

const ExportPage = () => {
	const {setTabs} = useHeader();

	const [caseIds, setCaseIds] = useState<number[]>([]);
	const [, setExportCaseIds] = useStorage<number[]>(
		`${STORAGE_KEYS.EXPORT_CASE_IDS}-${id}` as STORAGE_KEYS,
		{
			consentType: CONSENT_TYPE.NECESSARY,
			initialValue: [],
			storageType: 'persisted',
		}
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
