import React, { useContext } from "react";
import {ClayButtonWithIcon} from '@clayui/button';
import lang from '../../../../util/lang';
import {
	publishDefinitionRequest,
	retrieveDefinitionRequest,
	saveDefinitionRequest,
} from '../../../../util/fetchUtil';

// @ts-ignore
import {DefinitionBuilderContext} from '../../../../DefinitionBuilderContext';

interface DefinitionBuilderContextProps {
	definitionName: string
	setAlertMessage: (value: string) => void;
	setAlertType: (value: string) => void;
	setDefinitionName: (value: string) => void;
	setShowAlert: (value: boolean) => void;
	setVersion: (value: number) => void;
}

interface VersionRowProps {
	versionNumber: number
}

export function VersionRow({versionNumber}: VersionRowProps) {
	const { 
		definitionName,
		setAlertMessage,
		setAlertType,
		setDefinitionName,
		setShowAlert,
		setVersion,
	} = useContext(DefinitionBuilderContext) as DefinitionBuilderContextProps;

	const restoreSuccess = (response: Response) => {
		const alertMessage = lang.sub(
			Liferay.Language.get('restored-to-revision-x'),
			[String(versionNumber)]
		);

		setAlertMessage(alertMessage);
		setAlertType('success');

		setShowAlert(true);

		response.json().then(({name, version}) => {
			setDefinitionName(name);
			setVersion(parseInt(version, 10));
		});
	};

	const restoreFailed = () => {
		const alertMessage = Liferay.Language.get(
			'unable-to-restore-this-item'
		);

		setAlertMessage(alertMessage);
		setAlertType('danger');

		setShowAlert(true);
	};

	return (
		<div className="info-group">
			<div className="version-row">
				<label className="text-secondary">
					{Liferay.Language.get('version')} {versionNumber}
				</label>

				<ClayButtonWithIcon
					aria-labelledby={Liferay.Language.get('restore')}
					className="text-secondary"
					displayType="unstyled"
					onClick={() => {
						retrieveDefinitionRequest(definitionName, versionNumber)
							.then((response) => response.json())
							.then(
								({
									active,
									content,
									title,
									title_i18n,
									version,
								}) => {
									if (active) {
										publishDefinitionRequest({
											active,
											content,
											name: definitionName,
											title,
											title_i18n,
											version,
										}).then((response) => {
											if (response.ok) {
												restoreSuccess(response);
											}
											else {
												restoreFailed();
											}
										});
									}
									else {
										saveDefinitionRequest({
											active,
											content,
											name: definitionName,
											title,
											title_i18n,
											version,
										}).then((response) => {
											if (response.ok) {
												restoreSuccess(response);
											}
											else {
												restoreFailed();
											}
										});
									}
								}
							);
					}}
					symbol="restore"
					title={Liferay.Language.get('restore')}
				/>
			</div>

			<div className="sheet-subtitle" />
		</div>
	);
};