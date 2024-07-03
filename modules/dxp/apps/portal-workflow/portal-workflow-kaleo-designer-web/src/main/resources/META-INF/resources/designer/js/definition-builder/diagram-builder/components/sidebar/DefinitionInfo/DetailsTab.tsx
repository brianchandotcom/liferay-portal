/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';
import React from 'react';

import lang from '../../../../util/lang';

moment.locale(Liferay.ThemeDisplay.getBCP47LanguageId());

interface DetailsTabProps {
	definitionInfo: DefinitionInfo;
}

export function DetailsTab({definitionInfo}: DetailsTabProps) {
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
		Number(totalModifications) > 1
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
}
