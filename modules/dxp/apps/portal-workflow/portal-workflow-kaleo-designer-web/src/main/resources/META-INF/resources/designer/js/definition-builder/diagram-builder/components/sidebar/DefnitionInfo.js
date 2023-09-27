/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayTabs from '@clayui/tabs';
import moment from 'moment/min/moment-with-locales';
import React, {useContext, useState} from 'react';

import {DefinitionBuilderContext} from '../../../DefinitionBuilderContext';
import {
	publishDefinitionRequest,
	retrieveDefinitionRequest,
	saveDefinitionRequest,
} from '../../../util/fetchUtil';
import lang from '../../../util/lang';

moment.locale(Liferay.ThemeDisplay.getBCP47LanguageId());

const Details = ({definitionInfo}) => {
	const titleCreated = Liferay.Language.get('created');
	const titleLastModified = Liferay.Language.get('last-modified');
	const titleTotalModifications = Liferay.Language.get('total-modifications');

	const dateCreated = moment(definitionInfo.dateCreated).format(
		Liferay.Language.get('mmm-dd-yyyy-lt')
	);

	const dateModified = moment(definitionInfo.dateModified).format(
		Liferay.Language.get('mmm-dd-yyyy-lt')
	);

	const totalModifications = definitionInfo.totalModifications;

	const revisionMessage =
		totalModifications > 1
			? lang.sub(Liferay.Language.get('x-revisions'), [
					totalModifications,
			  ])
			: `${totalModifications} ${Liferay.Language.get('revision')}`;

	return (
		<>
			<div className="info-group">
				<label className="text-secondary">
					{titleCreated.toUpperCase()}
				</label>

				<span>{dateCreated}</span>
			</div>

			<div className="info-group">
				<label className="text-secondary">
					{titleLastModified.toUpperCase()}
				</label>

				<span>{dateModified}</span>
			</div>

			<div className="info-group">
				<label className="text-secondary">
					{titleTotalModifications.toUpperCase()}
				</label>

				<span>{revisionMessage}</span>
			</div>
		</>
	);
};

const VersionRow = ({versionNumber}) => {
	const {
		definitionName,
		setAlertMessage,
		setAlertType,
		setDefinitionName,
		setShowAlert,
		setVersion,
	} = useContext(DefinitionBuilderContext);

	const restoreSuccess = (response) => {
		const alertMessage = lang.sub(
			Liferay.Language.get('restored-to-revision-x'),
			[versionNumber]
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

const RevisionHistory = ({version}) => {
	const otherVersions = [];

	for (let i = version - 1; i > 0; i--) {
		otherVersions.push({versionNumber: i});
	}

	return (
		<>
			<div className="info-group">
				<label>
					{Liferay.Language.get('current-version')}: {version}
				</label>

				<div className="sheet-subtitle" />
			</div>

			{otherVersions.map(({versionNumber}, index) => (
				<VersionRow key={index} versionNumber={versionNumber} />
			))}
		</>
	);
};

const TABS = [
	{
		Component: Details,
		label: Liferay.Language.get('details'),
	},
	{
		Component: RevisionHistory,
		label: Liferay.Language.get('revision-history'),
	},
];

export default function DefinitionInfo() {
	const [activeIndex, setActiveIndex] = useState(0);

	const {definitionInfo, version} = useContext(DefinitionBuilderContext);

	return (
		<>
			<ClayTabs>
				{TABS.map(({label}, index) => (
					<ClayTabs.Item
						active={activeIndex === index}
						key={index}
						onClick={() => setActiveIndex(index)}
					>
						{label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content activeIndex={activeIndex} fade>
				{TABS.map(({Component}, index) => (
					<ClayTabs.TabPane key={index}>
						<Component
							definitionInfo={definitionInfo}
							version={version}
						/>
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
}
