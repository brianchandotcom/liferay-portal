/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal, {useModal} from '@clayui/modal';
import {API} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {FormEvent, useEffect, useState} from 'react';

import {FormDataJSONFormat, jsonToFormData} from '../../utils/formData';
import {ModalImportContent} from './ModalImportContent';
import {ModalImportWarning} from './ModalImportWarning';

interface ModalImportProps {
	JSONInputId: string;
	apiURL: string;
	handleOnClose?: () => void;
	importExtendedInfo?: {
		key: string;
		value: string;
	};
	importURL: string;
	label: string;
	nameMaxLength: string;
	portletNamespace: string;
	showModal?: boolean;
	title: string;
}

export type TFile = {
	fileName?: string;
	inputFile?: File | null;
};

export default function ModalImport({
	JSONInputId,
	apiURL,
	handleOnClose,
	importExtendedInfo,
	importURL,
	label,
	nameMaxLength,
	portletNamespace,
	showModal,
	title,
}: ModalImportProps) {
	const [error, setError] = useState<string>('');
	const [externalReferenceCode, setExternalReferenceCode] = useState<string>(
		''
	);
	const [{fileName, inputFile}, setFile] = useState<TFile>({});
	const [importFormData, setImportFormData] = useState<FormData>();
	const importModalComponentId = `${portletNamespace}importModal`;
	const [name, setName] = useState('');
	const [visible, setVisible] = useState(showModal ?? false);
	const [warningModalVisible, setWarningModalVisible] = useState(false);

	const handleImport = async (formData: FormData) => {
		try {
			await API.save({
				item: formData,
				method: 'POST',
				url: importURL,
			});

			window.location.reload();
		}
		catch (error) {
			setError((error as Error).message);
		}
	};

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			setError('');
			setExternalReferenceCode('');
			setFile({
				fileName: '',
				inputFile: null,
			});
			setName('');
			setWarningModalVisible(false);

			if (handleOnClose) {
				handleOnClose();
			}
		},
	});

	const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const formData = new FormData(event.currentTarget);
		const formDataObject: FormDataJSONFormat = {};
		formData.forEach((value, key) => {
			if (key.includes(JSONInputId)) {
				formDataObject[key] = inputFile as File;

				return;
			}

			formDataObject[key] = value;

			return;
		});

		if (Liferay.FeatureFlags['LPS-148856'] && importExtendedInfo) {
			formDataObject[importExtendedInfo.key] = importExtendedInfo.value;
		}

		const newFormData = jsonToFormData(formDataObject);

		const response = await fetch(`${apiURL}${externalReferenceCode}`);

		if (response.status === 204 || response.status === 404) {
			handleImport(newFormData);
		}
		else {
			setImportFormData(newFormData);
			setWarningModalVisible(true);
		}
	};

	useEffect(() => {
		Liferay.component(
			importModalComponentId,
			{
				open: () => {
					setVisible(true);
				},
			},
			{
				destroyOnNavigate: true,
			}
		);

		return () => Liferay.destroyComponent(importModalComponentId);
	}, [importModalComponentId, setVisible]);

	return visible ? (
		<ClayModal
			center
			observer={observer}
			status={warningModalVisible ? 'warning' : 'info'}
		>
			{warningModalVisible ? (
				<ModalImportWarning
					handleImport={() =>
						handleImport(importFormData as FormData)
					}
					handleOnClose={onClose}
					label={label}
				/>
			) : (
				<ModalImportContent
					JSONInputId={JSONInputId}
					apiURL={apiURL}
					error={error}
					externalReferenceCode={externalReferenceCode}
					fileName={fileName as string}
					handleOnClose={onClose}
					handleSubmit={handleSubmit}
					importURL={importURL}
					inputFile={inputFile as File}
					label={label}
					name={name}
					nameMaxLength={nameMaxLength}
					portletNamespace={portletNamespace}
					setError={setError}
					setExternalReferenceCode={setExternalReferenceCode}
					setFile={setFile}
					setName={setName}
					title={title}
				/>
			)}
		</ClayModal>
	) : null;
}
