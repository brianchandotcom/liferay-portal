/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {PartialResults} from 'data-engine-js-components-web';
import React, {useEffect, useState} from 'react';

import './DefaultPage.scss';
import DefaultPageHeader from './DefaultPageHeader';

const DefaultPage: React.FC<IProps> = ({
	dataEngineModule,
	displayChartAsTable,
	formDescription,
	formReportDataURL,
	formTitle,
	pageDescription,
	pageTitle,
	showPartialResultsToRespondents,
	showSubmitAgainButton,
}) => {
	const [showReport, setShowReport] = useState(false);

	useEffect(() => {
		const portalPopup = document.querySelector('.portal-popup');
		portalPopup?.classList.add('lfr-ddm__default-page-background');

		return () => {
			portalPopup?.classList.remove('lfr-ddm__default-page-background');
		};
	}, []);

	return (
		<>
			<div className="container-fluid container-fluid-max-xl lfr-ddm__default-page">
				<DefaultPageHeader
					description={formDescription}
					onClickBack={
						showReport ? () => setShowReport(false) : undefined
					}
					title={formTitle}
				/>

				{showReport ? (
					<PartialResults
						dataEngineModule={dataEngineModule}
						displayChartAsTable={displayChartAsTable}
						reportDataURL={formReportDataURL as string}
					/>
				) : (
					<div className="lfr-ddm__default-page-container">
						<div className="lfr-ddm__default-page-title">
							{pageTitle}
						</div>

						<p className="lfr-ddm__default-page-description">
							{pageDescription}
						</p>

						<div className="lfr-ddm__default-page-buttons">
							{showSubmitAgainButton && (
								<ClayButton
									displayType="secondary"
									onClick={() => window.location.reload()}
								>
									{Liferay.Language.get('submit-again')}
								</ClayButton>
							)}

							{showPartialResultsToRespondents &&
								formReportDataURL && (
									<ClayButton
										displayType="secondary"
										onClick={() => setShowReport(true)}
									>
										{Liferay.Language.get(
											'preview-existing-submissions'
										)}
									</ClayButton>
								)}
						</div>
					</div>
				)}
			</div>
		</>
	);
};

DefaultPage.displayName = 'DefaultPage';

export default DefaultPage;

interface IProps {
	dataEngineModule: string;
	displayChartAsTable: boolean;
	formDescription?: string;
	formReportDataURL?: string;
	formTitle: string;
	pageDescription: string;
	pageTitle: string;
	showPartialResultsToRespondents?: boolean;
	showSubmitAgainButton?: boolean;
}
